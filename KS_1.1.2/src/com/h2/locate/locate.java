package com.h2.locate;

import java.text.ParseException;
import java.util.Arrays;

import com.h2.constant.Parameters;
import com.h2.tool.Doublelocate;
import com.h2.tool.FiveLocation;
import com.h2.tool.Majorlocate;
import com.h2.tool.QuakeClass;
import com.mathworks.toolbox.javabuilder.MWException;

import DataExchange.Sensor;
import controller.ADMINISTRATOR;

/**
 * @author Hanlin Zhang
 */
public class locate {

	public void temporal_spatio_strength(
			String kind,
			Sensor[] allsensors,
			Sensor[] Motisensors,
			int countNumber,
			ADMINISTRATOR manager) throws MWException, ParseException {
		//设置计算需用的参数。
		SaveInfo saveAndcal = new SaveInfo();
		//保存所有传感器，包括激发和未激发的，未激发的用于保存波形。
		saveAndcal.setAllsensors(allsensors);
		//所有传感器接收到的最早绝对时间，由sensorTool类更新。
		saveAndcal.setEarlistTime(Motisensors[0].getAbsoluteTime());
		//保存即将计算的激发传感器信息，当三台站算法调用时，需要更新成满足三台站算法的激发传感器，因此该函数需要重置。
		saveAndcal.setMotisensorsAfterCut(Motisensors);
		
		//保存定位坐标。
		Sensor location_refine = new Sensor();
		
		switch (kind) {
			case "PSO":
		 		//calculate the two dimensional matrix.
		 		double coor[][] = new double[Motisensors.length][4];
		 		for(int i=0;i<Motisensors.length;i++) {
	 				coor[i][0]=Motisensors[i].getx();
	 				coor[i][1]=Motisensors[i].gety();
	 				coor[i][2]=Motisensors[i].getz();
	 				coor[i][3]=Motisensors[i].getSecTime();
		 		}
		 		
				//compute the quake coordination.
				location_refine = QuakeClass.PSO(coor);
				//the locate process probably return a NAN value or a INF value, so when this situation appears, the procedure will skip the current circulation.
				if(Double.isNaN(location_refine.getx())==false && Double.isInfinite(location_refine.getx()) == false){
					saveAndcal.setlocation_refine(location_refine);
					saveAndcal.generalProcess(kind,manager);
				}
				break;
			case "five":
				//calculate the coordinations of the quake source, location variable only store the quake time, not store the motivation time, and store the coordinations of the quake happening.
				location_refine = FiveLocation.getLocation(Motisensors);//calculate the quake time in milliseconds.
				saveAndcal.setlocation_refine(location_refine);
				saveAndcal.generalProcess(kind,manager);
				break;
			case "three":
				int count=0;//count the final valid satisfied the conditions' sensors.
				Sensor[] sensorsCut = new Sensor[0];
				
				//We first need to diagnose all sensors that satisfy the conditions, when the number of motivated sensors are greater than 3, this function just starts the calculation. 
				for(int i = 0; i < countNumber; i++) {
					double e1=Motisensors[i].getCrestortrough().getE1();
					if(Math.cos(Math.PI/4+e1/2)>=(-Parameters.S/Parameters.C)&&Math.cos(Math.PI/4+e1/2)<=(Parameters.S/Parameters.C)) {
						sensorsCut = Arrays.copyOf(sensorsCut, sensorsCut.length+1);
						sensorsCut[sensorsCut.length-1] = Motisensors[i];
						count++;
					}
				}
				if(count<=2) {
					System.out.println("超出限制条件无法计算");
				}
				//the number of sensors satisfy the condition is greater than 3, set the restrain to false.
				if(count>=3){
					saveAndcal.setMotisensorsAfterCut(sensorsCut);
					//compute the quake coordination.
					location_refine=Doublelocate.tripleStationLocate(sensorsCut);
					//we will calculate the relative quake time through this function.		
					location_refine.setSecTime(Doublelocate.quakeTime(sensorsCut[0], location_refine));
					//the locate process probably return a NAN value or a INF value, so when this situation appears, the procedure will skip the current circulation.
					if(Double.isNaN(location_refine.getx())==false && Double.isInfinite(location_refine.getx()) == false){
						saveAndcal.setlocation_refine(location_refine);
						saveAndcal.generalProcess(kind,manager);
					}
				}
				break;
			case "major":
				double [] PArrival = new double[Motisensors.length];
				for(int i = 0; i < countNumber; i++) {
					PArrival[i] = Motisensors[i].getSecTime();
				}
				//calculate the coordinations of the quake source, location variable only store the quake time, not store the motivation time, and store the coordinations of the quake happening.
				location_refine = Majorlocate.majorLocate(PArrival);//calculate the quake time in milliseconds.
				//we will calculate the relative quake time through this function.
				location_refine.setSecTime(Doublelocate.quakeTime(Motisensors[0], location_refine));
				saveAndcal.setlocation_refine(location_refine);
				saveAndcal.generalProcess(kind,manager);
				break;
			case "timelocation":
				double[][] taizhan=Parameters.SENSORINFO1[Parameters.diskNameNum];
				double [][] stationcoordinates=new double[taizhan.length][3];//各个台站相对坐标
				int a=manager.getBaseCoordinate();						
				for (int i = 0; i < taizhan.length; i++) {
					stationcoordinates[i][0]=taizhan[i][0]-taizhan[manager.getBaseCoordinate()][0];
					stationcoordinates[i][1]=taizhan[i][1]-taizhan[manager.getBaseCoordinate()][1];
					stationcoordinates[i][2]=taizhan[i][2]-taizhan[manager.getBaseCoordinate()][2];
				}
//				double [][] stationcoordinates=Parameters.SENSORINFO1[Parameters.diskNameNum];//各个台站坐标
				double [][] coordinate = new double[Motisensors.length][5];//激发坐标和到时
		 		for(int i=0;i<Motisensors.length;i++) {
		 			coordinate[i][0]=i+1;
		 			coordinate[i][1]=Motisensors[i].getx();
		 			coordinate[i][2]=Motisensors[i].gety();
		 			coordinate[i][3]=Motisensors[i].getz();
		 			coordinate[i][4]=Motisensors[i].getSecTime();
		 		}
		 		location_refine=QuakeClass.timelocate(Parameters.C,stationcoordinates, coordinate);
		 		saveAndcal.setlocation_refine(location_refine);
				saveAndcal.generalProcess(kind,manager);
		}
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月12日下午7:11:37
	 */
	public static void main(String[] args) {

	}

}
