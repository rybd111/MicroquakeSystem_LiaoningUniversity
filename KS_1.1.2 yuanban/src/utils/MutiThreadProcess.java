/**
 * 
 */
package utils;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.ui.internal.about.AboutData;

import com.h2.constant.Parameters;
import com.h2.strength.calStrength;

import DataExchange.Sensor;
import DataExchange.vectorExchange;
import controller.ADMINISTRATOR;
import middleware.tool.FindHistoryFile_GetData;
import mutiThread.MainThread;
import mutiThread.moveBufferPosition;
import mutiThread.obtainHeadTime;
import mutiThread.readTask;
import read.yang.readFile.ReadData;

/**
 * @author Hanlin Zhang
 */
public class MutiThreadProcess {

	/**
	 * Strength muti-thread process.
	 * @param s
	 * @param location_refine
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月6日上午9:11:37
	 */
	public static Sensor[] calStrengthMuti(Sensor[] s, Sensor location_refine) {
		//Compute the near quake magnitude which data only must from the motivated sensors we selected, so the sensors1 is used.
		ExecutorService executor_cal = Executors.newFixedThreadPool(Parameters.SensorNum);
		final CountDownLatch threadSignal_cal = new CountDownLatch(s.length);
		calStrength c[] = new calStrength[Parameters.SensorNum];		
		//calculate the near earthquake magnitude et al.
		for(int i=0;i<s.length;i++) {
			c[i] = new calStrength(s[i], location_refine, i, threadSignal_cal);
			executor_cal.execute(c[i]);//计算单个传感器的近震震级
		}
		try {threadSignal_cal.await();}
        catch (InterruptedException e1) {e1.printStackTrace();}
		executor_cal.shutdown();
		
		return s;
	}
	
	/**
	 * align muti-thread process.
	 * @param manager
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月6日上午9:11:10
	 */
	public static void alignMuti(ADMINISTRATOR manager) {
		final CountDownLatch threadSignal_duiqi = new CountDownLatch(Parameters.SensorNum);
		ExecutorService executor_duiqi = Executors.newFixedThreadPool(Parameters.SensorNum);
        //we obtain the time distance among these sensors' remote disk.
        for(int i=0;i<Parameters.SensorNum;i++) {
            obtainHeadTime aDuiQi = new obtainHeadTime(threadSignal_duiqi, MainThread.fileStr[i], i, manager);
            executor_duiqi.execute(aDuiQi);
        }
        
        try {threadSignal_duiqi.await();}catch (InterruptedException e1) {e1.printStackTrace();}
        executor_duiqi.shutdown();
	}
	
	/**
	 * move buffer muti-thread process.
	 * @param mo
	 * @param manager
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月6日上午9:20:44
	 */
	public static void moveBufferMuti(moveBufferPosition[] mo, ADMINISTRATOR manager) {
		final CountDownLatch threadSignal_find = new CountDownLatch(Parameters.SensorNum);
        ExecutorService executor_find = Executors.newFixedThreadPool(Parameters.SensorNum);
        for(int i=0;i<Parameters.SensorNum;i++) {
            mo[i] = new moveBufferPosition(threadSignal_find, i, manager);
            executor_find.execute(mo[i]);
        }
        try {threadSignal_find.await();}catch (InterruptedException e1) {e1.printStackTrace();}
        executor_find.shutdown();
	}
	
	/**
	 * readData muti-thread process.
	 * @param dataRecArray
	 * @param readDataArray
	 * @param manager
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月6日上午9:20:56
	 */
	public static void readDataMuti(vectorExchange[] dataRecArray, ReadData[] readDataArray, ADMINISTRATOR manager) {
		ExecutorService executor = Executors.newFixedThreadPool(Parameters.SensorNum);
        final CountDownLatch threadSignal = new CountDownLatch(Parameters.SensorNum);

        for (int i = 0; i < Parameters.SensorNum; i++) {
            readTask task = new readTask(threadSignal, i, dataRecArray[i], readDataArray[i], manager);
            executor.execute(task);
        }
        try {threadSignal.await();} catch (InterruptedException e1) {e1.printStackTrace();}
        executor.shutdown();
	}
	
	public static void getFileMuti(String[] sourcePath, String destiPath, String timeStr) throws ParseException {
		ExecutorService executor = Executors.newFixedThreadPool(sourcePath.length);
        final CountDownLatch threadSignal = new CountDownLatch(sourcePath.length);
        Date specifyTime = String2Date.str2Date2(timeStr);
        
        for(int i=0;i<sourcePath.length;i++) {
	        FindHistoryFile_GetData findHistoryFile = new FindHistoryFile_GetData(sourcePath[i], destiPath, specifyTime,threadSignal);
	        executor.execute(findHistoryFile);
        }
        try {threadSignal.await();} catch (InterruptedException e1) {e1.printStackTrace();}
        executor.shutdown();
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月6日上午8:55:35
	 */
	public static void main(String[] args) {

	}

}
