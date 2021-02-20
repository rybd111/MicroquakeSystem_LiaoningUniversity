package mutiThread;



import com.h2.constant.Parameters;
import DataExchange.vectorExchange;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;
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
    public static vectorExchange[] aDataRec = new vectorExchange[Parameters.SensorNum];
    /**volatile decorate String promises the latest String in current thread.*/
    public static volatile boolean exitVariable_visual = true;
    
    public void run() {
    	/** the visual model controller variable.*/
    	exitVariable_visual=false;
    	/** the log prefix print in the console.*/
    	SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();//do not call this function many times.
    	/** global super administrator saves all status in system for replacing the global variables.*/
    	ADMINISTRATOR manager = new ADMINISTRATOR();
    	
//        fileStr[0] = "I:/研究生阶段/矿山/程序修改记录/读新仪器数据融合-曹睿-马瑞强/新设备数据/2/3-西风井/";
//        fileStr[1] = "I:/研究生阶段/矿山/程序修改记录/读新仪器数据融合-曹睿-马瑞强/新设备数据/2/4-铁塔/";
        if(Parameters.offline==false) {
            System.out.println("开始读实时数据部分！");
            manager.onlineProcessing();
        }
        else if(Parameters.offline==true) {
        	
            System.out.println("开始读历史数据部分！");
            try {manager.offlineProcessing();} 
            catch (Exception e) {e.printStackTrace();}
        }
    }
}
