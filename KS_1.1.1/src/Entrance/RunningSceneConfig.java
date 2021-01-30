/**
 * 
 */
package Entrance;

import com.h2.constant.Parameters;

/**
 * @author Hanlin Zhang
 */
public class RunningSceneConfig {

	/** SafePattern 
	 * 在线运行时安全模式配置如下：
	 * 
	 */
	@SuppressWarnings("unused")
	private boolean SafePattern = true;
	private boolean StrongSafePattern = true;
	private boolean WeakSafePattern = true;
	
	private boolean AdjustPattern_store = true;
	private boolean AdjustPattern_unstore = true;
	
	/** 是否为真实环境部署？*/
	private boolean realEnvironment = false;
	
	RunningSceneConfig(){
		
		
	}
	
	
	private void NameDBUpdate2Region() {
		switch(Parameters.region_offline) {
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
	private void remoteDB() {
		/** 远程数据库*/
		Parameters.SevIP = "182.92.239.30:3306/ks";
	}
	private void localDB() {
		/** 本地数据库*/
		Parameters.SevIP = "localhost:3306/ks";
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
	
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月30日下午3:11:34
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
