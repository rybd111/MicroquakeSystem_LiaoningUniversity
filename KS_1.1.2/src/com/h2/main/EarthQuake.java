
package com.h2.main;

import java.util.Arrays;
import java.util.Vector;

import mutiThread.MainThread;
import utils.diagDataNum;
import utils.printRunningParameters;

import com.h2.backupData.WriteRecords;
import com.h2.backupData.WriteWaveIntoCSVFile;
import com.h2.constant.Parameters;
import com.h2.locate.locate;
import com.h2.tool.SensorTool;
import com.h2.tool.relativeStatus;
import DataExchange.Sensor;
import Entrance.MainTest;
import controller.ADMINISTRATOR;

/**
 * @revision 2019-12-3
 * We revise this function on the locating of three sensors and five sensors, which is packaged to a function to manage convenient.
 * And we also delete some variables to simplify the function such as threadstep1~threadstep3.
 * @author Baishuo Han, Hanlin Zhang, RQMa, Yali Hao, Ze Chen, et al.
 */
public class EarthQuake {
	/**
	 * calculate the space-time strength, and save the motivation information of all motivation sensors, save the information of motivation of three or five motivated sensors.
	 * @param ssen all sensors' data in three vectors.
	 * @return the information of space-time strength.
	 * @throws Exception
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	public static void runmain(ADMINISTRATOR manager)	throws Exception {
		Vector<String> ssen[][] = manager.getSensorData();
		//判断数据量是否足够？
		if(diagDataNum.diagnose(ssen)==false) return;	
		
		//We must initialize the Sensor object every time.
		Sensor[] sensors = SensorTool.initSensorInfo(Parameters.SensorNum,MainThread.fileStr,manager);
		//
		Vector<String> judgeMotiData = new Vector<String>();
		
		/**精细判断时，前面附加的数据条数*/
		int range = Parameters.refineRange;
		
		//Set every sensor's motivation flag to indicate the sensor is inspired or not.
		for (int i = 0; i < Parameters.SensorNum; i++) {
			//add extra data used to judge sensor's motivation status.
			for(int k=ssen[i][0].size()-range;k<ssen[i][0].size();k++)
				judgeMotiData.addElement(ssen[i][0].get(k));
			
			judgeMotiData.addAll(ssen[i][1]);
			//将数据不论是否激发都进行存储。
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
			SensorTool.motivate(judgeMotiData, sensors[i], i, range);//存储了激发时间和激发的标志位
			judgeMotiData.clear();
		}
		
		//we will diagnose there has a motivational sensor or not to reduce the complexity.
		Sensor sensor_latest = null;
		int countNumber = 0;
		for(int i=0;i<sensors.length;i++) {
			if(sensors[i].getSecTime()!=0) {
				sensor_latest = sensors[i];
			}
		}
		
		if(sensor_latest!=null) {
			//calculate the number of motivation sensors, and save the series to the l array.
			//Meanwhile, we will move the position to the absolute position in 30 seconds, which is point to the position in the now Vector. It's used to cut the motiData and used to calculate during quake magnitude.
			int[] l = new int[Parameters.SensorNum];
			int[] l1 = new int[0];
			String identity = null;
			
			for (int i=0;i<Parameters.SensorNum;i++){
				if (sensors[i].isSign()) {
					//the series of correspond with the name of path, which stow the motivated sensor.
					sensors[i].setSensorNum(i);
					for(int j=0;j<Parameters.diskName[Parameters.diskNameNum].length;j++) {
						if(MainTest.runningModel != MainTest.LOCAL_ONLINE_UNSTORAGE || MainTest.runningModel != MainTest.LOCAL_ONLINE_STORAGE) {
							if(Parameters.offline==false) {
								identity = MainThread.fileStr[i].replace(":/", "").toLowerCase();
							}
							else {
								identity = MainThread.fileParentPackage[i].replace("Test", "").toLowerCase();
							}
						}
						//测试本地在线时的计算部分。
						else {
							identity = MainThread.fileParentPackage[i].replace("Test", "").toLowerCase();
						}
						
						if(identity.equals(Parameters.diskName[Parameters.diskNameNum][j])) {
							l[i]=i+1;//record the number of motivated sensors.
							l1 = Arrays.copyOf(l1, l1.length+1);
							l1[l1.length-1] = i;//记录激发序号，从0开始。
							countNumber++;//统计激发数。
							sensors[i].setlineSeries(sensors[i].getlineSeries()+ssen[i][0].size());
							
							System.out.println(
									"激发台站: "+
									printRunningParameters.formToChar(MainThread.fileStr[i])+
									" "+
									"激发位置: "+
									printRunningParameters.formToChar(String.valueOf(sensors[i].getlineSeries())));
						}
					}
				}
			}
			
			//按照激发顺序先保存激发的，再保存未激发的。
			Sensor[] S = new Sensor[sensors.length];
			statusOfCompute status = new statusOfCompute();
			
			if(countNumber >= 3) {
				printRunningParameters.splitLine();
				//stow the info of all sensors.
				S = new relativeStatus(l1, countNumber, sensors, ssen, status, manager).stowInfoSensor();
			}
			
			/**
			 * panfu is the combination of motivated and unmotivated sensor split with " ".
			 * countNumber is the number of motivation sensor.
			 * S is the array of the Sensor class's object.
			 * THIS FUNTION IS TO WIRTE THE MOTIVATION DATA ON THE DISK.
			 */
			if(countNumber >= 3 && Parameters.isStorageAllMotivationCSV==1 && manager.getIsRealMoti() == true) {
				new WriteWaveIntoCSVFile().saveAllMotivationSensors(countNumber, S, status.getPanfu());
			}
			
			if(countNumber >= 3 && manager.getIsRealMoti() == true) {
				//write to a txt file to indicate the motivation time of each sensor.
				new WriteRecords().WriteSeveralMotiTime(status.getSensors1(), Parameters.AbsolutePath_allMotiTime_record);
			}
			
			locate loc = new locate();
			
			//if countNumber>=5, the procedure start calculating the earthquake magnitude and the location of quake happening.
			if(countNumber >= 5 && manager.getIsRealMoti() == true) {
//				loc.temporal_spatio_strength("five", S, status.getSensors1(), countNumber, manager);
			}
			
			//if the number of motivated sensors is greater than 3, we will calculate three location and PSO location.
			if(countNumber>=3 && manager.getIsRealMoti() == true){
//				loc.temporal_spatio_strength("three", S, status.getSensors1(), countNumber, manager);
				loc.temporal_spatio_strength("PSO", S, status.getSensors1(), countNumber, manager);
			}
			
			//if the number of motivated sensors is greater than 4, we will calculate four location-main event location.
			if(countNumber>=4 && manager.getIsRealMoti() == true) {
//				loc.temporal_spatio_strength("major", S, status.getSensors1(), countNumber, manager);
//				loc.temporal_spatio_strength("timelocation", S, status.getSensors1(), countNumber, manager);
			}
		}
		//we can hide this print when the console are so much content or display this print when we want to adjust the procedure.
        //output the reasons why the calculation process is not executing.
		System.out.println(
				"激发数: "+ 
				printRunningParameters.formToChar(String.valueOf(countNumber))+
				" 台站间的激发间隔时间是否小于"+
				printRunningParameters.formToChar(String.valueOf(Parameters.IntervalToOtherSensors))+
				"秒? "+
				printRunningParameters.formToChar(String.valueOf(manager.getIsRealMoti()))+
				" time："+
				printRunningParameters.formToChar(ssen[0][0].get(0).split(" ")[6]));
		
		//reset the global variable.
		manager.setIsRealMoti(true);
	}
}
