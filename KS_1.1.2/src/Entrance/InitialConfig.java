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

import middleware.tool.FindHistoryFile_GetData;
import mutiThread.MainThread;
import utils.ArrayMatch;
import utils.String2Date;
import utils.SubStrUtil;
import utils.ask_YorN;
import utils.fileFilter;
import utils.filePatternMatch;
import utils.outArray;
import utils.printRunningParameters;

import org.xvolks.jnative.misc.*;
import org.xvolks.jnative.util.Kernel32;
import org.xvolks.jnative.util.Kernel32.FileAttribute;

/**
 * 自动根据当前路径配置离线运行下的参数运行，并区分各个不同的矿区。
 * 
 * 1、数据的上一级路径中必须包含"x"几个文件夹。
 * 
 * 2、路径中必须包含矿区中文名字，用来匹配矿区坐标。
 * 
 * @author Hanlin Zhang
 */
public class InitialConfig {
	
	/** 封装地点名词，该词必须在以下变量中出现*/
	private String lo_pingdingshan[] = {"平顶山","平顶山十一矿","十一矿"};
	private String lo_hongyang[] = {"红阳三矿","三矿","红阳"};
	private String lo_madaotou[] = {"马道头"};
	private String lo_datong[] = {"大同","塔山矿","塔山"};
	private String[][] lo = {lo_pingdingshan, lo_hongyang, lo_madaotou,lo_datong};
	/** 路径下需要有几个离线跑数据的盘符*/
	private String prePath = "";

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
//			System.out.println("请配置正确的带有‘Testa-Testz’的离线数据路径。");
			System.exit(0);
		}
		if(Parameters.offline == true) {
			commonProcess(prePath);
		}
		//为了测试在线部分代码。
		else if(Parameters.offline == false) {
			commonProcess(prePath);
		}
	}
	
	/**
	 * 打开当前目录下面的所有文件，并提取所有文件名。
	 * 当前目录下必须含有"Testa"-"Testz"的文件夹，且文件夹下有hfmed数据时才有意义。
	 * 同时我们使用命名正则式进行数据文件的过滤，包括刘老师马老师两种仪器的数据文件。
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
			if(fs[i].isDirectory()) {
				//过滤包含bin和HFMED的所有文件，且需要包含Test字符串，因此必须在运行前将各种设备文件分开，以免出现错误。
				File[] fs1 = fileFilter.useFileFilter(fs[i].getPath());
				
				if(fs1 == null) {
					continue;
				}
				if(fs1.length>0) {
					panfu = Arrays.copyOf(panfu, panfu.length+1);
					panfu[panfu.length-1] = fs[i].getAbsolutePath();
					//将正斜杠换成反斜杠
					panfu[panfu.length-1] = pathProcess(panfu[panfu.length-1]);
				}
				//当前目录下没有数据
				else {
					continue;
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
							Parameters.diskNameNum = 2;
							break;
						case 1:
							Parameters.region = "hongyang";
							Parameters.diskNameNum = 0;
							break;
						case 2:
							Parameters.region = "madaotou";
							Parameters.diskNameNum = 3;
							break;
						case 3:
							Parameters.region = "datong";
							Parameters.diskNameNum = 1;
							break;
						default:
							System.out.println("路径中不包含能够识别的矿区位置！默认矿区为0号：红阳三矿");
							Parameters.region = "hongyang";
							Parameters.diskNameNum = 0;
						}				
					}
				}
			}
		}
	}
	
	private void commonProcess(String prePath) throws IOException {
		this.prePath = prePath;
		
		/** 读取当前路径下的所有文件夹，并提取出运行的盘符和对应的路径*/
		MainThread.fileStr = obtainOfflinePath();
		
		/**询问是否继续？*/
		ask_YorN.askWhenHasLess(MainThread.fileStr);
		
		/** 根据路径个数配置传感器数量*/
		Parameters.SensorNum = MainThread.fileStr.length;
		
		/** 配置区域*/
		regionConfig();
		
		/** 输出所有离线运行参数，供用户确认*/
		printRunningParameters.printAllParameters();
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
