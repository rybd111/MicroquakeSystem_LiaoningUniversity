package com.h2.tool;

import java.util.Vector;

import DataExchange.Sensor;
import mutiThread.moveBufferPosition;

public class InitialValue
{
	 /* cutOdata1返回波形数据的y值*/
	/**
	 * 从激发位置切分到容器末尾。
	 * @param data
	 * @param getlineSeriesNew
	 * @return
	 * @author Gang Zhang.
	 * @date revision 2021年2月14日下午7:59:16
	 */
	 public void cutOdata1(Vector<String> data,int getlineSeriesNew) {
		 double [] num=new double[data.size()];
		 double [] num2=new double[num.length-getlineSeriesNew];
		for(int i=0;i<data.size();i++) {
			String  s=null;
			s=data.get(i).toString();
			String [] ss=s.split(" ");
			num[i]=Double.valueOf(ss[5]);
		}
		for(int i=getlineSeriesNew;i<(num.length-getlineSeriesNew);i++)	{
			num2[i-getlineSeriesNew]=num[i];
		}
		this.standbylvbo = num2;
	 } 
	 
	 /* movingAverageFilter返回存储滤波后波形数据的y值*/
	 private double[] standbylvbo = null;
	 private double[] lvbo = null;//用来存储滤波后波形数据的y值
	 private final int mWindowSize = 4;//滑动窗口个数
	 
	 public void movingAverageFilter()
	 {
		double [] winArray=new double[mWindowSize];
		lvbo=new double[standbylvbo.length];
		int OIndex = 0;
		
		System.arraycopy(standbylvbo, 0, lvbo, 0, standbylvbo.length);
		   
		for (int i=0; i<standbylvbo.length; i++)//利用循环得到每个滑动窗口的值
		{
	        int wIndex = 0;
	        if ((i+mWindowSize)>standbylvbo.length) break;//如果数据小于活动窗口个数，跳出循环
		
			for (int j=i; j<(mWindowSize+i); j++) 
			{
			    winArray[wIndex] = standbylvbo[j];
			    wIndex ++;
			}
		
			lvbo[OIndex] = mean(winArray);//将求完平均值的数据存储到mBufout中
		    OIndex ++;
		}
	}
	 
	private double mean(double[] array){
		double sum=0;
		for(int i=0;i<array.length;i++)
		{
			sum+=array[i];
		}
		double avg=sum/array.length;
		return avg;	 
	}
	 
	public	double getInitialextremum(Vector<String> data, int getlineSeriesNew) {
		//切分数据
		cutOdata1(data, getlineSeriesNew);
		//滤波
		movingAverageFilter();
		//求初动极值
		
		double [] abslvbo=new double[lvbo.length];
		double a=0;
		for(int i=0;i<lvbo.length;i++)
		{
			abslvbo[i]=Math.abs(lvbo[i]);
		}
		
		for(int i=1;i<abslvbo.length-1;i++) 
		{
			if(abslvbo[i]>abslvbo[i-1]&&abslvbo[i]>abslvbo[i+1]) 
			{
				a = abslvbo[i];//波峰
				break;
			} 
			else if(abslvbo[i]<abslvbo[i-1]&&abslvbo[i]<abslvbo[i+1])
			{
				a = abslvbo[i];//波谷
				break;
			}
		}
		
		return a;
	}
}
	 
	 
	 

