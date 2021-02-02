/**
 * 
 */
package Entrance;

import java.io.File;

import com.h2.constant.Parameters;

/**
 * 设置8种情景，以方便调试
 * 同时，根据不同情景进行了不同的级联全局变量设置，实现了快速模式设置。
 * @author Hanlin Zhang
 */
public class RunningSceneConfig {
	
	public RunningSceneConfig(int s){
		switch (s) {
		case 1:
			LOCAL_OFFLINE_STORAGE();
			break;
		case 2:
			LOCAL_OFFLINE_UNSTORAGE();
			break;
		case 3:
			REMOTE_OFFLINE_STORAGE();
			break;
		case 4:
			REMOTE_OFFLINE_UNSTORAGE();
			break;
		case 5:
			LOCAL_ONLINE_STORAGE();
			break;
		case 6:
			LOCAL_ONLINE_UNSTORAGE();
			break;
		case 7:
			REMOTE_ONLINE_STORAGE();
			break;
		case 8:
			REMOTE_ONLINE_UNSTORAGE();
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
	
	private void SetOthers() {
		closeArbitaryStart();
		closeReadSecond();
	}
	private void Set_Offline() {
		Parameters.offline = true;
	}
	private void Set_Online() {
		Parameters.offline = false;
	}
	/**
	 * 根据region设定数据库名称
	 * 
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月31日上午10:17:35
	 */
	private void remoteDB() {
		/** 远程数据库*/
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
	private void localDB() {
		/** 本地数据库*/
		Parameters.SevIP = "localhost:3306/ks";
		/** 设置数据库名称*/
		Parameters.DatabaseName5 = "mine_quake_results";
	}
	
	private void openStorage() {
		/** 打开存储*/
		Parameters.isStorageAllMotivationCSV = 1;
		Parameters.isStorageEventRecord = 1;
		Parameters.isStorageDatabase = 1;
	}
	private void closeStorage() {
		/** 关闭存储*/
		Parameters.isStorageAllMotivationCSV = 0;
		Parameters.isStorageEventRecord = 0;
		Parameters.isStorageDatabase = 0;
	}
	private void openAdjust() {
		Parameters.Adjust = true;
	}
	private void closeAdjust() {
		Parameters.Adjust = false;
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
	 * 4 channels and 7 channels both exists in all files path.
	 * 所有的传感器4通道和7通道同时存在的情景，只针对老仪器
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月2日下午6:03:13
	 */
	private void setMixPattern() {
		Parameters.TongDaoDiagnose = 0;
	}
	/**
	 * 所有的传感器均为4通道的情景，只针对老仪器
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月2日下午6:04:29
	 */
	private void set4ChannelPattern() {
		Parameters.TongDaoDiagnose = 0;
	}
	/**
	 * 所有的传感器均为7通道的情景，只针对老仪器
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月2日下午6:06:05
	 */
	private void set7ChannelPattern() {
		Parameters.TongDaoDiagnose = 1;
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
