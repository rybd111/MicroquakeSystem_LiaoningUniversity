/**
 * 
 */
package com.h2.locate;

import java.text.ParseException;

import com.db.DbExcute;
import com.h2.constant.Parameters;
import com.h2.tool.Doublelocate;
import com.h2.tool.FiveLocation;
import com.h2.tool.Majorlocate;
import com.h2.tool.QuakeClass;
import com.h2.tool.Triplelocate;
import com.mathworks.toolbox.javabuilder.MWException;

import DataExchange.QuackResults;
import DataExchange.Sensor;

/**
 * @author Hanlin Zhang
 */
public class locate {

	public void temporal_spatio_strength(
			String kind, 
			Sensor[] allsensors, 
			Sensor[] sensors, 
			int countNumber) throws MWException, ParseException {
		
		QuackResults aQuackResults = new QuackResults();
		DbExcute aDbExcute = new DbExcute();
		
		SaveInfo saveAndcal = new SaveInfo();
		saveAndcal.setAllsensors(allsensors);
		saveAndcal.setsensors(sensors);
		saveAndcal.setQuackResults(aQuackResults);
		saveAndcal.setDbexcute(aDbExcute);
		
		Sensor[] sensors1 = new Sensor[countNumber];
		Sensor location_refine = new Sensor();
		
		double [] PArrival = new double[sensors1.length];
		//Take the top countNumber to calculate the quake location and quake magnitude, it may need to optimize later.
		for(int i = 0; i < countNumber; i++) {
			sensors1[i]=sensors[i];
			PArrival[i] = sensors1[i].getSecTime();
		}
		saveAndcal.setsensors1(sensors1);
		
		switch (kind) {
			case "PSO":
		 		//calculate the two dimensional matrix.
		 		double coor[][] = new double[sensors1.length][4];
		 		for(int i=0;i<sensors1.length;i++) {
	 				coor[i][0]=sensors1[i].getLatitude();
	 				coor[i][1]=sensors1[i].getLongtitude();
	 				coor[i][2]=sensors1[i].getAltitude();
	 				coor[i][3]=sensors1[i].getSecTime();
		 		}
		 		
				//compute the quake coordination.
				
				location_refine = QuakeClass.PSO(coor);
				//the locate process probably return a NAN value or a INF value, so when this situation appears, the procedure will skip the current circulation.
				if(Double.isNaN(location_refine.getLatitude())==false && Double.isInfinite(location_refine.getLatitude()) == false){
					saveAndcal.setlocation_refine(location_refine);
					saveAndcal.generalProcess(kind);
				}
				break;
			case "five":
				//calculate the coordinations of the quake source, location variable only store the quake time, not store the motivation time, and store the coordinations of the quake happening.
				location_refine = FiveLocation.getLocation(sensors1);//calculate the quake time in milliseconds.
				saveAndcal.setlocation_refine(location_refine);
				saveAndcal.generalProcess(kind);
				break;
			case "three":
				int count=0;//count the final valid satisfied the conditions' sensors.
				int[] l1 = new int[countNumber];
				
				//We first need to diagnose all sensors that satisfy the conditions, when the number of motivated sensors are greater than 3, this function just starts the calculation. 
				for(int i = 0; i < countNumber; i++) {
					double e1=sensors[i].getCrestortrough().getE1();
					if(Math.cos(Math.PI/4+e1/2)>=(-Parameters.S/Parameters.C)&&Math.cos(Math.PI/4+e1/2)<=(Parameters.S/Parameters.C)) {
						l1[count]=i;//record the sensors satisfied the angle conditions.
						count++;
					}
				}
				if(count<=2) {
					System.out.println("超出限制条件无法计算");
				}
				//the number of sensors satisfy the condition is greater than 3, set the restrain to false.
				if(count>=3){
					//Take the top 3 to calculate the quake location and quake magnitude, it may need to optimize later.
			 		for(int i = 0; i < 3; i++) {
						sensors1[i]=sensors[l1[i]];
					}
			 		saveAndcal.setsensors1(sensors1);
					//compute the quake coordination.
					location_refine=Triplelocate.tripleStationLocate(sensors1);
					//we will calculate the quake time through this function.			
					location_refine.setSecTime(Doublelocate.quakeTime(sensors1[0], location_refine));
					//the locate process probably return a NAN value or a INF value, so when this situation appears, the procedure will skip the current circulation.
					if(Double.isNaN(location_refine.getLatitude())==false && Double.isInfinite(location_refine.getLatitude()) == false){
						saveAndcal.setlocation_refine(location_refine);
						saveAndcal.generalProcess(kind);
					}
				}
				break;
			case "major":
				//calculate the coordinations of the quake source, location variable only store the quake time, not store the motivation time, and store the coordinations of the quake happening.
				location_refine = Majorlocate.majorLocate(PArrival);//calculate the quake time in milliseconds.
				//we will calculate the quake time through this function.
				location_refine.setSecTime(Doublelocate.quakeTime(sensors1[0], location_refine));
				saveAndcal.setlocation_refine(location_refine);
				saveAndcal.generalProcess(kind);
				break;
		}
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月12日下午7:11:37
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
