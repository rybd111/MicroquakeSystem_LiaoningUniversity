/**
 * 
 */
package controller;

import java.text.ParseException;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jblas.benchmark.Main;
import org.jfree.data.xy.VectorDataItem;

import com.h2.constant.Parameters;
import com.h2.main.EarthQuake;
import com.ibm.icu.text.DateFormat.BooleanAttribute;

import DataExchange.vectorExchange;
import javazoom.jl.decoder.Manager;
import mutiThread.MainThread;
import mutiThread.ReconnectToRemoteDisk;
import mutiThread.moveBufferPosition;
import mutiThread.obtainHeadTime;
import mutiThread.readTask;
import read.rqma.history.AlignFile;
import read.rqma.history.GetReadArray;
import read.yang.readFile.ReadData;
import utils.DateArrayToIntArray;
import utils.SubStrUtil;

/**
 * Manage the status of the system for replacing static variables.
 * @author Hanlin Zhang
 */
public class ADMINISTRATOR {
	//use which type of sensor.
	public boolean []isMrMa = new boolean[Parameters.SensorNum];
	//保存不同通道传感器的状态。
	public boolean mix_flag1 = false;
	public boolean mix_flag2 = false;
	//discSymbol indicate the number of different connection circumstances.
	private int discSymbol = -1;
	//read processing is normal or irregular.
	private boolean NewFile = false;
	private boolean NetError = false;
	//record the net error station, we initialize it at the beginning of the procedure.
	private int netErrID = -1;
	//record the interrupt time instance.
	private int timeInterrupt = -1;
	//record the phrase of the align time cost.
	private long startInstance = -1;
	private long endInstance = -1;
	//procedure is the first loop or not.
	private boolean isFirst = true;
	//is not real motivation.
	private boolean isRealMoti = false;
	//record the number of motivation sensors.
	private int[] numberMotiSeries;
	//update at the creating ReadData function's object to make sure this variable is the last day all the way.
	private String lastDate = "2019-1-13 02:05:03";
	//record the baseline sensor number.
	private int baseCoordinate = 0;
	//max time series of align.
	private int maxTimeSeries = 0;
	//file Name
	private String[] nameF = new String[Parameters.SensorNum];
	//align different array.
	private int duiqi[] = new int[Parameters.SensorNum];
	//the new file in each sensor we find.
	private String[] file1 = new String[Parameters.SensorNum];
	//the file creation time
    private String [] dateString=new String[Parameters.SensorNum];
    //data exchange.
  	private vectorExchange[] dataRecArray=new vectorExchange[Parameters.SensorNum];
  	//an object of moving buffer position
    private moveBufferPosition[] mo = new moveBufferPosition[Parameters.SensorNum];
    //read data 
    public ReadData[] readDataArray = new ReadData[Parameters.SensorNum];
    //data collector
    private Vector<String> sensorData[][] = new Vector[Parameters.SensorNum][3];
    
	//阻拦次数
	private int stopperCount = 0;
	
	
	
	public ADMINISTRATOR() {
		super();
		for(int i=0;i<Parameters.SensorNum;i++) {
			this.isMrMa[i] = false;
		}
	}
	
	//reset the discSymbol.----------------------------------------------------------------------
	public void resetDiscSymbol(ReconnectToRemoteDisk ReConnect) {
		this.discSymbol = ReConnect.orderNum-1;
	}
	public int getDiscSymbol() {
		return this.discSymbol;
	}
	
	//net error control.----------------------------------------------------------------------
	public void setNetError(boolean flag) {
		this.NetError = flag;
	}
	public boolean getNetError() {
		return this.NetError;
	}
	
	//new file flag control.----------------------------------------------------------------------
	public void setNewFile(boolean flag) {
		this.NewFile = flag;
	}
	public boolean getNewFile() {
		return this.NewFile;
	}
	
	//record net error station ID.----------------------------------------------------------------------
	public void setNetErrID(int num) {
		this.netErrID = num;
	}
	public int getNetErrID() {
		return this.netErrID;
	}
	
	//record the interrupt time instance.----------------------------------------------------------------------
	public void setTimeInterrupt(int timeCount) {
		this.timeInterrupt = timeCount;
	}
	public int getTimeInterrupt() {
		return this.timeInterrupt;
	}
	
	//record the phrase of the align time cost.----------------------------------------------------------------------
	public void setStartInstance(long startTime) {
		this.startInstance = startTime;
	}
	public long getStartInstance() {
		return this.startInstance;
	}
	public void setEndInstance(long endTime) {
		this.endInstance = endTime;
	}
	public long getEndInstance() {
		return this.endInstance;
	}
	
	//record procedure is the first loop or not.----------------------------------------------------------------------
	public void setIsFirst(boolean flag) {
		this.isFirst = flag;
	}
	public boolean getIsFirst() {
		return this.isFirst;
	}
	
	//is not real motivation.----------------------------------------------------------------------
	public void setIsRealMoti(boolean flag) {
		this.isRealMoti = flag;
	}
	public boolean getIsRealMoti() {
		return this.isRealMoti;
	}
	
	//record the motivated number.----------------------------------------------------------------------
	public void initNumberMotiSeries(int num) {
		this.numberMotiSeries = new int[num];
	}
	public void setNNumberMotiSeries(int series,int value) {
		this.numberMotiSeries[series] = value;
	}
	public int getNNumberMotiSeries(int series) {
		return this.numberMotiSeries[series];
	}
	
	//update at the creating ReadData function's object to make sure this variable is the last day all the way.---
	public void setLastDate(String date) {
		this.lastDate = date;
	}
	public String getLastDate() {
		return this.lastDate;
	}
	
	//record the baseline sensor number.----------------------------------------------------------------------
	public void setBaseCoordinate(int series) {
		this.baseCoordinate = series;
	}
	public int getBaseCoordinate() {
		return this.baseCoordinate;
	}
	
	public void setMaxTimeSeries(int maxSeries) {
		this.maxTimeSeries = maxSeries;
	}
	public int getMaxTimeSeries() {
		return this.maxTimeSeries;
	}
	
	//nameF
	public void resetNameF() {
		this.nameF = new String[Parameters.SensorNum];
		
		for(int i=0;i<this.nameF.length;i++) {
			this.nameF[i] = "";
		}
	}
	public String getNNameF(int series) {
		return this.nameF[series];
	}
	public String[] getNameF() {
		return this.nameF;
	}
	public void setNNameF(int series,String value) {
		this.nameF[series] = value;
	}
	public void setNameF(String[] nameF) {
		this.nameF = nameF;
	}
	
	//align array(duiqi)----------------------------------------------------------------------
	public void resetDuiqi() {
		this.duiqi = new int[Parameters.SensorNum];
		
		for(int i=0;i<this.duiqi.length;i++) {
			this.duiqi[i] = -1;
		}
	}
	public int getNDuiqi(int series) {
		return this.duiqi[series];
	}
	public int[] getDuiqi() {
		return this.duiqi;
	}
	
	public void setNDuiqi(int series,int value) {
		this.duiqi[series] = value;
	}
	public void setDuiqi(int[] duiqi) {
		this.duiqi = duiqi;
	}
	
	//file1----------------------------------------------------------------------
	public void resetFile1() {
		this.file1 = new String[Parameters.SensorNum];
		
		for(int i=0;i<this.file1.length;i++) {
			this.file1[i] = "";
		}
	}
	public String getNFile1(int series) {
		return this.file1[series];
	}
	public String[] getFile1() {
		return this.file1;
	}
	
	public void setNFile1(int series,String value) {
		this.file1[series] = value;
	}
	public void setFile1(String[] file1) {
		this.file1 = file1;
	}
	
	//设定各个盘符的对齐时间。----------------------------------------------------------------------
	public void resetDateString() {
		this.dateString = new String[Parameters.SensorNum];
		
		for(int i=0;i<this.dateString.length;i++) {
			this.dateString[i] = "";
		}
	}
	public String getNDateString(int series) {
		return this.dateString[series];
	}
	public String[] getDateString() {
		return this.dateString;
	}
	public void setNDateString(int series,String value) {
		this.dateString[series] = value;
	}
	
	//data exchange vector storage container.----------------------------------------------------------------------
	public void resetDataRec() {
		this.dataRecArray = new vectorExchange[Parameters.SensorNum];
		
		for(int i=0;i<this.dataRecArray.length;i++) {
			this.dataRecArray[i] = new vectorExchange();
			MainThread.aDataRec[i] = this.dataRecArray[i];
		}
	}
	public vectorExchange getNDataRec(int series) {
		return this.dataRecArray[series];
	}
	public void setNDataExchange(int series, vectorExchange dataRec) {
		this.dataRecArray[series] = dataRec;
	}
	
	//an object of moving buffer position---------------------------------------------------------------------------
	public void resetMoveBuffer() {
		this.mo = new moveBufferPosition[Parameters.SensorNum];
	}
	public moveBufferPosition getNMoveBuffer(int series) {
		return this.mo[series];
	}
	public void setNMoveBuffer(int series, moveBufferPosition movebuff) {
		this.mo[series] = movebuff;
	}
	
	//read data-----------------------------------------------------------------------------------------------------
	public void resetReadData() {
		this.readDataArray = new ReadData[Parameters.SensorNum];
	}
	public ReadData getNReadData(int series) {
		return this.readDataArray[series];
	}
	public void setNReadData(int series, ReadData readData) {
		this.readDataArray[series] = readData;
	}
	public ReadData[] getReadData() {
		return this.readDataArray;
	}
	public void setReadData(ReadData[] readData) {
		this.readDataArray = readData;
	}
	
	//data collector-----------------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	public void resetSensorData() {
		this.sensorData = new Vector[Parameters.SensorNum][3];
	}
	//获得一个传感器的三个容器内容。
	public Vector<String>[] getNSensorData(int series) {
		return this.sensorData[series];
	}
	//设置一个传感器的三个容器内容。
	public void setNSensorData(int series, Vector<String>[] SensorDataOne) {
		this.sensorData[series] = SensorDataOne;
	}
	//当前返回所有传感器的读数。
	public Vector<String>[][] getSensorData() {
		return this.sensorData;
	}
	//设置所有传感器的读数。
	public void setReadData(Vector<String>[][] SensorData) {
		this.sensorData = SensorData;
	}
	
	/**
	 * resetter resets the order of each drive or path.
	 * 当
	 * @param discSymbol
	 * @param ReConnect
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月15日上午12:20:13
	 */
	public void resetter(ReconnectToRemoteDisk ReConnect) {
		
            System.out.println("是否第一次？ " + this.getIsFirst() + "  进入了重对齐！");
			
            if(MainThread.fileStr.length<3) {
				this.discSymbol=0;
			}
	        else if(discSymbol<=-1) {
	        	this.discSymbol=ReConnect.orderNum-1;//When the all situations has been considered, the procedure will start from the beginning.
	        }
	        if(this.getIsFirst() == false) {
	            if(MainThread.fileStr.length>=3) {
	            	MainThread.fileStr = ReConnect.rearrange(discSymbol);
	            	//reset each variable initialized by sensorNum.
	            	this.resetDuiqi();
	            	this.resetFile1();
	            	this.resetDataRec();
	            	this.resetDateString();
	            	this.resetMoveBuffer();
	            	this.resetReadData();
	            	this.resetSensorData();
	            	this.NetError = false;
	            	this.NewFile = false;
	            }
	            for(int i=0;i<Parameters.SensorNum;i++) {
	                System.out.println("重新分配的盘符： " + MainThread.fileStr[i]);
	            }
	        }
	}
	
	/**
	 * 阻挡器，当网络发生错误时，生效。
	 * 阻拦大于10次，不再阻拦，但仅限于第一次的时候，因为后面不阻拦会产生错误。
	 * @param discSymbol
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月15日上午8:44:56
	 */
	public boolean stopper() {
		//阻挡器工作的前提是网络错误，NetError == true
		if(this.NetError == true) {
			//当阻拦次数大于10次时，设置第一次变量isFirst为false，以防止第一次设定的盘符出现故障，程序陷入死循环。
			if(this.stopperCount >= 10 && this.isFirst == true) {
				System.out.println("拦截次数大于10次");
				this.stopperCount = 0;
				this.isFirst = false;
				this.discSymbol--;
				return true;
			}
			//第一次时，不用变化重置的序号。
			else if(this.isFirst == true) {
				this.stopperCount++;
				return true;
			}
			//不是第一次时，不用再更新stopperCount变量。
			else {
				this.discSymbol--;
		      	return true;
			}
		}
		return false;
	}
	
	/**
	 * 睡眠器，防止程序陷入假死状态，设置延迟以随机启动。
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月15日上午9:04:33
	 */
	public void sleeper() {
		if(Parameters.isDelay==1) {
			Random r = new Random();
			//sleep any seconds to avoid the procedure reset mechanically.
        	try {
                Thread.sleep(1000*(r.nextInt(10)+1));
            } catch (InterruptedException e2) {	e2.printStackTrace();}
    	}
	}
	
	/**
	 * 对齐多线程过程。
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月15日下午1:08:54
	 */
	public void alignMuti() {
		final CountDownLatch threadSignal_duiqi = new CountDownLatch(Parameters.SensorNum);
		ExecutorService executor_duiqi = Executors.newFixedThreadPool(Parameters.SensorNum);
        //we obtain the time distance among these sensors' remote disk.
        for(int i=0;i<Parameters.SensorNum;i++) {
            obtainHeadTime aDuiQi = new obtainHeadTime(threadSignal_duiqi, MainThread.fileStr[i], i, this);
            executor_duiqi.execute(aDuiQi);
        }
        
        try {threadSignal_duiqi.await();}
        catch (InterruptedException e1) {e1.printStackTrace();}
        executor_duiqi.shutdown();
	}
	
	/**
	 * 挪动读指针多线程过程。
	 * @param mo
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月15日下午1:15:07
	 */
	public void moveBufferMuti() {
		final CountDownLatch threadSignal_find = new CountDownLatch(Parameters.SensorNum);
        ExecutorService executor_find = Executors.newFixedThreadPool(Parameters.SensorNum);
        for(int i=0;i<Parameters.SensorNum;i++) {
            this.mo[i] = new moveBufferPosition(threadSignal_find, i, this);
            executor_find.execute(mo[i]);
        }
        try {threadSignal_find.await();}
        catch (InterruptedException e1) {e1.printStackTrace();}
        executor_find.shutdown();
	}
	
	/**
	 * 读数据多线程过程。
	 * @param readDataArray
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月15日下午1:06:22
	 */
	public void readDateMuti() {
		ExecutorService executor = Executors.newFixedThreadPool(Parameters.SensorNum);
        final CountDownLatch threadSignal = new CountDownLatch(Parameters.SensorNum);

        for (int i = 0; i < Parameters.SensorNum; i++) {
            readTask task = new readTask(threadSignal, i, this.dataRecArray[i], this.readDataArray[i], this);
            executor.execute(task);
        }
        try {
            threadSignal.await();
            executor.shutdown();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
	}
	
	/**
	 * 计算与最新文件的时间距离数组，单位s。
	 * 
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月15日上午11:45:07
	 */
	public void timeDistancer() {
		//we find out the newest file in these sensors and find out the time distance array called 'DuiQi.duiqi'.
        DateArrayToIntArray aDateArrayToIntArray = new DateArrayToIntArray();
        try {this.duiqi = (aDateArrayToIntArray.IntArray(this.dateString));
        } catch (ParseException e) {e.printStackTrace();}
        try {System.out.println("对齐数组读取完毕！ 对齐的最大时间： "+aDateArrayToIntArray.getDateStr());
		} catch (ParseException e) {e.printStackTrace();}
        //获取最大时间对应的文件序号，用于新仪器的对齐。
        this.setMaxTimeSeries(aDateArrayToIntArray.getMaxSeries());
	}
	
	/**
	 * 数据收集器，整合所有读取到的数据。
	 * 
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月15日下午2:32:46
	 */
	public void dataCollector() {
//    	int diagnoseIsFull = 0;
        for(int i=0;i<Parameters.SensorNum;i++) {
            sensorData[i][0] = this.dataRecArray[i].getBeforeVector();
//            if(sensorData[i][1]==null) {diagnoseIsFull=1;}
            sensorData[i][1] = this.dataRecArray[i].getNowVector();
//            if(sensorData[i][1]==null) {diagnoseIsFull=1;}
            sensorData[i][2] = this.dataRecArray[i].getAfterVector();
//            if(sensorData[i][2]==null) {diagnoseIsFull=1;}
        }
        
//        if(diagnoseIsFull==1) continue;//when the three vector has data, we will enter the calculate function.
	}
	
	/**
	 * entrance the calculation.
	 * 
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月15日下午2:42:55
	 */
	public void calculation() {
		 //进入计算模块。
        try {EarthQuake.runmain(this);
        } catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * 调试发生器，当程序需要测试主线程重启流程时，设置adjust变量为true启动该过程。
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月15日上午9:06:49
	 */
	public boolean adjuster() {
		if(Parameters.Adjust == true) {
        	this.discSymbol--;
        	this.isFirst = false;
        	this.NewFile = true;
        	return true;
        }
		return false;
	}
	
	/**
	 * offline processing process.
	 * 
	 * @author Rq Ma, Hanlin Zhang.
	 * @throws Exception 
	 * @date revision 2021年2月15日下午2:55:44
	 */
	public void offlineProcessing() throws Exception {
        /** 传感器数据 */
        final Vector<String> sensorData[][] = new Vector[Parameters.SensorNum][3];
        int count = 1;
        //注意，不要删除这行注释
        //牛家村(Test1)、洗煤厂(Test2)、香山矿(Test3)、王家村(Test4)、十一矿工业广场老办公楼西南角花坛(Test5)、西风井(Test6)、
        MainThread.fileParentPackage= SubStrUtil.getFileParentPackage(MainThread.fileStr);//文件所在的目录名
        
        //初始化数据交换容器，暂不消除全局变量aDataRec
        this.resetDataRec();
        /**
         * AlignFile ：类似于读最新文件里的 Duiqi
         * 注意，注意，注意！！！在整个程序里必须只定义一次
         */
        AlignFile alignFile = new AlignFile(MainThread.fileStr, Parameters.StartTimeStr, this);
        GetReadArray readDataArray = new GetReadArray(alignFile, this);
        this.setReadData(readDataArray.getDataArray());
        
        while (true) {
            
    		while (this.getReadData() == null || this.getNewFile() == true) {
    			this.setNewFile(false);
    			count++;
                System.out.println("----------开始处理第 " + count + " 组数据---------");
                this.setReadData(readDataArray.getDataArray());

                if(this.getReadData() == null)
                	continue;
                
            }
    		
            //start the thread pool to read data from different position.
            if(this.getNetError() == false) {
            	this.readDateMuti();
            }
        	
        	this.dataCollector();
        	this.calculation();
            
            if(MainThread.exitVariable_visual==true) {
	        	break;
	        }
        }//end while(true)
	}
	
	/**
	 * online processing process.
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月15日下午2:55:59
	 */
	public void onlineProcessing() {
		System.out.println("开始读实时数据部分！");
        ReconnectToRemoteDisk ReConnect = new ReconnectToRemoteDisk(3, MainThread.fileStr);//重新分配盘符

        //初始化数据交换容器，暂不消除全局变量aDataRec
        this.resetDataRec();
        //测试用。
        MainThread.fileParentPackage= SubStrUtil.getFileParentPackage(MainThread.fileStr);//文件所在的目录名
        
        while(true) {
        	//当isdelay为1时睡眠器生效。
        	this.sleeper();
        	
			if(this.getNetError() == true||this.getNewFile() == true||this.getIsFirst() == true) {
	    		//重置器，重置盘符，初始化重置序号，第一次时，重置盘符功能生效。
				this.resetter(ReConnect);
	    		
	    		//开启对齐线程池--------------------------------------------------------------------
				this.alignMuti();
	
	    		//当adjust变量为真时，调试发生器生效。
	            if(this.adjuster() == true) {
	            	System.out.println("调试发生器生效！");
	            	continue;
	            }
	            
	            //发生网络错误时，阻拦器生效。
	            if(this.stopper() == true) {
	            	System.out.println("第1个阻拦器生效！");
	            	continue;
	            }
	            
	            //时间距离计算器。
	            this.timeDistancer();
	            
	            //开启移动线程池--------------------------------------------------------------------
	            this.moveBufferMuti();
	            
	            System.out.println("移动读指针完毕！");
	            
	            //当adjust变量为真时，调试发生器生效。
	            if(this.adjuster() == true) {
	            	System.out.println("调试发生器生效！");
	            	continue;
	            }
	            
	            //发生网络错误时，阻拦器生效。
	            if(this.stopper() == true) {
	            	System.out.println("第2个阻拦器生效！");
	            	continue;
	            }
	            
	            //当第一次对齐完成后，才能置第一次变量为FALSE，优化拦截次数，10次后按断线处理。
	            this.setIsFirst(false);
	            
	            //重置初始化盘符的序号。
	            this.resetDiscSymbol(ReConnect);
	        }
	
	        //开启读取数据线程池--------------------------------------------------------------------
	        if(this.getNetError() == false) {
	        	this.readDateMuti();
	        }
	        
	        //when produce a new file, there must has a vector is not full, so we discard the last several second data.
	        if(this.getNewFile() == false && this.getNetError() == false){
	        	this.dataCollector();
	        	this.calculation();
	        }
	        if(MainThread.exitVariable_visual==true) {
	        	break;
	        }  
		}//end while(true)
    }
}
