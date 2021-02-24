package middleware;

import java.text.ParseException;

import com.h2.constant.Parameters;
import com.h2.tool.CrestorTrough;
import com.h2.tool.Doublelocate;
import com.h2.tool.SensorTool;

import DataExchange.Sensor;
import controller.ADMINISTRATOR;
import mutiThread.MainThread;
import utils.TimeDifferent;

public class Three_Locate_alone {

	public static void main(String[] args) throws ParseException {
		String[] str = new String[3];
		int[] x = new int[3];
		int[] y = new int[3];
		int[] z = new int[3];

//		x[0] = 286;x[1] =-51;x[2] =532;//3
//		y[0] = -170;y[1] =79;y[2] = 72;//7
//		z[0] = -333;z[1] =351;z[2] = 195;//2
		
//		
		x[0] = 61;x[1] =-7;x[2] =-1372;//3
		y[0] = 198;y[1] =66;y[2] = -8;//7
		z[0] = -1135;z[1] =1212;z[2] = 1106;//2

		
		str[0] = "Test3/";str[1] = "Test4/";str[2] = "Test2/";
		ADMINISTRATOR manager = new ADMINISTRATOR();
		Sensor[] sensor = SensorTool.initSensorInfo(3,str,manager);
		
//		sensor[0].setSecTime(0);
//		sensor[1].setSecTime(0.0232);
//		sensor[2].setSecTime(0.0426);

		sensor[0].setSecTime(0);
		sensor[1].setSecTime(0.02);
		sensor[2].setSecTime(0.14);
		
		CrestorTrough temcre0=new CrestorTrough(Double.valueOf(x[0]),     Double.valueOf(y[0]),      Double.valueOf(z[0]));
		sensor[0].setCrestortrough(temcre0);
		
		CrestorTrough temcre1=new CrestorTrough(Double.valueOf(x[1]),     Double.valueOf(y[1]),      Double.valueOf(z[1]));
		sensor[1].setCrestortrough(temcre1);
		
		CrestorTrough temcre2=new CrestorTrough(Double.valueOf(x[2]),     Double.valueOf(y[2]),      Double.valueOf(z[2]));
		sensor[2].setCrestortrough(temcre2);
		
		int count=0;int[] l1 = new int[3];
		//We first need to diagnose all sensors that satisfy the conditions, when the number of motivated sensors are greater than 3, this function just starts the calculation. 
		for(int i = 0; i < 3; i++) {
			double e1=sensor[i].getCrestortrough().getE1();
			if(Math.cos(Math.PI/4+e1/2)>=(-Parameters.S/Parameters.C)&&Math.cos(Math.PI/4+e1/2)<=(Parameters.S/Parameters.C)) {
				l1[count]=i;//record the sensors satisfied the angle conditions.
//						System.out.print(l1[count]);
				count++;
			}
		}
		
		//compute the quake coordination.
		Sensor location=Doublelocate.tripleStationLocate(sensor);
		
		//we will calculate the quake time through this function.			
		location.setSecTime(Doublelocate.quakeTime(sensor[0], location));
		System.out.println(location.toString()+" "+location.getSecTime()+" "+location.getquackTime());
		
	}

	
}
