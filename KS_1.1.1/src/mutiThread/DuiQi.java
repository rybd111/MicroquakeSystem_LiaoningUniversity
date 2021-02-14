package mutiThread;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import com.h2.constant.Parameters;

import utils.DateArrayToIntArray;
import controller.ADMINISTRATOR;
import read.yang.readFile.FindNewFile;
import read.yang.readFile.ReadData;
import read.yang.readFile.ReadDateFromHead;
import read.yang.readFile.findNew;

/**
 * we need to raise the speed of align process, so we write this function to realize this purpose.
 * @author Hanlin Zhang.
 * @date 2021年1月16日
 */
public class DuiQi extends Thread{

	private CountDownLatch downLatch;
	private String fileName;
	private int i;
	private ADMINISTRATOR manager;
	
	public DuiQi() {
		super();
	}
	public DuiQi(
			CountDownLatch threadSignal_find,
			String fileStr,
			int i,
			ADMINISTRATOR manager) {
		super();
		this.downLatch = threadSignal_find;
		this.fileName = fileStr;
		this.i = i;
		this.manager = manager;
	}

	public void run() {
		try{
			manager.setNFile1(i, findNew.find(fileName,i,manager).getAbsolutePath());
			if(manager.isMrMa[i]==true) {
				MainThread.dateString[i] = ReadDateFromHead.readDataSegmentHead_MrMa_String(manager.getNFile1(i));
				System.out.println(MainThread.fileStr[i]+"MrMa  "+MainThread.dateString[i]);
			}
			else {
				MainThread.dateString[i] = ReadDateFromHead.readDataSegmentHeadall(manager.getNFile1(i));
				System.out.println(MainThread.fileStr[i]+"MrLiu  ："+MainThread.dateString[i]);
			}
		}
		catch(Exception e1){//the error of the network when the procedure are finding the file.
			manager.setNetError(true);
			manager.setNReduiqi(i, -1);
		}
		
		this.downLatch.countDown();
	}
}
