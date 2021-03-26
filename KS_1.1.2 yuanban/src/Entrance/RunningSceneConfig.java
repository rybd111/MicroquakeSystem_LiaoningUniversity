/**
 * 
 */
package Entrance;

import com.h2.constant.Parameters;

import utils.printRunningParameters;

/**
 * 设置8种情景，以方便调试
 * 同时，根据不同情景进行了不同的级联全局变量设置，实现了快速模式设置。
 * @author Hanlin Zhang
 */
public class RunningSceneConfig {
	
	public RunningSceneConfig() {
	}
	
	/**
	 * 配置情景模式
	 * @param s
	 */
	public RunningSceneConfig(int s){
		switch (s) {
		case 0:
			ADJUST_LOCAL();
			printRunningParameters.printModel("ADJUST_LOCAL");
			break;
		case 1:
			LOCAL_OFFLINE_STORAGE();
			printRunningParameters.printModel("LOCAL_OFFLINE_STORAGE");
			break;
		case 2:
			LOCAL_OFFLINE_UNSTORAGE();
			printRunningParameters.printModel("LOCAL_OFFLINE_UNSTORAGE");
			break;
		case 3:
			REMOTE_OFFLINE_STORAGE();
			printRunningParameters.printModel("REMOTE_OFFLINE_STORAGE");
			break;
		case 4:
			REMOTE_OFFLINE_UNSTORAGE();
			printRunningParameters.printModel("REMOTE_OFFLINE_UNSTORAGE");
			break;
		case 5:
			LOCAL_ONLINE_STORAGE();
			printRunningParameters.printModel("LOCAL_ONLINE_STORAGE");
			break;
		case 6:
			LOCAL_ONLINE_UNSTORAGE();
			printRunningParameters.printModel("LOCAL_ONLINE_UNSTORAGE");
			break;
		case 7:
			REMOTE_ONLINE_STORAGE();
			printRunningParameters.printModel("REMOTE_ONLINE_STORAGE");
			break;
		case 8:
			REMOTE_ONLINE_UNSTORAGE();
			printRunningParameters.printModel("REMOTE_ONLINE_UNSTORAGE");
			break;
		default:
			System.out.println("请选择模式");
			System.exit(0);
			break;
		}
		
	}
	
	private void LOCAL_OFFLINE_STORAGE() {
		Set_Offline();

		localDB();
		openStorage();
		SetOthers();
	}
	private void LOCAL_OFFLINE_UNSTORAGE() {
		Set_Offline();
		
		localDB();
		closeStorage();
		SetOthers();
	}
	private void REMOTE_OFFLINE_STORAGE() {
		Set_Offline();
		
		remoteDB();
		openStorage();
		SetOthers();
	}
	private void REMOTE_OFFLINE_UNSTORAGE() {
		Set_Offline();
		
		remoteDB();
		closeStorage();
		SetOthers();
	}
	private void LOCAL_ONLINE_STORAGE() {
		Set_Online();
		
		localDB();
		openStorage();
		SetOthers();
	}
	private void LOCAL_ONLINE_UNSTORAGE() {
		Set_Online();
		
		localDB();
		closeStorage();
		SetOthers();
	}
	private void REMOTE_ONLINE_STORAGE() {
		Set_Online();
		
		remoteDB();
		openStorage();
		SetOthers();
	}
	private void REMOTE_ONLINE_UNSTORAGE() {
		Set_Online();
		
		remoteDB();
		closeStorage();
		SetOthers();
	}
	private void ADJUST_LOCAL() {
		LOCAL_ONLINE_UNSTORAGE();
		openAdjust();
	}
	
	
	private void SetOthers() {
		closeArbitaryStart();
		closeReadSecond();
	}
	
	private void Set_Offline() {Parameters.offline = true;}
	private void Set_Online() {Parameters.offline = false;}
	
	/** 远程数据库*/
	private void remoteDB() {
		
		Parameters.SevIP = "182.92.239.30:3306/ks";
		/** 设置数据库名称*/
		switch(Parameters.region) {
		case "pingdingshan":
			Parameters.DatabaseName5 = "mine_quake_results_pingdingshan";
			break;
		case "hongyang":
			Parameters.DatabaseName5 = "mine_quake_results_hongyang";
			break;
		case "madaotou":
			Parameters.DatabaseName5 = "mine_quake_results_madaotou";
			break;
		}
	}
	/** 本地数据库*/
	private void localDB() {
		Parameters.SevIP = "localhost:3306/ks";
		/** 设置数据库名称*/
		Parameters.DatabaseName5 = "mine_quake_results";
	}
	/** 打开存储*/
	private void openStorage() {
		Parameters.isStorageAllMotivationCSV = 1;
		Parameters.isStorageEventRecord = 1;
		Parameters.isStorageDatabase = 1;
	}
	/** 关闭存储*/
	private void closeStorage() {
		Parameters.isStorageAllMotivationCSV = 0;
		Parameters.isStorageEventRecord = 0;
		Parameters.isStorageDatabase = 0;
	}
	/**开启调试模式*/
	private void openAdjust() {
		Parameters.Adjust = true;
		//关闭传感器间的到时差判断。
		Parameters.SSIntervalToOtherSensors = false;
	}
	private void closeAdjust() {
		Parameters.Adjust = false;
		//开启传感器间的到时差判断。
		Parameters.SSIntervalToOtherSensors = false;
	}
	private void openArbitaryStart() {
		Parameters.isDelay = 1;
	}
	private void closeArbitaryStart() {
		Parameters.isDelay = 0;
	}
	private void openReadSecond() {
		Parameters.readSecond = true;
	}
	private void closeReadSecond() {
		Parameters.readSecond = false;
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月30日下午3:11:34
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RunningSceneConfig r = new RunningSceneConfig(1);
	}

}
