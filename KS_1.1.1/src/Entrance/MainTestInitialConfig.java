/**
 * 
 */
package Entrance;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.h2.constant.Parameters;
import mutiThread.MainThread;

/**
 * 自动根据当前路径配置离线运行下的参数运行，并区分各个不同的矿区。
 * 
 * 1、数据的上一级路径中必须包含"Testx"几个文件夹。
 * 
 * 2、路径中必须包含矿区中文名字，用来匹配矿区坐标。
 * 
 * @author Hanlin Zhang
 */
public class MainTestInitialConfig {
	
	/** 指示当前是否正确配置了路径*/
	@SuppressWarnings("unused")
	private boolean offline_isEnoughNumber = true;
	private boolean offline_isExistDatafile = true;
	
	/** 封装后缀*/
	private String suffix[] = {"HFMED", "bin"};
	private String dataForm = suffix[0];
	
	/** 封装地点名词，该词必须在以下变量中出现*/
	private String lo_pingdingshan[] = {"平顶山","平顶山十一矿","十一矿"};
	private String lo_hongyang[] = {"红阳三矿","三矿","红阳"};
	private String lo_madaotou[] = {"马道头"};
	
	private String[][] lo = {lo_pingdingshan, lo_hongyang, lo_madaotou};
	
	/** 路径下需要有几个离线跑数据的盘符*/
	private String prePath = "I:/矿山/矿山数据/平顶山/20201231/";
	
	/**
	 * 通过读取对应路径下的文件夹文字来获取盘符名称以及完整路径。
	 * 根据路径中的矿区名自动配置矿区变量。
	 * @throws IOException 
	 */
	MainTestInitialConfig (String prePath) throws IOException {
		this.prePath = prePath;
		
		/** 读取当前路径下的所有文件夹，并提取出运行的盘符和对应的路径*/
		MainThread.fileStr = obtainPath();
		
		/** 我们认为小于2个盘符的路径并不符合，数量不够*/
		if(MainThread.fileStr.length<=2) {
			this.offline_isEnoughNumber = false;
			System.out.println("存在- " + "传感器数量不足" + " -问题         是否继续？按任意键继续——————————");
			System.in.read();
		}
		
		/** 根据路径个数配置传感器数量*/
		Parameters.SensorNum = MainThread.fileStr.length;
		
		/** 配置区域*/
		regionConfig();
		
		/** 输出所有离线运行参数*/
		printAllParameters();
	}
	
	/**
	 * 打开当前目录下面的所有文件，并提取所有文件名。
	 * 当前目录下必须含有"Testa"-"Testz"的文件夹，且文件夹下有hfmed数据时才有意义。
	 * 也就是用于离线跑数据。
	 * @author Hanlin Zhang.
	 * @throws IOException 
	 * @date revision 2021年1月30日上午8:39:11
	 */
	private String[] obtainPath() throws IOException {
		File file = new File(prePath);
		File[] fs = file.listFiles();
		
		//返回结果
		String panfu[] = new String[0];
		
		for(int i=0;i<fs.length;i++) {
			int pathLen = fs[i].getPath().length();
			String identity = fs[i].getPath().substring(pathLen-5, pathLen-1);
			if(fs[i].isDirectory() && identity.compareTo("Test")==0) {
				if(checkHFMEDData(fs[i])) {
					panfu = Arrays.copyOf(panfu, panfu.length+1);
					panfu[panfu.length-1] = fs[i].getAbsolutePath();
					//将正斜杠换成反斜杠
					panfu[panfu.length-1] = pathProcess(panfu[panfu.length-1]);
				}
				/** 当前目录下没有数据*/
				else {
					this.offline_isExistDatafile = false;
					System.out.println("存在- " + "传感器数量不足" + " -问题               是否继续？按任意键继续——————————" + 
							fs[i].getPath()+"下，没有HFMED数据文件");
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
			if(suff.equals(dataForm)) {
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
				if(this.prePath.split(this.lo[i][j]).equals(part[z])) {
					switch (i) {
					case 0:
						Parameters.region_offline = "pingdingshan";
						break;
					case 1:
						Parameters.region_offline = "hongyang";
						break;
					case 2:
						Parameters.region_offline = "madaotou";
						break;
					default:
						System.out.println("路径中不包含能够识别的矿区位置！默认矿区为0号：红阳三矿");
						Parameters.region_offline = "hongyang";
					}				
				}
			}
		}
		}
		
	}
	
	private void printAllParameters() throws IOException {
		
		System.out.println("自动配置的主路径为：");
		for(int i=0;i<MainThread.fileStr.length;i++) {
			System.out.println(MainThread.fileStr[i]);
		}
		
		
		System.out.println("自动配置的矿区为：");
		System.out.println(Parameters.region_offline);
		System.out.println("自动配置的传感器数量为：");
		System.out.println(Parameters.SensorNum);
		
		System.out.println("自动配置完毕，是否继续？按任意键继续——————————");
		System.in.read();System.in.read();
		
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
