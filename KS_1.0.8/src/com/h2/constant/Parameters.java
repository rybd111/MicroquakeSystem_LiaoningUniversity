package com.h2.constant;

import mutiThread.MainThread;

/**
 * the constant and partial static variable used in this procedure.
 * @author Baishuo Han, Hanlin Zhang.
 */
public class Parameters
{
	/**长时窗的时长，单位是毫秒*/
	public static final int LONGTIMEWINDOW = 50;// 单位是毫秒
	/**短时窗的时长，单位是毫秒*/
	public static final int SHORTTIMEWINDOW = 10;// 单位是毫秒
	/**
	 * 传感器的采样频率，单位是hz/s，文档中是10k，表示每秒有10000条数据
	 */
	public static int FREQUENCY = 4800;// 单位hz/s
	public static int readLen = 10;
	public static double distanceToSquareWave = 0.17;//整秒时间是否是方波由低电平到高电平的位置，不是则看刘老师软件中整秒距离方波由低到高电平的秒数，写入该位置，比如当前整秒与方波相差0.3s则该变量值为0.3.
	/**
	 * 用于单位转换，采样频率是秒，长短时窗的单位是毫秒
	 */
	private static final int TEMP = 1000;// 单位转换
	/**
	 * 用于通道转换中使用的阈值，HEAD表示通道上限与下限
	 */
	public static final int HEAD = 32767;
	public static final int TAIL = -32768;
	
	/**
	 * 存储虚窗口的数据，虚窗口保存上一个10秒数据中的后5秒数据，这个数用于跳过前边的数据
	 */
//	public static final int COUNT5sRECORD = 5 * FREQUENCY;
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
	 * 短时窗平均值与长时窗平均值的比值大于2.5，上次矫正
	 */
	public static double ShortCompareLong = 1.5;
	public static double ShortCompareLongAdjust=1.4;

	public static int afterRange = (Parameters.FREQUENCY+200)/10;
	public static int refineRange = (int) ((Parameters.FREQUENCY+200)*1.2);
//	public static final double afterRange_Threshold123 = 1000;
	public static double afterRange_Threshold456 = 500;
	public static double afterRange_ThresholdMin = 500;
	public static double afterRange_ThresholdMax = 1000;
	public static double refineRange_ThresholdMin = 500;
	public static double refineRange_ThresholdMax = 2000;
	
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
	 * 10s内时窗的个数，因为每次计算的数据都是10秒内的数据，目前传感器的时窗个数已经用不上了
	 */
	public static final int WINDOWNUMBER = 166;
	/**
	 * 长短时窗每次移动的距离（滑动窗口的跳数），暂时设置为移动100条数据
	 * 该值设置太小，则可能由于电脑性能不行，导致实时读取数据受限
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
	 * 设置传感器的数量，通过设定主函数中的fileStr设置
	 */
	public static int SensorNum = 7;
	/**
	 * 从0-5依次为各个盘符的背景噪声，背景噪声必须在传感器布置到矿区固定后，才能通过长时间观察确定
	 * 这个顺序必须与启动时的传感器序号顺序一致
	 */
	public static final double backGround[] ={29.0,17.0,12.5,5.6,0};
	public static final double backGroundVariance[] = {};
	/**
	 * 在这里写死传感器的坐标信息，主要有经度纬度海拔
	 * 传感器的信息 经度，维度，海拔，坐标为CAD单位，需在程序运行前设置
	 */
	public static final double[][] SENSORINFO = {
			{ 41518099.807,4595388.504,22.776 },//T
			{ 41518060.298,4594304.927,21.926  },//U
			{ 41520207.356,4597983.404,22.661  },//W
			{ 41520815.875,4597384.576,25.468  },//X
			{ 41519304.125,4595913.485,23.921  },//Z
			{ 41519926.476,4597275.978,20.705  },//Y
			{ 41516707.440,4593163.619,22.564  },//V
			{ 41516836.655,4596627.472,21.545  },//S
			{ 41517290.0374,4599537.3261,24.5649  }//R
	};//从上起为牛家村、洗煤厂、香山矿、王家村、十一矿工业广场老办公楼西南角花坛、西风井、打钻工区
	public static final String region = "红阳";
	/**
	 * 通道数量跳过字节设置，在旧设备上使用
	 */
	public static int WenJianTou = 284;//4通道跳过242，7通道跳过284,this variable is changed by ReadData class and 
	
	public static int ShuJuTou1=10;//新的设备只有10个字节的头，接下来就是数据。
	public static int ShuJu=210;//数据占210个字节。
	public static int Shi=10;//每次读10个字节。
	
	public static int Yizhen = 220;//新设备一帧是220个字节其中前10个字节是头。
	public static int YIMiaoG=15720;//一秒一共有15720个字节,算上了每帧的10个字节头
	public static int YIMiao=15000;//一秒的数据共有15000个字节
	public static int San=3;//3个字节进行显示

	/**
	 * The during time we set to cut the data from 30s data.
	 */
	public static int startTime = 3;//开始的截取时间，激发位置前面5s
	public static int endTime = 15;//结束的截取时间，激发位置后面10s
	/**
	 * 通道数为123时，使用123通道
	 * 通道数为456时，使用456通道
	 * 通道数为123456时，使用123456通道
	 */
//	public static final int TongDao=123456;
	public static int TongDaoDiagnose=0;//If there has only one sensor's channel numbers are 4, this variable becomes 0.
	public static int motivationDiagnose=1;//加上精细判断。
	/**
	 * 初始化盘符信息，用于持续时间震级判断、激发容器数据的保存，须与Mainthread中的fileStr数组相同
	 * 必须与传感器数量相同，且该变量必须在每次部署到新地点后，直接修改，否则无法运行程序！！！！
	 */
	public static String[] diskName = { "t:/" , "u:/" , "w:/" , "x:/" , "z:/" , "y:/" , "v:/" , "s:/" , "r:/"};
	/**
	 * when we will store data to txt file, we need to set this variable to 1.
	 */
	public static final int isStorageToTxt = 0;
	/**
	 * when we will store one minute long data, we need to set this variable to 1.
	 */
	public static int isStorageOneMinuteData = 0;
	/**
	 * when we will store data to database, we need to set this variable to 1.
	 */
	public static int isStorageDatabase = 1;
	/**
	 * when we will store each motivation sensor data to csv file, we need to set this variable to 1.
	 */
	public static int isStorageEachMotivationCSV = 0;
	/**
	 * when we will store all motivation sensor data to csv file, we need to set this variable to 1.
	 */
	public static int isStorageAllMotivationCSV = 1;
	/**
	 * when we will store record of each event, we need to set this variable to 1.
	 */
	public static int isStorageEventRecord = 1;
	public static int isDelay = 0;
	/**
	 * 设置三台站、五台站txt存储路径
	 * 默认为：D://ConstructionData//3moti//
	 */
	public static String AbsolutePath3 = "D:/data/ConstructionData/3moti/";
	public static String AbsolutePath5 = "D:/data/ConstructionData/5moti/";
	/**
	 * the record position.
	 */
//	public static final String AbsolutePath3_record = "D:/data/";
	public static String AbsolutePath5_record = "D:/data/ConstructionData/";

	public static String AbsolutePath_CSV3 = "D:/data/ConstructionData/3moti/";
	public static String AbsolutePath_CSV5 = "D:/data/ConstructionData/5moti/";
	public static String AbsolutePath_allMotiTime_record = "D:/data/ConstructionData/TimeRecords.csv";
	/**
	 * 1分钟的数据存放位置
	 */
	public static final String AbsolutePathMinute = "D:/data/ConstructionData/oneMinutedata/";
	/**
	 * 5台站、3台站存入的数据库表名
	 */
	public static final String DatabaseName5 = "mine_quack_results";
	public static final String DatabaseName5_updated = "mine_quack_5_results_updated";
	public static final String DatabaseName4 = "mine_quack_4_results";
	public static final String DatabaseName3 = "mine_quack_3_results";
	public static final String DatabaseName3_updated = "mine_quack_3_results_updated";
	/**
	 * 测试重复变量，当出现重复盘符时，该变量起作用。
	 * @description
	 * 该变量与盘符数量相同（不同的盘符），如x、x、x、y、y，则此时盘符数量为2
	 * @description
	 * 我们预留了9个传感器的位置，但initPanfu长度一定要大于diskName数组的长度，否则可能会出错
	 * @description
	 * 每个元素为0时，表示当前传感器对应的盘符没有存储过文件，存储过后，将对应的位置元素置1
	 * @description
	 * 这样再出现该盘符时，不会再次存储一次，造成磁盘空间的浪费和数据的冗余，导致后期处理数据的麻烦
	 */
	public static final int [] initPanfu = {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0};
	/**
	 * true为调试状态，激发次数增大，且没有激发限时，便于测试数据库与相关功能的正确性和bug
	 * false为正常运行状态，所有参数均按照当地实际参数设置
	 */
	public static boolean Adjust=false;
	/**
	 * true indicate we will minus a fixed value on the magnitude.
	 * false indicate we will not minus a fixed value on the magnitude.
	 */
	public static boolean MinusAFixedOnMagtitude = true;
	public static double MinusAFixedValue = 2.8;
	/**
	 * if adjust the procedure to read the second new file endwith hfmed
	 * please turn this variable to true.
	 */
	public static boolean readSecond=false;
	/**
	 * plus times
	 */
	public static double plusSingle_coefficient = 0.00001562;
	public static double plusDouble_coefficient_45 = 0.00000298;
	public static double plusDouble_coefficient_12 = 0.00004768;
	
	/**
	 * control to run procedure in a offline way.
	 */
	public static boolean offline=true;
	
	/**where the data are reading from?
	 * There are two regions we distribute called datong, pingdingshan.
	 * */
	private static String [] station= {"hongyang","datong","pingdingshan"};
	public static String region_offline =station[0];
	/**
	 * the time to read when procedure start.
	 */
//	public static final String timeStr = "200214123000";
	public static String timeStr = "200711080000";
	/**the data file must store in a fold which name ends with "1" or "2" or "3" or "4" and etc.
	 * Please modify this variable to adapt different mining area.
	 * */
//	public static final String[] diskName_offline = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
	public static final String[] diskName_offline = { "s" , "t" , "u" , "w" , "x" , "y" , "z" , "v" , "r"};
//	public static final String[] diskName_offline = { "t" , "u" , "w" , "v" , "z" , "y" , "x"};
	/**the location of all sensor which must be correspond with diskName_offline in sequence.*/
	public static final double[][] SENSORINFO_offline_datong = {
//		 { 1987, 2567, 1560.4 }, //   1号S  Test1
//         { 2291, 2618, 1546 },   //   2号U  Test2
//         { 1689, 2383, 1561.2 }, //   3号V  Test3
//         { 2016, 3034, 1563.8 }, //   4号W  Test4
//         { 928, 2763, 1544 },   //   5号X  Test5
//         { 1940, 2400, 1562 },   //   6号Y  Test6
//         { 1587, 2614, 1554.8 }, //   7号Z  Test7
	         
		{ 541689, 4422383, 1561.2 },//3号V we also need to confirm the coordination of datong for the sensors removing from the original position.
		{ 542016, 4423034, 1563.8 },//4号W
		{ 540928, 4422763, 1544 },//5号X
		{ 541940, 4422400, 1562 },//6号Y
		{ 541587, 4422614, 1554.8 },//7号Z
		{ 542291, 4422618, 1546 },//2号U
		{ 541987, 4422567, 1560.4 },//1号T
	};//从上起为牛家村、洗煤厂、香山矿、王家村、十一矿工业广场老办公楼西南角花坛、西风井、打钻工区
	public static final double[][] SENSORINFO_offline_hongyang = {
			{ 41516836.655,4596627.472,21.545  },//S Test1
			{ 41518099.807,4595388.504,22.776 },//T Test2
			{ 41518060.298,4594304.927,21.926  },//U Test3
			{ 41520207.356,4597983.404,22.661  },//W Test4
			{ 41520815.875,4597384.576,25.468  },//X Test5
			{ 41519926.476,4597275.978,20.705  },//Y Test6
			{ 41519304.125,4595913.485,23.921  },//Z Test7
			{ 41516707.440,4593163.619,22.564  },//V Test8
			{ 41517290.0374,4599537.3261,24.5649  }//R Test9
	};
	
	public static final double[][] SENSORINFO_offline_pingdingshan = {
			{ 3744774.016, 38422332.101, 157.019 },//T Test1 牛家村
			{ 3743774.578, 38421827.120, 120.191 },//U Test2 洗煤厂
			{ 3744698.415, 38421314.653, 126.809 },//W Test3 香山矿
			{ 3744199.610, 38423376.100, 202.175 },//V Test4 王家村
			{ 3742996.232, 38423392.741, 117.530 },//Z Test5 工业广场
			{ 3746036.362, 38419962.476, 127.009 },//Y Test6 西风井
			{ 3743713.362, 38423292.665, 139.238 }//X Test7 打钻工区
	};
	
	public static final double[][] SENSORINFO_offline_shuangyashan = {
			{ 44442821, 5181516, 89.0 },//the disk name is not clear.
			{ 44440849, 5181084, 115.8 },
			{ 44443148, 5178624, 110.2 },
			{ 44441763, 5179060, 104.4 },
			{ 44442327, 5180765, 93.3 }
	};//从左起为西风井、火药库、永华村、水塔、工业广场
	
	/**motiData storage path, you can modify this path to adapt to your need.
	 * Then, we need to create several folds as the same as the length of diskName_offline
	 * such as "Text1","Text2","Text3","Text4", and et al.
	 * */
	public static final String AbsolutePath3_offline = "D:/data/ConstructionData/3moti/";
	public static final String AbsolutePath5_offline = "D:/data/ConstructionData/5moti/";
	public static final String AbsolutePath_CSV3_offline = "D:/data/ConstructionData/3moti/";
	public static final String AbsolutePath_CSV5_offline = "D:/data/ConstructionData/5moti/";
	
	/**
	 * 程序注释需要加上前标，以防止出错无法判断出错位置
	 * 命名规则：各部分提示符_自部分名称，如计算部分的近震震级表示为：compute_nearQuake
	 * 各自部分的提示符：
	 * 计算部分：compute
	 * 	 进震震级：nearQuake
	 *     能量：energy
	 *   持续时间震级：duiringQuake
	 *   三点定位：threeLocation
	 *   五点定位：fiveLocation
	 *   主事件定位：mainLocation
	 *   
	 * 读部分：read
	 *  对齐：readDatadui
	 *  读1s：readData
	 *  
	 * 衔接部分（主程序部分）：main
	 *  启动两大步：
	 *    查找最新文件：find
	 *    对齐：duiqi
	 * 
	 * 数据库存储部分：DB
	 *  写入：write
	 *  查询：select
	 *  
	 * 前后台交互部分：sev
	 *  查询：select
	 *  传输：translate1~translateN
	 *  
	 */
	public static final String compute_nearQuake = "compute_nearQuake";
	public static final String compute_energy = "compute_energy";
	public static final String compute_duiringQuake = "compute_duiringQuake";
	public static final String compute_threeLocation = "compute_threeLocation";
	public static final String compute_fiveLocation = "compute_fiveLocation";
	public static final String compute_mainLocation = "compute_mainLocation";
	
	public static final String read_readDatadui = "read_readDatadui";
	public static final String read_readData = "read_readData";
	
	public static final String main_find = "main_find";
	public static final String main_duiqi = "main_duiqi";
	
	public static final String DB_write = "DB_write";
	public static final String DB_select = "DB_select";
	
	public static final String sev_select = "sev_select";
	public static final String sev_translate1 = "sev_translate1";
	public static final String sev_translate2 = "sev_translate2";
	public static final String sev_translate3 = "sev_translate3";
	public static final String sev_translate4 = "sev_translate4";
	public static final String sev_translate5 = "sev_translate5";

}
