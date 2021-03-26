package mutiThread;

import java.util.concurrent.CountDownLatch;
import com.h2.constant.Parameters;
import controller.ADMINISTRATOR;
import read.yang.readFile.ReadData;
import utils.printRunningParameters;


/**
 * we change the serial mechanism to parallel mechanism to raise the speed of the align process.
 * @author Hanlin Zhang.
 *
 */
public class moveBufferPosition extends Thread{
	
	private CountDownLatch downLatch;
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
		//创建读对象并进行指针挪动。
		mission();
		this.downLatch.countDown();
	}
	
	private void mission() {
		try {
			synchronized(this) {
				//新建读对象。
				manager.setNReadData(i, new ReadData(MainThread.fileStr[i],i,manager));
				//计时。
				manager.setStartInstance(System.currentTimeMillis());
				//读对象指针挪动。
				kuai = manager.getNReadData(i).readDataAlignOnline();
			}
			if(kuai==-1){
				System.out.println(MainThread.fileStr[i]+"盘网络状况不佳，重新对齐");
				manager.setNetError(true);
			}
			else {
				//计时
				manager.setEndInstance(System.currentTimeMillis());
				System.out.println(
						printRunningParameters.formToChar(MainThread.fileStr[i])
						+"盘对齐花费："+
						printRunningParameters.formToChar((manager.getEndInstance()-manager.getStartInstance())/Parameters.TEMP+"秒")
						);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			manager.setNetError(true);
		}
	}
}
