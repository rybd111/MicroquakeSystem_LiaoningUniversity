/**
 * 
 */
package middleware;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import javax.swing.filechooser.FileSystemView;

import org.eclipse.jface.text.source.AnnotationPainter.ITextStyleStrategy;

import com.h2.constant.Parameters;

import Entrance.InitialConfig;
import middleware.tool.FindHistoryFile;
import mutiThread.MainThread;
import utils.Date2String;
import utils.ScanInNum;
import utils.String2Date;
import utils.ask_YorN;
import utils.outArray;
import utils.printRunningParameters;

/**
 * Pull a fixed data range or file querying from different remote disks.(not accomplish)
 * @revision 2020-09-23
 * @author Hanlin Zhang
 * 
 */
public class pullDataFromSensor {
	
	private String startTime="";
	private String endTime="";
	private String timeStr = "";
	private String destPath = "";
	private boolean isTest;
	
	/**
	 * timeString must be yyyy-MM-ddHH:mm:ss
	 * kind select in "file" or "filedata".
	 * @param timeString
	 * @throws IOException 
	 * @throws ParseException 
	 */
	pullDataFromSensor(
			String startTime,
			String endTime,
			String timeStr,
			String kind,
			String destPath,
			boolean isTest) throws IOException, ParseException {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.timeStr = timeStr;
		this.destPath = destPath;
		this.isTest = isTest;
		
		if(kind.equals("file")) {
			pullFileFromRemoteInFile();
		}
		if(kind.equals("data")) {
			pullDataFromRemoteDiskInSec();
		}
	}
	
	/**
	 * 从所有网络盘符中获取给定时刻的文件。
	 * @throws IOException
	 * @throws ParseException
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月20日上午11:33:29
	 */
	private void pullFileFromRemoteInFile() throws IOException, ParseException {
		/** 返回存有HFMED的盘符，但此时不能确定是网络映射盘符，因此需要进一步验证*/
		MainThread.fileStr = scanAlldisk();
		
		if(ask_YorN.askYesOrNo() == true) {
			MainThread.fileStr = ask_YorN.determineDisk(MainThread.fileStr);
		}
		
		/**询问是否继续？*/
		ask_YorN.askWhenHasLess();
		
		/** 输出所有离线运行参数，供用户确认*/
		printRunningParameters.printpullParameters(this.timeStr);
		
		/**拉取*/
        FindHistoryFile.launch(MainThread.fileStr, destPath, timeStr);
	}
	
	private void pullDataFromRemoteDiskInSec() {
		
	}
	
	private File[] comingPullFiles() {
		
		return null;
	}
	
	private boolean queryFile() {
		
		return false;
	}
	
	/**
	 * 查看他们是否为网络映射盘，若是，还需要判断他们是否为传感器映射盘符。
	 * 通过查看磁盘根目录下是否包含多个含有Test_开头的文件夹进一步识别。
	 * @return
	 * @author Hanlin Zhang.
	 * @throws IOException 
	 * @date revision 2021年1月30日下午10:36:21
	 */
	private String[] scanAlldisk() throws IOException {
		File[] roots = File.listRoots();
		String[] sasroots = new String[0];
		//加入数字位数约束，为0时，不加数字位数约束
		int numberNum = 12;
		
		//决定网络映射盘符。
		for(int i=0;i<roots.length;i++) {
			String Type = FileSystemView.getFileSystemView().getSystemTypeDescription(roots[i]);
			if(Type.equals("网络驱动器")) {
//				if(determineDisk(roots[i].listFiles(), numberNum)==true) {
					sasroots = Arrays.copyOf(sasroots, sasroots.length+1);
					sasroots[sasroots.length-1] = roots[i].getAbsolutePath();
					sasroots[sasroots.length-1].toLowerCase();
//				}
			}
			//测试用代码。
			if(this.isTest == true) {
				if(Type.equals("本地磁盘")) {
					if(determineDisk(roots[i].listFiles(), numberNum)==true) {
						sasroots = Arrays.copyOf(sasroots, sasroots.length+1);
						sasroots[sasroots.length-1] = roots[i].getAbsolutePath();
						sasroots[sasroots.length-1].toLowerCase();
					}
				}
			}
		}
		if(sasroots.length==0) {
			System.out.println("没有扫描到任何盘，请使用调试模式，程序退出");
			System.exit(0);
		}
		
		return sasroots;
	}
	
	/**
	 * 基于1级目录下一定有HFMED或bin为后缀的文件为基础来定义
	 * 在此基础上，区分本地磁盘与挂载磁盘
	 * 否则此函数失效。
	 * @param roots
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月30日下午7:36:50
	 */
	private boolean determineDisk(File[] files, int numberNum) {
		for(File file : files) {
			String name = file.getName();
			if(numberNum <= 0) {
				if(name.contains("_")) {
					return true;
				}
			}
			else if(name.contains("_")) {
				if(name.split("_").length>=2) {
					if(name.split("_")[1].length() >= numberNum) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * test function.
	 * @param args
	 * @author Hanlin Zhang.
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		//注意此路径后面必须加上"/".
        String destPath = "I:\\矿山\\矿山数据\\大同\\1月14日大同塔山矿震动/";
//        String time = "20"+Parameters.StartTimeStr;
        String timeStr = "20" + "190114020001";
        String kind = "file";
        boolean isTest = true;
        
        pullDataFromSensor p = new pullDataFromSensor("", "", timeStr, kind, destPath, isTest);
        
	}

}
