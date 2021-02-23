package middleware.tool;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.h2.constant.Parameters;
import com.sleepycat.je.txn.ThinLockImpl;

import cn.hutool.bloomfilter.bitMap.LongMap;
import utils.Date2String;
import utils.String2Date;
import utils.SubStrUtil;
import utils.fileFilter;
import utils.filePatternMatch;
import visual.Start;

public class FindHistoryFile implements Runnable {
	//parent是实际数据文件的根路径。
	private String sourcePath = "";
	//目的路径.
	private String destiPath = "";
	//Date是限定的时间约束。
	private Date time;
	//保存所有符合条件的文件。
	private File[] files = new File[0];
	private CountDownLatch latch;
	
    public FindHistoryFile(
    		String parent,
    		String destiPath,
    		Date time,
    		CountDownLatch latch) {
    	this.sourcePath = parent;
    	this.destiPath = destiPath;
    	this.time = time;
    	this.latch = latch;
    }
    
    /**
     * 定位给定时间所在的绝对路径。
     * 注意：此函数只能定位二级目录，因为再往下的目录没有任何意义，如果遍历所有目录，不太可能找到，而且效率变差。
     * @author Hanlin Zhang.
     * @date revision 2021年2月19日上午11:15:38
     */
    @SuppressWarnings({ "deprecation", "unused" })
	public void run() {
    	File[] Dic = new File(this.sourcePath).listFiles();
    	//根路径下没有HFMED文件，则查找是否有给定时间的文件夹？
    	if(fileFilter.filter(this.sourcePath) == false) {
    		try {
				entranceDirectory(Dic);
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
    	}
    	//根路径下有HFMED文件，则查找是否有给定时间的文件？
    	else {
    		try {
				if(getFile(sourcePath)==false) {
					entranceDirectory(Dic);
				}
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
    	}
    	this.latch.countDown();
    }
    
    /**
     * 深入二级文件夹进行判断，如果没有则输出没有找到。
     * @param Dic
     * @throws ParseException
     * @author Hanlin Zhang.
     * @throws IOException 
     * @date revision 2021年2月19日下午12:34:45
     */
    private void entranceDirectory(File[] Dic) throws ParseException, IOException {
    	boolean flag = false;
    	
    	for(int i=0;i<Dic.length;i++) {
			//看是否在文件夹内？
			if(Dic[i].isDirectory()) {
				flag = true;
				//文件夹名符合传感器文件的格式？
				if(filePatternMatch.match_HFMED(Dic[i].getName())==true) {
//					System.out.println("location定位结果" + Dic[i].getPath());
					if(getFile(Dic[i].getPath())==false) {
						System.out.println("在" + Dic[i].getPath() + "下，没有找到符合给定时间：" + Date2String.date2str(time) + " 的文件。");
					}
				}
			}
		}
    	if(flag == false) {
    		System.out.println("在" + this.sourcePath + "下没有找到文件夹");
    	}
    }
    
    /**
     * 找到给定时刻对应的文件，该文件起止时间包含该时刻
     * 没有则返回false
     * @throws ParseException
     * @author Hanlin Zhang.
     * @throws IOException 
     * @date revision 2021年2月19日上午9:04:34
     */
    @SuppressWarnings("unused")
	private boolean getFile(String parent) throws ParseException, IOException {
    	boolean flag = false;
    	//过滤符合给定时间点的文件。
    	this.files = fileFilter.TimeFilter(parent, this.time);
        //拷贝这些文件，并设置标志位。
        if(this.files.length>0) {
        	copyFileFromRemote();
        	flag = true;
        }
        return flag;
    }
    
    /**
     * 给定目标路径，则将查找到的文件复制到指定位置，并根据源文件所属盘符进行文件夹的自动创建。
     * @param destiPath
     * @author Hanlin Zhang.
     * @throws IOException 
     * @throws ParseException 
     * @date revision 2021年2月19日上午9:05:23
     */
    public void copyFileFromRemote() throws IOException, ParseException {
		for(int i=0;i<this.files.length;i++) {
			//获取根目录作为盘符名字。
			String panfu = SubStrUtil.contentCut_root(this.files[i].getParent());
			//在目的路径创建文件夹。
			String filePathInter = this.destiPath + panfu;
			createFolder(filePathInter);
			//根据目的路径生成新的绝对路径。
			String filePathA = filePathInter + "/" + this.files[i].getName();
			//在绝对路径申请新的file。
			File dest = new File(filePathA);
			//拷贝文件，并计时
			long m1 = System.currentTimeMillis();
			copyFile(this.files[i], dest);
			double cost = (System.currentTimeMillis() - m1)/1000.0/60;
	    	System.out.println(filePathA + "拷贝完成 用时：" + cost + "分钟。");
		}
    }
    
    /**
     * 网上查的快速拷贝文件的方法。
     * 
     * @author Hanlin Zhang.
     * @throws IOException 
     * @date revision 2021年2月19日上午10:16:00
     */
    @SuppressWarnings("resource")
	private void copyFile(File source, File dest) throws IOException {
    	FileChannel inputChannel = null;
    	FileChannel outputChannel = null;
    	try {
	    	inputChannel = new FileInputStream(source).getChannel();
	    	outputChannel = new FileOutputStream(dest).getChannel();
	    	outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
    	} finally {
	    	inputChannel.close();
	    	outputChannel.close();
    	}
    }
    
    private void createFolder(String path) {
    	File file = new File(path);
    	file.mkdir();
    }
    
    /**
     * 启动。
     * @param sourcePath 所有根路径。
     * @param destiPath 目标路径。
     * @param timeStr 时间格式为yyyymmddhhmmss.
     * @throws ParseException
     * @throws IOException
     * @author Hanlin Zhang.
     * @date revision 2021年2月19日下午4:11:39
     */
    public static void launch(String[] sourcePath, String destiPath, String timeStr) throws ParseException, IOException {
    	ExecutorService executor = Executors.newFixedThreadPool(sourcePath.length);
        final CountDownLatch threadSignal = new CountDownLatch(sourcePath.length);
        
        Date specifyTime = String2Date.str2Date2(timeStr);
        
        System.out.println("抓取开始---------------------------！！");
    	long m1 = System.currentTimeMillis();
        for(int i=0;i<sourcePath.length;i++) {
	        FindHistoryFile findHistoryFile = new FindHistoryFile(sourcePath[i], destiPath, specifyTime,threadSignal);
	        executor.execute(findHistoryFile);
        }
        try {
            threadSignal.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        executor.shutdown();
        
        double cost = (System.currentTimeMillis() - m1)/1000.0;
    	System.out.println("抓取完毕---------------------------！！_ 共用时："+ cost + "秒");
    }
    
    public static void main(String[] args) throws ParseException, IOException {
//        String sourcePath= "I:\\矿山\\矿山数据\\1月14日大同塔山矿震动\\Test1\\";
    	//盘符给定是一个数组或字符串，若为字符串，则应该是完整的直接包含HFMED的父路径，若为数组，应当是远程传感器的盘符。
        String sourcePath[] = {"x:/","w:/","y:/"};
        //时间格式为yyyymmddhhmmss.
        String timeStr = "20" + "190114020001";
        //注意此路径后面必须加上"/".
        String destiPath = "I:\\矿山\\矿山数据\\大同\\1月14日大同塔山矿震动\\";
        Date specifyTime = String2Date.str2Date2(timeStr);
        
        ExecutorService executor = Executors.newFixedThreadPool(Parameters.SensorNum);
        final CountDownLatch threadSignal = new CountDownLatch(Parameters.SensorNum);
        
        for(int i=0;i<sourcePath.length;i++) {
	        FindHistoryFile findHistoryFile = new FindHistoryFile(sourcePath[i], destiPath, specifyTime, threadSignal);
	        executor.execute(findHistoryFile);
        }
        
        try {
            threadSignal.await();
            executor.shutdown();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

}
