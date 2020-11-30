package testKS;

import java.io.IOException;

import com.h2.constant.Parameters;
import com.h2.constant.Sensor;
import com.h2.tool.SensorTool;
import com.h2.tool.stringJoin;

import mutiThread.MainThread;
public class Test {

	public static void main(String[] args) throws IOException {
//		long a=GetLocation.getLocation();
//		System.out.println("a"+a);
		
		String[] a = new String[4];
		a[0]="w:/";
		a[1]="z:/";
		a[2]="x:/";
		a[3]="y:/";
		
		MainThread.fileStr[0] = "z:/";
//		MainThread.fileStr[1] = "y:/";
//		MainThread.fileStr[2] = "w:/";
//		MainThread.fileStr[3] = "s:/";
//		MainThread.fileStr[4] = "x:/";
//		MainThread.fileStr[5] = "x:/";
//		MainThread.fileStr[6] = "y:/";
//		MainThread.fileStr[7] = "z:/";
		
		Sensor [] s = SensorTool.initSensorInfo(Parameters.SensorNum,MainThread.fileStr);
		
		stringJoin.SJoin_Array(a, s);
	}
}
