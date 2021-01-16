package com.h2.strength;

import java.text.ParseException;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import com.h2.constant.Sensor;
import com.h2.tool.QuakeClass;
import com.h2.tool.calDuringTimePar;
import com.mathworks.toolbox.javabuilder.MWException;

/**
 * 用于计算近震震级、能量的线程
 * 
 * @author 韩百硕, Hanlin Zhang.
 *
 */
public class calStrength extends Thread 
{
	private Sensor sensor;
	private Sensor location;
	private int i;
	private CountDownLatch downLatch;
	public calStrength(Sensor s, Sensor lo, int i, CountDownLatch downLatch)
	{
		this.sensor = s;
		this.i=i;//sensor ID.
		this.location = lo;
		this.downLatch = downLatch;
	}
	
	public void run()
	{
		//calculate the near quake magnitude and energy.
		try {
			QuakeClass.SensorMaxFudu(this.sensor, this.i);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (MWException e) {
			e.printStackTrace();
		}
		QuakeClass.getOneEarthQuakeGrade(location, sensor);
		this.downLatch.countDown();
	}
	public void cal()
	{
		//calculate the near quake magnitude.
		try {
			QuakeClass.SensorMaxFudu(this.sensor, this.i);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (MWException e) {
			e.printStackTrace();
		}
		QuakeClass.getOneEarthQuakeGrade(location, sensor);
	}
}
