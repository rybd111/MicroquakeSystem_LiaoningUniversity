package com.h2.tool;

import java.text.ParseException;
import java.util.Vector;
import com.h2.constant.Parameters;
import com.mathworks.toolbox.javabuilder.MWException;

import DataExchange.Sensor;
import PSO.pso_locate;
import ELMdiag.Class1;
import Energy_matlab.Energy_;
import mutiThread.MainThread;
import timelocation.Time_Locate;
import utils.Data2Object_MATLAB;
import utils.DistanceAroundSensors;
import utils.SelectChannel;

public class QuakeClass
{
	//用于指示是否使用45通道进行判断。
	private Boolean isChannel45 = false;
	
	public QuakeClass() {
		
	}
	
	/**
	 * compute the max amplitude of every sensor.
	 * @param sen the sensor status object used to store the status of every sensor.
	 * @param data the three vector data(30s) of every sensor.
	 * @author Baishuo Han, Hanlin Zhang, Yali Hao.
	 * @throws MWException 
	 */
	@SuppressWarnings("unused")
	public void SensorMaxFudu(Sensor sen, int th) throws ParseException, MWException
	{

		//get the data cutting from 30s data.
		Vector<String> motiPreLa = sen.getCutVectorData();
		
		//矩张量的初动极值，需要
		InitialValue filter=new InitialValue();
		sen.setInitialextremum(filter.getInitialextremum(motiPreLa,sen.getlineSeriesNew()));
		//计算近震震级
		calQuakeProcess(motiPreLa, sen);
		
		//计算持续时间震级		
//		sen.setDuring(calDuringTime(motiPreLa, sen, th));//持续时间，取lg，然后直接存入duringTime中，作为一个参数使用
		//write motivation data to the local disk.
//		writeToDisk.writeToTxt(sen, motiNum, panfu, th, motiPreLa);
		
		//calculate the energy.
//		sen.setEnergy(EMD(motiPreLa, sen, th));
		sen.setEnergy(Energy_MATLAB(motiPreLa));
//		sen.setClass1(ELM_MATLAB(motiPreLa));
	}
	
	private void calQuakeProcess(Vector<String> motiPreLa, Sensor sen) {
		//calculate the quack grade of each sensor.
		// 1：先扫一遍10秒的数据，确定用哪一个通道,顺便确定通道的最大值
		getFlagMaxValue(motiPreLa, sen);
		
		// 3：计算最大振幅,并将结果存入传感器
		sen.setFudu(getBTime(sen, motiPreLa));//单位是秒
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
	private void getFlagMaxValue(Vector<String> data, Sensor sen)
	{
		String[] inte = new String[7];
		// 存储1245通道的最大值
		int max1 = 0;
		int max2 = 0;
		int max4 = 0;
		int max5 = 0;
		
		//找最大值并判断量程是否越界，达到最大量程则使用大量程。
		for (int i = 0; i < data.size(); i++)
		{
			inte = data.get(i).split(" ");
			
			if(Parameters.TongDaoDiagnose==1) {//若有6个通道，判断才有意义
				if (SelectChannel.testValue(inte[4]) || SelectChannel.testValue(inte[3])) {
					this.isChannel45 = true;
				}
			}
			
			//find the two group max value.
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
	private double getBTime(Sensor sen, Vector<String> data)
	{
		int num=0;
		//System.out.println("第"+th+"台站"+data.size()+"的getBTime函数数据大小");
		if (!this.isChannel45) {// 选择45通道
		
			//double gain we need to multiply 512.
			if(Parameters.TongDaoDiagnose==1) {
				num = 512;
				
				double An = getRecordCount(sen, 5, data) / (double) Parameters.FREQUENCY;//求最大值到第一个小于0的数的时间间隔
				sen.setBn(An);
				An =calArea(5, data)*Parameters.plusDouble_coefficient_45 * 1000;
				
				double Ae = getRecordCount(sen, 4, data) / (double) Parameters.FREQUENCY;
				sen.setBe(Ae);
				Ae =calArea(4, data)*Parameters.plusDouble_coefficient_45 * 1000;
				
				return ((Ae + An) *num ) / 2;
			}
			//single gain we need to multiply 100.
			else {
				num = 100;
				
				double An = getRecordCount(sen, 5, data) / (double) Parameters.FREQUENCY;//求最大值到第一个小于0的数的时间间隔
				sen.setBn(An);
				An =calArea(5, data)*Parameters.plusSingle_coefficient * 1000;
				
				double Ae = getRecordCount(sen, 4, data) / (double) Parameters.FREQUENCY;
				sen.setBe(Ae);
				Ae =calArea(4, data)*Parameters.plusSingle_coefficient * 1000;
				
				return ((Ae + An) *num ) / 2;
			}
		}
		//exceed the scope.
		else{
			num=32;
			
			double An = getRecordCount(sen, 2, data) / (double) Parameters.FREQUENCY;
			sen.setBn(An);
			An =calArea(2, data)*Parameters.plusDouble_coefficient_12 * 1000;
			
			double Ae = getRecordCount(sen, 1, data) / (double) Parameters.FREQUENCY;
			sen.setBe(Ae);
			Ae =calArea(1, data)*Parameters.plusDouble_coefficient_12 * 1000;
			
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
	private int begin1 = 0;// 标识最大值的那条记录
	private int end1 = 0;// 标识0值的那条记录
	private int begin2 = 0;// 标识最大值的那条记录
	private int end2 = 0;// 标识0值的那条记录
	
	private int getRecordCount(Sensor sen, int channel, Vector<String> data)
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
		while (Integer.parseInt(data.get(begin).split(" ")[channel - 1]) !=  Max) {
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
		this.begin1=begin;
		this.end1=end;
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
		this.begin2=begin;
		this.end2=end;
		double sum1 = 0;
		double sum2 = 0;
		
//		System.out.print("第"+th+"号T/4处序号为："+QuakeClass.begin1[th]+" "+QuakeClass.end1[th]+"     ");
//		System.out.println(QuakeClass.begin2[th]+" "+QuakeClass.end2[th]+" "+Max);
		
		for(int i=this.begin1;i<this.end1;i++){
			sum1+=Math.abs(Integer.parseInt(data.get(i).split(" ")[channel - 1]));//出现超出数组范围错误		
		}
		for(int i=this.end2;i<this.begin2;i++){
			sum2+=Math.abs(Integer.parseInt(data.get(i).split(" ")[channel - 1]));
		}
		if(sum1<sum2) 
			return (this.begin2 - this.end2);
		else 
			return (this.end1 - this.begin1);
	}
	
	/**
	 * Calculate the integral of the weave between the postion of the max value and the zero value.
	 * @param channel
	 * @param data the weave.
	 * @param th the series number of sensor.
	 * @return the integral value.
	 * @author Hanlin Zhang.
	 */
	private double calArea(int channel, Vector<String> data){
		double sum1=0;
		double sum2=0;
		double maxSum=0;
		
		for(int i=this.begin1;i<=this.end1;i++){
			sum1+=Math.abs(Integer.parseInt(data.get(i).split(" ")[channel - 1]));
		}
		for(int i=this.end2;i<=this.begin2;i++){
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
	 * Calculate the final near earthquake grade of the one sensor. 
	 * @param s A sensor as a benchmark.
	 * @param s2 The remine of other sensors.
	 * @return The near earthquake grade of the one sensor.
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	public void getOneEarthQuakeGrade(Sensor s, Sensor s2)
	{
		double distance = DistanceAroundSensors.getDistance(s, s2);
		//此处如果不加震中距，则震级计算相对偏大，而且能量在10E7与10E8几乎差不多，故采用减去固定震级、加上震中距的方式。
		s2.setEarthClassFinal(Math.log10(s2.getFudu()) + getR(distance));
//		s2.setEarthClassFinal(Math.log10(s2.getFudu()));	
	}

	/**
	 * Confirm the distance in the process of computing near earthquake grade.
	 * @param d the distance.
	 * @return the value we should plus on the near earthquake grade as the final consequence.
	 * @author Baishuo Han.
	 */
	private double getR(double d)
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
		double lastingTime = 0.0;//持续时间，单位：ms
		int k=0,duringTime=0;
		for(int h=sen.getlineSeries();h<data.size()-Parameters.FREQUENCY;h++){//从激发位置往后滑动1s长度的时间窗口，步长为1.
			for(int i=h;i<h+Parameters.FREQUENCY;i++){
				array[i]=Double.parseDouble(data.get(i).split(" ")[5]);//将激发位置推迟1s的数据保存到array数组中
			}
			variance=varianceImperative(array);
			if(h==sen.getlineSeries())	System.out.println("方差为："+variance);
			for(int i=0;i<MainThread.fileStr.length;i++) {
				if(MainThread.fileStr[th].equals(Parameters.diskName[Parameters.diskNameNum][i])){
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

	/**
	 * calculate the energy of one sensor.
	 * @param a
	 * @return Energy of a wave from one sensor.
	 * @throws MWException
	 * @author Hanlin Zhang.
	 */
	public static double Energy_MATLAB(Vector<String> a) throws MWException {
		String inte[];
		int channel=5;
		
		for (int i = 0; i < a.size(); i++){
			inte = a.get(i).split(" ");
			if(Parameters.TongDaoDiagnose==1) {//若有6个通道，判断才有意义
				if (SelectChannel.testValue(inte[4]) || SelectChannel.testValue(inte[3])){
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
		int channel=5;
		
		for (int i = 0; i < a.size(); i++){
			inte = a.get(i).split(" ");
			if(Parameters.TongDaoDiagnose==1) {//若有6个通道，判断才有意义
				if (SelectChannel.testValue(inte[4]) || SelectChannel.testValue(inte[3])){
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
		
		s.setx(Double.parseDouble(result[0].toString()));
		s.sety(Double.parseDouble(result[1].toString()));
		s.setz(Double.parseDouble(result[2].toString()));
		s.setSecTime(Double.parseDouble(result[3].toString()));
		
		return s;
	}
	
	
//	时差定位算法
	public static Sensor timelocate(int c,double[][] stationcoordinates,double[][] coordinate) {
		Time_Locate tLocate = null;
		double [] results1=new double[4];
		Sensor s = new Sensor();	
		try {
			tLocate = new Time_Locate();
		} catch (MWException e) {
			e.printStackTrace();
		}
		
		try {
			Object station1 = Data2Object_MATLAB.array2Object(stationcoordinates);
			Object coor1 = Data2Object_MATLAB.array2Object(coordinate);
			Object c1 = Data2Object_MATLAB.num2Object(Parameters.C);
			Object[] results=tLocate.timelocation(4, c1, station1, coor1);
			results1[0]=Double.parseDouble(results[0].toString());
			results1[1]=Double.parseDouble(results[1].toString());
			results1[2]=Double.parseDouble(results[2].toString());
			results1[3]=Double.parseDouble(results[3].toString());
			s.setx(results1[0]);
			s.sety(results1[1]);
			s.setz(results1[2]);
			s.setSecTime(results1[3]);
		} catch (MWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;		
	}

	
}
