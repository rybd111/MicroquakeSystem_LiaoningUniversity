/**
 * 
 */
package utils;

/**
 * @author Hanlin Zhang
 */
public class Parameters {
	/**
	 * 5台站、3台站存入的数据库表名
	 */
	public static String DatabaseName[] = {
			"mine_quake_station_hongyang",
			"mine_quake_station_datong",
			"mine_quake_station_pingdingshan",
			"mine_quake_station_madaotou"
			};
	/**
	 * 远程数据库IP地址，在部署时修改，但一般情况下不用修改
	 */
	public static String SevIP = "182.92.239.30/KS";
	/**where the data are reading from?
	 * There are two regions we distribute called datong, pingdingshan.
	 * This variable will effect the coordination of this procedure, please confirm it twice.
	 */
	public static String [] station= {"hongyang","datong","pingdingshan","madaotou"};
	public static String region = station[2];
	/**the data file must store in a fold which name ends with "s" or "t" or "z" or "s" and etc.
	 * Please modify this variable to adapt different mining area.
	 */
	//diskNameNum need not to modify manually.
	public static int diskNameNum = 0;
	/** we need to config this variable manually with local drive.*/
	public static final String[][] diskName = {
		{ "t" , "u" , "w" , "x" , "z" , "y" , "v" , "s" , "r"},//hongyang
		{ "v", "w", "x", "y", "z", "u", "t"},//datong
		{ "z", "t", "y", "v", "x", "w", "u"},//pingdingshan
		{ "o", "p", "q", "z"}//madaotou
	};
	public static final String[][] diskName1 = {
		{ "杨甸子" , "树碑子" , "北青堆子" , "车队" , "工业广场" , "火药库" , "南风井" , "蒿子屯" , "李大人"},//hongyang
		{ "3", "4", "5", "6", "7", "2", "1"},//datong
		{ "牛家村", "洗煤厂", "香山矿", "王家村", "工业广场", "西风井", "打钻工区"},//pingdingshan
		{ "sel", "nhy", "wmz", "tbz"}//madaotou
	};
	/**the location of all sensor which must be correspond with diskName in sequence.*/
	public static final double[][] SENSORINFO_datong = {
		{ 541689, 4422383, 1561.2 },//3号V we also need to confirm the coordination of datong for the sensors removing from the original position.
		{ 542016, 4423034, 1563.8 },//4号W
		{ 540928, 4422763, 1544 },//5号X
		{ 541940, 4422400, 1562 },//6号Y
		{ 541587, 4422614, 1554.8 },//7号Z
		{ 542291, 4422618, 1546 },//2号U
		{ 541987, 4422567, 1560.4 },//1号T
	};//从上起为牛家村、洗煤厂、香山矿、王家村、十一矿工业广场老办公楼西南角花坛、西风井、打钻工区
	
	public static final double[][] SENSORINFO_hongyang = {
		{ 41518099.807,4595388.504,22.776 },//T 杨甸子
		{ 41518060.298,4594304.927,21.926  },//U 树碑子
		{ 41520207.356,4597983.404,22.661  },//W 北青堆子
		{ 41520815.875,4597384.576,25.468  },//X 车队
		{ 41519304.125,4595913.485,23.921  },//Z 工业广场
		{ 41519926.476,4597275.978,20.705  },//Y 火药库
		{ 41516707.440,4593163.619,22.564  },//V 南风井
		{ 41516836.655,4596627.472,21.545  },//S 蒿子屯
		{ 41517290.0374,4599537.3261,24.5649 }//R 李大人
	};
	
	public static final double[][] SENSORINFO_pingdingshan = {
		{ 3744774.016, 38422332.101, 157.019 },//Z 牛家村
		{ 3743774.578, 38421827.120, 120.191 },//T 洗煤厂
		{ 3744698.415, 38421314.653, 126.809 },//Y 香山矿
		{ 3744199.610, 38423376.100, 202.175 },//V 王家村
		{ 3742996.232, 38423392.741, 117.530 },//X 十一矿工业广场老办公楼西南角花坛
		{ 3746036.362, 38419962.476, 127.009 },//W 西风井
		{ 3743713.362, 38423292.665, 139.238 }//U 打钻工区
	};
	
	public static final double[][] SENSORINFO_shuangyashan = {
		{ 44442821, 5181516, 89.0 },//the disk name is not clear.
		{ 44440849, 5181084, 115.8 },
		{ 44443148, 5178624, 110.2 },
		{ 44441763, 5179060, 104.4 },
		{ 44442327, 5180765, 93.3 }
	};//从左起为西风井、火药库、永华村、水塔、工业广场
	
	public static final double[][] SENSORINFO_madaotou = {
		{ 4409609.1825,512398.0950,1458.0541 },//O sel 
		{ 4416002.6574,517084.2615,1469.6346 },//P nhy 
		{ 4413453.8746,513392.0561,1453.3081 },//Q wmz 
		{ 4408689.1946,517174.4868,1489.6023 },//Z tbz 
	};
	/**
	 * this variable must put correspond to the variable of 'station' in sequence.
	 */
	public static final double[][][] SENSORINFO1 = {
			SENSORINFO_hongyang,
			SENSORINFO_datong,
			SENSORINFO_pingdingshan,
			SENSORINFO_madaotou};
}
