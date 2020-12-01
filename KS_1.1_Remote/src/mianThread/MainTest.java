package mianThread;

import com.h2.main.EarthQuake;

import mutiThread.MainThread;
import visual.Preferences;


/**
 * when we test our behind procedure, we can execute this function.
 * @author Yilong Zhang, Hanlin Zhang.
 *
 */
public class MainTest {
	static String outStr = "";

	public static void main(String[] agrs) {//定时器 

		// 通过CountDownLatch 控制线程结束
		MainThread aMain = new MainThread();
		aMain.start();
//		visual_thread visual = new visual_thread();
//    	visual.start();

//		Thread aThread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while (true) {
//					System.out.println("获取数据++++"+EarthQuake.outString);
//					try {
//						Thread.sleep(4000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println("睡眠");
//				}
//			}
//		});
		/*aThread.start();*/
		// 通过唤醒 控制线程结束
		/*
		 * MainThread2 aMainThread2 =new MainThread2(outStr);
		 * System.out.println("主线程开始"); aMainThread2.start();
		 */

		// 通过notify,wait 唤醒线程机制
		/*
		 * MainThread3 aMainThread3 =new MainThread3(outStr);
		 * aMainThread3.start();
		 */
	}
}
