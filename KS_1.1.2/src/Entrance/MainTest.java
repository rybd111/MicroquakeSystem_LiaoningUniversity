package Entrance;

import java.io.IOException;
import java.text.ParseException;

import mutiThread.MainThread;


/**
 * when we test our behind procedure, we can execute this function.
 * @author Yilong Zhang, Hanlin Zhang.
 *
 */
public class MainTest {
	
	public static final int LOCAL_OFFLINE_STORAGE = 1;
	public static final int LOCAL_OFFLINE_UNSTORAGE = 2;
	public static final int REMOTE_OFFLINE_STORAGE = 3;
	public static final int REMOTE_OFFLINE_UNSTORAGE = 4;
	public static final int LOCAL_ONLINE_STORAGE = 5;
	public static final int LOCAL_ONLINE_UNSTORAGE = 6;
	public static final int REMOTE_ONLINE_STORAGE = 7;
	public static final int REMOTE_ONLINE_UNSTORAGE = 8;
	//调试模式默认为本地非存储模式。
	public static final int ADJUST_LOCAL = 0;

	public static int runningModel = LOCAL_ONLINE_UNSTORAGE;
	
	public static void main(String[] agrs) throws IOException, ParseException {//定时器
		/** 情景配置，共有8种模式，每一种对应一个整形数值，在MainTest类中定义。*/
		new RunningSceneConfig(runningModel);
		
		/** 配置离线运行数据路径和传感器数量，根据prePath下的数据路径自动获取
		 * 具体参看InitialConfig类说明
		 * */
		String prePath = "I:/矿山/矿山数据/新设备数据/5/";
//		String prePath = "I:/矿山/矿山数据/红阳三矿/20200726/";
		InitialConfig config = new InitialConfig(prePath);
		
		/** 启动线程*/
		MainThread aMain = new MainThread();
		aMain.start();
	}
}
