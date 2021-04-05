package com.h2.tool;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.h2.constant.Parameters;
import com.h2.main.statusOfCompute;

import DataExchange.Sensor;
import controller.ADMINISTRATOR;
import mutiThread.MainThread;
import utils.ComparatorBySectime;
import utils.CutData;
import utils.Date2String;
import utils.String2Date;
import utils.stringJoin;


public class relativeStatus{
	//保存序号数组
	private int[] numberMotiSeries;
	//激发的传感器位置序号不为0，即下标从1开始。.
//	private int[] l;
	//所有激发传感器的序号，与激发传感器个数相同。
	private int[] newl;
	//激发个数。
	private int countNumber;
	//数据。
	private Vector<String>[][] ssen;
	//所有传感器的状态对象数组。
	private Sensor[] sensors;
	//保存激发的传感器数组。
	private statusOfCompute status;
	private ADMINISTRATOR manager;
	
	public relativeStatus() {
		
	}
	
	public relativeStatus(
//			int [] l, 
			int [] newl, 
			int countNumber, 
			Sensor[] sensors,
			Vector<String>[][] ssen,
			statusOfCompute status,
			ADMINISTRATOR manager) {
		
		this.newl = newl;
		this.countNumber = countNumber;
		this.ssen = ssen;
		this.sensors = sensors;
		this.status = status;
		this.manager = manager;
	}
	
	/**
	 * this function is used to get the relative P arrival time point as the sensor 
	 * which the PArrival is not 0 for the sensor_latest. 
	 * @param sensors all sensor the procedure initializing at starting.
	 * @author Hanlin Zhang
	 */
	@SuppressWarnings("unused")
	public Sensor[] P_RelativeArrivalTime() {
		
		int nqk=-1;
		//初始化保存序号数组。
		this.numberMotiSeries = new int[this.newl.length];
		//按照到时排序。
		Sensor[] S = sortAccordingToPArrival();
		//计算相对于第一个到时的相对时间。
		for(int i=1;i<this.newl.length;i++){
			S[i].setSecTime(S[i].getSecTime()-S[0].getSecTime());//主要减去第一个不为零的时间
		}
		//置第一个到时传感器的到时为0.
		S[0].setSecTime(0.0);//we set the first sensor's P arrival time to 0.
		//调试模式下不对到时差做约束。
		if(Parameters.Adjust==true){
			manager.setIsRealMoti(true);
		}
		//倒时差小于IntervalToOtherSensors时，认为是一个事件，否则不是一个事件。
		else{
			if(Parameters.SSIntervalToOtherSensors==true){
				if(Math.abs(S[this.newl.length-1].getSecTime())>Parameters.IntervalToOtherSensors)
					manager.setIsRealMoti(false);
			}
			else
				manager.setIsRealMoti(true);
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
	public Sensor[] sortAccordingToPArrival() {
		
		//缩减当前sensors为激发传感器个数。
		Sensor[] S = new Sensor[this.newl.length];
		int count = 0;
		//赋值。
		for(int i=0;i<this.newl.length;i++) {
			S[count] = sensors[this.newl[i]];//the motivation sensor.
			count++;
		}
		
		//sort according to SecTime,可使用comparator进行排序。
		Arrays.sort(S, new ComparatorBySectime());
		//保存排序后的传感器号。
		for(int i=0;i<this.newl.length;i++) {
			this.numberMotiSeries[i] = S[i].getSensorNum();
		}
		
		Sensor[] newS = new Sensor[this.newl.length];
		newS = S.clone();
		
//		for(int i=0;i<l.length;i++) {
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
	public Sensor[] stowInfoSensor() {
		
		//sensors1为激发传感器，sensors2为未激发的传感器。
		Sensor[] sensors1 = new Sensor[countNumber];//save the sensors after sorting from short to long.
		Sensor[] sensors2 = new Sensor[Parameters.SensorNum-countNumber];
		//S保存所有的。
		Sensor[] S = new Sensor[sensors1.length+sensors2.length];
		
		//used to stow the data of each sensor's 10s or 18s length.
		@SuppressWarnings("unchecked")
		//保存所有截取后的激发数据。
		Vector<String>[] motiPreLa = new Vector[Parameters.SensorNum];
		@SuppressWarnings("unchecked")
		//保存所有数据，按照传感器号。
		Vector<String>[] inteData = new Vector[Parameters.SensorNum];
		//保存当前激发盘符。
		String panfu="";
		
		//更新传感器的相对到时，并根据到时排序。
		sensors1 = P_RelativeArrivalTime();//sort the sensors and calculate the relative P arrival time according Sectime variable.
		
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
			inteData[i].addAll(ssen[this.numberMotiSeries[i]][0]);
			inteData[i].addAll(ssen[this.numberMotiSeries[i]][1]);
			inteData[i].addAll(ssen[this.numberMotiSeries[i]][2]);
		}
		//剪切数据并对齐，保存剪切数据。
		for(int i=0;i<countNumber;i++) {
			motiPreLa[i] = CutData.cutOdata(inteData[i], sensors1, Parameters.startTime, Parameters.endTime, sensors1[i]);
			sensors1[i].setCutVectorData(motiPreLa[i]);
		}
		
		//no motivation sensor also need to set the wave data according to the first motivated sensor's time.
		int n=countNumber;
		for(int i=countNumber;i<Parameters.SensorNum;i++) {
//			if(n<Parameters.SensorNum) {
//				if(l[i]==0) {
					inteData[n].addAll(ssen[i][0]);
					inteData[n].addAll(ssen[i][1]);
					inteData[n].addAll(ssen[i][2]);
					n++;
//				}
//			}
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
				motiPreLa[n] = CutData.cutOdata(inteData[n], sensors1, Parameters.startTime, Parameters.endTime, sensors[i]);
				sensors[i].setCutVectorData(motiPreLa[n]);
				sensors[i].setlineSeriesNew(0);
				sensors2[n1] = sensors[i];
				n++;n1++;
			}
		}
		
		//get the complete string of all sensor used to the name of the motivated file split with " ".
		
		//当没有未激发盘符时，去掉一个空格。
		if(Parameters.offline==false) {
			String unMotivationSensor = stringJoin.SJoin_Array(MainThread.fileStr, sensors2);
			if(unMotivationSensor != "") {
				panfu = panfu+ " " + unMotivationSensor;
				panfu=panfu.replaceAll(":/", "");//替换掉盘符中的:/
			}
			else {
				panfu=panfu.replaceAll(":/", "");//替换掉盘符中的:/
			}
		}
		else {
			String unMotivationSensor = stringJoin.SJoin_Array(MainThread.fileParentPackage, sensors2);
			if(unMotivationSensor != "") {
				panfu = panfu+ " " + unMotivationSensor;
				panfu=panfu.replaceAll(":/", "");//替换掉盘符中的:/
			}
			else {
				panfu=panfu.replaceAll(":/", "");//替换掉盘符中的:/
			}
		}
		//复制数组。
		
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
		
		//set the name of path all.
		for(int i=0;i<S.length;i++) {
			S[i].setpanfu(panfu);//存储盘符字符串
		}
		
		//save to the status.
		status.setPanfu(panfu);
		status.setSensors1(sensors1);
		
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
	@Deprecated
	public static String PArrivalTime(Vector<String> data, Sensor sensor) throws ParseException {
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
	
	/**
	 * motivation_Diagnose_alone used.
	 * 计算激发位置的绝对时间，通过data第一条+到时来确定，即返回值激发位置时间一定大于data第一条数据的绝对时间。
	 * @param data
	 * @param SecTime
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月18日上午10:33:35
	 */
	public String PArrivalAbsoluteTime(
			Vector<String> data,
			double SecTime
			) {
		String P = "";
		P=String.valueOf(SecTime);
		String date = "";
		Date d = new Date();
		
		
		if(SecTime<1) {
			try {
				d=String2Date.str2Date(data.get(0).split(" ")[6]);
				date=Date2String.date2str(d)+"."+P.split("\\.")[1];
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			return date;
		}
		else {
			try {
				d=String2Date.str2Date(data.get(0).split(" ")[6]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance(); //内存溢出的出错位置。~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
			calendar.setTime(d);
			calendar.add(Calendar.SECOND, (int) Math.floor(SecTime));
			d=calendar.getTime();
			try {
				date = Date2String.date2str(d)+"."+P.split("\\.")[1];
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return date;
		}
		
	}
}
