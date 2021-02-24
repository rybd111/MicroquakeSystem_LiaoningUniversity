/**
 * 
 */
package mutiThread;

import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import com.h2.constant.Parameters;

import DataExchange.vectorExchange;
import controller.ADMINISTRATOR;
import read.yang.readFile.ReadData;

/**
 * @author Hanlin Zhang
 */
/**
 * Read data from original data file.
 * But we can not read different format for different format data file.
 * @author Yilong Zhang, Xingdong Yang, Hanlin Zhang
 */
public class readTask implements Runnable{
    private CountDownLatch downLatch;
    private int sensorID;
    private vectorExchange dataRec;
    private ReadData readData;
    private Vector<String> aVector;
    private Vector<String> temVector;
    private int num;
    private ADMINISTRATOR manager;

    public readTask(
    		CountDownLatch downLatch, 
    		int sensorNum, 
    		vectorExchange dataRec, 
    		ReadData readData,
    		ADMINISTRATOR manager) {
        super();
        this.downLatch = downLatch;
        this.sensorID = sensorNum;
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
        	if(Parameters.offline==true)
                temVector = readData.getOfflineData();
            else
                temVector = readData.getOnlineData();//获取1s数据，传递盘符、盘号
        	
            //新文件拦截器
            if(manager.getNewFile() == true) {
                System.out.println("第"+sensorID+"号"+manager.getNewFile()+"进入while");
                return;
            }
            //网络拦截器
            if(manager.getNetError()==true) {
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
