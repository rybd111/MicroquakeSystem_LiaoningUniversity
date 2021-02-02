/**
 * 
 */
package DataExchange;

import mutiThread.MainThread;

/**
 * Pull a fixed data range or file querying from different remote disks.(not accomplish)
 * @revision 2020-09-23
 * @author Hanlin Zhang
 * 
 */
public class pullDataFromSensor {
	
	private String startTime=" ";
	private String endTime=" ";
	
	/**
	 * timeString must be yyyymmddhhmmss
	 * kind select in "file" or "filedata".
	 * @param timeString
	 */
	pullDataFromSensor(String startTime, String endTime, String kind) {
		this.startTime = startTime;
		this.endTime = endTime;
		
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
	 * @date revision 2021年2月2日下午8:44:18
	 */
	public void pullFileFromRemoteDisk() {
		//
		
		//compare different files in each sensor's drive.
		for(int i=0;i<MainThread.fileStr.length;i++) {
			
		}
	}
	
	public void pullDataFromRemoteDiskInSec() {
		
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
