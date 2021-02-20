/**
 * 
 */
package Entrance;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.jar.Attributes.Name;

import javax.swing.filechooser.FileSystemView;

import com.h2.constant.Parameters;
import com.ibm.icu.impl.UResource.Array;
import com.ibm.icu.impl.UResource.Value;
import com.mysql.cj.x.protobuf.MysqlxResultset.RowOrBuilder;
import com.orsoncharts.data.Values;

import middleware.tool.FindHistoryFile;
import mutiThread.MainThread;
import utils.ArrayMatch;
import utils.String2Date;
import utils.SubStrUtil;
import utils.outArray;
import org.xvolks.jnative.misc.*;
import org.xvolks.jnative.util.Kernel32;
import org.xvolks.jnative.util.Kernel32.FileAttribute;

/**
 * 自动根据当前路径配置离线运行下的参数运行，并区分各个不同的矿区。
 * 
 * 1、数据的上一级路径中必须包含"Testx"几个文件夹。
 * 
 * 2、路径中必须包含矿区中文名字，用来匹配矿区坐标。
 * 
 * @author Hanlin Zhang
 */
public class InitialConfig {
	
	
	
	private String[] dataForm = Parameters.suffix;
	
	/** 封装地点名词，该词必须在以下变量中出现*/
	private String lo_pingdingshan[] = {"平顶山","平顶山十一矿","十一矿"};
	private String lo_hongyang[] = {"红阳三矿","三矿","红阳"};
	private String lo_madaotou[] = {"马道头"};
	private String lo_datong[] = {"大同","塔山矿","塔山"};
	
	private String[][] lo = {lo_pingdingshan, lo_hongyang, lo_madaotou,lo_datong};
	
	/** 路径下需要有几个离线跑数据的盘符*/
	private String prePath = "";
	
	/**
	 * 
	 */
	public InitialConfig() {
		super();
	}
	/**
	 * 通过读取对应路径下的文件夹文字来获取盘符名称以及完整路径及传感器数量。
	 * 根据路径中的矿区名自动配置矿区变量。
	 * 输出参数配置情况。
	 * @param prePath can be "pull" or the absolute path of offline
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("unused")
	public InitialConfig(String prePath) throws IOException, ParseException {
		//前缀路径长度不足2，则认为前缀路径错误。
		if(prePath.length()<=1) {
			System.out.println("请配置正确的带有‘Testa-Testz’的离线数据路径。");
			System.exit(0);
		}
		if(Parameters.offline == true && prePath.equals("pull") == false) {
			commonProcess(prePath);
		}
		//为了测试在线部分代码。
		else if(Parameters.offline == false && prePath.equals("pull") == false) {
			commonProcess(prePath);
		}
	}
	
	/**
	 * 打开当前目录下面的所有文件，并提取所有文件名。
	 * 当前目录下必须含有"Testa"-"Testz"的文件夹，且文件夹下有hfmed数据时才有意义。
	 * 也就是用于离线跑数据。
	 * @author Hanlin Zhang.
	 * @throws IOException 
	 * @date revision 2021年1月30日上午8:39:11
	 */
	private String[] obtainOfflinePath() throws IOException {
		File file = new File(prePath);
		File[] fs = file.listFiles();
		
		//返回结果
		String panfu[] = new String[0];
		
		for(int i=0;i<fs.length;i++) {
			int pathLen = fs[i].getPath().length();
//			String identity = fs[i].getPath().substring(pathLen-5, pathLen-1);
			if(fs[i].isDirectory()) {
				if(checkHFMEDData(fs[i]) || checkBINData(fs[i])) {
					panfu = Arrays.copyOf(panfu, panfu.length+1);
					panfu[panfu.length-1] = fs[i].getAbsolutePath();
					//将正斜杠换成反斜杠
					panfu[panfu.length-1] = pathProcess(panfu[panfu.length-1]);
				}
				/** 当前目录下没有数据*/
				else {
					System.out.println(fs[i].getPath()+"下，没有HFMED数据文件，是否继续？？？");
					System.in.read();
				}
			}
		}
		
		return panfu;
	}
	
	/**
	 * 处理path变为正斜杠，避免出现路径的错误
	 * 该函数仅限于处理真正存放数据的父目录，即数据的上一级文件夹。
	 * @param path
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月30日上午11:01:42
	 */
	private String pathProcess(String path) {
		path = path.replaceAll("\\\\", "/") + "/";
		
		return path;
	}
	
	private boolean checkHFMEDData(File fs) {
		boolean flag = false;
		String[] hfmed = fs.list();
		
		for(int i=0;i<hfmed.length;i++) {
			//检测到有hfmed，则可以执行
			String suff = hfmed[i].split("\\.")[1];
			if(suff.equals(this.dataForm[0])) {
				flag = true;
			}
		}
		return flag;
	}
	
	private boolean checkBINData(File fs) {
		boolean flag = false;
		String[] bin = fs.list();
		
		for(int i=0;i<bin.length;i++) {
			//检测到有hfmed，则可以执行
			String suff = bin[i].split("\\.")[1];
			if(suff.equals(this.dataForm[1])) {
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 根据路径中包含的矿区位置进行region_offline变量的设置。
	 * 如果想手动设置
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月30日下午2:44:58
	 */
	private void regionConfig() {
		String part[] = this.prePath.split("/"); 
		
		for(int z=0;z<part.length;z++) {
		for(int i=0;i<lo.length;i++) {
			for(int j=0;j<lo[i].length;j++) {
				if(part[z].contains(this.lo[i][j])) {
					switch (i) {
					case 0:
						Parameters.region = "pingdingshan";
						break;
					case 1:
						Parameters.region = "hongyang";
						break;
					case 2:
						Parameters.region = "madaotou";
						break;
					case 3:
						Parameters.region = "datong";
						break;
					default:
						System.out.println("路径中不包含能够识别的矿区位置！默认矿区为0号：红阳三矿");
						Parameters.region = "hongyang";
					}				
				}
			}
		}
		}
		
	}
	
	public void printAllParameters() throws IOException {
		
		System.out.println("自动配置的主路径为： ");
		for(int i=0;i<MainThread.fileStr.length;i++) {
			System.out.println(MainThread.fileStr[i]);
		}
		
		System.out.print("自动配置的矿区为： ");
		System.out.println(Parameters.region);
		System.out.print("自动配置的传感器数量为： ");
		System.out.println(Parameters.SensorNum);
		
		System.out.println("其余参数： ");
		System.out.println("FREQUENCY： "+ Parameters.FREQUENCY);
		System.out.println("distanceToSquareWave： "+ Parameters.distanceToSquareWave);
		System.out.println("SevIP： "+ Parameters.SevIP);
		System.out.println("SENSORINFO1： ");
		
		outArray.outArray_double(Parameters.SENSORINFO1, Parameters.diskNameNum);
		System.out.println("StartTimeStr： "+ Parameters.StartTimeStr);
		
		System.out.println("自动配置完毕，是否继续？按任意键继续——————————");
		System.in.read();System.in.read();
		
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
//			if(Type.equals("本地磁盘")) {
//				if(determineDisk(roots[i].listFiles(), numberNum)==true) {
//					sasroots = Arrays.copyOf(sasroots, sasroots.length+1);
//					sasroots[sasroots.length-1] = roots[i].getAbsolutePath();
//				}
//			}
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
	
	private void commonProcess(String prePath) throws IOException {
		this.prePath = prePath;
		
		/** 读取当前路径下的所有文件夹，并提取出运行的盘符和对应的路径*/
		MainThread.fileStr = obtainOfflinePath();
		
		// 我们认为小于2个盘符的路径并不符合，数量不够
		if(MainThread.fileStr.length<=2) {
			System.out.println("传感器数量不足，个数为：" + MainThread.fileStr.length + "         是否继续？按任意键继续——————————");
			System.in.read();
		}
		
		/** 根据路径个数配置传感器数量*/
		Parameters.SensorNum = MainThread.fileStr.length;
		
		/** 配置区域*/
		regionConfig();
		
		/** 输出所有离线运行参数，供用户确认*/
		printAllParameters();
	}
	
	public void pullFileFromRemote(String destiPath, String timeStr) throws IOException, ParseException {
		/** 返回存有HFMED的盘符，但此时不能确定是网络映射盘符，因此需要进一步验证*/
		MainThread.fileStr = scanAlldisk();
		
		// 我们认为小于2个盘符的路径并不符合，数量不够
		if(MainThread.fileStr.length<=2) {
			System.out.println("传感器数量不足，个数为：" + MainThread.fileStr.length + "         是否继续？按任意键继续——————————");
			System.in.read();
		}
		
		/** 根据路径个数配置传感器数量*/
		Parameters.SensorNum = MainThread.fileStr.length;
		
		/** 按照区域配置diskNameNum*/
		Parameters.diskNameNum = ArrayMatch.match_String(Parameters.station ,Parameters.region);
		
		/** 输出所有离线运行参数，供用户确认*/
		printAllParameters();
		
		/**拉取*/
        FindHistoryFile.launch(MainThread.fileStr, destiPath, timeStr);
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 下午6:19:27
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
