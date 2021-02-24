package com.yhy.getinformation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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

import utils.ArrayMatch;
import utils.GetNetDisk;
import utils.Parameters;

/**
 * This class is designed to get the corresponding station information.
 * @author Haiyou Yu
 * @version 1.0 2020-11-14
 */

public class GetStationInfo {
	//存储所有远程盘符。
	private String remoteDisk[];
	//存储
	private float[] averageNetSpeed = new float[0];
	public GetStationInfo(){
		
	}
	
	private static Map<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
	
	/**
	 * 获取包括网络速度、磁盘信息、盘符号、盘符名在内的4项指标。
	 * @param date, a Date
	 * @return all station information
	 */
	public ArrayList<TableProperty> getAllStationsInformation(Date date) {
		//获取1级或2级目录有数据文件的远程盘符。
		GetNetDisk getNetDisk = new GetNetDisk();
		try {
			this.remoteDisk = getNetDisk.getRemoteDiskName();
		} catch (IOException e1) {e1.printStackTrace();}
		catch (ParseException e1) {e1.printStackTrace();}
		
		ArrayList<TableProperty> all = new ArrayList<TableProperty>();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String day = sf.format(date);
		
		//若没有连通磁盘，则返回空，此时所有台站均为offline。
		if(remoteDisk == null) {
			TableProperty tp = new TableProperty();
			tp.setDay(day);
		}
		
		//获取传感器盘符名称以及坐标。
		initStationInformation();
		
		
		//System.out.println("day:"+day);
		Set<String> setKey = map.keySet();
		for(String key:setKey) {
			TableProperty tp = new TableProperty();
			tp.setDay(day);
			tp.setPanfu(key.replace(":\\", ""));
			List<String> lists = map.get(key);
			//System.out.println(key+lists.toString());
			tp.setLocation(lists.get(0));
			tp.setxData(lists.get(1));
			tp.setyData(lists.get(2));
			tp.setzData(lists.get(3));
			
			ArrayList<String> information = null;
			try {
				information = getNetSpeedAndDiskInformation(key);
				//System.out.println("information:"+information.toString()+" size:"+information.size());
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//Why use information.get(0)==null? Because information is a ArrayList and it contains a NULL value.
				if(information.get(0)==null) {   //if information is null,then set its status to offline
					tp.setStatus("offline");
					tp.setNetspeed(null);
					tp.setUnused(null);
					tp.setUsed(null);
					tp.setTotal(null);
				}    
				else if(information.size()>1&&information.size()<=4){ //if information is not null,it should contains netspeed、unused、used、total
					tp.setStatus("online");
					tp.setNetspeed(information.get(0));
					tp.setUnused(information.get(1));
					tp.setUsed(information.get(2));
					tp.setTotal(information.get(3));
				}
				//System.out.println(tp.toString());
				all.add(tp);
			}
		}
		return all;
	}
	
	/**
	 * save the station's basic information to the map,including panfu,location,xData,yData,zData
	 */
	private void initStationInformation() {
		//获取当前区域的序号。
		Parameters.diskNameNum = ArrayMatch.match_String(Parameters.station, Parameters.region);
		//确定当前数据库名。
		DatabaseUtil.TABLENAME = Parameters.DatabaseName[Parameters.diskNameNum];
		
		DecimalFormat df = new DecimalFormat("0.00000");
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
	
	/**
	 * 整合网速与磁盘空间。
	 * @param diskName
	 * @return  a single station's information including netspeed,unused,used,total
	 */
	public ArrayList<String> getNetSpeedAndDiskInformation(String diskName) {
		ArrayList<String> information = new ArrayList<String>();
		information.add(getNetWorkSpeed(diskName));
		information.addAll(getDiskInformation(diskName));
		return information;
	}
	
	/**
	 * 获取网速。
	 * @param diskNumber
	 * @return a single station's information including netspeed
	 */
	public String getNetWorkSpeed(String diskNumber) {
		// Source file
		//Note: I put the test file (testnetspeed.txt) in the C disk root list,
		//you should change you path when the environment is different.
		String j = System.getProperty("user.dir");//get the procedure absolute path.
		String nettestPath = j+"/testnetspeed.txt";
		//创建文件。
		File source = new File(nettestPath);
		//获取文件长度。
		long sourceFileSize = source.length();
		//destination path
		String filePath = diskNumber + File.separator + "testnetspeed.txt";
		File dest = new File(filePath);
		
		if(!dest.exists()) {
			try {
				dest.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String networkSpeed = "";
		long start = System.currentTimeMillis();
		long end;
		float netSpeed = 0;
		boolean result = false;
		try {
			result = copyFileUsingFileChannels(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(result) {
			end = System.currentTimeMillis();
			//拷贝文件的时间。
			long timeDifference = end -start;
			
			//change milliseconds to seconds
			BigDecimal a = new BigDecimal(timeDifference);
			BigDecimal b = new BigDecimal(1000);
			BigDecimal seconds = a.divide(b, 2, BigDecimal.ROUND_HALF_UP);
		    
		    //change file's byte size to KB
		    BigDecimal fileSize = new BigDecimal(sourceFileSize/1024);
		    //change BigDecimal to float, as kbps kb/s and save 2 bit after decimal point.
		    netSpeed =  fileSize.divide(seconds,2, BigDecimal.ROUND_HALF_UP).floatValue();
		    //计算平均网速，以提高精度。
		    this.averageNetSpeed = Arrays.copyOf(this.averageNetSpeed, this.averageNetSpeed.length+1);
		    this.averageNetSpeed[this.averageNetSpeed.length-1] = netSpeed;
		    float averageSpeed = mean(averageNetSpeed);
		    
		    // if network speed greater than 1000kbps, its unit becomes Mbps.
		    if(averageSpeed > 1000) {
			    BigDecimal temp = new BigDecimal(averageSpeed);
			    averageSpeed = temp.divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP).floatValue();
			    networkSpeed = String.valueOf(averageSpeed)+"Mbps";
			    //System.out.println("The network speed is : " + netSpeed+"Mbps");
		    }else{
			    networkSpeed = String.valueOf(averageSpeed)+"kbps";
		    	//System.out.println("The network speed is : " + netSpeed+"kbps");
		    }
		    
		}else {
			System.out.println("something wrong.");
		}
		return networkSpeed;
	}
	
	@SuppressWarnings("resource")
	private static boolean copyFileUsingFileChannels(File source, File dest) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		boolean flag =false;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
			flag = true;
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
		return flag;
	}
	
	/**
	 * 获取磁盘空间
	 * @param diskNumber
	 * @return a single station's information including disk's unused,used,total information
	 */
	public static ArrayList<String> getDiskInformation(String diskName) {
		ArrayList<String> list = new ArrayList<String>();
		String unused = "";
		String used = "";
		String total = "";
		File file = new File(diskName);
		
		try {
			unused = String.valueOf(file.getFreeSpace() / 1024 / 1024 / 1024)+"G";
			used = String.valueOf((file.getTotalSpace() - file.getFreeSpace()) / 1024 / 1024 / 1024)+"G";
			total = String.valueOf(file.getTotalSpace() / 1024 / 1024 / 1024)+"G";
			list.add(unused);
			list.add(used);
			list.add(total);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return list;
	}

	/**
	 * 求平均值。
	 * @param array
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月24日下午7:04:57
	 */
	private float mean(float[] array){
		float sum=0;
		for(int i=0;i<array.length;i++)
		{
			sum+=array[i];
		}
		float avg=sum/array.length;
		return avg;
	}
	
	//----------------------------------------------------------------
	//This is the test code.
	public static void main(String[] args) {
		
		Parameters.region = "hongyang";
		GetStationInfo get = new GetStationInfo();
		
		ArrayList<TableProperty> all = get.getAllStationsInformation(new Date());
		
		for(TableProperty t:all) {
			System.out.println(t.toString());
		}

	}
	//end test code.
	//---------------------------------------------------------------
	
}
