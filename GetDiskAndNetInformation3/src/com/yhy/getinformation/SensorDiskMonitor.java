package com.yhy.getinformation;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;  
import java.util.concurrent.ScheduledExecutorService;  
import java.util.concurrent.TimeUnit;

import com.mysql.cj.exceptions.StatementIsClosedException;

import utils.Parameters;
import utils.currentDate;  
/**
 * The aim of this class is executing the task regularly.
 * @author Haiyou Yu, Hanlin Zhang.
 * @version 1.0 2020-11-14
 */
public class SensorDiskMonitor {
	//睡眠时间。
	public static int SleepTime = 1000*60*60;//one hour.
	
	public static void runMain() throws IOException, ParseException {
		System.out.println("---------------------------------------");
		System.out.print("当前扫描间隔时间："+ SleepTime/1000 +"秒     ");
		System.out.println("当前扫描的时间：" + currentDate.currentTime());
		
		ArrayList<TableProperty> al = new GetStationInfo().getAllStationsInformation();
		
		if(al == null) {
			//虽然提前返回了，但仍需要标识当前循环结束了。
			System.out.println("---------------------------------------");
			return;
		}
		for(TableProperty tp:al) {
			String[] str = tp.getStringArray();
			System.out.println(tp.toString());
			//插入数据库。
			DatabaseUtil.insert(str,true);
		}
		try {
			Thread.sleep(10);
			DatabaseUtil.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("---------------------------------------"); 
	}
	
	/**
	 * 通过设置SleepTime进行更新频率间隔时间的设置。
	 * @author Hanlin Zhang.
	 * @throws ParseException 
	 * @throws IOException 
	 * @date revision 2021年2月24日下午11:14:32
	 */
	public static void doTask() throws IOException, ParseException {
		while(true) {
			runMain();
			try {Thread.sleep(SleepTime);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public static void main(String[] args) throws IOException, ParseException {  
		 doTask();
	}
}

