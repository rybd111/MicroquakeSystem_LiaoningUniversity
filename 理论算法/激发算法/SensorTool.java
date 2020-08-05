/**
 * @author 韩百硕
 */
package com.h2.tool;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.h2.constant.Parameters;
import com.h2.constant.Sensor;

import mutiThread.MainThread;

/**
 * when we initial the sensor, and diagnose the morivated position, then we will use this class.
 * @author 韩百硕, Hanlin Zhang.
 */
public class SensorTool
{
	/**
	 * 初始化传感器信息
	 * 
	 * @param count
	 *            传感器的数量
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Sensor[] initSensorInfo(int count,String [] Str)
	{
		Sensor[] sensors = new Sensor[count];
		//a sequence of correspond places.
		int [] k = new int [Parameters.SensorNum];
		if(Parameters.offline==false) {
			for(int j=0;j<Str.length;j++){
				for(int i=0;i<Parameters.diskName.length;i++)
					if(Str[j] .equals(Parameters.diskName[i])) k[j]=i;
			}
			
			for (int i = 0; i < count; i++)
			{
				sensors[i]=new Sensor();
				(sensors[i]).setLatitude(Parameters.SENSORINFO[k[i]][0]);
				(sensors[i]).setLongtitude(Parameters.SENSORINFO[k[i]][1]);
				(sensors[i]).setAltitude(Parameters.SENSORINFO[k[i]][2]);
			}
			return sensors;
		}else {
			for(int j=0;j<Str.length;j++){
				for(int i=0;i<Parameters.diskName_offline.length;i++)
					if(Str[j].substring(Str[j].length()-2, Str[j].length()-1).equals(Parameters.diskName_offline[i]))
						k[j]=i;
			}
			if(Parameters.region_offline=="hongyang") {
				for (int i = 0; i < count; i++)
				{
					sensors[i]=new Sensor();
					//(sensors[i]).setBackupFile(Parameters.EARTHDATAFILE[k[i]]);
					(sensors[i]).setLatitude(Parameters.SENSORINFO_offline_hongyang[k[i]][0]);
					(sensors[i]).setLongtitude(Parameters.SENSORINFO_offline_hongyang[k[i]][1]);
					(sensors[i]).setAltitude(Parameters.SENSORINFO_offline_hongyang[k[i]][2]);
				}
			}
			if(Parameters.region_offline=="datong") {
				for (int i = 0; i < count; i++)
				{
					sensors[i]=new Sensor();
					//(sensors[i]).setBackupFile(Parameters.EARTHDATAFILE[k[i]]);
					(sensors[i]).setLatitude(Parameters.SENSORINFO_offline_datong[k[i]][0]);
					(sensors[i]).setLongtitude(Parameters.SENSORINFO_offline_datong[k[i]][1]);
					(sensors[i]).setAltitude(Parameters.SENSORINFO_offline_datong[k[i]][2]);
				}
			}
			if(Parameters.region_offline=="pingdingshan") {
				for (int i = 0; i < count; i++)
				{
					sensors[i]=new Sensor();
					//(sensors[i]).setBackupFile(Parameters.EARTHDATAFILE[k[i]]);
					(sensors[i]).setLatitude(Parameters.SENSORINFO_offline_pingdingshan[k[i]][0]);
					(sensors[i]).setLongtitude(Parameters.SENSORINFO_offline_pingdingshan[k[i]][1]);
					(sensors[i]).setAltitude(Parameters.SENSORINFO_offline_pingdingshan[k[i]][2]);
				}
			}
			return sensors;
		}
		
	}

	/**
	 * 在这10s内激发的传感器,并设置激发传感器的标识和激发时间
	 * 
	 * @param data
	 * @param sensor
	 * @return
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	private static boolean tunnel=false;
	@SuppressWarnings("unused")
	public static boolean motivate(Vector<String> data, Sensor sensor,int th) throws ParseException
	{
		int lineSeries = 0;
		boolean flag=false;
		
		//the hop number is 100, i starts from the first data of the first sliding window to the first data of the last sliding window.
		for(int i=0;i<data.size()-Parameters.N-Parameters.refineRange;i+=Parameters.INTERVAL)//滑动窗口跳数可以任意设置，但小于50时效率极低，i为窗口的第一条数据开始位置，到最后一个窗口
		{
			if(!sensor.isSign())
			{
				lineSeries=getToken(data, i);//激发位置，滑动距离为Parameters.INTERVAL个点，并判断使用哪三个通道
				
				//if there has a sensor has motivated, it perhaps the last windows.
				if(lineSeries!=0)
				{
					if(Parameters.motivationDiagnose==1) {
						flag=getAverage(data,lineSeries,th);
						if(flag==true) {
							//set the flag signal.
							sensor.setSign(true);
							
							//there set the position(series) in now vector, it means the relative position in 10s vector.
							sensor.setlineSeries(lineSeries);
							System.out.println("激发位置："+"  "+sensor.getlineSeries());
							//The unit is in milliseconds, the frequency of sensor is calculated in 5000Hz.
							sensor.setSecTime(Double.valueOf(lineSeries)/Double.valueOf((Parameters.FREQUENCY+200)));
							
							//Set the absolute time in GPS time.
							sensor.setAbsoluteTime(relativeStatus.PArrivalTime(data, sensor,th));
							
							//we obtain the time of the first time of the now vector.
							sensor.setTime(data.get(lineSeries).split(" ")[6]);
						}
					}
					else {
						//set the flag signal.
						sensor.setSign(true);
						
						//there set the position(series) in now vector, it means the relative position in 10s vector.
						sensor.setlineSeries(lineSeries);
						
						//The unit is in milliseconds, the frequency of sensor is calculated in 5000Hz.
						sensor.setSecTime(Double.valueOf(lineSeries)/Double.valueOf((Parameters.FREQUENCY+200)));
						
						//Set the absolute time in GPS time.
						sensor.setAbsoluteTime(relativeStatus.PArrivalTime(data, sensor,th));
						
						//we obtain the time of the first time of the now vector.
						sensor.setTime(data.get(lineSeries).split(" ")[6]);
					}
				}
				
				//if the 456 chunnel is overflow, then we select the 123 chunnel.
				if(tunnel){
					CrestorTrough temcre=new CrestorTrough(Double.parseDouble(data.get(lineSeries).split(" ")[0]),
														   Double.parseDouble(data.get(lineSeries).split(" ")[1]),
							                               Double.parseDouble(data.get(lineSeries).split(" ")[2]));
					sensor.setCrestortrough(temcre);
				}else{
					CrestorTrough temcre=new CrestorTrough(Double.parseDouble(data.get(lineSeries).split(" ")[3]),
							                               Double.parseDouble(data.get(lineSeries).split(" ")[4]),
                                                           Double.parseDouble(data.get(lineSeries).split(" ")[5]));
					sensor.setCrestortrough(temcre);
				}
			}
		}
		
		tunnel=false;
		return sensor.isSign() ? true : false;
	}

	/**
	 * 确定一个传感器在60ms内是否被激发 思想：将一个时窗内的数据(600条数据)全部读取到vector中，
	 * 并在读取的时候判断是否有数据超出阈值，跟据读取的结果给channel赋值。
	 * @param sensor 传感器
	 * @param number 每隔一定点个数就进行一次长短时窗的判断
	 * @return 激发的位置
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	@SuppressWarnings("unused")
	public static int getToken(Vector<String> data, int number)
	{

		int line=0;
		int[] iStr = new int[6]; // the value of slicing position.
		boolean channel = false;//determine which chunnel we select, true is the 123 chunnel, or 456 chunnel.
		int inte = -1;//the average value.
		String s;
		
		Vector<String> container = new Vector<String>();//the current data in window. 

		int count = 0;//loop variable.
		
		//some variable in this function.
		long sumLong = 0;
		long sumShort = 0;
		double aveLong = 0;
		double aveShort = 0;
		
		//the first number of the first data.
		int lineNumber = number;
		
		container.clear();
		
		// 读取本时窗的数据，拆分data容器的内容，保存到container容器中，并同时判断每个通道是否超出最大阈值，若超出，则启用另3个通道
		while (count < Parameters.N)//the all data in total window among short and long.
		{
			s = data.get(lineNumber + count);
			String[] str = s.split(" ");
			
			for (int i = 3; i < 6; i++)// 共7列数据，最后一列为时间
				iStr[i] = Integer.parseInt(str[i]);
			
			container.add(s);
			
			count++;
		}
		
		//get the average value of the long window.
		if (channel){//read the first three rows.
			for (int i = 0; i < Parameters.N1; i++){
				String[] str = container.get(i).split(" ");
				inte = Math.abs(Integer.parseInt(str[2]));
				sumLong += inte;
			}
		} 
		else{
			for (int i = 0; i < Parameters.N1; i++){
				String[] str = container.get(i).split(" ");
				inte = Math.abs(Integer.parseInt(str[5]));
				sumLong += inte;
			}
		}
		aveLong = (double) sumLong / Parameters.N1;

		//get the average value of the short window.
		if (channel){//as the same of the before situation.
			for (int i = Parameters.N1; i < Parameters.N; i++){//长时窗最后一个点到短时窗最后一个点
				String[] str = container.get(i).split(" ");
				inte = Math.abs(Integer.parseInt(str[2]));
				sumShort += inte;
			}
		} 
		else {
			for (int i = Parameters.N1; i < Parameters.N; i++){//长时窗最后一个点到短时窗最后一个点
				String[] str = container.get(i).split(" ");
				inte = Math.abs(Integer.parseInt(str[5]));
				sumShort += inte;
			}
		}
		
		aveShort = (double) sumShort / Parameters.N2;
		if(Parameters.Adjust==false){
		//短长视窗比值大于2.5，并且振幅绝对值大于500时，判定为激发 
			if((aveShort / aveLong) >= Parameters.ShortCompareLong){
				if(channel){
					if(Math.abs(Integer.parseInt(container.get(Parameters.N-Parameters.N2).split(" ")[2])) > Parameters.ThresholdZ){
						line=lineNumber+Parameters.N1;
						return line;
					}
					else 
						return line;
				}
				else{
					if(Math.abs(Integer.parseInt(container.get(Parameters.N-Parameters.N2).split(" ")[5])) > Parameters.ThresholdZ){
						line=lineNumber+Parameters.N-Parameters.N2;//记录最后一个激发时窗在now容器中的位置
						return line;
					}
					else 
						return line;
				}
			}
			else return line;
		}
		else{
			if((aveShort / aveLong) >= Parameters.ShortCompareLongAdjust){
				if(channel){
					line=lineNumber+Parameters.N1;
					return line;
				}
				else{
					line=lineNumber+Parameters.N-Parameters.N2;//记录最后一个激发时窗在now容器中的位置
					return line;
				}
			}
			else return line;
		}
	}

	
	/**
	 * experiment function used to calculate the area of 500 points after motivation position
	 * @param lineSeries  the absolutely position in now vector. 
	 * @author Hanlin Zhang.
	 */
	@SuppressWarnings("unused")
	public static boolean getAverage(Vector<String> data, int lineSeries,int th) {
		double maxA = 0.0;
		double sumA = 0.0;
		double average = 0.0;

		for(int i=0;i<Parameters.afterRange;i++) {//the scope of the diagnosing.
			average+=Math.abs(Integer.parseInt(data.get(i+lineSeries).split(" ")[5]));
		}
		average = average/Parameters.afterRange;
		if(average>=Parameters.afterRange_Threshold456) {
			if(Parameters.offline==true) {
//				System.out.println(MainThread.fileParentPackage[th]+"在"+Parameters.afterRange/(Parameters.FREQUENCY+200)+"秒内的平均振幅为："+average);
				// we will find the next condition.
				for(int i=0;i<Parameters.refineRange;i++) {
					sumA+=Math.abs(Integer.parseInt(data.get(i+lineSeries).split(" ")[5]));
				}
				sumA = sumA/Parameters.refineRange;
				if(sumA>=Parameters.refineRange_Threshold456) {
					System.out.println(MainThread.fileParentPackage[th]+"在"+Parameters.afterRange/(Parameters.FREQUENCY+200)+"秒内的平均振幅为_"+sumA);
					return true;
				}
				else
					return false;
			}
			else {
//				System.out.println(MainThread.fileStr[th]+"在"+Parameters.afterRange_Threshold456/(Parameters.FREQUENCY+200)+"秒内的平均振幅为："+average);
				// we will find the next condition.
				for(int i=0;i<Parameters.refineRange;i++) {
					sumA+=Math.abs(Integer.parseInt(data.get(i+lineSeries).split(" ")[5]));
				}
				sumA = sumA/Parameters.refineRange;
				if(sumA>=Parameters.refineRange_Threshold456) {
					System.out.println(MainThread.fileStr[th]+"在"+Parameters.afterRange/(Parameters.FREQUENCY+200)+"秒内的平均振幅为_"+sumA);
					return true;
				}
				else
					return false;
			}
		}
		else {
			return false;
		}
	}
}
