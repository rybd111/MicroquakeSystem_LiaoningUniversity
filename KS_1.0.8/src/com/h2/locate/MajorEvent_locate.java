package com.h2.locate;

import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.db.DbExcute;
import com.h2.backupData.WriteRecords;
import com.h2.constant.Parameters;
import com.h2.constant.Sensor;
import com.h2.thread.ThreadStep3;
import com.h2.tool.Doublelocate;
import com.h2.tool.Location2;
import com.h2.tool.Majorlocate;
import com.h2.tool.calDuringTimePar;
import com.mathworks.toolbox.javabuilder.MWException;

import bean.QuackResults;
import utils.StringToDateTime;
import utils.TimeDifferent;
import utils.one_dim_array_max_min;

public class MajorEvent_locate {
	/**
	 * 
	 * @param sensors save the sensor's status.
	 * @param aQuackResults save the information for inserting into the database.
	 * @param sensorThread3 calculate the quack grade.
	 * @param sensorData 30s data
	 * @param l the motivated sensors number array
	 * @param sensor_latest the first sensor's P arrival time equals not 0.
	 * @param aDbExcute execute the database operating steps.
	 * @return the information of space-time strength. 
	 * @throws ParseException
	 * @throws IOException
	 * @author Baishuo Han, Hanlin Zhang.
	 * @throws MWException 
	 */
	@SuppressWarnings("unused")
	public static String major(Sensor[] sensors, QuackResults aQuackResults, ThreadStep3[] sensorThread3,
			DbExcute aDbExcute) throws ParseException, IOException, MWException {
		
		String outString=" ";
		//Take the top 5 to calculate the quake location and quake magnitude, it may need to optimize later.
		Sensor[] sensors1 = new Sensor[4];
		double [] PArrival = new double[sensors1.length];
		
		for(int i = 0; i < 4; i++) {
			sensors1[i]=sensors[i];
			PArrival[i] = sensors1[i].getSecTime();
		}
		
		//calculate the coordinations of the quake source, location variable only store the quake time, not store the motivation time, and store the coordinations of the quake happening.
		Sensor location_refine = Majorlocate.majorLocate(PArrival);//calculate the quake time in milliseconds.
		
		//we will calculate the quake time through this function.
		location_refine.setSecTime(Doublelocate.quakeTime(sensors1[0], location_refine));
		
		//we calculate the real quake time by reduce the first receiving quake signal sensor and the quake time in second.
		String intequackTime = TimeDifferent.TimeDistance(sensors[0].getAbsoluteTime(), location_refine.getSecTime()); //the time of refine quake time;
		
		//compute the quake time and quake coordination, the two sensors' locate is also adapt to the three location.
		location_refine.setquackTime(intequackTime);
		
		//Compute the near quake magnitude which data only must from the motivated sensors we selected, so the sensors1 is used.
		ExecutorService executor_cal = Executors.newFixedThreadPool(Parameters.SensorNum);
		final CountDownLatch threadSignal_cal = new CountDownLatch(sensors1.length);
		//calculate the near earthquake magnitude et al.
		for(int i=0;i<sensors1.length;i++) {
			sensorThread3[i] = new ThreadStep3(sensors1[i], location_refine, i, threadSignal_cal);
			executor_cal.execute(sensorThread3[i]);//计算单个传感器的近震震级
		}
		try {threadSignal_cal.await();}
        catch (InterruptedException e1) {e1.printStackTrace();}
		
//		for(int i=0;i<sensors1.length;i++) {
//			sensorThread3[i] = new ThreadStep3(sensors1[i], location_refine, i, threadSignal_cal);
//			sensorThread3[i].cal();//计算单个传感器的近震震级
//		}
//		if(Parameters.isStorageEachMotivationCSV==1) {
//			writeToDisk.writeToCSV(sensors1, 5, location_refine.getquackTime(), sensors1[0].getpanfu()+" ");
//		}
		
		//We integrate every sensors quake magnitude to compute the last quake magnitude.
		float earthQuakeFinal = 0;
		for (Sensor sen : sensors1)	earthQuakeFinal += sen.getEarthClassFinal();
		earthQuakeFinal /= 5;//For this method is only support 5 sensors, so we must divide 5 to calculate the last quake magnitude.
		if(Parameters.MinusAFixedOnMagtitude==true)
			earthQuakeFinal = (float) (earthQuakeFinal-Parameters.MinusAFixedValue);// We discuss the consequen to minus 0.7 to reduce the final quake magnitude at datong coal mine.
		
		//We compute the minimum energy of all sensors as the final energy.
		double finalEnergy = 0.0;double []energy = new double[sensors1.length];
		int finalClass = 0;int [] class1 = new int[sensors1.length];
		for (int i=0;i<sensors1.length;i++) {
			energy[i] = sensors1[i].getEnergy();
			class1[i] = sensors1[i].getClass1();
		}
		finalEnergy = one_dim_array_max_min.mindouble(energy);
		finalClass = one_dim_array_max_min.getMethod_4(class1);
		
//		System.out.println("该事件的分类为："+finalClass);
		
		//calculate the during grade with 5 sensors.
//		float duringEarthQuake = calDuringTimePar.computeDuringQuakeGrade(sensors1,4);
		
		//we will set 0 when the consequence appears NAN value.
		String quakeString = (Float.compare(Float.NaN, earthQuakeFinal) == 0) ? "0"	: String.format("%.2f", earthQuakeFinal);//修改震级，保留两位小数
//		double quakeStringDuring = (Float.compare(Float.NaN, duringEarthQuake) == 0) ? 0:  duringEarthQuake;//修改震级，保留两位小数
		String result = location_refine.toString()+" "+quakeString+" "+finalEnergy+" "+location_refine.getquackTime();//坐标+时间+震级
		
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		
		//we also record the quake location in a '.txt' file, for calculating manually.
		if(Parameters.isStorageEventRecord==1) {
			int dif = TimeDifferent.DateDifferent(intequackTime, WriteRecords.lastDate);
			//cut the quack time to the day.
			String dateInFileName = intequackTime.substring(0, 10);
			//If the difference between the current calculated time and the last time is more than 1 day, the storage file is changed to a new.
			if(dif>=1) {
				WriteRecords.Write(sensors1,sensors[0],location_refine,Parameters.AbsolutePath5_record+dateInFileName+"_QuakeRecords.csv",quakeString, finalEnergy, "主事件");
				WriteRecords.insertALine(Parameters.AbsolutePath5_record+dateInFileName+"_QuakeRecords.csv");
			}
			else {
				WriteRecords.Write(sensors1,sensors[0],location_refine,Parameters.AbsolutePath5_record+dateInFileName+"_QuakeRecords.csv",quakeString, finalEnergy, "主事件");
				WriteRecords.insertALine(Parameters.AbsolutePath5_record+dateInFileName+"_QuakeRecords.csv");
			}
		}
		
		aQuackResults.setxData(Double.parseDouble(nf.format(location_refine.getLatitude())));
		aQuackResults.setyData(Double.parseDouble(nf.format(location_refine.getLongtitude())));
		aQuackResults.setzData(Double.parseDouble(nf.format(location_refine.getAltitude())));
		aQuackResults.setQuackTime(location_refine.getquackTime());
		aQuackResults.setQuackGrade(Double.parseDouble(quakeString));//近震震级
		aQuackResults.setDuringGrade(0);//持续时间震级
		aQuackResults.setParrival(location_refine.getSecTime());//P波到时，精确到毫秒
		aQuackResults.setPanfu(sensors1[0].getpanfu());//盘符
		aQuackResults.setNengliang(finalEnergy);//能量，待解决
		aQuackResults.setFilename_S(sensors1[0].getFilename());//文件名，当前第一个台站的文件名，其他台站需要进一步改变第一个字符为其他台站，则为其他台站的文件名。
		aQuackResults.setTensor(0);//矩张量
		aQuackResults.setKind("major");
		
		//output the five locate consequence.
		System.out.println("主事件："+aQuackResults.toString());//在控制台输出结果
		
		//diagnose is not open the function of storing into the database.
		if(Parameters.isStorageDatabase == 1) {
			try {
//				aDbExcute.addElement(aQuackResults);
			} catch (Exception e) {
				System.out.println("add to database error");
			}
		}
		outString = result;
		return outString;
	}
}
