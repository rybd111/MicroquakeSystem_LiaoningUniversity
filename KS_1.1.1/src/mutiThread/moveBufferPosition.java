package mutiThread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import com.h2.constant.Parameters;

import controller.ADMINISTRATOR;
import read.yang.readFile.FindNewFile;
import read.yang.readFile.ReadData;
import read.yang.readFile.findNew;


/**
 * we change the serial mechanism to parallel mechanism to raise the speed of the align process.
 * @author Hanlin Zhang.
 *
 */
public class moveBufferPosition extends Thread{
	
	private CountDownLatch downLatch;
	public ReadData readDataArray;
	private int kuai;
	private int i;
	private ADMINISTRATOR manager;
	
	public moveBufferPosition(CountDownLatch threadSignal_duiqi,int i,ADMINISTRATOR manager) {
		this.downLatch=threadSignal_duiqi;
		this.kuai=0;
		this.i = i;
		this.manager = manager;
	}
	public void run() {
		//-------------------------------------------------------新建读对象
		if(manager.isMrMa[i]==false) {
			try {
				readDataArray=new ReadData(MainThread.fileStr[i],i,manager); 
				MainThread.m1 = System.currentTimeMillis();
				kuai=readDataArray.readDataDui(MainThread.fileStr[i],i);
				if(kuai==-1){
					System.out.println(MainThread.fileStr[i]+"盘网络状况不佳，重新对齐");
					ReadData.netError=true;
					MainThread.reduiqi[i]=-1;
				}
				else {
					MainThread.m2 = System.currentTimeMillis();
					System.out.println(MainThread.fileStr[i]+"盘对齐花费："+(MainThread.m2-MainThread.m1)/1000+"s");
					MainThread.reduiqi[i]=0;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				ReadData.netError=true;
				MainThread.reduiqi[i]=-1;
			}
		}
		else {
			try {
				readDataArray=new ReadData(MainThread.fileStr[i],i,manager); 
				MainThread.m1 = System.currentTimeMillis();
				kuai=readDataArray.readDataDui_MrMa(MainThread.fileStr[i],i);
				if(kuai==-1){
					System.out.println(MainThread.fileStr[i]+"盘网络状况不佳，重新对齐");
					ReadData.netError=true;
					MainThread.reduiqi[i]=-1;
				}
				else {
					MainThread.m2 = System.currentTimeMillis();
					System.out.println(MainThread.fileStr[i]+"盘对齐花费："+(MainThread.m2-MainThread.m1)/1000+"s");
					MainThread.reduiqi[i]=0;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				ReadData.netError=true;
				MainThread.reduiqi[i]=-1;
			}
		}
		this.downLatch.countDown();
	}
	public static int duiqiProcessing(int discSymbol,ReadData[] readDataArray) {
		//aduiqi = null;aReadData01 = null;aReadData02 = null;aReadData03 = null;aReadData04 = null;aReadData05 = null;
		String firstStatus = "第一种情况：没产生网络错误，产生了新文件！";
		System.out.println(firstStatus);
		//recordStatus.recordToTxt_status(firstStatus);
		int[] kuai = new int[Parameters.SensorNum];
		//-------------------------------------------------------新建读对象
		try {
			for(int i=0;i<MainThread.fileStr.length;i++) {
				try {
//					readDataArray[i]=new ReadData(MainThread.fileStr[i],i);
				}
				catch(Exception e) {
					System.out.println(i+"号网络状况不佳，重新对齐");
					return -1;
				}
			}
		}
		catch (Exception e1) {e1.printStackTrace();return -1;}
		//-------------------------------------------------------对齐 
		for(int i=0;i<Parameters.SensorNum;i++) readDataArray[i].timeCount=0;
		
		try {
			MainThread.m1 = System.currentTimeMillis();
			for(int i=0;i<Parameters.SensorNum;i++) {
				kuai[i]=readDataArray[i].readDataDui(MainThread.fileStr[i],i);
				if(kuai[i]==-1){System.out.println(i+"号网络状况不佳，重新对齐");continue;}
			}
			MainThread.m2 = System.currentTimeMillis();
			System.out.println("对齐花费："+(MainThread.m2-MainThread.m1)/1000+"s");
			return 0;
		}
		catch (Exception e) {e.printStackTrace();return -1;}
	}
}
