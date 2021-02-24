package com.yhy.getinformation;


import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;  
import java.util.concurrent.ScheduledExecutorService;  
import java.util.concurrent.TimeUnit;

import com.mysql.cj.exceptions.StatementIsClosedException;

import utils.Parameters;  


/**
 * The aim of this class is executing the task regularly.
 * @author Haiyou Yu
 * @version 1.0 2020-11-14
 */
public class SensorDiskMonitor {

	//---------------------------------------------------------------
	//This is the test code
	public static void main(String[] args) {  
		 doTask();
	}
	//end test code
	//----------------------------------------------------------------
	
	public static void runMain() {
		System.out.println("---------------------------------------");

		ArrayList<TableProperty> al = new GetStationInfo().getAllStationsInformation(new Date());
		
		if(al == null) return;
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
	
	public static int SleepTime = 1000*60*60;//one hour.
	
	/**
	 * 通过设置SleepTime进行更新频率间隔时间的设置。
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月24日下午11:14:32
	 */
	public static void doTask() {
//		SensorDiskMonitor task = new SensorDiskMonitor();
//		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//		// parameters: the specified task, the first running delay, running period, time unit for period
//		service.scheduleAtFixedRate(task, 0, 12, TimeUnit.MINUTES);
		while(true) {
			runMain();
			try {Thread.sleep(SleepTime);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}

