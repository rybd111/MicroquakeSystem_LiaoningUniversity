package com.yhy.getinformation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import utils.ArrayMatch;
import utils.Date2String;
import utils.GetNetDisk;
import utils.Parameters;

/**
 * This class is designed to get the corresponding station information.
 * @author Haiyou Yu, Hanlin Zhang
 * @version 1.0 2020-11-14
 */

public class GetStationInfo {
	//存储所有远程盘符。
	private String remoteDisk[];
	
	private Map<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
	
	/**
	 * 获取包括网络速度、磁盘信息、盘符号、盘符名在内的4项指标。
	 * @param date, a Date
	 * @return all station information
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public ArrayList<TableProperty> getAllStationsInformation() {
		//获取1级或2级目录有数据文件的远程盘符。
		GetNetDisk getNetDisk = new GetNetDisk();
		try {
			this.remoteDisk = getNetDisk.getRemoteDiskName();
		} catch (IOException e1) {e1.printStackTrace();}
		catch (ParseException e1) {e1.printStackTrace();}
		//若没有连通磁盘，则返回空，此时所有台站均为offline。
		if(remoteDisk == null) {
			return null;
		}
		
		//获取传感器盘符名称以及坐标.
		initStationInformation();
		
		ArrayList<TableProperty> all = getStationInfomation();
		
		return all;
	}
	
	/**
	 * save the station's basic information to the map,including panfu,location,xData,yData,zData
	 */
	private void initStationInformation() {
		//获取当前区域。
		try {getRegionFromConfig();} catch (IOException e) {e.printStackTrace();}		
		//获取当前区域的序号。
		Parameters.diskNameNum = ArrayMatch.match_String(Parameters.station, Parameters.region);
		//确定当前数据库名。
		DatabaseUtil.TABLENAME = Parameters.DatabaseName[Parameters.diskNameNum];
		//将坐标保留至小数点后两位。
		DecimalFormat df = new DecimalFormat("0.00");
		try {
			for(String panfu:remoteDisk) {
				if(!map.containsKey(panfu)) {
					//获取当前盘符在预定义盘符数组中所在的位置。
					String newpanfu = panfu.replace(":\\", "").toLowerCase();
					int series = ArrayMatch.match_String(Parameters.diskName[Parameters.diskNameNum], newpanfu);
					ArrayList<String> values = new ArrayList<String>();
					values.add(Parameters.diskName1[Parameters.diskNameNum][series]);
					String x = df.format(Parameters.SENSORINFO1[Parameters.diskNameNum][series][0]);
					String y = df.format(Parameters.SENSORINFO1[Parameters.diskNameNum][series][1]);
					String z = df.format(Parameters.SENSORINFO1[Parameters.diskNameNum][series][2]);
					values.add(x);
					values.add(y);
					values.add(z);
					map.put(panfu, values);
				}
			}
		}
		catch (Exception e) {
			System.out.println("很有可能是矿区写错了，导致初始化时扫描到的盘符与diskName中的名字不符，出现越界异常，请修改配置文件'regionConfig.ini'文件。");
		}
	}
	
	/**
	 * 整合网速与磁盘空间，改成多线程比较好。
	 * @param diskName
	 * @return  a single station's information including netspeed,unused,used,total
	 */
	public ArrayList<TableProperty> getStationInfomation() {
		
		ExecutorService executor = Executors.newFixedThreadPool(remoteDisk.length);
        final CountDownLatch threadSignal = new CountDownLatch(remoteDisk.length);
        getInfoMuti[] get = new getInfoMuti[remoteDisk.length];
        
        for (int i = 0; i < remoteDisk.length; i++) {
        	get[i] = new getInfoMuti(
        			remoteDisk[i],
        			this.map.get(remoteDisk[i]),
        			threadSignal
        			);
            executor.execute(get[i]);
        }
        try {
            threadSignal.await();
            executor.shutdown();
        } catch (InterruptedException e1) {e1.printStackTrace();}
		
        //汇总所有盘符信息与平均网速。
        ArrayList<TableProperty> all = new ArrayList<TableProperty>();
        for (int i = 0; i < remoteDisk.length; i++) {
        	all.addAll(get[i].all);
        }
        
		return all;
	}
	
	/**
	 * 读取配置文件的region。
	 * @throws IOException
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月25日下午2:54:14
	 */
	private void getRegionFromConfig() throws IOException {
		String j = System.getProperty("user.dir");//get the procedure absolute path.
		String configPath = j+"/regionConfig.ini";
		File file = new File(configPath);
		//(文件完整路径),编码格式
        @SuppressWarnings("resource")
		BufferedReader reader =	reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
        String line = null;

        while((line=reader.readLine())!=null){//when the procedure read the last line in csv file, the length of it will become 1.
            if(line.length()>1) { //this line has content.
				if(!line.substring(0, 1).equals("#")) {//this line is an annotation.
					String item[] = line.split("=");
					//去除空格。
					item[0] = item[0].replaceAll(" ", "");
					item[1] = item[1].replaceAll(" ", "");
					 
					if(item[0].equals("region")) {
						Parameters.region = String.valueOf(item[1]);
					}
				}
            }
        }
	}
	
	/**
	 * test code.
	 * @param args
	 * @author Haiyou Yu.
	 * @date revision 2021年2月24日下午10:45:03
	 */
	public static void main(String[] args) {
		
//		Parameters.region = "hongyang";
//		GetStationInfo get = new GetStationInfo();
//		
//		ArrayList<TableProperty> all = get.getAllStationsInformation(new Date());
//		
//		for(TableProperty t:all) {
//			System.out.println(t.toString());
//		}

	}
	
}
