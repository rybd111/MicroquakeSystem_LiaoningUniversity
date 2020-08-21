/**
 * 
 */
package com.h2.backupData;

import mutiThread.MainThread;

/**
 * @author Hanlin Zhang
 */
public class pullFileFromSensor {

	private String startTime=" ";
	private String endTime=" ";
	
	/**
	 * timeString must be yyyymmddhhmmss
	 * @param timeString
	 */
	public pullFileFromSensor(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
		
		//compare different files in each sensor's drive.
		for(int i=0;i<MainThread.fileStr.length;i++) {
			
		}
	}
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
