package com.h2.locate;

import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.db.DbExcute;
import com.h2.backupData.WriteRecords;
import com.h2.backupData.writeToDisk;
import com.h2.constant.Parameters;
import com.h2.constant.Sensor;
import com.h2.main.EarthQuake;
import com.h2.thread.ThreadStep3;
import com.h2.tool.Doublelocate;
import com.h2.tool.QuakeClass;
import com.h2.tool.Triplelocate;
import com.h2.tool.calDuringTimePar;
import com.h2.tool.stringJoin;
import com.mathworks.toolbox.javabuilder.MWException;

import bean.QuackResults;
import mutiThread.MainThread;
import utils.StringToDateTime;
import utils.Tensor;
import utils.TimeDifferent;
import utils.one_dim_array_max_min;

public class PSO_Locate {
	/**
	 * @param sensors save the sensor's status.
	 * @param aQuackResults save the information for inserting into the database.
	 * @param sensorThread3 calculate the quack grade.
	 * @param sensorData 30s data
	 * @param l the motivated sensors number array
	 * @param sensor_latest the first sensor's P arrival time equals not 0.
	 * @param aDbExcute execute the database operating steps.
	 * @param countNumber the number of motivation sensors.
	 * @return the information of space-time strength. 
	 * @throws ParseException
	 * @throws IOException
	 * @author Baishuo Han, Hanlin Zhang.
	 * @throws MWException 
	 */
	@SuppressWarnings("unused")
	public static String pso(Sensor[] allsensors, Sensor[] sensors, QuackResults aQuackResults, ThreadStep3[] sensorThread3,
			DbExcute aDbExcute, int countNumber) throws ParseException, IOException, MWException {
		
		String outString=" ";
		
		//the number of sensors satisfy the condition is greater than 3, set the restrain to false.
		if(countNumber>=3){
			//Take the top 3 to calculate the quake location and quake magnitude, it may need to optimize later.
			Sensor[] sensors1 = new Sensor[countNumber];
			for(int i = 0; i < countNumber; i++) {
				sensors1[i]=sensors[i];
			}
			
	 		//calculate the two dimensional matrix.
	 		double coor[][] = new double[sensors1.length][4];
	 		for(int i=0;i<sensors1.length;i++) {
 				coor[i][0]=sensors1[i].getLatitude();
 				coor[i][1]=sensors1[i].getLongtitude();
 				coor[i][2]=sensors1[i].getAltitude();
 				coor[i][3]=sensors1[i].getSecTime();
	 		}
	 		
			//compute the quake coordination.
			Sensor location_refine = new Sensor();
			location_refine = QuakeClass.PSO(coor);
			
			String intequackTime = TimeDifferent.TimeDistance(sensors[0].getAbsoluteTime(), location_refine.getSecTime()); //the time of refine quake time;
			
			//compute the quake time and quake coordination, the two sensors' locate is also adapt to the three location.
			location_refine.setquackTime(intequackTime);
			
			//the locate process probably return a NAN value or a INF value, so when this situation appears, the procedure will skip the current circulation.
			if(Double.isNaN(location_refine.getLatitude())==false && Double.isInfinite(location_refine.getLatitude()) == false){
				
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
				
				//We integrate every sensors quake magnitude to compute the last quake magnitude.
				float earthQuakeFinal = 0;
				for (Sensor sen : sensors1)	earthQuakeFinal += sen.getEarthClassFinal();
				earthQuakeFinal /= countNumber;//For this method is only support 5 sensors, so we must divide 5 to calculate the last quake magnitude.
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
				
				//矩张量计算
				Tensor tensors=new Tensor();
				Object b=tensors.moment_tensor(allsensors, sensors1, location_refine);
				double c=Double.parseDouble(b.toString());
				
//				System.out.println("该事件的分类为："+finalClass);
				
				//calculate the during grade with 3 sensors.
//				float duringEarthQuake = calDuringTimePar.computeDuringQuakeGrade(sensors1,3);
				
				//we will set 0 when the consequence appears NAN value.
				String quakeString = (Float.compare(Float.NaN, earthQuakeFinal) == 0) ? "0"	: String.format("%.2f", earthQuakeFinal);//修改震级，保留两位小数
//				double quakeStringDuring = (Float.compare(Float.NaN, duringEarthQuake) == 0) ? 0:  duringEarthQuake;//修改震级，保留两位小数
				String result = location_refine.toString()+" "+quakeString+" "+finalEnergy+" "+location_refine.getquackTime();//坐标+时间+震级
				
				//将各double坐标转换成数据库组形式
				java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
				nf.setGroupingUsed(false);
				
				//we also record the quake location in a '.csv' file for manually computation.
				if(Parameters.isStorageEventRecord==1) {
					int dif = TimeDifferent.DateDifferent(intequackTime, WriteRecords.lastDate);
					//cut the quack time to the day.
					String dateInFileName = intequackTime.substring(0, 10);
					//If the difference between the current calculated time and the last time is more than 1 day, the storage file is changed to a new.
					if(dif>=1) {
						WriteRecords.Write(sensors1,sensors[0],location_refine,Parameters.AbsolutePath5_record+dateInFileName+"_QuakeRecords.csv", quakeString, finalEnergy, "粒子群");
//						if(countNumber==3) {
//							WriteRecords.insertALine(Parameters.AbsolutePath5_record+dateInFileName+"_QuakeRecords.csv");
//						}
					}
					else {
						WriteRecords.Write(sensors1,sensors[0],location_refine,Parameters.AbsolutePath5_record+dateInFileName+"_QuakeRecords.csv", quakeString, finalEnergy, "粒子群");
//						if(countNumber==3) {
//							WriteRecords.insertALine(Parameters.AbsolutePath5_record+dateInFileName+"_QuakeRecords.csv");
//						}
					}
				}
				
				aQuackResults.setxData(Double.parseDouble(nf.format(location_refine.getLatitude())));
				aQuackResults.setyData(Double.parseDouble(nf.format(location_refine.getLongtitude())));
				aQuackResults.setzData(Double.parseDouble(nf.format(location_refine.getAltitude())));
				aQuackResults.setQuackTime(location_refine.getquackTime());
				aQuackResults.setQuackGrade(Double.parseDouble(quakeString));
				aQuackResults.setParrival(location_refine.getSecTime());//P波到时，精确到毫秒
				aQuackResults.setPanfu(sensors1[0].getpanfu());
				aQuackResults.setDuringGrade(0);//持续时间震级
				aQuackResults.setNengliang(finalEnergy);//能量，待解决
				aQuackResults.setFilename_S(sensors1[0].getFilename());
				aQuackResults.setTensor(c);//矩张量
				aQuackResults.setKind("PSO");

				//output the three locate consequence.
				System.out.println("粒子群："+aQuackResults.toString());//在控制台输出结果
				
				//diagnose is not open the function of storing into the database.
				if(Parameters.isStorageDatabase ==1) {
					try {
						aDbExcute.addElement(aQuackResults);
					} catch (Exception e) {
						System.out.println("add to database error");
					}
				}
				outString = result;
				return outString;
			}
			return outString;
		}
		return outString;
	}
}
