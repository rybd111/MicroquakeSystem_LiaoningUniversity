package Entrance;

import java.io.IOException;

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

	public static void main(String[] agrs) throws IOException {//定时器
		
		String prePath = "I:/矿山/矿山数据/平顶山/20201231/";
		
		/** 配置离线运行数据路径和传感器数量，根据prePath下的数据路径自动获取
		 * 具体参看MainTestInitialConfig类说明
		 * */
		MainTestInitialConfig config = new MainTestInitialConfig(prePath);
		
		/** 情景配置*/
		
		
		
		// 通过CountDownLatch 控制线程结束
		MainThread aMain = new MainThread();
		aMain.start();

	}
}
