package com.h2.tool;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.h2.constant.Parameters;
import com.h2.main.EarthQuake;
import com.h2.main.statusOfCompute;

import DataExchange.Sensor;
import controller.ADMINISTRATOR;
import mutiThread.MainThread;
import utils.Date2String;
import utils.String2Date;
import utils.TimeDifferent;
import utils.stringJoin;


public class relativeStatus {
	
//	public static int[] numberMotiSeries;
	/**
	 * this function is used to get the relative P arrival time point as the sensor which the PArrival is not 0 for the sensor_latest. 
	 * @param sensors all sensor the procedure initializing at starting.
	 * @author Hanlin Zhang
	 */
	@SuppressWarnings("unused")
	public static Sensor[] P_RelativeArrivalTime(
			Sensor[] sensors, 
			int[] l, 
			int num,
			ADMINISTRATOR manager) throws ParseException {
		int nqk=-1;
//		numberMotiSeries = new int[num];
		manager.initNumberMotiSeries(num);
		Sensor[] S = sortAccordingToPArrival(sensors,l,num,manager);
		
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
			manager.setIsRealMoti(true);
//			EarthQuake.realMoti=true;//其中若有一个台站的相对其他台站相差大于1s则认为该事件不是同时激发的，且他们无效	
		}
		else{
			if(Parameters.SSIntervalToOtherSensors==true){
				if(Math.abs(S[num-1].getSecTime())>Parameters.IntervalToOtherSensors)
					manager.setIsRealMoti(false);
//					EarthQuake.realMoti=false;//其中若有一个台站的相对其他台站相差大于1s则认为该事件不是同时激发的，且他们无效	
			}
			else
				manager.setIsRealMoti(true);
//				EarthQuake.realMoti=true;
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
	public static Sensor[] sortAccordingToPArrival(
			Sensor[] sensors, 
			int[] l, 
			int num,
			ADMINISTRATOR manager) {
		
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
				manager.setNNumberMotiSeries(count, S[nqk].getSensorNum());
//				numberMotiSeries[count]=S[nqk].getSensorNum();
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
	 * 
	 * @param l
	 * @param l1
	 * @param countNumber
	 * @param sensors
	 * @param ssen
	 * @return
	 * @throws ParseException
	 * @author Hanlin Zhang.
	 */
	public static Sensor[] stowInfoSensor(
			int[] l, 
			int[] l1, 
			int countNumber, 
			Sensor[] sensors, 
			Vector<String> ssen[][], 
			statusOfCompute status,
			ADMINISTRATOR manager) throws ParseException {
		
		Sensor[] sensors1 = new Sensor[countNumber];//save the sensors after sorting from short to long.
		Sensor[] sensors2 = new Sensor[Parameters.SensorNum-countNumber];
		
		Sensor[] S = new Sensor[sensors1.length+sensors2.length];
		
		//used to stow the data of each sensor's 10s or 18s length.
		@SuppressWarnings("unchecked")
		Vector<String>[] motiPreLa = new Vector[Parameters.SensorNum];
		@SuppressWarnings("unchecked")
		Vector<String>[] inteData = new Vector[Parameters.SensorNum];
		
		String panfu="";
		int[] newl = new int[countNumber];//merge l to newl.
		int count=0;// a counter.
		
		//merge l to avoid the series array l appearing two series number repetition.
		for(int i=0;i<Parameters.SensorNum;i++) {
			if(l1[i]==0&&i==0) {
				newl[count] = l1[i];
				count++;
			}
			else if(l1[i]!=0) {
				newl[count] = l1[i];
				count++;
			}
		}
		
		//Get the relative time point as second, this function is update the sensors object's setSecTime method's Sectime variable.
		//Meanwhile, this function's return sensors is sorted.
		sensors1 = relativeStatus.P_RelativeArrivalTime(sensors,newl,countNumber,manager);//sort the sensors and calculate the relative P arrival time according Sectime variable.
		
		//storage the current motivation sensors.
		if(Parameters.offline==false) {
			panfu = stringJoin.SJoin_Array(MainThread.fileStr, sensors1);
			panfu=panfu.replaceAll(":/", "");//替换掉盘符中的:/
		}
		else {
			panfu = stringJoin.SJoin_Array(MainThread.fileParentPackage, sensors1);
			panfu = panfu.replaceAll("Test", "");
		}
		
		//initialization.
		for(int i=0;i<Parameters.SensorNum;i++) {
			motiPreLa[i] = new Vector<String>();
			inteData[i] = new Vector<String>();
		}
		
		//integrate 30s data to one Vector.
		for(int i=0;i<countNumber;i++) {

			inteData[i].addAll(ssen[manager.getNNumberMotiSeries(i)][0]);
			inteData[i].addAll(ssen[manager.getNNumberMotiSeries(i)][1]);
			inteData[i].addAll(ssen[manager.getNNumberMotiSeries(i)][2]);
//			inteData[i].addAll(ssen[relativeStatus.numberMotiSeries[i]][0]);
//			inteData[i].addAll(ssen[relativeStatus.numberMotiSeries[i]][1]);
//			inteData[i].addAll(ssen[relativeStatus.numberMotiSeries[i]][2]);
		}
		for(int i=0;i<countNumber;i++) {
			motiPreLa[i] = QuakeClass.cutOdata(inteData[i], sensors1, Parameters.startTime, Parameters.endTime, sensors1[i]);
			sensors1[i].setCutVectorData(motiPreLa[i]);
		}
		
		//no motivation sensor also need to set the wave data according to the first motivated sensor's time.
		int n=countNumber;
		for(int i=0;i<Parameters.SensorNum;i++) {
			if(n<Parameters.SensorNum) {
				if(l[i]==0) {
					inteData[n].addAll(ssen[i][0]);
					inteData[n].addAll(ssen[i][1]);
					inteData[n].addAll(ssen[i][2]);
					n++;
				}
			}
		}
		
		int n1 = 0;
		n=countNumber;
		for(int i=0;i<Parameters.SensorNum;i++) {
			if(sensors[i].isSign()==false) {
				//the series of correspond with the name of path, which stow the unmotivated sensor.
				sensors[i].setSensorNum(i);
				
				//set attributes same as the sensors0.
				sensors[i].setlineSeries(sensors1[0].getlineSeries());
				sensors[i].setSecTime(sensors1[0].getSecTime());
				sensors[i].setAbsoluteTime(sensors1[0].getAbsoluteTime());
				sensors[i].setTime(sensors1[0].getTime());
				
				//cut the position the same as sensors1[0] with no motivation sensors.
				motiPreLa[n] = QuakeClass.cutOdata(inteData[n], sensors1, Parameters.startTime, Parameters.endTime, sensors[i]);
				sensors[i].setCutVectorData(motiPreLa[n]);
				sensors[i].setlineSeriesNew(0);
				sensors2[n1] = sensors[i];
				n++;n1++;
			}
		}
		
		//get the complete string of all sensor used to the name of the motivated file split with " ".
		if(Parameters.offline==false) {
			panfu = panfu+ " " + stringJoin.SJoin_Array(MainThread.fileStr, sensors2);
			panfu=panfu.replaceAll(":/", "");//替换掉盘符中的:/
		}
		else {
			panfu = panfu+ " " + stringJoin.SJoin_Array(MainThread.fileParentPackage, sensors2);
			panfu = panfu.replaceAll("Test", "");
		}
		
		//set the name of path all.
		for(int i=0;i<sensors2.length;i++) {
			sensors2[i].setpanfu(panfu);//存储盘符字符串
		}
		for(int i=0;i<sensors1.length;i++) {
			sensors1[i].setpanfu(panfu);//存储盘符字符串
		}
		
		//save to the status.
		status.setPanfu(panfu);
		status.setSensors1(sensors1);
		
		//merge sensor1 sensor2 to S.
		n=0;
		for(int i=0;i<S.length;i++) {
			if(i<sensors1.length) {
				S[i] = sensors1[i];
			}
			else {
				S[i] = sensors2[n];
				n++;
			}
		}
		
		return S;
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
