package com.h2.tool;


import DataExchange.Sensor;

/**
 * this function used to calculate the during grade.
 * @author Hanlin Zhang, Yali Hao.
 */
public class calDuringTimePar {
	
	public static double[] calAandB(double []MD, double[] duringTime){
		double a1=0.0,b1=0.0;
		double[] a=new double[MD.length-1];
		double[] b=new double[MD.length-1];
		
		for(int i=0;i<MD.length-1;i++) {
			b[i]=(MD[0]-MD[i+1])/(duringTime[0]-duringTime[i+1]);
			a[i]=MD[i+1]-b1*duringTime[i+1];
		}
		//sum all values of a and b.
		for(int i=0;i<MD.length-1;i++) {
			a1 = a1+a[i];
			b1 = b1+b[i];
		}
		//calculate the average of the a and b.
		a1/=MD.length-1;
		b1/=MD.length-1;
		
		double [] conse = new double[2];
		conse[0]=a1;
		conse[1]=b1;
//		System.out.println(Parameters.compute_duiringQuake+"a的值："+conse[0]+"b的值"+conse[1]);
		
		return conse;//返回a与b，平均值
	}
	public static float computeDuringQuakeGrade(Sensor[] sensors, int num) {
		float duringEarthQuake = 0;
		double [] MD = new double[num];
		double [] duringTime = new double[num];
		
		for(int i =0;i<num;i++){//分别存储近震震级、持续时间
			MD[i] = sensors[i].getEarthClassFinal();
			duringTime[i] = sensors[i].getDuring();
		}
		double [] par = new double[2];//存储ab两个参数
		par = calAandB(MD, duringTime);//计算参数ab
		for(int i=0;i<num;i++){
			duringEarthQuake += par[0]+par[1]*sensors[i].getDuring();
		}
		duringEarthQuake = duringEarthQuake/num;//求出最终持续时间震级
		
		return duringEarthQuake;
	}
}
