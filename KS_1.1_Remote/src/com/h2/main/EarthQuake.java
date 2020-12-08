
package com.h2.main;

import java.util.Vector;
import mutiThread.MainThread;

import com.h2.backupData.WriteRecords;
import com.h2.backupData.writeToDisk;
import com.h2.constant.Parameters;
import com.h2.constant.Sensor;
import com.h2.thread.ThreadStep3;
import com.h2.tool.QuakeClass;
import com.h2.tool.SensorTool;
import com.h2.tool.relativeStatus;
import com.h2.tool.stringJoin;

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
	
	/**indicate the motivation is not a real valid motivation, if this is a real motivation it will become true, or it will be set to false.*/
	public static boolean realMoti = true;
	
	/**
	 * calculate the space-time strength, and save the motivation information of all motivation sensors, save the information of motivation of three or five motivated sensors.
	 * @param ssen
	 * @return the information of space-time strength.
	 * @throws Exception
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	@SuppressWarnings("unused")
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
		ThreadStep3[] sensorThread3 = new ThreadStep3[Parameters.SensorNum];
		
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
			sensors[i].setTenVectorData(judgeMotiData);
			//写入txt
//			if(i==5) {
//				String temp = judgeMotiData.get(0).split(" ")[6].replaceAll("-", "");
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
		int countNumber = 0;
		if(sensor_latest!=null) {
			//calculate the number of motivation sensors, and save the series to the l array.
			//Meanwhile, we will move the position to the absolute position in 30 seconds, which is point to the position in the now Vector. It's used to cut the motiData and used to calculate during quake magnitude.
			
			int[] l = new int[Parameters.SensorNum];
			int[] l1 = new int[Parameters.SensorNum];
			for (int i=0;i<Parameters.SensorNum;i++){
				if (sensors[i].isSign()) {
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
						for(int j=0;j<Parameters.diskName_offline.length;j++) {
							if(MainThread.fileParentPackage[i].replace("Test", "").equals(Parameters.diskName_offline[j])) {
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
			
			for(int i=0;i<Parameters.initPanfu.length;i++)
				Parameters.initPanfu[i]=0;
			if(countNumber>0) {
			Sensor[] sensors1 = new Sensor[countNumber];//save the sensors after sorting from short to long.
			String panfu="";
			int[] newl = new int[countNumber];//merge l to newl.
			int count=0;// a counter.
			Vector<String>[] motiPreLa = new Vector[countNumber];
			Vector<String>[] inteData = new Vector[countNumber];
			
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
				sensors1 = relativeStatus.P_RelativeArrivalTime(sensors,newl,countNumber);//sort the sensors and calculate the relative P arrival time according Sectime variable.
				
				//storage the current motivation sensors.
				if(Parameters.offline==false) {
					panfu = stringJoin.SJoin_Array(MainThread.fileStr, sensors1);
					panfu=panfu.replaceAll(":/", "");//替换掉盘符中的:/
					for(int i=0;i<sensors1.length;i++) {
						sensors1[i].setpanfu(panfu);//存储盘符字符串
					}
				}
				else {
					panfu = stringJoin.SJoin_Array(MainThread.fileParentPackage, sensors1);
					panfu = panfu.replaceAll("Test", "");
					for(int i=0;i<sensors1.length;i++) {
						sensors1[i].setpanfu(panfu);//存储盘符字符串
					}
				}
				
				//initialization.
				for(int i=0;i<countNumber;i++) {
					motiPreLa[i] = new Vector<String>();
					inteData[i] = new Vector<String>();
				}
				
				//integrate 30s data to one Vector.
				for(int i=0;i<countNumber;i++) {
					inteData[i].addAll(ssen[relativeStatus.numberMotiSeries[i]][0]);
					inteData[i].addAll(ssen[relativeStatus.numberMotiSeries[i]][1]);
					inteData[i].addAll(ssen[relativeStatus.numberMotiSeries[i]][2]);
				}
				for(int i=0;i<countNumber;i++) {
					motiPreLa[i] = QuakeClass.cutOdata(inteData[i], sensors1, Parameters.startTime, Parameters.endTime, sensors1[i]);
					sensors1[i].setCutVectorData(motiPreLa[i]);
				}
			
			//write the motivation data on the disk. 
//			if(countNumber>2 && Parameters.isStorageAllMotivationCSV==1 && EarthQuake.realMoti==true) {
				writeToDisk.saveAllMotivationSensors(countNumber, sensors1);
//			}
				//因为没有其他传感器，所以取第一个传感器。
				QuakeClass.SensorMaxFudu(sensors1[0], 0);
//				QuakeClass.getOneEarthQuakeGrade(s, s2);
//			if(countNumber >= 5 && EarthQuake.realMoti==true) {
				//write to a txt file to indicate the motivation time of each sensor.
				WriteRecords.WriteSeveralMotiTime(sensors1, Parameters.AbsolutePath_recordsOfOneSensor);
//			}
			}
			//if countNumber>=5, the procedure start calculating the earthquake magnitude and the location of quake happening.
//			if(countNumber >= 5 && EarthQuake.realMoti==true) {
//				outString = Five_Locate.five(sensors1, aQuackResults, sensorThread3, aDbExcute);	
//			}
			
			//if the number of motivated sensors is greater than 3, we will calculate three location.
//			if(countNumber>=3 && EarthQuake.realMoti==true){
//				outString = Three_Locate.three(sensors1, aQuackResults, sensorThread3, aDbExcute, countNumber);
//			}
			
			//if the number of motivated sensors is greater than 4, we will calculate four location-main event location.
//			if(countNumber>=4 && EarthQuake.realMoti==true) {
//				//outString = MajorEvent_locate.major(sensors1, aQuackResults, sensorThread3, aDbExcute);
//				MajorEvent_locate.major(sensors1, aQuackResults, sensorThread3, aDbExcute);
//			}
			
			//we can hide this print when the console are so much content or display this print when we want to adjust the procedure.
//			if(countNumber>=3) {
//				output the reasons why the calculation process is not executing.
				
//			}
		}
		System.out.println("激发个数："+countNumber+" 台站间的激发间隔时间是否小于"+Parameters.IntervalToOtherSensors+"?"+
				EarthQuake.realMoti+" "+	sensorData[0][0].get(0).split(" ")[6]);
		//reset the global variable.
		EarthQuake.realMoti=true;
		return outString;
	}
}
