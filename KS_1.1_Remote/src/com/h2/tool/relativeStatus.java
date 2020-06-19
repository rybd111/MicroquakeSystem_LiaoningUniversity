package com.h2.tool;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.h2.constant.Parameters;
import com.h2.constant.Sensor;
import com.h2.main.EarthQuake;

import mutiThread.MainThread;
import utils.Date2String;
import utils.String2Date;
import utils.TimeDifferent;


public class relativeStatus {
	
	public static int[] numberMotiSeries;
	/**
	 * this function is used to get the relative P arrival time point as the sensor which the PArrival is not 0 for the sensor_latest. 
	 * @param sensors all sensor the procedure initializing at starting.
	 * @author Hanlin Zhang
	 */
	@SuppressWarnings("unused")
	public static Sensor[] P_RelativeArrivalTime(Sensor[] sensors, int[] l, int num) throws ParseException {
		int nqk=-1;
		numberMotiSeries = new int[num];
		Sensor[] S = sortAccordingToPArrival(sensors,l,num);
		
		for(int i=1;i<num;i++){
			S[i].setSecTime(S[i].getSecTime()-S[0].getSecTime());//主要减去第一个不为零的时间
		}
//		sensors[nqk].setAbsoluteTime(TimeDifferent.TimeDistance(sensors[nqk].getTime(), sensors[nqk].getSecTime()));//set the absolute time to save to the csv file.
		S[0].setSecTime(0.0);//we set the first sensor's P arrival time to 0.

//		for(int i=0;i<num;i++){
//			System.out.println("after minus"+S[i].getSecTime());
//		}
		
//		System.out.println("P波到时："+sensors[i].getSecTime());
		if(Parameters.Adjust==true){
			EarthQuake.realMoti=true;//其中若有一个台站的相对其他台站相差大于1s则认为该事件不是同时激发的，且他们无效	
		}
		else{
			if(Parameters.SSIntervalToOtherSensors==true){
				if(Math.abs(S[num-1].getSecTime())>Parameters.IntervalToOtherSensors)
					EarthQuake.realMoti=false;//其中若有一个台站的相对其他台站相差大于1s则认为该事件不是同时激发的，且他们无效	
			}
			else
				EarthQuake.realMoti=true;
		}

		return S;	
	}
	
	/**
	 * sort the sensors according to P arrival time.
	 * @param sensors
	 * @param l
	 * @param num
	 * @return
	 */
	public static Sensor[] sortAccordingToPArrival(Sensor[] sensors, int[] l, int num) {
		
		Sensor[] S = new Sensor[num];
		int count = 0;
		
		for(int i=0;i<l.length;i++) {
			S[count] = sensors[l[i]];//the motivation sensor.
			count++;
		}
		
		count=0;
		double min=1.0/0;//a INF number.
		Sensor[] newS = new Sensor[num];
		double[] PArrival = new double[num];
		int nqk = 0;
		
		for(int i=0;i<num;i++) {
			PArrival[i] = S[i].getSecTime();
		}
		
		//sort according to SecTime.
		for(int j=0;j<num;j++) {
			for(int i=0;i<num;i++) {
				if(PArrival[i]<min) {
					min = PArrival[i];
					nqk=i;
				}
			}
			if(min<1.0/0) {
				PArrival[nqk]=1.0/0;
				newS[j] = S[nqk];
				numberMotiSeries[count]=S[nqk].getSensorNum();
				count++;
			}
			min = 1.0/0;
		}
		
//		for(int i=0;i<num;i++) {
//			System.out.println("老到时 "+S[i].getSecTime()+" Series:"+S[i].getSensorNum());
//			System.out.println("到时排序是否正确 "+newS[i].getSecTime()+" newSeries:"+newS[i].getSensorNum());
//			
//		}

		return newS;
	}
	
	
	/**
	 * the function is used to calculate the absolute time of one sensor, when we get the sensor a double type P arrival time and a  
	 * @param data in now vector.
	 * @param sensor the motivated sensor status.
	 * @param i which sensor the procedure calculate in this time.
	 * @return the absolute time of P arrival time.
	 * @throws ParseException
	 * @author Hanlin Zhang.
	 */
	public static String PArrivalTime(Vector<String> data, Sensor sensor,int i) throws ParseException {
		String P = "";
		P=String.valueOf(sensor.getSecTime());
//		System.out.print(MainThread.fileStr[i]+data.get(0).split(" ")[6]+" ");
//		System.out.print(sensor.getSecTime()+" ");
		
		Date d = new Date();
		if(sensor.getSecTime()<1) {
			d=String2Date.str2Date(data.get(0).split(" ")[6]);
			String date=Date2String.date2str(d)+"."+P.split("\\.")[1];
//			System.out.println(date);
			return date;
		}
		else {
			d=String2Date.str2Date(data.get(0).split(" ")[6]);
			Calendar calendar = Calendar.getInstance(); //内存溢出的出错位置。~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
			calendar.setTime(d);
			calendar.add(Calendar.SECOND, (int) Math.floor(sensor.getSecTime()));
			d=calendar.getTime();
			String date = Date2String.date2str(d)+"."+P.split("\\.")[1];
//			System.out.println(date);
			return date;
		}
		
	}
}
