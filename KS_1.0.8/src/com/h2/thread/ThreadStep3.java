package com.h2.thread;

import java.text.ParseException;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import com.h2.constant.Sensor;
import com.h2.tool.QuakeClass;
import com.h2.tool.calDuringTimePar;
import com.mathworks.toolbox.javabuilder.MWException;

/**
 * 用于计算震级的多线程
 * 
 * @author 韩百硕
 *
 */
public class ThreadStep3 extends Thread 
{
	private Sensor sensor;
	private Vector<String> dataCalculate = new Vector<String>();
	private Sensor location;
	private int i;
	private int[] l;
	private CountDownLatch downLatch;
	public ThreadStep3(Sensor s, Sensor lo, int i, CountDownLatch downLatch)
	{
		this.sensor = s;
		this.i=i;//sensor ID.
		this.location = lo;
		this.downLatch = downLatch;
	}

	public void run()
	{
		//calculate the near quake magnitude.
		try {
			QuakeClass.SensorMaxFudu(this.sensor, this.dataCalculate, this.i);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QuakeClass.getOneEarthQuakeGrade(location, sensor);
		this.downLatch.countDown();
	}
	public void cal()
	{
		//calculate the near quake magnitude.
		try {
			QuakeClass.SensorMaxFudu(this.sensor, this.dataCalculate, this.i);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QuakeClass.getOneEarthQuakeGrade(location, sensor);
//		this.downLatch.countDown();
	}
}
