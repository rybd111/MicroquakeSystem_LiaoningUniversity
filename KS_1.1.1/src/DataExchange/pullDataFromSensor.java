/**
 * 
 */
package DataExchange;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import Entrance.MainTestInitialConfig;
import mutiThread.MainThread;
import utils.ScanInNum;
import utils.outArray;

/**
 * Pull a fixed data range or file querying from different remote disks.(not accomplish)
 * @revision 2020-09-23
 * @author Hanlin Zhang
 * 
 */
public class pullDataFromSensor {
	
	private String startTime="";
	private String endTime="";
	private String timeStamp = "";
	
	/**
	 * timeString must be yyyy-MM-ddHH:mm:ss
	 * kind select in "file" or "filedata".
	 * @param timeString
	 * @throws IOException 
	 */
	pullDataFromSensor(String startTime, String endTime, String timeStamp, String kind) throws IOException {
		this.startTime = startTime;
		this.endTime = endTime;
		this.timeStamp = timeStamp;
		
		if(kind.equals("file")) {
			pullFileFromRemoteDisk();
		}
		if(kind.equals("filedata")) {
			pullDataFromRemoteDiskInSec();
		}
	}
	
	/**
	 * 
	 * @author Hanlin Zhang.
	 * @throws IOException 
	 * @date revision 2021年2月2日下午8:44:18
	 */
	private void pullFileFromRemoteDisk() throws IOException {
		//确定盘符
		new MainTestInitialConfig("pull");
		
		//确定最终查找的盘符。
		outArray.outArray_String(MainThread.fileStr);
		
		System.out.println("请输入选择的盘符序号");
		int series[] = ScanInNum.ScanInNum();
		String[] newfileStr = new String[0]; 
		
		for(int i=0;i<series.length;i++) {	
			newfileStr = Arrays.copyOf(newfileStr, newfileStr.length+1);
			newfileStr[newfileStr.length-1] = MainThread.fileStr[series[i]];  
		}
		
		//查找某时刻对应的所有盘符上的文件
		File[] f = comingPullFiles();
		
		//写入Windows脚本程序并执行。
		writeToEcho();
		excuteEcho();
	}
	
	private File[] comingPullFiles() {
		
		return null;
	}
	
	private boolean queryFile() {
		
		return false;
	}
	
	private void writeToEcho() {
		
	}
	
	private void excuteEcho() {
		
	}
	
	
	private void pullDataFromRemoteDiskInSec() {
		
	}
	
	/**
	 * test function.
	 * @param args
	 * @author Hanlin Zhang.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
