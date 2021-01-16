
package com.h2.main;

import java.util.Vector;
import bean.QuackResults;
import mutiThread.MainThread;

import com.db.DbExcute;
import com.h2.backupData.WriteRecords;
import com.h2.backupData.writeToDisk;
import com.h2.constant.Parameters;
import com.h2.constant.Sensor;
import com.h2.locate.Five_Locate;
import com.h2.locate.MajorEvent_locate;
import com.h2.locate.PSO_Locate;
import com.h2.locate.Three_Locate;
import com.h2.strength.calStrength;
import com.h2.tool.SensorTool;
import com.h2.tool.relativeStatus;

/**
 * @revision 2019-12-3
 * We revise this function on the locating of three sensors and five sensors, which is packaged to a function to manage convenient.
 * And we also delete some variables to simplify the function such as threadstep1~threadstep3.
 * @author Baishuo Han, Hanlin Zhang, RQMa, Yali Hao, Ze Chen, et al.
 */
public class EarthQuake {
	/**
	 * @param ssen all sensors' data in three vectors.
	 * @return the " " or the consequence of computation, " " indicates there are no sensors are inspired or the number of data is not enough.
	 */
	
	/**Return computation results String.*/
	public static String outString = "";
	
	/**Used to execute the sql of database.*/
	static DbExcute aDbExcute = new DbExcute();

	/**store the computation consequence of every location methods.*/
	static QuackResults aQuackResults=new QuackResults();
	
	/**indicate the motivation is not a real valid motivation, if this is a real motivation it will become true, or it will be set to false.*/
	public static boolean realMoti = true;
	
	/**
	 * calculate the space-time strength, and save the motivation information of all motivation sensors, save the information of motivation of three or five motivated sensors.
	 * @param ssen
	 * @return the information of space-time strength.
	 * @throws Exception
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	public static String runmain(Vector<String> ssen[][])	throws Exception {
		
		//the number of data must enough to calculate which satisfied to 10s, or it will appear mistake consequence for the current data.
		for (Vector<String>[] vectors : ssen) {
			for (Vector<String> vector : vectors) {
				if (vector.size() < Parameters.FREQUENCY * Parameters.readLen)	return " ";//this function must be return a String to foreground.
			}
		}
		
		//We must initialize the Sensor object, when the procedure first use.
		Sensor[] sensors = SensorTool.initSensorInfo(Parameters.SensorNum,MainThread.fileStr);
		
		// sensorData[2][1] indicate the third sensor's second vector.
		Vector<String> sensorData[][] = ssen;
		calStrength[] sensorThread3 = new calStrength[Parameters.SensorNum];
		
		Vector<String> judgeMotiData = new Vector<String>();
		
		//Set every sensor's motivation flag to indicate the sensor is inspired or not.
		for (int i = 0; i < Parameters.SensorNum; i++) {
			//add extra data used to judge sensor's motivation status.
			for(int k=sensorData[i][0].size()-(Parameters.refineRange*2);k<sensorData[i][0].size();k++)
				judgeMotiData.addElement(sensorData[i][0].get(k));
			
			judgeMotiData.addAll(sensorData[i][1]);
			
//			for(int k=0;k<(Parameters.FREQUENCY+200)*3;k++) {
//				judgeMotiData.addElement(sensorData[i][2].get(k));
//			}
//			String jing = judgeMotiData.get(0).split(" ")[6];
//			if(jing.equals("2020-07-2815:27:45")) {
//				System.out.println();
//			}
			//experience.
//			sensors[i].setTenVectorData(judgeMotiData);
			//写入txt
//			if(i==5) {
//				String temp = judgeMotiData.get(i).split(" ")[6].replaceAll("-", "");
//				temp = temp.replaceAll(":", "");
//				writeToDisk.write_array("E:/"+temp+".txt", judgeMotiData);
//			}
			SensorTool.motivate(judgeMotiData, sensors[i],i);//存储了激发时间和激发的标志位
			judgeMotiData.clear();
		}
		
		//we will diagnose there has a motivational sensor or not to reduce the complexity.
		Sensor sensor_latest = new Sensor();
		for(int i=0;i<sensors.length;i++) {
			if(sensors[i].getSecTime()!=0)
				sensor_latest = sensors[i];
		}
		
		if(sensor_latest!=null) {
			//calculate the number of motivation sensors, and save the series to the l array.
			//Meanwhile, we will move the position to the absolute position in 30 seconds, which is point to the position in the now Vector. It's used to cut the motiData and used to calculate during quake magnitude.
			int countNumber = 0;
			int[] l = new int[Parameters.SensorNum];
			int[] l1 = new int[Parameters.SensorNum];
			for (int i=0;i<Parameters.SensorNum;i++){
				if (sensors[i].isSign()) {
					//the series of correspond with the name of path, which stow the motivated sensor.
					sensors[i].setSensorNum(i);
					if(Parameters.offline==false) {
						for(int j=0;j<Parameters.diskName.length;j++) {
							if(MainThread.fileStr[i].equals(Parameters.diskName[j])) {
								if(Parameters.initPanfu[j]==0) {
									l[i]=i+1;//record the number of motivated sensors.
									l1[countNumber]=i;
									countNumber++;
									sensors[i].setlineSeries(sensors[i].getlineSeries()+sensorData[i][0].size());
									System.out.println("激发台站"+MainThread.fileStr[i]+"激发位置"+sensors[i].getlineSeries());
									Parameters.initPanfu[j]=1;
								}
							}
						}
					}
					else {
						for(int j=0;j<Parameters.diskName_offline[Parameters.diskNameNum].length;j++) {
							if(MainThread.fileParentPackage[i].replace("Test", "").equals(Parameters.diskName_offline[Parameters.diskNameNum][j])) {
//							if(MainThread.fileParentPackage[i].equals(Parameters.diskName_offline[Parameters.diskNameNum][j])) {
								if(Parameters.initPanfu[j]==0) {
									l[i]=i+1;//record the number of motivated sensors.
									l1[countNumber]=i;
									countNumber++;
									sensors[i].setlineSeries(sensors[i].getlineSeries()+sensorData[i][0].size());
									System.out.println("激发台站"+MainThread.fileStr[i]+"激发位置"+sensors[i].getlineSeries());
									Parameters.initPanfu[j]=1;
								}
							}
						}
					}
				}
			}
			//Reset the global variable.
			for(int i=0;i<Parameters.initPanfu.length;i++)
				Parameters.initPanfu[i]=0;
			
			Sensor[] S = new Sensor[sensors.length];
			statusOfCompute status = new statusOfCompute();
			
			if(countNumber>2) {
				//stow the info of all sensors.
				S = relativeStatus.stowInfoSensor(l, l1, countNumber, sensors, ssen, status);
			}
			
			/**
			 * panfu is the combination of motivated and unmotivated sensor split with " ".
			 * countNumber is the number of motivation sensor.
			 * S is the array of the Sensor class's object.
			 * THIS FUNTION IS TO WIRTE THE MOTIVATION DATA ON THE DISK.
			 */
			if(countNumber>2 && Parameters.isStorageAllMotivationCSV==1 && EarthQuake.realMoti==true) {
				writeToDisk.saveAllMotivationSensors(countNumber, S, status.getPanfu());
			}
			
			if(countNumber >= 5 && EarthQuake.realMoti==true) {
				//write to a txt file to indicate the motivation time of each sensor.
				WriteRecords.WriteSeveralMotiTime(status.getSensors1(), Parameters.AbsolutePath_allMotiTime_record);
				//write to datacenter.
				WriteRecords.WriteSeveralMotiTime(status.getSensors1(), Parameters.AbsolutePath_allMotiTime_record_dataCenter);
			}
			
			//if countNumber>=5, the procedure start calculating the earthquake magnitude and the location of quake happening.
			if(countNumber >= 5 && EarthQuake.realMoti==true) {
				Five_Locate.five(sensors, status.getSensors1(), aQuackResults, sensorThread3, aDbExcute, countNumber);aQuackResults=new QuackResults(); aDbExcute = new DbExcute();
			}
			
			//if the number of motivated sensors is greater than 3, we will calculate three location.
			if(countNumber>=3 && EarthQuake.realMoti==true){
				outString = Three_Locate.three(sensors, status.getSensors1(), aQuackResults, sensorThread3, aDbExcute, countNumber);aQuackResults=new QuackResults(); aDbExcute = new DbExcute();
				PSO_Locate.pso(sensors, status.getSensors1(), aQuackResults, sensorThread3, aDbExcute, countNumber);aQuackResults=new QuackResults(); aDbExcute = new DbExcute();
			}
			
			//if the number of motivated sensors is greater than 4, we will calculate four location-main event location.
			if(countNumber>=4 && EarthQuake.realMoti==true) {
				//outString = MajorEvent_locate.major(sensors1, aQuackResults, sensorThread3, aDbExcute);
				MajorEvent_locate.major(sensors, status.getSensors1(), aQuackResults, sensorThread3, aDbExcute);aQuackResults=new QuackResults(); aDbExcute = new DbExcute();
			}
			
			//calculate quake grade.
			
			//we can hide this print when the console are so much content or display this print when we want to adjust the procedure.
//			if(countNumber>=3) {
//				output the reasons why the calculation process is not executing.
				System.out.println("激发个数："+countNumber+" 台站间的激发间隔时间是否小于"+Parameters.IntervalToOtherSensors+"?"+
						EarthQuake.realMoti+" "+	sensorData[0][0].get(0).split(" ")[6]);
//			}
		}
		
		//reset the global variable.
		EarthQuake.realMoti=true;
		return outString;
	}
}
