/**
 * 
 */
package utils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

import javax.swing.filechooser.FileSystemView;

import com.yhy.getinformation.SensorDiskMonitor;

/**
 * Pull a fixed data range or file querying from different remote disks.(not accomplish)
 * @revision 2020-09-23
 * @author Hanlin Zhang
 * 
 */
public class GetNetDisk {
	
	private boolean isTest = false;
	
	public GetNetDisk() {
	}
	/**
	 * timeString must be yyyy-MM-ddHH:mm:ss
	 * kind select in "file" or "filedata".
	 * @param timeString
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public GetNetDisk(boolean isTest) throws IOException, ParseException {
		this.isTest = isTest;
	}
	
	/**
	 * 从所有网络盘符中获取给定时刻的文件。
	 * @throws IOException
	 * @throws ParseException
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月20日上午11:33:29
	 */
	public String[] getRemoteDiskName() throws IOException, ParseException {
		/** 返回存有HFMED的盘符，但此时不能确定是网络映射盘符，因此需要进一步验证*/
		String[] fileStr = scanAlldisk();
		
		/** 输出所有离线运行参数，供用户确认*/
		printRunningParameters.printScanPath(fileStr);
		
		return fileStr;
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
		System.out.println("开始扫描");
		
		if(roots.length==0) {
			System.out.println("没有扫描到任何盘，程序继续，下一个单位时间"+SensorDiskMonitor.SleepTime/1000+"秒后再次更新。");
			return null;
		}
		
		//决定网络映射盘符。
		for(int i=0;i<roots.length;i++) {
			String Type = FileSystemView.getFileSystemView().getSystemTypeDescription(roots[i]);
			if(Type.equals("网络驱动器")) {
//				if(determineDiskIsContainsHFMED_BIN(roots[i].listFiles())==true) {
					sasroots = Arrays.copyOf(sasroots, sasroots.length+1);
					sasroots[sasroots.length-1] = roots[i].getAbsolutePath();
					sasroots[sasroots.length-1].toLowerCase();
//				}
			}
			//测试用代码。
			if(this.isTest == true) {
				if(Type.equals("本地磁盘")) {
					if(determineDiskIsContainsHFMED_BIN(roots[i].listFiles())==true) {
						sasroots = Arrays.copyOf(sasroots, sasroots.length+1);
						sasroots[sasroots.length-1] = roots[i].getAbsolutePath();
						sasroots[sasroots.length-1].toLowerCase();
					}
				}
			}
		}
		if(sasroots.length==0) {
			System.out.println("没有扫描到任何盘，程序继续，下一个单位时间"+SensorDiskMonitor.SleepTime/1000+"秒后再次更新。");
			return null;
		}
		
		return sasroots;
	}
	
	/**
	 * 老仪器刘老师基于1级目录下一定有装有HFMED的文件夹。
	 * 新仪器马老师根目录下有数据文件bin。
	 * 在此基础上，区分本地磁盘与挂载磁盘下是否有数据文件，否则就不处理了，因为可能是挂载的其他用途的磁盘。
	 * 否则此函数失效。
	 * @param roots
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月30日下午7:36:50
	 */
	private boolean determineDiskIsContainsHFMED_BIN(File[] files) {
		for(File file : files) {
			if(
					filePatternMatch.match_HFMED(file.getName()) || 
					filePatternMatch.match_BIN(file.getName())
					){
				return true;
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
        boolean isTest = true;
        GetNetDisk p = new GetNetDisk(isTest);
        p.getRemoteDiskName();
	}

}
