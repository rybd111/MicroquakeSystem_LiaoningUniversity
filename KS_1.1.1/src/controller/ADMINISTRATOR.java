/**
 * 
 */
package controller;

import org.jblas.benchmark.Main;

import com.h2.constant.Parameters;

import mutiThread.MainThread;
import mutiThread.ReconnectToRemoteDisk;

/**
 * Manage the status of the system for replacing static variables.
 * @author Hanlin Zhang
 */
public class ADMINISTRATOR {
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
	//procedure is the first loop or not.
	private boolean isFirst = true;
	//is not real motivation.
	private boolean isRealMoti = false;
	//record the number of motivation sensors.
	private int[] numberMotiSeries;
	//update at the creating ReadData function's object to make sure this variable is the last day all the way.
	private String lastDate = "2019-1-13 02:05:03";
	//record the baseline sensor number.
	private int baseCoordinate = 0;
	//align different array.
	private int duiqi[] = new int[Parameters.SensorNum];
	private int reduiqi[] = new int[Parameters.SensorNum];
	//the new file in each sensor we find.
	private String[] file1 = new String[Parameters.SensorNum];
	
	public ADMINISTRATOR() {
		super();
		for(int i=0;i<Parameters.SensorNum;i++) {
			this.isMrMa[i] = false;
		}
	}
	
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
	
	//is not real motivation.
	public void setIsRealMoti(boolean flag) {
		this.isRealMoti = flag;
	}
	public boolean getIsRealMoti() {
		return this.isRealMoti;
	}
	
	//record the motivated number.
	public void initNumberMotiSeries(int num) {
		this.numberMotiSeries = new int[num];
	}
	public void setNNumberMotiSeries(int series,int value) {
		this.numberMotiSeries[series] = value;
	}
	public int getNNumberMotiSeries(int series) {
		return this.numberMotiSeries[series];
	}
	
	//update at the creating ReadData function's object to make sure this variable is the last day all the way.
	public void setLastDate(String date) {
		this.lastDate = date;
	}
	public String getLastDate() {
		return this.lastDate;
	}
	
	//record the baseline sensor number.
	public void setBaseCoordinate(int series) {
		this.baseCoordinate = series;
	}
	public int getBaseCoordinate() {
		return this.baseCoordinate;
	}
	
	//align array(duiqi)
	/**
	 * reset reduiqi array to 0.
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月14日下午6:11:24
	 */
	public void resetReduiqi() {
		this.reduiqi = new int[Parameters.SensorNum];
		
		for(int i=0;i<this.reduiqi.length;i++) {
			this.reduiqi[i] = 0;
		}
	}
	/**
	 * series is the number of reduiqi array you want obtain.
	 * @param series
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月14日下午6:10:36
	 */
	public int getNReduiqi(int series) {
		return this.reduiqi[series];
	}
	/**
	 * series is the number of element in reduiqi.
	 * value is the value you want set.
	 * @param series
	 * @param value
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月14日下午6:09:53
	 */
	public void setNReduiqi(int series,int value) {
		this.reduiqi[series] = value;
	}
	//duiqi
	public void resetDuiqi() {
		this.duiqi = new int[Parameters.SensorNum];
		
		for(int i=0;i<this.duiqi.length;i++) {
			this.duiqi[i] = -1;
		}
	}
	
	public int getNDuiqi(int series) {
		return this.duiqi[series];
	}
	public int[] getDuiqi() {
		return this.duiqi;
	}
	
	public void setNDuiqi(int series,int value) {
		this.duiqi[series] = value;
	}
	public void setDuiqi(int[] duiqi) {
		this.duiqi = duiqi;
	}
	//file1
	public void resetFile1() {
		this.file1 = new String[Parameters.SensorNum];
		
		for(int i=0;i<this.file1.length;i++) {
			this.file1[i] = "";
		}
	}
	
	public String getNFile1(int series) {
		return this.file1[series];
	}
	public String[] getFile1() {
		return this.file1;
	}
	
	public void setNFile1(int series,String value) {
		this.file1[series] = value;
	}
	public void setFile1(String[] file1) {
		this.file1 = file1;
	}
	
	/**
	 * resetter resets the order of each drive or path.
	 * @param discSymbol
	 * @param ReConnect
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月15日上午12:20:13
	 */
	public void resetter(int discSymbol, ReconnectToRemoteDisk ReConnect) {
		if(MainThread.fileStr.length<3)
            discSymbol=0;
        else if(discSymbol<=-1)
            discSymbol=ReConnect.orderNum-1;//When the all situations has been considered, the procedure will start from the beginning.
        if(this.getIsFirst() == false) {
//        if(isFirst==false) {
            if(MainThread.fileStr.length>=3) {
            	MainThread.fileStr = ReConnect.rearrange(discSymbol);
            	//reset each variable initialized by sensorNum.
            	this.resetDuiqi();
            	this.resetReduiqi();
            	this.resetFile1();
            }
            for(int i=0;i<Parameters.SensorNum;i++) {
                System.out.println(MainThread.fileStr[i]);
            }
        }
	}
	
	
	public boolean stopper(int discSymbol) {
		
		for(int i=0;i<Parameters.SensorNum;i++){
            if(this.getNReduiqi(i) == -1) {
            	discSymbol--;
            	this.resetReduiqi();
            	return true;
            }
        }
        
        return false;
	}
	
}
