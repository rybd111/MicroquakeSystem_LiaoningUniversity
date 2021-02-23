/**
 * 
 */
package com.h2.tool;

import java.text.ParseException;
import java.util.Vector;

import DataExchange.Sensor;

/**
 * @author Hanlin Zhang
 */
public interface SensorSectimeSortProcess {
	
	//计算P波到时的相对时间。
	public Sensor[] P_RelativeArrivalTime();
	//根据P波到时升序排序。
	public Sensor[] sortAccordingToPArrival();
	//返回带有激发数据和盘符信息等的Sensor对象数组，通过P波到时排序并截取激发位置前后18s的数据，同时保存传感器基本信息
	public Sensor[] stowInfoSensor();
	/**
	 * 根据到时秒，返回距离data数据的
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月23日上午11:11:58
	 */
	public String PArrivalAbsoluteTime(Vector<String> data, double secTime);
	
}
