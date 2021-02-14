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
	
	public moveBufferPosition(
			CountDownLatch threadSignal_duiqi,
			int i,
			ADMINISTRATOR manager) {
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
				manager.setStartInstance(System.currentTimeMillis());
				kuai=readDataArray.readDataDui(MainThread.fileStr[i],i);
				if(kuai==-1){
					System.out.println(MainThread.fileStr[i]+"盘网络状况不佳，重新对齐");
					manager.setNetError(true);
					manager.setNReduiqi(i, -1);
				}
				else {
					manager.setEndInstance(System.currentTimeMillis());
					System.out.println(MainThread.fileStr[i]+"盘对齐花费："+(manager.getEndInstance()-manager.getStartInstance())/1000+"s");
					manager.setNReduiqi(i, 0);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				manager.setNetError(true);
				manager.setNReduiqi(i, -1);
			}
		}
		else {
			try {
				readDataArray=new ReadData(MainThread.fileStr[i],i,manager); 
				manager.setStartInstance(System.currentTimeMillis());
				kuai=readDataArray.readDataDui_MrMa(MainThread.fileStr[i],i);
				if(kuai==-1){
					System.out.println(MainThread.fileStr[i]+"盘网络状况不佳，重新对齐");
					manager.setNetError(true);
					manager.setNReduiqi(i, -1);
				}
				else {
					manager.setEndInstance(System.currentTimeMillis());
					System.out.println(MainThread.fileStr[i]+"盘对齐花费："+(manager.getEndInstance()-manager.getStartInstance())/1000+"s");
					manager.setNReduiqi(i, 0);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				manager.setNetError(true);
				manager.setNReduiqi(i, -1);
			}
		}
		this.downLatch.countDown();
	}
}
