/**
 * 
 */
package utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.h2.constant.Parameters;

import mutiThread.MainThread;

/**
 * @author Hanlin Zhang
 */
public class printRunningParameters {

	
	public static void printAllParameters(
//			String fileStr[],
//			String region,
//			int sensorNum,
//			int frequency,
//			double distanceToSquareWave,
//			String SevIP,
//			double[][][] sensorINFO,
//			int diskNameNum
			) throws IOException {
		
		System.out.println("自动配置的主路径为： ");
		for(int i=0;i<MainThread.fileStr.length;i++) {
			System.out.println(formToChar(MainThread.fileStr[i]));
		}
		
		System.out.print("自动配置的矿区为： ");
		System.out.println(formToChar(Parameters.region));
		System.out.print("自动配置的传感器数量为： ");
		System.out.println(formToChar(String.valueOf(Parameters.SensorNum)));
		
		System.out.println("其余参数： ");
		System.out.println("FREQUENCY： "+ formToChar(String.valueOf(Parameters.FREQUENCY)));
		System.out.println("distanceToSquareWave： "+ formToChar(String.valueOf(Parameters.distanceToSquareWave)));
		System.out.println("SevIP： "+ formToChar(Parameters.SevIP));
		System.out.println("SENSORINFO1： ");
		
		outArray.outArray_double(Parameters.SENSORINFO1, Parameters.diskNameNum);
		System.out.println("StartTimeStr： "+ formToChar(Parameters.StartTimeStr));
		
		System.out.println("自动配置完毕，是否继续？按任意键继续——————————");
		System.in.read();System.in.read();
		
	}
	
	public static void printpullParameters(String timeStr) throws IOException {
		
		System.out.println("自动配置的主路径为： ");
		for(int i=0;i<MainThread.fileStr.length;i++) {
			System.out.println(formToChar(MainThread.fileStr[i]));
		}
		
		String timeStamp = "";
		try {
			Date date = String2Date.str2Date2(timeStr);
			timeStamp = Date2String.date2str(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("给定的时刻为： "+ formToChar(timeStamp));
		
		System.out.println("自动配置完毕——————————");
	}
	
	/**
	 * 输出特定格式。
	 * @param out 
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月20日下午2:59:00
	 */
	public static String formToChar(String out) {
		return "["+out+"]";
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月20日下午12:19:31
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
