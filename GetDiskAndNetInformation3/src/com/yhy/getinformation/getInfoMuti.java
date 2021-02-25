/**
 * 
 */
package com.yhy.getinformation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import utils.Date2String;

/**
 * @author Hanlin Zhang
 */
public class getInfoMuti implements Runnable{
	
	private String key = "";
	private List<String> lists;
	public ArrayList<TableProperty> all = new ArrayList<TableProperty>();
	private CountDownLatch latch;
	
	//获取传感器信息。
	private ArrayList<String> information = new ArrayList<String>();
	
	public getInfoMuti(
			String key,
			List<String> lists,
			CountDownLatch latch
			) {
		this.latch = latch;
		this.key = key;
		this.lists = lists;
	}
	
	public void run() {
		mission1();
		this.latch.countDown();
	}
	
	private void mission1() {
		//时间转换成天为单位。
		Date date = new Date();
		String day = "";
		try {day = Date2String.date2str3(date);} catch (ParseException e1) {e1.printStackTrace();}
		//取出map中的关键字，盘符号开头、盘符名、坐标。
			TableProperty tp = new TableProperty();
			tp.setDay(day);
			tp.setPanfu(key.replace(":\\", ""));
			tp.setLocation(lists.get(0));
			tp.setxData(lists.get(1));
			tp.setyData(lists.get(2));
			tp.setzData(lists.get(3));
			//任务2获取传感器的网速、剩余空间。
			this.information = mission2(key);
			//Why use information.get(0)==null? Because information is a ArrayList and it contains a NULL value.
			if(information.get(0)==null) {//if netspeed is null,then set its status to offline
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
			//只保存了一个台站的所有信息，最后需要汇总。
			this.all.add(tp);
	}
	
	/**
	 * 获取网速与剩余空间。
	 * @param diskname
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月25日下午1:29:13
	 */
	private ArrayList<String> mission2(String diskname) {
		ArrayList<String> information = new ArrayList<String>();
		information.add(getNetWorkSpeed(diskname));
		information.addAll(getDiskInformation(diskname));
		return information;
	}
	
	/**
	 * 获取网速。
	 * @param diskNumber
	 * @return a single station's information including netspeed
	 */
	private String getNetWorkSpeed(String diskname) {
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
		String filePath = diskname + File.separator + "testnetspeed.txt";
		File dest = new File(filePath);
		//测量拷贝文件的时间。
		long start = System.currentTimeMillis();
		try {copyFileUsingFileChannels(source, dest);} catch (IOException e) {e.printStackTrace();}
		long end = System.currentTimeMillis();
		//拷贝文件的时间。
		long timeDifference = end -start;
		//change milliseconds to seconds
		BigDecimal a = new BigDecimal(timeDifference);
		BigDecimal b = new BigDecimal(1000);
		BigDecimal seconds = a.divide(b, 2, BigDecimal.ROUND_HALF_UP);
	    //change file's byte size to KB
	    BigDecimal fileSize = new BigDecimal(sourceFileSize/1024);
	    //change BigDecimal to float, as kbps kb/s and save 2 bit after decimal point.
	    float netSpeed =  fileSize.divide(seconds,2, BigDecimal.ROUND_HALF_UP).floatValue();
	    // if network speed greater than 1000kbps, its unit becomes Mbps.
	    String networkSpeed = null;
	    if(netSpeed > 1000) {
		    BigDecimal temp = new BigDecimal(netSpeed);
		    netSpeed = temp.divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP).floatValue();
		    networkSpeed = String.valueOf(netSpeed)+"Mbps";
	    }else{
		    networkSpeed = String.valueOf(netSpeed)+"kbps";
	    }
	    
		return networkSpeed;
	}
	
	/**
	 * 拷贝文件。
	 * @param source
	 * @param dest
	 * @throws IOException
	 * @author Haiyou Yu, Hanlin Zhang.
	 * @date revision 2021年2月24日下午10:44:02
	 */
	@SuppressWarnings("resource")
	private void copyFileUsingFileChannels(File source, File dest) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}
	
	/**
	 * 获取磁盘空间
	 * @param diskNumber
	 * @return a single station's information including disk's unused,used,total information
	 */
	private ArrayList<String> getDiskInformation(String diskname) {
		ArrayList<String> list = new ArrayList<String>();
		String unused = "";
		String used = "";
		String total = "";
		File file = new File(diskname);
		
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
}
