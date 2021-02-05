package com.h2.constant;

/**
 * The constant and partial static variable used in this procedure.
 * @author Yilong Zhang, Baishuo Han, Hanlin Zhang, RQ Ma, Rui Cao, et al.
 * @modified 2020-12-30
 */
public class Parameters
{
	
//---------------------need to modify when we distribute on the central machine in different locations.-------------------------- 
	/**
	 * 传感器的采样频率，单位是hz/s，文档中是10k，表示每秒有10000条数据
	 * 一般设置为：实际频率-200，比如实际频率为5k，则我们设置频率为4800
	 */
	public static int FREQUENCY = 4800;// 单位hz/s
	/**
	 * 第一个GPS波形左端到时间线的时间距离（秒），所以当部署时，需要查看各个测点方波起始处是否为整秒
	 * 整秒时间是否是方波由低电平到高电平的位置，不是则看刘老师软件中整秒距离方波由低到高电平的秒数，写入该位置
	 * 比如当前整秒与方波相差0.3s则该变量值为0.3.
	 */
	public static double distanceToSquareWave = 0.17;
	/**
	 * 短时窗平均值与长时窗平均值的比值大于1.5，此比值暂时不需要进行调整了
	 * 在调试状态时，比值为1.4，且没有任何阈值限制，可以测试计算模块，但只能在集中式环境下测试，分布式状态下无法测试
	 */
	public static double ShortCompareLong = 1.5;
	public static double ShortCompareLongAdjust=1.4;
	/**
	 * 设置判断激发窗口的扩展判定范围（个）
	 * 在激发处的后面进行判断，若在阈值外，则认定为是一个事件
	 * 这些参数一般情况下不用修改
	 */
	public static int beforeRange = (Parameters.FREQUENCY+200)/10;
	public static int afterRange = (Parameters.FREQUENCY+200)/10;
	public static int refineRange = (int) ((Parameters.FREQUENCY+200)*1.2);
	/**
	 * 阈值限定激发条件，可能会误触发，需要根据各地矿区的实际情况确定
	 */
	public static double beforeRange_Threshold = 50000;
	public static double afterRange_ThresholdMin = 500;
	public static double afterRange_ThresholdMax = 1000;
	public static double refineRange_ThresholdMin = 500;
	public static double refineRange_ThresholdMax = 2000;
	/**
	 * 设置传感器的数量，通过设定主函数中的fileStr设置
	 */
	public static int SensorNum = 10;
	/**
	 * when we will store data to database, we need to set this variable to 1.
	 */
	public static int isStorageDatabase = 1;
	/**
	 * when we will store all motivation sensor data to csv file, we need to set this variable to 1.
	 */
	public static int isStorageAllMotivationCSV = 1;
	/**
	 * when we will store record of each event, we need to set this variable to 1.
	 */
	public static int isStorageEventRecord = 1;
	/**
	 * 当盘符信号不是很强时，需要进行延迟处理，以使程序能够延迟启动，避免出现频繁请求，浪费流量
	 */
	public static int isDelay = 0;
	/**
	 * 5台站、3台站存入的数据库表名
	 */
	public static String DatabaseName5 = "mine_quake_results";
	/**
	 * 远程数据库IP地址，在部署时修改，但一般情况下不用修改
	 */
	public static String SevIP = "localhost:3306/KS";
	/**
	 * if adjust the procedure to read the second new file endwith hfmed
	 * please turn this variable to true.
	 */
	public static boolean readSecond=false;
	/**
	 * true为调试状态，激发次数增大，且没有激发限时，便于测试数据库与相关功能的正确性和bug
	 * false为正常运行状态，所有参数均按照当地实际参数设置
	 */
	public static boolean Adjust=false;
	
//-------------------------do not need to modified when distribute in different locations-------------------------------------------------------------------------
	/**长时窗的时长，单位是毫秒*/
	public static final int LONGTIMEWINDOW = 50;// 单位是毫秒
	/**短时窗的时长，单位是毫秒*/
	public static final int SHORTTIMEWINDOW = 10;// 单位是毫秒
	/**
	 * 读取数据的长度（秒）
	 */
	public static int readLen = 10;
	/**
	 * 用于单位转换，采样频率是秒，长短时窗的单位是毫秒
	 */
	private static final int TEMP = 1000;// 单位转换
	/**
	 * 用于通道转换中使用的阈值，HEAD表示通道上限与下限，超过上限或下限，则转换大量程通道进行判断与计算
	 */
	public static final int HEAD = 32767;
	public static final int TAIL = -32768;
	/**
	 * 长时窗采样点个数
	 */
	public static final int N1 = LONGTIMEWINDOW * (FREQUENCY+200) / TEMP;
	/**
	 * 短时窗采样点个数
	 */
	public static final int N2 = SHORTTIMEWINDOW * (FREQUENCY+200) / TEMP;
	/**
	 * (一个长时窗+一个短时窗)时窗采样点总个数
	 */
	public static final int N = (LONGTIMEWINDOW + SHORTTIMEWINDOW) * (FREQUENCY+200) / TEMP;
	/**
	 * 方差，计算持续时间和持续震级时使用
	 */
	public static double refineRange_variance = 0.0;
	/**
	 * 距离其他传感器的传输花费时间，大于1s则认为不时同时发生的事件，但要根据实际点之间的距离和波速进行调整。
	 */
	public static int IntervalToOtherSensors=2;
	/**
	 * when it is true, then the time interval among all sensors are turn on.
	 */
	public static boolean SSIntervalToOtherSensors=true;
	/**
	 * 长短时窗每次移动的距离（滑动窗口的跳数），暂时设置为移动100条数据
	 * 该值设置太小，则可能由于电脑性能不行，(can not satisfy the real-time condition.)
	 * But it direct affect the precision of this procedure.
	 */
	public static int INTERVAL = 50;
	/**
	 * P波波速，只能通过放炮准确测定，否则只能估算，对于定位结果影响较大
	 */
	public static int C = 3850;
	/**
	 * S波波速，通过P波波速计算，三台站定位使用，对定位结果影响较大
	 */
	public static final double S=C/Math.sqrt(3);
	/**
	 * 从0-5依次为各个盘符的背景噪声，背景噪声必须在传感器布置到矿区固定后，才能通过长时间观察确定
	 * 这个顺序必须与启动时的传感器序号顺序一致
	 */
	public static final double backGround[] ={29.0,17.0,12.5,5.6,0};
	public static final double backGroundVariance[] = {};
	/**
	 * 通道数量跳过字节设置，在旧设备上使用
	 */
	public static int WenJianTou = 284;//4通道跳过242，7通道跳过284,this variable is changed by ReadData class and 
	public static int ShuJuTou1=10;//新的设备只有10个字节的头，接下来就是数据。
	public static int ShuJu=210;//数据占210个字节。
	public static int Shi=10;//每次读10个字节。
	/**
	 * 在新设备上使用
	 */
	public static int Yizhen = 220;//新设备一帧是220个字节其中前10个字节是头。
	public static int YIMiaoG=15720;//一秒一共有15720个字节,算上了每帧的10个字节头
	public static int YIMiao=15000;//一秒的数据共有15000个字节
	public static int San=3;//3个字节进行显示
	/**
	 * The during time we set to cut the data from 30s data.
	 */
	public static int startTime = 3;//开始的截取时间，激发位置前面5s
	public static int endTime = 15;//结束的截取时间，激发位置后面10s
	public static final int READTIMER = startTime + endTime;// 单位是秒
	/**
	 * 通道数为123时，使用123通道
	 * 通道数为456时，使用456通道
	 * 通道数为123456时，使用123456通道
	 */
	public static int TongDaoDiagnose=0;//If there has only one sensor's channel numbers are 4, this variable becomes 0.
	public static int motivationDiagnose=1;//加上精细判断。
	/**
	 * the record position.
	 */
	public static final String prePath = "D:/data/ConstructionData/";
	public static String AbsolutePath5_record = prePath;
	
	public static String AbsolutePath_CSV3 = prePath + "3moti/";
	public static String AbsolutePath_CSV5 = prePath + "5moti/";
	public static String AbsolutePath_allMotiTime_record = prePath + "TimeRecords.csv";
	/**
	 * true indicate we will minus a fixed value on the magnitude.
	 * false indicate we will not minus a fixed value on the magnitude.
	 */
	public static boolean MinusAFixedOnMagtitude = true;
	public static double MinusAFixedValue = 2.8;
	/**
	 * plus times
	 */
	public static final double plusSingle_coefficient = 0.00001562;
	public static final double plusDouble_coefficient_45 = 0.00000298;
	public static final double plusDouble_coefficient_12 = 0.00004768;
	/** 封装后缀*/
	public final static String suffix[] = {"HFMED", "bin"};
	
	/**
	 * control to run procedure in a offline way.
	 */
	public static boolean offline=false;
	/**
	 * the time to read when procedure start.
	 */
	public static String StartTimeStr = "190711080000";
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
