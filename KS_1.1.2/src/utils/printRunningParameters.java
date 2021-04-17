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

	/**
	 * 输出当前后台配置运行的基本信息。
	 * @param outString
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月10日下午4:10:43
	 */
	public static void printModel(String outString) {
		System.out.println("当前后台使用的模式是："+ outString);
	}
	
	public static void printAllParameters() throws IOException {
		
		System.out.println("自动配置的主路径为： ");
		for(int i=0;i<MainThread.fileStr.length;i++) {
			System.out.println(formToChar(MainThread.fileStr[i]));
		}
		System.out.println("自动配置的矿区为： "+formToChar(Parameters.region));
		System.out.println("自动配置的传感器数量为： "+formToChar(String.valueOf(Parameters.SensorNum)));
		System.out.println("FREQUENCY： "+ formToChar(String.valueOf(Parameters.FREQUENCY)));
		System.out.println("distanceToSquareWave： "+ formToChar(String.valueOf(Parameters.distanceToSquareWave)));
		System.out.println("当前配置的服务器IP为： "+ formToChar(Parameters.SevIP));
		System.out.println("当前存储的数据库名为： "+ formToChar(Parameters.DatabaseName5));
		System.out.println("到时差判断是否开启："+ Parameters.SSIntervalToOtherSensors);
		System.out.println("调试模式是否开启："+ Parameters.Adjust);
		
		if(Parameters.TongDaoDiagnose == 1) {
			System.out.println("通道判断是否为混合模式：否    " + "是否进行通道判断" + printRunningParameters.formToChar(String.valueOf(Parameters.TongDaoDiagnose)));
		}
		else {
			System.out.println("通道判断是否为混合模式：是    " + "是否进行通道判断" + printRunningParameters.formToChar(String.valueOf(Parameters.TongDaoDiagnose)));
		}
		
		System.out.println("自动配置的传感器坐标--SENSORINFO1： ");
		outArray.outArray_double(Parameters.SENSORINFO1, Parameters.diskNameNum);
		System.out.println("StartTimeStr： " + formToChar(Parameters.StartTimeStr));
		System.out.println("自动配置完毕，是否继续？按任意键继续——————————");
//		System.in.read();System.in.read();
		
	}
	
	public static void printScanPath(String timeStr) throws IOException {
		
		System.out.println("扫描到的包含数据文件的远程路径：  ");
		for(int i=0;i<MainThread.fileStr.length;i++) {
			System.out.println(formToChar(MainThread.fileStr[i]));
		}
		
		String timeStamp = "";
		try {
			Date date = String2Date.str2Date2(timeStr);
			timeStamp = Date2String.date2str(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("给定的时刻为： "+ formToChar(timeStamp));
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
	
	public static void splitLine() {
		System.out.println("_________________________________________________________________________________________________________________________");
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
