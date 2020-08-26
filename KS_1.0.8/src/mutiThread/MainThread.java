package mutiThread;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.h2.backupData.WriteRecords;
import com.h2.backupData.saveOri;
import com.h2.constant.Parameters;
import com.h2.main.EarthQuake;
import com.h2.tool.motiDiag;
import com.rqma.history.*;
import com.yang.readFile.ReadData;
import mianThread.DuiQi;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;
import utils.Date2String;
import utils.DateArrayToIntArray;
import utils.String2Date;
import visual.Preferences;
import visual.chartFrame;
import bean.DataRec;
import controller.ADMINISTRATOR;

/**
 * main procedure entrance.
 * @author Yilong Zhang, Hanlin Zhang, Chengfeng Liu, rqma, Rui Cao, et al.
 */
public class MainThread extends Thread{
	/**
     * fileStr must define at first with different path for your destination.
     */
    public static String fileStr[] = new String[Parameters.SensorNum];
    /**this variable obtain the parent fold name.*/
    public static String fileParentPackage[] = new String[Parameters.SensorNum];
    /**this variable exchange data to the foreground.*/
    public static DataRec[] aDataRec = new DataRec[Parameters.SensorNum];
    /**a counter count per 10 sec. for storing one minute data.*/
    public static int countRestart = 0;
    /**reduiqi status*/
    public static int []reduiqi=new int[Parameters.SensorNum];
    /**Is not restart the procedure*/
    public static int IsContinue=0;
    /**is not the first time start the procedure*/
    public static boolean isFirst = true;
    /**the file creation time*/
    public static String [] dateString=new String[Parameters.SensorNum];
    /**these two variable are not using in procedure.*/
    public static long m1=0,m2=0;
    /**volatile decorate String promises the latest String in current thread.*/
    public static volatile boolean exitVariable_visual = true;
    
    @SuppressWarnings("unused")
    public void run() {
    	/** the visual model controller variable.*/
    	exitVariable_visual=false;
    	
    	/** the log prefix print in the console.*/
    	SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();//do not call this function many times.
        
    	/** global super administrator saves all status in system for replacing the global variables.*/
    	ADMINISTRATOR manager = new ADMINISTRATOR();
    	
    	/**when we need to read data offline, we can use the absolute path as follows.*/

//    	 fileStr[0] = "x:/红阳三矿/201911/Tests/";
//         fileStr[1] = "x:/红阳三矿/201911/Testt/";
//         fileStr[2] = "x:/红阳三矿/201911/Testu/";
//         fileStr[3] = "x:/红阳三矿/201911/Testv/";
//         fileStr[4] = "x:/红阳三矿/201911/Testw/";
//         fileStr[5] = "x:/红阳三矿/201911/Testx/";
//         fileStr[6] = "x:/红阳三矿/201911/Testy/";
//         fileStr[7] = "x:/红阳三矿/201911/Testz/";
    	
//    	fileStr[0] = "I:\\研究生阶段\\矿山\\矿山数据\\红阳三矿\\12.21红阳三矿2.2地震\\Testv\\";
        
//        fileStr[0] = "I:/研究生阶段/矿山/程序修改记录/读新仪器数据融合-曹睿-马瑞强/新设备数据/2/3-西风井/";
//        fileStr[1] = "I:/研究生阶段/矿山/程序修改记录/读新仪器数据融合-曹睿-马瑞强/新设备数据/2/4-铁塔/";
    	
        
//    	fileStr[0] = "I:/研究生阶段/矿山/矿山数据/平顶山/20190329/Testu/";
//    	fileStr[1] = "I:/研究生阶段/矿山/矿山数据/平顶山/20190329/Testw/";
//    	fileStr[2] = "I:/研究生阶段/矿山/矿山数据/平顶山/20190329/Testx/";
//    	fileStr[3] = "I:/研究生阶段/矿山/矿山数据/平顶山/20190329/Testy/";
//    	fileStr[4] = "I:/研究生阶段/矿山/矿山数据/平顶山/20190329/Testz/";

    	fileStr[0] = "I:/研究生阶段/矿山/矿山数据/红阳三矿/20200711/Testr/";
        fileStr[1] = "I:/研究生阶段/矿山/矿山数据/红阳三矿/20200711/Testt/";
        fileStr[2] = "I:/研究生阶段/矿山/矿山数据/红阳三矿/20200711/Testu/";
        fileStr[3] = "I:/研究生阶段/矿山/矿山数据/红阳三矿/20200711/Testw/";
        fileStr[4] = "I:/研究生阶段/矿山/矿山数据/红阳三矿/20200711/Testx/";
        fileStr[5] = "I:/研究生阶段/矿山/矿山数据/红阳三矿/20200711/Testy/";
        fileStr[6] = "I:/研究生阶段/矿山/矿山数据/红阳三矿/20200711/Testz/";
        
//        fileStr[0] = "I:/研究生阶段/矿山/矿山数据/红阳三矿2.14/Test1/";
//        fileStr[1] = "I:/研究生阶段/矿山/矿山数据/红阳三矿2.14/Test2/";
        
        /**when we need to read data online, we can use the absolute path as follows.*/
//		fileStr[0] = "z:/";
//		fileStr[1] = "y:/";
//		fileStr[2] = "w:/";
//		fileStr[3] = "s:/";
//		fileStr[4] = "x:/";
//		fileStr[5] = "x:/";
//		fileStr[6] = "y:/";
//		fileStr[7] = "z:/";
        Vector<String> oneMinuteData[] = new Vector[Parameters.SensorNum];
        for(int i=0;i<Parameters.SensorNum;i++)	oneMinuteData[i]=new Vector<String>();
        
        if(Parameters.offline==false) {
            System.out.println("开始读实时数据主线程！");
            Vector<String>[] beforeVector=new Vector[Parameters.SensorNum];
            Vector<String>[] nowVector=new Vector[Parameters.SensorNum];
            Vector<String>[] afterVector=new Vector[Parameters.SensorNum];
            ReconnectToRemoteDisk ReConnect = new ReconnectToRemoteDisk(3,fileStr);//重新分配盘符
            DataRec[] dataRecArray=new DataRec[Parameters.SensorNum];
            Random r = new Random();

            for(int i=0;i<Parameters.SensorNum;i++) {
                dataRecArray[i] = new DataRec(beforeVector[i],nowVector[i],afterVector[i]);
                aDataRec[i] = dataRecArray[i];
            }

            final Vector<String> sensorData[][] = new Vector[Parameters.SensorNum][3];

            //there may need only one tread pool cause thread pool can be used repeat.
			ExecutorService executor_readdata = Executors.newFixedThreadPool(Parameters.SensorNum);

			ExecutorService executor_duiqi = Executors.newFixedThreadPool(Parameters.SensorNum);

            ExecutorService executor_find = Executors.newFixedThreadPool(Parameters.SensorNum);

            /**diagnose the connection of different position sensors from the last series number.*/
            int discSymbol = ReConnect.arrayList.size()-1;

            /**an object of moving buffer position*/
            moveBufferPosition []mo = new moveBufferPosition[Parameters.SensorNum];
            
            while(true) {
                
            	if(Parameters.isDelay==1) {
	            	//sleep any seconds to avoid the procedure reset mechanically.
	            	try {
	                    Thread.sleep(1000*(r.nextInt(10)+1));
	                } catch (InterruptedException e2) {	e2.printStackTrace();}
            	}
            	
                //the procedure will reset when the sensors produce a new file or there produces net error.
                if(ReadData.netError==true||ReadData.newData==true||isFirst==true){
                	
                    System.out.println("进入了重对齐！");
                    MainThread.countRestart = 0;
                    
                    if(fileStr.length<3)
                        discSymbol=0;
                    else if(discSymbol<=-1)
                        discSymbol=ReConnect.arrayList.size()-1;//When the all situations has been considered, the procedure will start from the beginning.
                    
                    if(isFirst==false) {
                        if(fileStr.length>=3)
                            fileStr = ReConnect.rearrange(discSymbol);
                        for(int i=0;i<Parameters.SensorNum;i++) {
                            DuiQi.duiqi[i]=0;
                            System.out.print(fileStr[i]);
                        }
                        System.out.println();
                    }
                    
                    final CountDownLatch threadSignal_duiqi = new CountDownLatch(Parameters.SensorNum);
                    
                    //we obtain the time distance among these sensors' remote disk.
                    for(int i=0;i<Parameters.SensorNum;i++) {
                        DuiQi aDuiQi = new DuiQi(threadSignal_duiqi,fileStr[i],i,manager);
                        executor_duiqi.execute(aDuiQi);
                    }

                    try {threadSignal_duiqi.await();}
                    catch (InterruptedException e1) {e1.printStackTrace();}

                    for(int i=0;i<Parameters.SensorNum;i++){
                        if(reduiqi[i]==-1) {
                            IsContinue=-1;
                            break;
                        }
                    }
                    if(IsContinue==-1){
                        discSymbol--;
                        IsContinue=0;
                        for(int i=0;i<Parameters.SensorNum;i++)
                            reduiqi[i]=0;
                        continue;
                    }
                    
                    //we find out the newest file in these sensors and find out the time distance array called 'DuiQi.duiqi'.
                    DateArrayToIntArray aDateArrayToIntArray =new DateArrayToIntArray();
                    try {
                        DuiQi.duiqi = aDateArrayToIntArray.IntArray(dateString);
                    } catch (ParseException e) {e.printStackTrace();}
                    
                    Date DateMax=new Date();
                    DateMax = aDateArrayToIntArray.getDateStr();
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println("对齐的最大时间："+sdf.format(DateMax));
                    
                    final CountDownLatch threadSignal_find = new CountDownLatch(Parameters.SensorNum);
                    for(int i=0;i<Parameters.SensorNum;i++) {
                        mo[i] = new moveBufferPosition(threadSignal_find,i,manager);
                        executor_find.execute(mo[i]);
                    }
                    try {threadSignal_find.await();}
                    catch (InterruptedException e1) {e1.printStackTrace();}

                    for(int i=0;i<Parameters.SensorNum;i++){
                        if(reduiqi[i]==-1) {
                            IsContinue=-1;
                            break;
                        }
                    }
                    if(IsContinue==-1){
                        for(int i=0;i<Parameters.SensorNum;i++)
                            reduiqi[i]=0;
                        IsContinue=0;
                        continue;
                    }
                    
                    //Initialize every variable in MainThread.java.
                    MainThread.isFirst=false;
                    ReadData.netError = false;
                    ReadData.newData = false;
                    discSymbol=ReConnect.arrayList.size()-1;
                    IsContinue=0;
                }

                //--------------------------------------------------------------------开启线程池，读取5个地点数据
                if(ReadData.netError == false){
                    final CountDownLatch threadSignal_readdata = new CountDownLatch(Parameters.SensorNum);
                    for(int i=0;i<Parameters.SensorNum;i++) {
                        readTask task = new readTask(threadSignal_readdata,i,dataRecArray[i],mo[i].readDataArray,fileStr[i],manager);
                        executor_readdata.execute(task);
                    }
                    try {threadSignal_readdata.await();}
                    catch (InterruptedException e1) {e1.printStackTrace();}
                }
                
                saveOri save = new saveOri();//save the one minute data.
                motiDiag moti = new motiDiag();//diagnose the position of the one minute data as current conditions.
                int[] motiPos = new int[1000];//record the motivation positions in one minute data.
                int diagnoseIsFull = 0;

                //when produce a new file, there must has a vector is not full, so we discard the last several second data.
                if(ReadData.newData == false && ReadData.netError==false){
                    MainThread.countRestart++;

                    for(int i=0;i<Parameters.SensorNum;i++) {
                        sensorData[i][0] = dataRecArray[i].getBeforeVector();
                        if(sensorData[i][1]==null) {diagnoseIsFull=1;}
                        sensorData[i][1] = dataRecArray[i].getNowVector();
                        if(sensorData[i][1]==null) {diagnoseIsFull=1;}
                        sensorData[i][2] = dataRecArray[i].getAfterVector();
                        if(sensorData[i][2]==null) {diagnoseIsFull=1;}
                    }
                    
                    if(diagnoseIsFull==1) continue;//when the three vector has data, we will enter the calculate function.
                    
                    if(Parameters.isStorageOneMinuteData == 1) {
                        for(int i=0;i<MainThread.fileStr.length;i++){
                            oneMinuteData[i].addAll(sensorData[i][2]);
                            if(MainThread.countRestart%6==0){//每逢6就将数据导出到txt
                                motiPos = moti.motiD(oneMinuteData[i]);//对0号台站进行激发数据的测试
                               	save.saveOrii(oneMinuteData[i], MainThread.fileStr[i],motiPos,i);//保存最新容器中的10s数据，以1分钟为单位
                                oneMinuteData[i].clear();
                            }
                        }
                        for(int i=0;i<motiPos.length;i++) motiPos[i]=0;
                        for(int i=0;i<Parameters.diskName_offline.length;i++)
                            Parameters.initPanfu[i] = 0;
                    }
                    try {
                        EarthQuake.runmain(sensorData);
                    } catch (Exception e) {e.printStackTrace();}
                }
                if(exitVariable_visual==true) {
		        	break;
		        }
            }//end while(true)
        }
        else if(Parameters.offline==true) {
        	
            System.out.println("开始读历史数据主线程！");
            /**
             * 要计算的起始时间
             */
//            String timeStr = "200214130000";
            String timeStr = Parameters.timeStr;
            /**
             * 五个传感器数据
             */
            final Vector<String> sensorData[][] = new Vector[Parameters.SensorNum][3];
            
            int count = 0;

            //注意，不要删除这行注释
            //牛家村(Test1)、洗煤厂(Test2)、香山矿(Test3)、王家村(Test4)、十一矿工业广场老办公楼西南角花坛(Test5)、西风井(Test6)、
            fileParentPackage= SubStrUtil.getFileParentPackage(fileStr);//文件所在的目录名
            
            DataRec[] dataRecArray = new DataRec[Parameters.SensorNum];

            for (int i = 0; i < Parameters.SensorNum; i++) {
                dataRecArray[i] = new DataRec(null, null, null);//不然会出现空指针
//                aDataRec[i] = dataRecArray[i];
            }

            /**
             * AlignFile ：类似于读最新文件里的 Duiqi
             * 注意，注意，注意！！！在整个程序里必须只定义一次
             */
            AlignFile alignFile = new AlignFile();

            ReadData[] readDataArray = getDataArray(alignFile, timeStr, manager);
            count++;
            if (readDataArray != null)
                System.out.println("----------开始处理第 " + count + " 组数据---------");
            //begin endless loop.
            while (true) {
                
            	synchronized (this) {
                    while (readDataArray == null || ReadData.newData == true) {
                        count++;
                        System.out.println("----------开始处理第 " + count + " 组数据---------");
                        readDataArray = getDataArray(alignFile, timeStr, manager);
                        if(readDataArray==null)
                            continue;
                        ReadData.newData = false;
                    }
                    ReadData.netError = false;

                    //start the thread pool to read data from different position.
                    if (ReadData.netError == false) {

                        ExecutorService executor = Executors.newFixedThreadPool(Parameters.SensorNum);
                        final CountDownLatch threadSignal = new CountDownLatch(Parameters.SensorNum);

                        for (int i = 0; i < Parameters.SensorNum; i++) {
                            readTask task = new readTask(threadSignal, i, dataRecArray[i], readDataArray[i], fileStr[i], manager);
                            executor.execute(task);
                        }
                        try {
                            threadSignal.await();
                            executor.shutdown();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                
                if (ReadData.netError == false) {
                    MainThread.countRestart++;

                    for (int i = 0; i < Parameters.SensorNum; i++) {
                        if (dataRecArray[i].getBeforeVector() == null)
                            dataRecArray[i].setBeforeVector(new Vector<String>());
                        if (dataRecArray[i].getNowVector() == null)
                            dataRecArray[i].setNowVector(new Vector<String>());
                        
                        sensorData[i][0] = dataRecArray[i].getBeforeVector();
                        sensorData[i][1] = dataRecArray[i].getNowVector();
                        sensorData[i][2] = dataRecArray[i].getAfterVector();
//                        System.out.println(fileParentPackage[i]+sensorData[i][2].size()+" "+sensorData[i][2].get(0).split(" ")[6]);
                    }
                    saveOri save = new saveOri();//save the one minute data.
                    motiDiag moti = new motiDiag();//diagnose the position of the one minute data as current conditions.
                    int[] motiPos = new int[1000];//record the motivation positions in one minute data.
                    
                    if(Parameters.isStorageOneMinuteData == 1) {
                        for(int i=0;i<Parameters.SensorNum;i++){
                            oneMinuteData[i].addAll(sensorData[i][2]);
                            if(MainThread.countRestart%6==0){//每逢6就将数据导出到txt
                                motiPos = moti.motiD(oneMinuteData[i]);//对0号台站进行激发数据的测试
                                save.saveOrii(oneMinuteData[i], MainThread.fileParentPackage[i],motiPos,i);//保存最新容器中的10s数据，以1分钟为单位
                                oneMinuteData[i].clear();
                            }
                        }
                        for(int i=0;i<motiPos.length;i++) motiPos[i]=0;
                        for(int i=0;i<Parameters.diskName_offline.length;i++)
                            Parameters.initPanfu[i] = 0;
                    }
                    try {
                        EarthQuake.runmain(sensorData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(exitVariable_visual==true) {
		        	break;
		        }
            }//end while(true)
        }
    }
    /**
     * 该函数体里的内容，会在程序里出现两次，为了避免代码的冗余，故放在一个函数体了，在两个地方调用函数即可
     *
     * @param alignFile
     * @param timeStr
     * @return 五个 ReadData
     */
    private ReadData[] getDataArray(AlignFile alignFile, String timeStr, ADMINISTRATOR manager) {
        /**保存对齐后的timeCount变量*/
        int kuai[] = new int[Parameters.SensorNum];
        /**统计对齐时间开销*/

        /**
         * 定义 ReadData
         */
        ReadData[] readDataArray = new ReadData[Parameters.SensorNum];
        long m1 = 0;
        long m2 = 0;
        try {
            AlignFile.align = alignFile.getAlign(fileStr, timeStr);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        for (int i = 0; i < Parameters.SensorNum; i++) {
            if (AlignFile.align[i] == -1) {
                System.out.println("对齐文件时出错");
                return null;
            }
        }
        
        String file[] = alignFile.paths_original;//文件路径
        for (int i = 0; i < Parameters.SensorNum; i++) {
            try {
                readDataArray[i] = new ReadData(file[i], i, manager);
            } catch (Exception e1) {
                e1.printStackTrace();
                return null;
            }
        }
       
        m1 = System.currentTimeMillis();
        for (int i = 0; i < Parameters.SensorNum; i++) {
            try {
                readDataArray[i].timeCount = 0;
                kuai[i] = readDataArray[i].readDataAlign(fileStr[i], i);
                if (kuai[i] == -1) {
                    System.out.println("该组数据无法对齐，开始对齐下一组---------");
                    readDataArray=null;
                    return null;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        m2 = System.currentTimeMillis();
        System.out.println("对齐时间花费：" + (m2 - m1) + "ms");

        return readDataArray;
    }
}


class readTask implements Runnable{
    private CountDownLatch downLatch;
    private int sensorID;
    private String sensorName;
    private DataRec dataRec;
    private ReadData readData;
    private Vector<String> aVector;
    private Vector<String> temVector;
    private int num;
    private ADMINISTRATOR manager;

    public readTask(CountDownLatch downLatch, int sensorName, DataRec dataRec, ReadData readData,String fileStr,ADMINISTRATOR manager) {
        super();
        this.downLatch = downLatch;
        this.sensorID = sensorName;
        this.sensorName = fileStr;
        this.dataRec = dataRec;
        this.readData = readData;
        this.manager = manager;
        
        num=1;
        aVector = new Vector<String>();
        temVector = new Vector<String>();
    }

    @SuppressWarnings("unused")
    private void doWork() throws Exception {

        while (num <= Parameters.readLen) {//读取10s的数据
            if(manager.isMrMa[this.sensorID]==false) {
	        	if(Parameters.offline==true)
	                temVector = readData.getOfflineData(sensorName, sensorID);
	            else
	                temVector = readData.getData(sensorName,sensorID);//获取1s数据，传递盘符、盘号
            }
            else {
//            	if(Parameters.offline==true)
            		temVector = readData.getData(sensorName, sensorID);
//            		System.out.println("1s_data"+ temVector.size());
            }
            
            if(ReadData.newData==true)	{
                System.out.println("第"+sensorID+"号"+ReadData.newData+"进入while");
                return;
            }
            if(ReadData.netError==true)	{
                return;
            }
            aVector.addAll(temVector);
            num++;
        }
        dataRec.DataSwap(aVector);
    }
    public void run(){
        try {this.doWork();}
        catch (Exception e) {e.printStackTrace();}
        this.downLatch.countDown();
    }
}