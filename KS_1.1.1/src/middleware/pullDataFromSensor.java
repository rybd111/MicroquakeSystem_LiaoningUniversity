/**
 * 
 */
package middleware;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

import Entrance.InitialConfig;
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
	private String destPath = "";
	
	/**
	 * timeString must be yyyy-MM-ddHH:mm:ss
	 * kind select in "file" or "filedata".
	 * @param timeString
	 * @throws IOException 
	 * @throws ParseException 
	 */
	pullDataFromSensor(
			String startTime,
			String endTime,
			String timeStamp,
			String kind,
			String destPath) throws IOException, ParseException {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.timeStamp = timeStamp;
		this.destPath = destPath;
		
		if(kind.equals("getfile")) {
			pullFileFromRemoteDiskInFile();
		}
		if(kind.equals("getSpecialdata")) {
			pullDataFromRemoteDiskInSec();
		}
	}
	
	/**
	 * 从所有网络盘符中获取给定时刻的文件。
	 * @author Hanlin Zhang.
	 * @throws IOException 
	 * @throws ParseException 
	 * @date revision 2021年2月2日下午8:44:18
	 */
	private void pullFileFromRemoteDiskInFile() throws IOException, ParseException {
		InitialConfig config = new InitialConfig();
		//注意此路径后面必须加上"/".
        String destiPath = "I:\\矿山\\矿山数据\\大同\\1月14日大同塔山矿震动/";
//        String time = "20"+Parameters.StartTimeStr;
        String timeStr = "20" + "190114020001";
        
		config.pullFileFromRemote(destiPath, timeStr);
	}
	
	private File[] comingPullFiles() {
		
		return null;
	}
	
	private boolean queryFile() {
		
		return false;
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
