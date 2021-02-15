package mutiThread;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.h2.constant.Parameters;
import com.h2.main.EarthQuake;

import DataExchange.vectorExchange;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;
import utils.Date2String;
import utils.DateArrayToIntArray;
import controller.ADMINISTRATOR;
import read.rqma.history.*;
import read.yang.readFile.ReadData;

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
    public static vectorExchange[] aDataRec = new vectorExchange[Parameters.SensorNum];
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
//        fileStr[0] = "I:/研究生阶段/矿山/程序修改记录/读新仪器数据融合-曹睿-马瑞强/新设备数据/2/3-西风井/";
//        fileStr[1] = "I:/研究生阶段/矿山/程序修改记录/读新仪器数据融合-曹睿-马瑞强/新设备数据/2/4-铁塔/";
        if(Parameters.offline==false) {
            System.out.println("开始读实时数据部分！");
            manager.onlineProcessing();
        }
        else if(Parameters.offline==true) {
        	
            System.out.println("开始读历史数据部分！");
            manager.offlineProcessing();
        }
    }
}
