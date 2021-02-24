/**
 * 
 */
package testKS;

import com.h2.constant.Parameters;

/**
 * @author Hanlin Zhang
 */
public class testClubVariable {

	//use which type of sensor.
	public boolean []isMrMa = new boolean[Parameters.SensorNum];
	
	//read processing is normal or irregular.
	private boolean NewFile = false;
	private boolean NetError = false;
	//record the net error station, we initialize it at the beginning of the procedure.
	private int netErrID = -1;
	//record the interrupt time instance.
	private int timeInterrupt = -1;
	//record the phrase of the align time cost.
	private long startInstance = -1;
	private long endInstance = -1;
	
	//align procedure is normal or irregular.
	private boolean isReAlign = false;
	//Is not restart the procedure
	private int isContinue = -1;
	//procedure is the first loop or not.
	private boolean isFirst = false;
	//align different array.
	public int duiqi[] = new int[Parameters.SensorNum];
	//the new file in each sensor we find.
	public String[] file1 = new String[Parameters.SensorNum];
	
	
	//net error control.
	public void setNetError(boolean flag) {
		this.NetError = flag;
	}
	public boolean getNetError() {
		return this.NetError;
	}
	
	//new file flag control.
	public void setNewFile(boolean flag) {
		this.NewFile = flag;
	}
	public boolean getNewFile() {
		return this.NewFile;
	}
	
	//record net error station ID.
	public void setNetErrID(int num) {
		this.netErrID = num;
	}
	public int getNetErrID() {
		return this.netErrID;
	}
	
	//record the interrupt time instance.
	public void setTimeInterrupt(int timeCount) {
		this.timeInterrupt = timeCount;
	}
	public int getTimeInterrupt() {
		return this.timeInterrupt;
	}
	
	//record the phrase of the align time cost.
	public void setStartInstance(long startTime) {
		this.startInstance = startTime;
	}
	public long getStartInstance() {
		return this.startInstance;
	}
	public void setEndInstance(long endTime) {
		this.endInstance = endTime;
	}
	public long getEndInstance() {
		return this.endInstance;
	}
	
	//record procedure is the first loop or not.
	public void setIsFirst(boolean flag) {
		this.isFirst = flag;
	}
	public boolean getIsFirst() {
		return this.isFirst;
	}
	
	//Is not restart the procedure
	public void setIsContinue(int flag) {
		this.isContinue = flag;
	}
	public int getIsContinue() {
		return this.isContinue;
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月14日下午5:55:41
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0;i<2;i++) {
			testClubVariable t = new testClubVariable();
			
			t.setStartInstance(System.currentTimeMillis());
			
			System.out.println(t.duiqi.length);
			
			
			Parameters.SensorNum = 2;
			
			t.setEndInstance(System.currentTimeMillis());
			
		}
		
	}

}
