/**
 * 
 */
package controller;

import com.h2.constant.Parameters;

/**
 * Manage the status of the system for replacing static variables.
 * @author Hanlin Zhang
 */
public class ADMINISTRATOR {
	//use which type of sensor.
	public boolean []isMrMa = new boolean[Parameters.SensorNum];
//	public boolean []isMrLiu = new boolean[Parameters.SensorNum];
	//read processing is normal or irregular.
	public boolean isProductNewFile = false;
	public boolean isEmergeNetError = false;
	//align procedure is normal or irregular.
	public boolean isReAlign = false;
	public boolean isContinue = false;
	//procedure is the first loop or not.
	public boolean isFirst = false;
	//visual supervising.
	public boolean exitVariable_visual=false;
	//align different array.
	public int duiqi[] = new int[Parameters.SensorNum];
	//the new file in each sensor we find.
	public String[] file1 = new String[Parameters.SensorNum];
	
	public ADMINISTRATOR() {
		super();
		for(int i=0;i<Parameters.SensorNum;i++) {
			isMrMa[i] = false;
//			isMrLiu[i] = false;
		}
	}
	
}
