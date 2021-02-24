package middleware;

import java.text.ParseException;

import com.h2.constant.Parameters;
import com.h2.tool.CrestorTrough;
import com.h2.tool.Doublelocate;
import com.h2.tool.FiveLocation;
import com.h2.tool.SensorTool;

import DataExchange.Sensor;
import controller.ADMINISTRATOR;

public class Five_Locate_alone {

	public static void main(String[] args) throws ParseException {
		String[] str = new String[5];
		int[] x = new int[3];
		int[] y = new int[3];
		int[] z = new int[3];
		
		
		x[0] = 286;x[1] =-51;x[2] =532;//3
		y[0] = -170;y[1] =79;y[2] = 72;//7
		z[0] = -333;z[1] =351;z[2] = 195;//2
		
		str[0] = "Test3/";
		str[1] = "Test4/";
		str[2] = "Test2/";
		str[3] = "Test6/";
		str[4] = "Test7/";
		ADMINISTRATOR manager = new ADMINISTRATOR();
		
		Sensor[] sensor = SensorTool.initSensorInfo(5,str,manager);
		
		sensor[0].setSecTime(0);
		sensor[1].setSecTime(0.0232);
		sensor[2].setSecTime(0.0426);
		sensor[3].setSecTime(0.11);
		sensor[4].setSecTime(0.112);
		
		
		
		int count=0;int[] l1 = new int[3];
		 
		
		
		//calculate the coordinations of the quake source, location variable only store the quake time, not store the motivation time, and store the coordinations of the quake happening.
		Sensor location = FiveLocation.getLocation(sensor);//calculate the quake time in milliseconds.
				
		//we will calculate the quake time through this function.			
		location.setSecTime(Doublelocate.quakeTime(sensor[0], location));
		System.out.println(location.toString()+" "+location.getSecTime());
		
	}

}
