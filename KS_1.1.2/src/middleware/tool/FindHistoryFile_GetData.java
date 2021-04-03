package middleware.tool;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import com.h2.constant.Parameters;
import read.yang.readFile.findNew;
import utils.Date2String;
import utils.MutiThreadProcess;
import utils.String2Date;
import utils.SubStrUtil;
import utils.fileFilter;

/**
 * 此类只能找到二级目录，再往深处寻找没意义。
 * @author Hanlin Zhang
 */
public class FindHistoryFile_GetData implements Runnable {
	//parent是实际数据文件的根路径。
	private String sourcePath = "";
	//目的路径.
	private String destiPath = "";
	//Date是限定的时间约束。
	private Date time;
	//保存所有符合给定时间的文件。
	private File[] specifyFiles = new File[0];
	//保存所有在文件夹下满足文件特征的数据文件。
	private File[] satisfiedFiles = new File[0];
	private CountDownLatch latch;
	
    public FindHistoryFile_GetData(
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
	public void run() {
    	File[] Dic = new File(this.sourcePath).listFiles();
    	
    	//匹配时间
    	
    	
    	//按照最后修改时间排序。
		Arrays.sort(Dic, new findNew.CompratorByLastModified());
    	
    	this.satisfiedFiles = fileFilter.useFileFilterFileArray(Dic);
    	
    	//根路径下没有HFMED文件，则查找是否有给定时间的文件夹？
    	if(this.satisfiedFiles.length == 0) {
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
    	
//    	File f = matchTime(Dic);
//    	System.out.println("找到的满足条件的文件夹为：" + f.getName());
    	
    	for(int i=0;i<Dic.length;i++) {
			//看是否在文件夹内？
			if(Dic[i].isDirectory()) {
				flag = true;
				if(getFile(Dic[i].getPath())==false) {
					System.out.println("在" + Dic[i].getPath() + "下，没有找到符合给定时间：" + Date2String.date2str(time) + " 的文件。");
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
    	//是否包含有效数据？
    	boolean flag = false;
    	
    	File updateFile = new File(parent);
    	//更新satisfiedFiles数组
    	this.satisfiedFiles = updateFile.listFiles();
    	
    	//即当前parent目录下没有文件，因此也就不满足查找条件，返回false，避免后续文件出现空指针异常。
    	if(this.satisfiedFiles == null) {
    		return false;
    	}
    	
    	//过滤符合给定时间点的文件。
    	this.specifyFiles = fileFilter.TimeFilter(this.satisfiedFiles, this.time);
        //拷贝这些文件，并设置标志位。
        if(this.specifyFiles.length>0) {
        	System.out.println("在" + parent + "下，找到符合给定时间：" + Date2String.date2str(this.time) + " 的文件。");
        	System.out.println("找到的文件数量：" + this.specifyFiles.length);
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
		for(int i=0;i<this.specifyFiles.length;i++) {
			//获取根目录作为盘符名字。
			String panfu = SubStrUtil.contentCut_root(this.specifyFiles[i].getParent());
			//在目的路径创建文件夹。
			String filePathInter = this.destiPath + panfu;
			createFolder(filePathInter);
			//根据目的路径生成新的绝对路径。
			String filePathA = filePathInter + "/" + this.specifyFiles[i].getName();
			//在绝对路径申请新的file。
			File destfile = new File(filePathA);
			//拷贝文件，并计时
			long m1 = System.currentTimeMillis();
			copyFile(this.specifyFiles[i], destfile);
			double cost = (System.currentTimeMillis() - m1)/1000.0/60;
	    	System.out.println(filePathA + "拷贝完成 用时：" + cost + "分钟。");
		}
    }
    
    /**
     * 网上查的快速拷贝文件的方法。
     * 可输出进度条，更方便查看，但后续如果不用挂载方式进行拷贝，可能需要修改路径的获取方式。
     * @author Hanlin Zhang.
     * @throws IOException 
     * @date revision 2021年2月19日上午10:16:00
     */
    @SuppressWarnings("resource")
	public void copyFile(File source, File dest) throws IOException {
    	FileChannel inputChannel = null;
    	FileChannel outputChannel = null;
    	
    	try {
    		//获取输入流，并输出流的同步数据复制，拷贝文件数据。
	    	inputChannel = new FileInputStream(source).getChannel();
	    	outputChannel = new FileOutputStream(dest).getChannel();
	    	
	    	System.out.println("source file size:" + inputChannel.size());
	    	
	    	//每次只拷贝10个字节，这样便于输出进度条，并预估拷贝完成时间。
	    	int groupNumber = (int)inputChannel.size() / 10;
	    	
	    	for(int i = 0; i < groupNumber; i++) {
	    		
		    	long m1 = System.currentTimeMillis();//starttime
	    		outputChannel.transferFrom(inputChannel, outputChannel.size(), 10);
	    		long m2 = System.currentTimeMillis();//endtime
	    		//时间开销，单位：s
	    		double timeCost = (m2 - m1)/1000.0;
	    		//拷贝进度，当前已拷贝的数据大小/总大小，单位%。
	    		double progress = outputChannel.size()*1.0/inputChannel.size()*1.0;
	    		//获取当前拷贝10B的速度 单位：B/s
	    		double currentSpeed = 10/timeCost;
	    		//预估完成时间（剩余秒数 ，剩余拷贝文件大小/速度）单位：s
	    		double completeTime = (inputChannel.size()-outputChannel.size()) / currentSpeed;
	    		
	    		if(i%10000 == 0) {
		    		System.out.println(
		    				"已拷贝的文件大小为： " + outputChannel.size() + "B " + 
		    				"占总文件的百分比为：" + progress*100.0 + "% " +
		    				"当前的拷贝速度估计为：" + currentSpeed + "B/s " + 
		    				"预估剩余完成时间为：" + completeTime + "s"
		    		);
	    		}
	    	}
	    	
	    	//把最后剩余的字节数拷贝。
	    	outputChannel.transferFrom(inputChannel, outputChannel.size(), inputChannel.size());
	    	
	    	System.out.println("destination file size:" + outputChannel.size());
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
     * 直接匹配文件夹的时间，以缩小寻找的时间开销。
     * 我们只需要找到第一个小于查找时间的文件夹即可，那个文件必然在这个文件夹中。
     * @param file
     * 输入的file是有序的，根据lastmodified属性排序
     * @return
     */
    private File matchTime(File[] f) {
    	//获取要匹配的时间
    	long standardTime = this.time.getTime();
    	
    	File result = null;
    	
    	//获取每个文件夹的匹配时间。
    	for(int i=0;i<f.length;i++) {
    		//当前文件夹的时间小于文件时间才对，
    		long processTime = timeProcess(f[i].getName());
    		if(processTime == -1) {
    			return null;
    		}
    		else if(processTime - standardTime <= 0) {
    			result = f[i];
    		}
    	}
    	
    	return result;
    }
    
    private long timeProcess(String A) {
    	
    	String part[] = A.split("_");
    	
    	if(part.length<=1) {
    		return -1;
    	}
    	
		A = "20" + part[1];
		Date d = null;
		
		try {
			d = String2Date.str2Date2(A);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long result = d.getTime();
		
		return result;
		
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
        
        System.out.println("抓取开始---------------------------！！");
    	long m1 = System.currentTimeMillis();

    	MutiThreadProcess.getFileMuti(sourcePath, destiPath, timeStr);
    	
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
        
//        ExecutorService executor = Executors.newFixedThreadPool(Parameters.SensorNum);
        final CountDownLatch threadSignal = new CountDownLatch(Parameters.SensorNum);
//        
//        for(int i=0;i<sourcePath.length;i++) {
//	        FindHistoryFile_GetData findHistoryFile = new FindHistoryFile_GetData(sourcePath[i], destiPath, specifyTime, threadSignal);
//	        executor.execute(findHistoryFile);
//        }
//        
//        try {
//            threadSignal.await();
//            executor.shutdown();
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
    	
    	//测试进度条机制是否正确？
        FindHistoryFile_GetData findHistoryFile = new FindHistoryFile_GetData(
        		"C:/Users/zhang/Desktop/Hfmed Browser/VERSION.dll",
        		"C:/Users/zhang/Desktop/", 
        		specifyTime,
        		threadSignal
        		);
        
        File file1 = new File("C:/Users/zhang/Desktop/Hfmed Browser/111.txt");
        
        File file2 = new File("C:/Users/zhang/Desktop/111.txt");
        
        findHistoryFile.copyFile(
        		file1, 
        		file2
        		);
    }

}
