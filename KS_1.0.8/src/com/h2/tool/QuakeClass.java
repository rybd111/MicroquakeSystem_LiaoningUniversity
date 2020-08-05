package com.h2.tool;

import java.awt.print.Printable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import com.h2.main.*;
import com.h2.backupData.WriteMotiData;
import com.h2.backupData.writeToDisk;
import com.h2.constant.Parameters;
import com.h2.constant.Sensor;
import com.h2.thread.ThreadStep3;
import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWException;

import PSO.pso_locate;
import ELMdiag.Class1;
import Energy_matlab.Energy_;
import mutiThread.MainThread;
import utils.Data2Object_MATLAB;

public class QuakeClass
{
	static ArrayList<List<double[]>> imfa= new ArrayList<List<double[]>>(/*capacity*/);
	static int s=0;
	static double[] arr=new double[Parameters.SensorNum];
	static int s3=0;
	/**
	 * compute the max amplitude of every sensor.
	 * @param sen the sensor status object used to store the status of every sensor.
	 * @param data the three vector data(30s) of every sensor.
	 * @author Baishuo Han, Hanlin Zhang, Yali Hao.
	 * @throws MWException 
	 */
	@SuppressWarnings("unused")
	public static void SensorMaxFudu(Sensor sen, Vector<String> data, int th) throws ParseException, MWException
	{

		//store the data cutting from 30s data.
		Vector<String> motiPreLa = new Vector<String>();
		//切分为激发时间处前Parameters.startTime、后Parameters.endTime数据
		motiPreLa = sen.getCutVectorData();
		
		//calculate the quack grade of each sensor.
		// 1：先扫一遍10秒的数据，确定用哪一个通道,顺便确定通道的最大值
		boolean flag = getFlag(motiPreLa, sen, th);
		
		// 3：计算最大振幅,并将结果存入传感器
		sen.setFudu(getBTime(sen, flag, motiPreLa, th));// 单位是秒
		
		//计算持续时间震级		
//		sen.setDuring(calDuringTime(motiPreLa, sen, th));//持续时间，取lg，然后直接存入duringTime中，作为一个参数使用
		//write motivation data to the local disk.
//		writeToDisk.writeToTxt(sen, motiNum, panfu, th, motiPreLa);
		
		//calculate the energy.
//		sen.setEnergy(EMD(motiPreLa, sen, th));
		sen.setEnergy(Energy_MATLAB(motiPreLa));
//		sen.setClass1(ELM_MATLAB(motiPreLa));
	}
	/**
	 * Cut a period of time data modified by artificial setting.
	 * @param data   the 30s data to be prepared to cut.
	 * @param s   array of sensors.
	 * @param PreSeconds  time before P arrival.
	 * @param AfterSeconds   time after P arrival.
	 * @param sen   the sensor we need to process.
	 * @return   Return the data after cutting. "Vector<String>"
	 * @author Hanlin Zhang.
	 */
	public static Vector<String> cutOdata(Vector<String> data, Sensor[] s, int PreSeconds, int AfterSeconds, Sensor sen) {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		Date motiDate = new Date();
		Date initmotiDate = new Date();
		Date t1 = new Date();
		
		long timeDiff = 0;
		boolean flag1 = true;
		//diagnose the sensors' P arrival time is exceed 1 second, procedure will cut one more second before the motivating line, and cut one less second after the motivating line.
				
		try {
			motiDate = format1.parse(sen.getTime());
			initmotiDate = format1.parse(s[0].getTime());
		}
		catch (ParseException e1) {e1.printStackTrace();}
		
		if(motiDate.getTime()-initmotiDate.getTime()>1) {
			PreSeconds++;
			AfterSeconds--;
		}
		
		Vector<String> resultSet = new Vector<String>();
		
		for(int i = 0; i < data.size(); i++){
			try {t1 = format1.parse(data.get(i).split(" ")[6]);}
			catch (ParseException e) {e.printStackTrace();}
			timeDiff = (motiDate.getTime()-t1.getTime())/1000;
			if(timeDiff < PreSeconds && timeDiff >= -AfterSeconds){
				resultSet.add(data.get(i));
				if(flag1==true) {
					flag1=false;
					sen.setlineSeriesNew(sen.getlineSeries()-i);//the motivation position in CSV file.
				}
			}
		}
		
		return resultSet;
	}
	
//	/**
//	 * 以日期为单位切分String型容器
//	 * @param data
//	 * @param D
//	 * @return
//	 */
//	public static Vector<String> cutOdata1(Vector<String> data, int line, int PreSeconds, int AfterSeconds, Sensor sen) {
//		DateFormat format1 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
//		Date motiDate = new Date();
//		Date t1 = new Date();
//		long timeDiff = 0;
//		boolean flag1 = true;
//		try {
//			motiDate = format1.parse(data.get(line).split(" ")[6]);
//		}
//		catch (ParseException e1) {e1.printStackTrace();}
//		
//		Vector<String> resultSet = new Vector<String>();
//		
//		for(int i = 0; i < data.size(); i++){
//			try {
//				t1 = format1.parse(data.get(i).split(" ")[6]);
//			}
//			catch (ParseException e) {e.printStackTrace();}
//			timeDiff = (motiDate.getTime()-t1.getTime())/1000;
//			if(timeDiff < PreSeconds && timeDiff >= -AfterSeconds){
//				resultSet.add(data.get(i));
////				if(flag1==true) {
////					flag1=false;
////					sen.setlineSeriesNew(sen.getlineSeries()-1);
////				}
//			}
//		}
//		
//		return resultSet;
//	}
	
	/**
	 * Cut the 30s data as unit of seconds.
	 * @param data the 30s data.
	 * @param line motivation data.
	 * @param startTime the length before the time of P arrival.
	 * @param endTime the length after the time of P arrival.
	 * @return Return the data after cutting. "Vector<String>"
	 * @author Hanlin Zhang.
	 */
	private static Vector<String> cutOdata1(Vector<String> data, int line, int startTime, int endTime) {
		Vector<String> resultSet = new Vector<String>();
		
		for(int i = line-Parameters.FREQUENCY*startTime; i < line+Parameters.FREQUENCY*endTime; i++){//设置为6s，当在now容器中的初始时刻激发时
			if(i<=0){
				System.out.println("当i小于0时，输出line"+line);
				System.out.println("当i小于0时，输出data大小"+data.size());
				System.out.println("当i小于0时，输出i"+i);
			}
			resultSet.add(data.get(i));
		}
		return resultSet;
	}
	
	/**
	 * 确定读取哪一个通道. 先扫一遍10秒的数据，确定用哪一个通道,顺便确定通道的最大值，若超出阈值，则flag为true
	 * @param data the data after cutting operation.
	 * @param sen the motivated sensor.
	 * @param th the series number of sensor. 
	 * @return A flag indicates that be used in the next step. 
	 * False indicates the 4 and 5 channel.
	 * True indicates the 1 and 2 channel.
	 * @author Baishuo Han.
	 */
	@SuppressWarnings("unused")
	private static boolean getFlag(Vector<String> data, Sensor sen, int th)
	{
		boolean flag = false;//标识
		String[] inte = new String[7];
		// 存储1245通道的最大值
		int max1 = 0;
		int max2 = 0;
		int max4 = 0;
		int max5 = 0;
//		System.out.println("第"+th+"台站");
//		System.out.println("第"+th+"台站"+data.size()+"的getFlag函数数据大小");
		for (int i = 0; i < data.size(); i++)
		{
			inte = data.get(i).split(" ");
			
			if(Parameters.TongDaoDiagnose==1) {//若有6个通道，判断才有意义
				if (testValue(inte[4]) || testValue(inte[3]))
				{
					flag = true;
				}
			}
			
			//fine the two group max value.
			max1 = (Math.abs(max1) > Math.abs(Integer.parseInt(inte[0]))) ? max1 : Integer.parseInt(inte[0]);
			max2 = (Math.abs(max2) > Math.abs(Integer.parseInt(inte[1]))) ? max2 : Integer.parseInt(inte[1]);
			max4 = (Math.abs(max4) > Math.abs(Integer.parseInt(inte[3]))) ? max4 : Integer.parseInt(inte[3]);
			max5 = (Math.abs(max5) > Math.abs(Integer.parseInt(inte[4]))) ? max5 : Integer.parseInt(inte[4]);
		}
		//save the max value.
		sen.setMax1(max1);
		sen.setMax2(max2);
		sen.setMax4(max4);
		sen.setMax5(max5);
		return flag;
	}

	/**
	 * Calculate the during time from the position of max value to the first zero position.
	 * Calculate the amplitude of the current data.
	 * @param sen the motivation sensor.
	 * @param flag the channel we choose embody by this flag.
	 * @param data the data after cutting operation.
	 * @return Return the max amplitude of the current data according to An and Ae.
	 * An is the weave of the north direction.
	 * Ae is the weave of the east direction.
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	///对应算法公式中的     a - b
	@SuppressWarnings("unused")
	private static double getBTime(Sensor sen, boolean flag, Vector<String> data, int th)
	{
		int num=0;
		//System.out.println("第"+th+"台站"+data.size()+"的getBTime函数数据大小");
		if (!flag) {// 选择45通道
		
			//double gain we need to multiply 512.
			if(Parameters.TongDaoDiagnose==1) {
				num = 512;
				
				double An = getRecordCount(sen, 5, data, th) / (double) Parameters.FREQUENCY;//求最大值到第一个小于0的数的时间间隔
				sen.setBn(An);
				An =calSum(5, data,th)*Parameters.plusDouble_coefficient_45 * 1000;
				
				double Ae = getRecordCount(sen, 4, data, th) / (double) Parameters.FREQUENCY;
				sen.setBe(Ae);
				Ae =calSum(4, data,th)*Parameters.plusDouble_coefficient_45 * 1000;
				
				return ((Ae + An) *num ) / 2;
			}
			//single gain we need to multiply 100.
			else {
				num = 100;
				
				double An = getRecordCount(sen, 5, data, th) / (double) Parameters.FREQUENCY;//求最大值到第一个小于0的数的时间间隔
				sen.setBn(An);
				An =calSum(5, data,th)*Parameters.plusSingle_coefficient * 1000;
				
				double Ae = getRecordCount(sen, 4, data, th) / (double) Parameters.FREQUENCY;
				sen.setBe(Ae);
				Ae =calSum(4, data,th)*Parameters.plusSingle_coefficient * 1000;
				
				return ((Ae + An) *num ) / 2;
			}
		}
		//exceed the scope.
		else{
			num=32;
			
			double An = getRecordCount(sen, 2, data, th) / (double) Parameters.FREQUENCY;
			sen.setBn(An);
			An =calSum(2, data,th)*Parameters.plusDouble_coefficient_12 * 1000;
			
			double Ae = getRecordCount(sen, 1, data, th) / (double) Parameters.FREQUENCY;
			sen.setBe(Ae);
			Ae =calSum(1, data,th)*Parameters.plusDouble_coefficient_12 * 1000;
			
			return ((Ae + An) *num ) / 2;
		}
	}

	/**
	 * Calculate the number of points between the max value and the zero value.
	 * Calculate the position of the max value and the zero value.
	 * @param sen the motivation sensor.
	 * @param channel 
	 * @return the number of points between the max value and the zero position.
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	private static int []begin1 = new int[Parameters.SensorNum];// 标识最大值的那条记录
	private static int []end1 = new int[Parameters.SensorNum];// 标识0值的那条记录
	private static int []begin2 = new int[Parameters.SensorNum];// 标识最大值的那条记录
	private static int []end2 = new int[Parameters.SensorNum];// 标识0值的那条记录
	
	private static int getRecordCount(Sensor sen, int channel, Vector<String> data, int th)
	{
		int end=0;int begin=0;//用于求后T/4的数据面积
	
		// 1：先取指定channel的最大值
		int Max = 0;
		switch (channel)
		{
			case 1:	Max = sen.getMax1();break;
			case 2:	Max = sen.getMax2();break;
			case 4:	Max = sen.getMax4();break;
			case 5:	Max = sen.getMax5();break;
		}
		// 2：定位到最大值的那条记录
		while (Integer.parseInt(data.get(begin).split(" ")[channel - 1]) !=  Max)
		{
			begin++;
		}

		// 3:定位到值为0的那条记录
		if(Max>0){
			for(int i=begin;i<data.size();i++) {
				if(Integer.parseInt(data.get(i).split(" ")[channel-1])<=0) {
					end=i-1;
					break;
				}
			}
		}
		else{
			for(int i=begin;i<data.size();i++) {
				if(Integer.parseInt(data.get(i).split(" ")[channel-1])>=0) {
					end=i-1;
					break;
				}
			}
			
		}
		QuakeClass.begin1[th]=begin;
		QuakeClass.end1[th]=end;
		//用于求前T/4的数据面积

		// 3:定位到值为0的那条记录
		if(Max>0){
			for(int i=begin;i>=0;i--) {
				if(Integer.parseInt(data.get(i).split(" ")[channel-1])<=0) {
					end=i+1;
					break;
				}
			}
		}
		else{//Max小于0
			for(int i=begin;i>=0;i--) {
				if(Integer.parseInt(data.get(i).split(" ")[channel-1])>=0) {
					end=i+1;
					break;
				}
			}
		}
		QuakeClass.begin2[th]=begin;
		QuakeClass.end2[th]=end;
		double sum1 = 0;
		double sum2 = 0;
		
//		System.out.print("第"+th+"号T/4处序号为："+QuakeClass.begin1[th]+" "+QuakeClass.end1[th]+"     ");
//		System.out.println(QuakeClass.begin2[th]+" "+QuakeClass.end2[th]+" "+Max);
		
		for(int i=QuakeClass.begin1[th];i<QuakeClass.end1[th];i++){
			sum1+=Math.abs(Integer.parseInt(data.get(i).split(" ")[channel - 1]));//出现超出数组范围错误		
		}
		for(int i=QuakeClass.end2[th];i<QuakeClass.begin2[th];i++){
			sum2+=Math.abs(Integer.parseInt(data.get(i).split(" ")[channel - 1]));
		}
		if(sum1<sum2) 
			return (QuakeClass.begin2[th] - QuakeClass.end2[th]);
		else 
			return (QuakeClass.end1[th] - QuakeClass.begin1[th]);
	}
	
	/**
	 * Calculate the integral of the weave between the postion of the max value and the zero value.
	 * @param channel
	 * @param data the weave.
	 * @param th the series number of sensor.
	 * @return the integral value.
	 * @author Hanlin Zhang.
	 */
	private static double calSum(int channel, Vector<String> data, int th){
		double sum1=0;
		double sum2=0;
		double maxSum=0;
		
		for(int i=QuakeClass.begin1[th];i<=QuakeClass.end1[th];i++){
			sum1+=Math.abs(Integer.parseInt(data.get(i).split(" ")[channel - 1]));
		}
		for(int i=QuakeClass.end2[th];i<=QuakeClass.begin2[th];i++){
			sum2+=Math.abs(Integer.parseInt(data.get(i).split(" ")[channel - 1]));
		}
		if(Math.abs(sum1)>Math.abs(sum2)) {
			maxSum=sum1/(Parameters.FREQUENCY+200);//乘以频率，得面积
		}
		else {
			maxSum=sum2/(Parameters.FREQUENCY+200);
		}
//		System.out.print("第"+th+"个台站的"+"两个面积分别为："+sum1/5000+"和"+sum2/5000+"其中的最大值为"+maxSum+" ");
//		System.out.print("最大值 "+QuakeClass.begin1[th]+" 后T/4零值  "+QuakeClass.end1[th]+" ");
//		System.out.println("最大值 "+QuakeClass.begin2[th]+" 前T/4零值  "+QuakeClass.end2[th]+" ");
		
		return maxSum;//可能为0
	}

	/**
	 * Diagnose the channel exceed the scope or not according to the hardware parameters.
	 * @param s the corresponding of the channel value distilled from vector which is String type.
	 * @return A flag which is true indicates exceed the max scope or not.
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	private static boolean testValue(String s)
	{
		int a = Integer.parseInt(s);
		if (a >= Parameters.HEAD || a <= Parameters.TAIL){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Calculate the final near earthquake grade of the one sensor. 
	 * @param s A sensor as a benchmark.
	 * @param s2 The remine of other sensors.
	 * @return The near earthquake grade of the one sensor.
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	public static void getOneEarthQuakeGrade(Sensor s, Sensor s2)
	{
		double distance = getDistance(s, s2);
		double dis_s1Tos2 = getD(s,s2);
//		System.out.println("震中距："+distance);
//		System.out.println("震中距（两点平面距离）："+dis_s1Tos2);
		/*if (distance < 0.0 || distance > 5.0)
		{
			s2.setEarthClassFinal(Math.log10(s2.getFudu()) + getR(distance));
		} else
		{*/
			s2.setEarthClassFinal(Math.log10(s2.getFudu()) + getR(distance));
			
		/*}*/
	}

	/**
	 * Calculate the distance between two sensors by spherical distance of the earth.
	 * @param s1 sensor1.
	 * @param s2 sensor2.
	 * @return The distance among each sensor to the others.
	 * @author Baishuo Han.
	 */
	public static double getDistance(Sensor s1, Sensor s2)
	{
		// http://www.jianshu.com/p/18efaabab98e
		double radLat1 = rad(s1.getLatitude() / 100);
		double radLat2 = rad(s2.getLatitude() / 100);
		double a = radLat1 - radLat2;
		double b = rad(s1.getLongtitude() / 100) - rad(s2.getLongtitude() / 100);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return Math.abs(s / 1000);
	}
	
	/**
	 * Calculate the directly distance between two sensors.
	 * @param s1 sensor1.
	 * @param s2 sensor2.
	 * @return The distance.
	 * @author Hanlin Zhang.
	 */
	public static double getD(Sensor s1, Sensor s2){
		double x0 = s1.getLatitude();
		double y0 = s1.getAltitude();
		
		double x1 = s2.getLatitude();
		double y1 = s2.getAltitude();
		
		double dis_s1Tos2 = Math.sqrt(Math.pow((x0-x1), 2)+Math.pow((y0-y1), 2));
		return dis_s1Tos2;
	}

	/**
	 * The angle transfer to the radian.
	 * @param d the angle.
	 * @return the radian.
	 * @author Baishuo Han.
	 */
	private static double rad(double d)
	{
		return d * Math.PI / 180.0;
	}

	/**
	 * Confirm the distance in the process of computing near earthquake grade.
	 * @param d the distance.
	 * @return the value we should plus on the near earthquake grade as the final consequence.
	 * @author Baishuo Han.
	 */
	private static double getR(double d)
	{
		if (d >= 0 && d < 0.5)	return 0.48;
		if (d >= 0.5 && d < 1)	return 0.78;
		if (d >= 1 && d < 1.5)	return 1.05;
		if (d >= 1.5 && d < 2)	return 1.21;
		if (d >= 2 && d < 2.5)	return 1.36;
		if (d >= 2.5 && d < 3)	return 1.47;
		if (d >= 3 && d < 3.5)	return 1.57;
		if (d >= 3.5 && d < 4)	return 1.66;
		if (d >= 4 && d < 4.5)	return 1.73;
		if (d >= 4.5 && d <= 5.0)	return 1.8;
		if(d>5.0)	return 1.8;
		return 0;
	}
	
	/**
	 * Calculate the duration magnitude, but we need to calculate the variance of each sensor.
	 * @param data the weave.
	 * @param sen the sensor.
	 * @param th the series number of sensor.
	 * @return the duration magnitude.
	 * @author Yali Hao.
	 */
	public static double calDuringTime(Vector<String> data, Sensor sen, int th){
		double array[]=new double[Parameters.FREQUENCY];
		double variance;
		double fuDu=0.0;	
		double lastingTime = 0.0;//持续时间，单位：ms
		int k=0,duringTime=0;
		for(int h=sen.getlineSeries();h<data.size()-Parameters.FREQUENCY;h++){//从激发位置往后滑动1s长度的时间窗口，步长为1.
			for(int i=h;i<h+Parameters.FREQUENCY;i++){
				array[i]=Double.parseDouble(data.get(i).split(" ")[5]);//将激发位置推迟1s的数据保存到array数组中
			}
			variance=varianceImperative(array);
			if(h==sen.getlineSeries())	System.out.println("方差为："+variance);
			for(int i=0;i<MainThread.fileStr.length;i++) {
				if(MainThread.fileStr[th].equals(Parameters.diskName[i])){
					if(variance<=2*Parameters.backGround[i]){
						k=h+Parameters.FREQUENCY;//记录方差等于2倍背景噪声的位置作为持续时间的结束位置，此时算法停止
						duringTime=k-sen.getlineSeries();//持续的数据的条个数
						lastingTime = duringTime*0.2;//持续的总时间，单位：ms
						break;
					}
				}
			}
		}
		return Math.log10(lastingTime);//直接取lg
	}

	/**
	 * 
	 * @param population
	 * @return 
	 * @author Yali Hao.
	 */
	public static double varianceImperative(double[] population) {		
		double average = 0.0;		
		for (double p : population) {
			average += p;		
		}
		average /= population.length;
		double variance = 0.0;
		for (double p : population) {
			variance += (p - average) * (p - average);		
		}
		return variance / population.length;	
	}

	/** the earth radius.*/
	private static final double EARTH_RADIUS = 6378137;
	
	/**
	 * Calculate the energy of one sensor.
	 * @param data
	 * @param sen
	 * @param th
	 * @author Yali Hao.
	 * @return 
	 */
	public static double EMD(Vector<String> data, Sensor sen, int th){
		return suoyou(data);
	}

	public  static double suoyou(Vector<String> data) {
		boolean flag = false;
		int i=0;
		int w=0;
		int x=0;
		double sum1=0;
		double[] array=new double[data.size()];
		int  arrayx[]=new int[data.size()];
		double yMax[] =new double[data.size()];
		double yMin[]=new double[data.size()];
		int XMax[]=new int[data.size()];
		int XMin[]=new int[data.size()];
		//回传时数据不一致
		if(s==0) {
			for(int h=Parameters.startTime*Parameters.FREQUENCY;h<Parameters.endTime*Parameters.FREQUENCY;h++){//h表示x轴的值	
			//for(int h=5*Parameters.FREQUENCY;h<data.size()-Parameters.FREQUENCY;h++){//h表示x轴的值
				array[i]=Double.parseDouble(data.get(h).split(" ")[5]);//取序列对应的y值存到array[i]
				arrayx[i]=x;//把对应的x轴的值存到	arrayx[i]
				x++;
				i++;
			}	
			s++;
		}
		else {
			for(int h=0;h<data.size();h++){//h表示x轴的值
				array[i]=Double.parseDouble(data.get(h));//取序列对应的y值存到array[i]
				arrayx[i]=h;//把对应的x轴的值存到	arrayx[i]
				i++;
			}	
		}
		int  jizhi[]=new int [2];
		double minarray[]=array.clone();
		double maxarray[]=array.clone();
		double temp[]=new double[data.size()];
		double temp1[]=new double[data.size()];
		double temp2[]=array.clone();
		double temp3[]=new double[data.size()];
		double avg[]=new double[data.size()];
		String temp4[]= new String[data.size()];
		Vector<String> object2;//Vector定义
		int s1;
		int s2;
		//遍历数组，找到极大值和极小值的数组，当s==array.length-1停下来遍历完成
		int[]jizhi1=jizhi(array, arrayx, yMax, yMin, XMax, XMin, jizhi);
		//极大值形成包络线
		temp = addmax(arrayx, yMax, XMax, maxarray, temp,jizhi1);
		//极小值形成包络线
		temp1 = addmax(arrayx, yMin, XMin, minarray, temp1,jizhi1);
		avg=avg(minarray, temp, temp1, avg);
		temp2=imf(array, maxarray, temp2, avg);
		int[]jizhi2=jizhi(temp2, arrayx, yMax, yMin, XMax, XMin, jizhi);
		s1=jizhi2[0]+jizhi2[1];
	//		System.out.println("极值点的个数" + s1);
		s2=lingdian(w, temp2);
	//		System.out.println("零点的个数" + s2);
		double sum=0.0;
		sum=QuakeClass.sumavg(avg);//
		while(Math.abs(s1-s2)>=20) {//||sum!=0
			 yMax=new double[data.size()];
			 yMin=new double[data.size()];
			 XMax=new int[data.size()];
			 XMin=new int[data.size()];
			 double[] minarray1=temp2.clone();
			double[] maxarray1=temp2.clone();
			
			jizhi1=jizhi(temp2, arrayx, yMax, yMin, XMax, XMin, jizhi);
			if(yMax.length==jizhi1[0]) {
	//				System.out.println("极大值的个数是对的");
			}else {
	//				System.out.println("极大值的个数是错的");
				yMax=QuakeClass.subBytes(yMax, 0, jizhi1[0]);
			}
			if(yMin.length==jizhi1[1]) {
	//				System.out.println("极小值的个数是对的");
			}else {
	//				System.out.println("极小值的个数是错的");
				yMin=QuakeClass.subBytes(yMin, 0, jizhi1[1]);
			}
			if(XMax.length==jizhi1[1]) {
	//				System.out.println("x的极小值的个数是对的");
			}else {
	//				System.out.println("x的极小值的个数是错的");
				XMax=QuakeClass.subBytes1(XMax, 0, jizhi1[0]);
			}
			if(XMin.length==jizhi1[1]) {
	//				System.out.println("x的极小值的个数是对的");
			}else {
	//				System.out.println("x的极小值的个数是错的");
				XMin=QuakeClass.subBytes1(XMin, 0, jizhi1[1]);
			}
			if(yMax.length==1) {
				temp=maxarray1;
			}else {
			temp = addmax(arrayx, yMax, XMax, maxarray1, temp, jizhi1);
			}if(yMin.length==1) {
				temp1=minarray1;
			}else {
			temp1 = addmin(arrayx, yMin, XMin, minarray1, temp1,jizhi1);}
			//画蛇添足
			yMax=new double[data.size()];
			yMin=new double[data.size()];
			XMax=new int[data.size()];
			XMin=new int[data.size()];
			avg=avg(minarray1, temp, temp1, avg);
			sum=QuakeClass.sumavg(avg);
			temp2=imf(temp2, maxarray, temp2, avg);
	//			System.out.println("yMax==="+yMax.length);
			int[]jizhi3=jizhi(temp2, arrayx, yMax, yMin, XMax, XMin, jizhi);
			s1=jizhi3[0]+jizhi3[1]; 
	//			System.out.println("极值点的个数" + s1);
			s2=lingdian(w, temp2);
	//			System.out.println("零点的个数"+ s2);
		}
	
		while(Math.abs(s1-s2)<=20 &&flag==false) {//&&sum==0
	//			List<double[]> list=Arrays.asList(imf(minarray, maxarray, temp2, avg));
			List<double[]> list=Arrays.asList(temp2);
	//			System.out.println("1111");
			imfa.add(list);
			
			for(int i1=0;i1<array.length;i1++) {
				temp3[i1]= array[i1]-temp2[i1];
			} 
			for (int j = 0; j <temp3.length-1; j++) {
				if(temp3[j]>=temp3[j+1]) {
					if((j+1)==temp3.length-1) {
						System.out.println("是单调的1");
						//直接进行计算
	//						System.out.println("imfa.size()======"+imfa.size());
						for (int i2 = 0; i2 <imfa.size(); i2++) {
							List<double[]> array2=imfa.get(i2);
	//							System.out.println("array2.size()==="+array2.size());
							for (int j2= 0; j2 < array2.size(); j2++) {
	//								System.out.println(j2+"array2.get(j2)"+array2.get(j2).toString());
								double[] b = array2.get(j2); //表示b的平方
							for(int k=0;k<b.length;k++) {
								double a=Math.pow(b[k], 2);	
								sum1=sum1+a;
							}
							}
						}
						sum1=sum1/5000;
						flag=true;
						System.out.println("sum1===="+sum1);
						s=0;
						sum1=0;
						flag=true;
						break;
					}
					continue;
				} 
				for (int j1 = 0; j1 < temp3.length-1; j1++) {
					if(temp3[j1]<=temp3[j1+1]) {
						if((j1+1)==temp3.length-1) {
	//							System.out.println("是单调的2");
							for (int i2 = 0; i2 <imfa.size(); i2++) {
	//								System.out.println("imfa.size()"+imfa.size());
								List<double[]> array2=imfa.get(i2);
								for (int j2= 0; j2 < array2.size(); j2++) {
	//									System.out.println(array2.get(j2));
									double[] b = array2.get(j2); //表示b的平方
									for(int k=0;k<b.length;k++) {
										double a=Math.pow(b[k], 2);	
										sum1=sum1+a;
									}
								}
							}
							sum1=sum1/5000;
//								System.out.println("sum2===="+sum1);
							s=0;
							flag=true;
							break;
							//直接进行计算
						}
						continue;
					}
				}
				if(flag) {
					break;
				}
	//					System.out.println("不是单调的");
			}
			if(flag) {
				break;
			}
			//回传 
			for(int i1=0;i1<temp4.length;i1++) {
				temp4[i1]=String.valueOf(temp3[i1]);
			}
			object2=new Vector<String>(Arrays.asList(temp4));//[] -> array -> vector
		    suoyou(object2);
		}
//		System.out.println("sum="+sum1);
		return sum1;
	}

	/** 找到极大值极小值，返回一个数组，数组内第一个存极大值，第二个存极小值
	 * */
	private static int[]  jizhi(double[] array, int[] arrayx, double[] yMax, double[] yMin, int[] XMax, int[] XMin,int[] jizhi) {
		int m = 0;
		int n = 0;
		int i=0;
		for(int s=0;s<array.length;s++) {
			if(s==array.length-2) {
				while(i<1) {
					//第一个存极大值
					jizhi[i]=m;
					i++;
					//第二个存极小值
					jizhi[i]=n;
				}
				return jizhi;
			}
			if(array[s+1]>array[s]&&array[s+1]>array[s+2]) {
				yMax[m]=array[s+1];//找到极大值
				XMax[m]=arrayx[s+1];//极大值对应的下标
				m++;
			}
			if(array[s+1]<array[s]&&array[s+1]<array[s+2]) {
				yMin[n]=array[s+1];//找到极小值
				XMin[n]=arrayx[s+1];//极小值对应的下标
				n++;
				
			}
		}
		return jizhi;
	}

	/**插值
	 * */
	private static double[] addmin(int[] arrayx, double[] yMax, int[] XMax, double[] maxarray, double[] temp,int[]jizhi1) {
		int s = jizhi1[1];
		for(int h=0;h<s-1;h++) {
			temp=addvalue(yMax, XMax, h, maxarray,arrayx);
		}
		return temp;
	}
	
	/**形成暂时的imf分量
	 * */
	private static double[] imf(double[] array, double[] maxarray, double[] temp2, double[] avg) {
		for(int i1=0;i1<maxarray.length;i1++) {
			temp2[i1]= array[i1]-avg[i1];
		}
		return temp2;
	}
	
	/**
	 * 包络线形成平均值
	 * @return 
	 * */
	private static double[] avg(double[] minarray, double[] temp, double[] temp1, double[] avg) {
		for(int i1=0;i1<minarray.length;i1++){
			avg[i1]	= (temp1[i1]+temp[i1])/2;
		}
		return avg;
	}
	
	private static int lingdian(int w,double[] temp2) {
		w =0;
		for(int i=0;i<temp2.length-1;i++) {
			if((temp2[i]*temp2[i+1])<0) {
				w++;
			}
		}
		
		return w;
		
	}
	
	private static double[] addmax(int[] arrayx, double[] yMax, int[] XMax, double[] maxarray, double[] temp,int[]jizhi1) {
		int m = jizhi1[0];
		for(int h=0;h<m-1;h++) {
			temp=addvalue(yMax, XMax, h, maxarray,arrayx);
		}
		return temp;
	}

	/** 用相邻两个极大值的平均值代替原来的相邻两个极大值之间的y
	array存的最大值，array2 最大值的下标即对应的x值， array3全部的数据y值array4全部的数据x的值	
	 * */
	public  static double[] addvalue(double[] yMax,int  array2[],int m,double[] maxarray,int array4[]) {
		double avg=(yMax[m]+yMax[m+1])/2;
		for(int i=array2[m]+1;i<array2[m+1];i++) {
			maxarray[i]=avg; 
		}
		return maxarray;
	}

	public static double sumavg(double[] avg) {
	double sum=0;
		for(int i1=0;i1<avg.length;i1++) {
		sum=sum+avg[i1];
	}
		return sum;
	}
	
	public static double [] subBytes(double[] src, int begin, int count) {
		double[] bs = new double[count];
	    System.arraycopy(src, begin, bs, 0, count);
	    return bs;
	}
	public static int [] subBytes1(int[] xMax, int begin, int count) {
		int[] bs = new int[count];
	    System.arraycopy(xMax, begin, bs, 0, count);
	    return bs;
	}
	/**
	 * calculate the energy of one sensor.
	 * @param a
	 * @return Energy of a wave from one sensor.
	 * @throws MWException
	 * @author Hanlin Zhang.
	 */
	public static double Energy_MATLAB(Vector<String> a) throws MWException {
		String inte[];
		boolean flag=false;
		int channel=5;
		
		for (int i = 0; i < a.size(); i++){
			inte = a.get(i).split(" ");
			if(Parameters.TongDaoDiagnose==1) {//若有6个通道，判断才有意义
				if (testValue(inte[4]) || testValue(inte[3])){
					channel=2;
				}
			}
		}
		
		Object aObject=Data2Object_MATLAB.data2Object(a, channel);
		Energy_ calEnergy = new Energy_();
		Object energy[] = calEnergy.Energy(1, aObject);
		
		return Double.parseDouble(energy[0].toString());
	}
	
	/**
	 * calculate the class of each sensor's motidata.
	 * @param a
	 * @return
	 * @throws MWException
	 * @author Hanlin Zhang.
	 */
	
	public static int ELM_MATLAB(Vector<String> a) throws MWException {
		String inte[];
		boolean flag=false;
		int channel=5;
		
		for (int i = 0; i < a.size(); i++){
			inte = a.get(i).split(" ");
			if(Parameters.TongDaoDiagnose==1) {//若有6个通道，判断才有意义
				if (testValue(inte[4]) || testValue(inte[3])){
					channel=2;
				}
			}
		}
		
		Object aObject=Data2Object_MATLAB.data2Object_65000(a, channel);
		Class1 c = new Class1();
		Object c1[] = c.ELM(1, aObject);
//		System.out.println("class"+ c1[0].toString());
		return Integer.parseInt(c1[0].toString());
	}
	
	public static Sensor PSO(double [][]Info) throws MWException {
		
		pso_locate p = new pso_locate();
		Object d = Data2Object_MATLAB.array2Object(Info);
		Object c = Data2Object_MATLAB.num2Object(Parameters.C);
		Object result[] = p.PSO(4, d, c);
		
		Sensor s = new Sensor();
		
		s.setLatitude(Double.parseDouble(result[0].toString()));
		s.setLongtitude(Double.parseDouble(result[1].toString()));
		s.setAltitude(Double.parseDouble(result[2].toString()));
		s.setSecTime(Double.parseDouble(result[3].toString()));
		
		System.out.print("x:"+s.getLatitude());
		System.out.print("y:"+s.getLongtitude());
		System.out.print("z:"+s.getAltitude());
		System.out.print("t"+s.getSecTime());
		System.out.println();
//		System.out.print("x:"+result[0].toString());
//		System.out.print("y:"+result[1].toString());
//		System.out.print("z:"+result[2].toString());
//		System.out.println();
		return s;
	}
	
}
