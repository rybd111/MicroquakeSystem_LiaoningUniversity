package Entrance;

import java.io.IOException;
import java.text.ParseException;

import com.h2.constant.ConfigToParameters;
import com.h2.constant.Parameters;

import mutiThread.MainThread;
import utils.printRunningParameters;


/**
 * When we test our behind procedure, we can execute this function.
 * This is the complete background function. 
 * @author Yilong Zhang, Hanlin Zhang.
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

	public static int runningModel = 1;
	public static String prePath = "I:/矿山/矿山数据/红阳三矿/20210326/";
	
	public static void main(String[] agrs) throws IOException, ParseException {//定时器
		//只要运行程序后台，都读取配置文件。
		new ConfigToParameters();
		
		//根据情景模式选择不同的运行代码。
		if(runningModel == REMOTE_ONLINE_STORAGE || runningModel == REMOTE_ONLINE_UNSTORAGE) {
			new RunningSceneConfig(runningModel);
			printRunningParameters.printAllParameters();
		}
		else if(runningModel == LOCAL_OFFLINE_STORAGE || runningModel == LOCAL_OFFLINE_UNSTORAGE) {
			new RunningSceneConfig(runningModel);
			InitialConfig config = new InitialConfig(MainTest.prePath);
		}
		
		/** 启动主线程*/
		MainThread aMain = new MainThread();
		aMain.start();
	}
}
