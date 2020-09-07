package com.h2.tool;

import java.util.Vector;

import com.h2.constant.Sensor;

public class Filter
{
	 /* cutOdata1返回波形数据的y值*/
	 public static double[] cutOdata1(Vector<String> data,int getlineSeriesNew) {
		 String [] num=new String [data.size()];
		 
		 double [] num1=new double[num.length];
		 double [] num2=new double[num1.length-getlineSeriesNew];
		 Vector a=new Vector();
		for(int i=0;i<data.size();i++)
		{
			String  s=null;
			s=data.get(i).toString();
			String [] ss=s.split(" ");
			num[i]=ss[5];
		}
	
		for(int i=0;i<num.length;i++)
		{
			num1[i]=Double.parseDouble(num[i]);
		}

		for(int i=getlineSeriesNew;i<(num1.length-getlineSeriesNew);i++)
		{
			num2[i-getlineSeriesNew]=num1[i];
		}
		return num2;
		 
	 } 
	 
	 
	 
	 /* movingAverageFilter返回存储滤波后波形数据的y值*/
	 private  double[] lvbo = null;//用来存储滤波后波形数据的y值
	 private final int mWindowSize = 4;//滑动窗口个数
	 public double[] movingAverageFilter(double[] num2)
	 {
//		 double [] motiPreLa1=new double[motiPreLa.size()];
//		 for(int i=0;i<motiPreLa.size();i++)
//		 	{
//			 	motiPreLa1[i]=Double.parseDouble(motiPreLa.get(i).toString());//类型转换
//		 	}
		 double [] winArray=new double[mWindowSize];
		 lvbo=new double[num2.length];
		 int OIndex = 0;
		   System.arraycopy(num2, 0, lvbo, 0,
				   num2.length);
		   
		   for (int i=0; i<num2.length; i++)//利用循环得到每个滑动窗口的值
		   {
	            int wIndex = 0;
	            if ((i+mWindowSize)>num2.length) break;//如果数据小于活动窗口个数，跳出循环
	            for (int j=i; j<(mWindowSize+i); j++) 
	            {
		            winArray[wIndex] = num2[j];
		            wIndex ++;
	            }

	        lvbo[OIndex] = mean(winArray);//将求完平均值的数据存储到mBufout中
	        OIndex ++;

		   }

	        return lvbo;

	  }
	 
	 private static	double mean(double[] array){
		 double sum=0;
		 for(int i=0;i<array.length;i++)
		 {
			 sum+=array[i];
		 }
		 double avg=sum/array.length;
		 return avg;	 
	 }
	 
	 public	double Initialextremum(double [] lvbo) {
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
       			 a= abslvbo[i];//波峰
       			break;
       		
       		  } 
       		  else if(abslvbo[i]<abslvbo[i-1]&&abslvbo[i]<abslvbo[i+1])
       		  {
       			a=abslvbo[i];//波谷
       			break;
       		  }
       	  
         }
         return a;
	
		 
	 }

	
	 
}
	 
	 
	 

