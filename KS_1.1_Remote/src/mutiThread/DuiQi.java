package mutiThread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import com.h2.constant.Parameters;
import com.yang.readFile.FindNewFile;
import com.yang.readFile.findNew;
import com.yang.readFile.ReadData;
import com.yang.readFile.ReadDataSegmentHead;
import utils.DateArrayToIntArray;
import controller.ADMINISTRATOR;

/**
 * we need to raise the speed of align process, so we write this function to realize this purpose.
 * @author Hanlin Zhang, 
 *
 */
public class DuiQi extends Thread{

	/**
	 * the distance between the latest file and the current file.
	 */
	public static int duiqi[] = new int[Parameters.SensorNum];
	/**
	 * the latest file ended with '.hfmed' or '.bin' we found in the correspond path on a disk of each sensor.
	 */
	public static String[] file1 = new String[Parameters.SensorNum];
	
	private CountDownLatch downLatch;
	private String fileName;
	private int i;
	private ADMINISTRATOR manager;
	
	public DuiQi() {
		super();
		duiqi[i]=0;
	}
	public DuiQi(CountDownLatch threadSignal_find,String fileStr,int i,ADMINISTRATOR manager) {
		super();
		duiqi[i] = 0;
		this.downLatch = threadSignal_find;
		this.fileName = fileStr;
		this.i = i;
		this.manager = manager;
	}

	public void run() {
		try{
			file1[i]=findNew.find(fileName,i,manager).getAbsolutePath();
			if(manager.isMrMa[i]==true) {
				MainThread.dateString[i] = ReadDataSegmentHead.readDataSegmentHead_MrMa_String(file1[i]);
				System.out.println(MainThread.fileStr[i]+"MrMa最新文件的时间为："+MainThread.dateString[i]);
			}
			else {
				MainThread.dateString[i] = ReadDataSegmentHead.readDataSegmentHeadall(file1[i]);
				System.out.println(MainThread.fileStr[i]+"MrLiu最新文件的时间为："+MainThread.dateString[i]);
			}
		}
		catch(Exception e1){//the error of the network when the procedure are finding the file.
			ReadData.netError = true;
			MainThread.reduiqi[i]=-1;
		}
		
		this.downLatch.countDown();
	}
	/**
	 * @ The original duiqi function.
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static int[] DuiQiMain(String[] fileName)
			throws Exception {
		
		FindNewFile aFile =new FindNewFile();
		for(int i=0;i<fileName.length;i++) {
//			fileName1[i]=aFile.researchFile(fileName[i], i).getAbsolutePath();
//			file1[i]=findNew.find(fileName[i],i).getAbsolutePath();
		}

		int[] TimeDifferertInt = new int[fileName.length];

		String[] dateString = new String[TimeDifferertInt.length];

		// 一个传感器的时间
		try{
			for(int i=0;i<fileName.length;i++) {
				String dateStr = ReadDataSegmentHead.readDataSegmentHeadall(file1[i]);
				dateString[i] = dateStr;
			}
		}
		catch(Exception e1){
			ReadData.netError = true;
			for(int k = 0; k < Parameters.SensorNum; k++){
				DuiQi.duiqi[k] = -1;
			}
			return DuiQi.duiqi;//返回了-1的数组
		}
		System.out.println("对齐数组为：");
		for(int i = 0; i <fileName.length; i++){
			System.out.println(dateString[i]);
		}
				
		DateArrayToIntArray aDateArrayToIntArray =new DateArrayToIntArray();
		TimeDifferertInt = aDateArrayToIntArray.IntArray(dateString); 
		Date DateMax  =new Date();
		DateMax = aDateArrayToIntArray.getDateStr();
				 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		//String str=sdf.format(date); 
		
		System.out.println("对齐的最大时间："+sdf.format(DateMax));
		
		
		System.out.println("开始对齐");
		return TimeDifferertInt;
	}

}
