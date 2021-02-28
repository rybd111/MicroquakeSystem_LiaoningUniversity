package com.h2.strength;

import java.text.ParseException;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import com.h2.tool.QuakeClass;
import com.h2.tool.calDuringTimePar;
import com.mathworks.toolbox.javabuilder.MWException;

import DataExchange.Sensor;

/**
 * 用于计算近震震级、能量的线程
 * @author 韩百硕, Hanlin Zhang.
 */
public class calStrength extends Thread 
{
	private Sensor MotiSensorsAfterCut;
	private Sensor location_refine;
	private int i;
	private CountDownLatch downLatch;
	
	public calStrength(Sensor MotiSensorsAfterCut, Sensor location_refine, int i, CountDownLatch downLatch)
	{
		this.MotiSensorsAfterCut = MotiSensorsAfterCut;
		this.i=i;//sensor ID.
		this.location_refine = location_refine;
		this.downLatch = downLatch;
	}
	
	public void run()
	{
		QuakeClass q = new QuakeClass();
		//calculate the near quake magnitude and energy.
		try {
			q.SensorMaxFudu(this.MotiSensorsAfterCut, this.i);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (MWException e) {
			e.printStackTrace();
		}
		//这里加上了震中距，但宋义敏老师说矿震范围小，不宜加震中距。
		q.getOneEarthQuakeGrade(this.location_refine, this.MotiSensorsAfterCut);
		this.downLatch.countDown();
	}
}
