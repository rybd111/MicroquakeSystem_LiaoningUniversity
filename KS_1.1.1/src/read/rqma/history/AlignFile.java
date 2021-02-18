package read.rqma.history;

import com.h2.constant.Parameters;
import mutiThread.MainThread;
import read.yang.readFile.ReadDateFromHead;
import utils.Date2String;
import utils.DateArrayToIntArray;
import utils.String2Date;

import java.io.File;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

/**
 * @Description:
 * @Auther: RQMA
 * @Date: 4/25/2019 5:54 PM
 */
public class AlignFile {
	//当前目录下的最早最晚时间。
    String str_oldest_s;
    String str_newest_s;
    //五个台站所读文件中的最老最新时间
    long oldest_date_s;
    long newest_date_s;
    long oldest_date_e;
    long newest_date_e;
    //所有输入的存放hfmed文件的根目录
    private String[] fileStr;
    //时间线，小于该时间线的所有文件全部舍弃。
    private String timeStr;
    
    private String dateStr;
    //文件排序类
    public TimeLine time_sorted[] = new TimeLine[Parameters.SensorNum];
    //五个台站的全路径
    public String[] paths_original = new String[Parameters.SensorNum];
    //存放所有文件名和所在文件夹名
    private File[][] allfiles = new File[Parameters.SensorNum][24 * 360];
    //第一次读，需要根据输入的目录名读文件
    private boolean first = true;
    //文件时间差数组，单位：秒。
    public static int align[] = new int[Parameters.SensorNum];
    
    public AlignFile(String[] fileStr, String timeStr) {
        super();
        this.fileStr = fileStr;
        this.timeStr = timeStr;
        for(int i=0;i<Parameters.SensorNum;i++) {
    		align[i] = -1;
    	}
    }

    //获取时间距离单位：秒。
    public int[] getAlign() throws Exception {
        if (first) {
        	//找到所有大于time的文件，此时每个同目录下的文件已经有序，并存于time_sorted。
            for (int i = 0; i < Parameters.SensorNum; i++) {
                this.allfiles[i] = get(fileStr[i]);//得到输入目录下的所有文件名
                //设置每个大目录下的第一个大于time的文件属性，分别为大目录序号、位置、文件名、大目录的根目录。
                time_sorted[i] = new TimeLine();
                time_sorted[i].setId(i);
                time_sorted[i].setPosition(0);
                String name = allfiles[i][0].getName();
                time_sorted[i].setFilename(contentCut(name));
                time_sorted[i].setFilepath(fileStr[i]);
                time_sorted[i].setFile(allfiles[i][0]);
            }
            timecheck();
            first = false;
        }
        else {
        	updatedNewFile();
            timecheck();
        }

        /**
         * for循环用于路径还原
         */
        for(int j=0;j<time_sorted.length;j++) {
    		String path = time_sorted[time_sorted[j].id].getFile().getAbsolutePath();
    		paths_original[time_sorted[j].id]=path;
        }

        /**
         * 以下与读新文件那里采取的方式一致
         */
        int[] TimeDifferertInt = new int[Parameters.SensorNum];
        String[] dateString = new String[Parameters.SensorNum];

        try {
            for (int i = 0; i < Parameters.SensorNum; i++) {
                System.out.println("第个"+(i+1)+"台站欲处理的文件为"+paths_original[i]);
                dateString[i] = ReadDateFromHead.readDataSegmentHeadall(paths_original[i]);
            }

        } catch (Exception e) {
            for (int k = 0; k < Parameters.SensorNum; k++) {
                AlignFile.align[k] = -1;
            }
            return AlignFile.align;//返回了-1的数组
        }
        System.out.println("当前组的段头时间：");

        for (int i = 0; i < Parameters.SensorNum; i++) {
            System.out.println(dateString[i]);
        }
        DateArrayToIntArray aDateArrayToIntArray = new DateArrayToIntArray();
        TimeDifferertInt = aDateArrayToIntArray.IntArray(dateString);

        Date DateMax = aDateArrayToIntArray.getDate();

        this.dateStr = Date2String.date2str(DateMax);
        System.out.println("当前组最大时间：" + this.dateStr);
        System.out.print("当前组时间差：");
        for (int i = 0; i < Parameters.SensorNum; i++) {
            System.out.print(TimeDifferertInt[i] + " ");
        }
        System.out.println();
        return TimeDifferertInt;
    }
    
    @SuppressWarnings("unused")
	private void timecheck() throws ParseException {
    	updatedStartEndTime();
        /**
         * 必须保证需要处理的一组文件的时间差在一小时内，所以  (newest_date - oldest_date) >= 3600 * 1000
         * 后来跑程序发现，一个台站的下一个文件与上一个文件相隔不是严格的一小时，可能57，58分钟左右，如果按照一小时算，当五台站中有几个是同一个台站时，会导致一个台站的上一个文件与下一个文件在同一组计算
           因此,时间差取 57分钟  (3600-3*60) * 1000
           注意，以后若观察到下一个文件与上一个文件相差更少，需要再调整时间差
        */
	    /**
	    * 意想不到是，大同的文件半小时产生一份，少年，这个bug找累了吗，哈哈哈...... 2019.06.19
	    * 但实际情况不可能所有文件都是一个间隔，会有变化，因此此方法失效。2021.02.18
	    * 实际只需使用最早结束时间和最新开始时间作比较就可以得知他们是否有重叠。
	    */
        while ((newest_date_s - oldest_date_e) >= 0) {//再次更新。
        	updatedNewFile();
            updatedStartEndTime();
        }
    }

    /**
     * 得到所有大于time的文件名
     * @param path
     * @param sid
     * @param time
     * @return 所有文件名
     */
    @SuppressWarnings("unused")
	private File [] get(String path) {
        
    	File file = new File(path);
        //过滤器，过滤后缀为HFMED的文件。
    	FileAccept fileAccept = new FileAccept();
        fileAccept.setExtendName("HFMED");
        
        File[] files = file.listFiles(fileAccept);
        
        if (files == null)
            return null;
        
        //排序根据文件名，此处排序只跟同目录下文件比较，因此不用考虑文件名不匹配的问题。
        Arrays.sort(files, new ComparatorByFileName());
        
        int i=0;
        
        //取大于timeStr的所有文件。
        for (; i <  files.length; i++) {
            if(contentCut(files[i].getName()).compareTo(timeStr)>=0){
                break;
            }
        }
        
        File temp[] = new File[0];
        
        for(int j=i;j<files.length;j++) {
        	temp = Arrays.copyOf(temp, temp.length+1);
        	temp[temp.length-1] = files[j];
        }
        
        return temp;
    }
    
    /**
     * 获得下划线后面的时间内容。
     * @param fileName
     * @return
     * @author Hanlin Zhang.
     * @date revision 2021年2月18日下午7:01:11
     */
    private String contentCut(String fileName) {
    	String part[] = fileName.split("_");
    	if(part.length<=1) {
    		return null;
    	}
    	else {
    		String []str=new String[2];
            str = fileName.split("_");
            String results = str[1].split("\\u002E")[0];
            return  results;
    	}
    }
    
    private void updatedNewFile() {
    	int id = time_sorted[0].getId();
        time_sorted[0].position += 1;
        int pos = (time_sorted[0].position);
        if(pos>=allfiles[id].length){
            System.out.println("该台站此目录下的所有文件已处理完毕");
            MainThread.exitVariable_visual = true;
            System.exit(0);
        }
        time_sorted[0].setFilename(contentCut(allfiles[id][pos].getName()));
        time_sorted[0].setFile(allfiles[id][pos]);
    }
    
    private void updatedStartEndTime() throws ParseException {
    	Arrays.sort(time_sorted, new ComparatorTimeLine());
    	
    	str_oldest_s = "20" + time_sorted[0].getFilename();//最老时间字符串
        str_newest_s = "20" + time_sorted[Parameters.SensorNum-1].getFilename();//最新时间字符串
        
        oldest_date_s = String2Date.str2Date2(str_oldest_s).getTime(); //最老开始时间
        newest_date_s = String2Date.str2Date2(str_newest_s).getTime(); ///最新开始时间
        oldest_date_e = time_sorted[0].getFile().lastModified();//最老结束时间。
        newest_date_e = time_sorted[0].getFile().lastModified(); //最新开始时间
    }
}
