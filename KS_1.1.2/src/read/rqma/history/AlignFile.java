package read.rqma.history;

import com.h2.constant.Parameters;
import controller.ADMINISTRATOR;
import mutiThread.MainThread;
import read.yang.readFile.ReadDateFromHead;
import utils.ComparatorTimeLineTime;
import utils.Date2String;
import utils.DateArrayToIntArray;
import utils.String2Date;
import utils.SubStrUtil;
import utils.fileFilter;
import utils.filePatternMatch;
import utils.printRunningParameters;

import java.io.File;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: RQMA, Hanlin Zhang
 * @Date: 4/25/2019 5:54 PM
 */
public class AlignFile {
    //所有输入的存放hfmed文件的根目录
    private String[] fileStr;
    //时间线，小于该时间线的所有文件全部舍弃。
    private String timeStr;
    //manager初始化。
    private ADMINISTRATOR manager;
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
    
    public AlignFile(String[] fileStr, String timeStr, ADMINISTRATOR manger) {
        super();
        this.fileStr = fileStr;
        this.timeStr = timeStr;
        this.manager = manger;
        
        for(int i=0;i<Parameters.SensorNum;i++) {
    		align[i] = -1;
    	}
    }
    //获取时间距离单位：秒。
    public int[] getAlign() throws Exception {
        if (first) {
        	//找到所有大于time的文件，此时每个同目录下的文件已经有序，并存于time_sorted。
            for (int i = 0; i < Parameters.SensorNum; i++) {
                this.allfiles[i] = fileFilter.DataFileFilter(fileStr[i],timeStr);//得到输入目录下的所有文件名
                //设置每个大目录下的第一个大于time的文件属性，分别为大目录序号、位置、文件名、大目录的根目录。
                time_sorted[i] = new TimeLine();
                time_sorted[i].setId(i);
                time_sorted[i].setPosition(0);
                String name = allfiles[i][0].getName();
                time_sorted[i].setFilename(name);
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
        for (int i = 0; i < Parameters.SensorNum; i++) {
            // System.out.println(path);
             for(int j=0;j<time_sorted.length;j++) {
             	if(time_sorted[j].id==i) {
             		String path = time_sorted[j].getFile().getAbsolutePath();
             		paths_original[i]=path;
             	}
             }
         }
        

        /**
         * 以下与读新文件那里采取的方式一致
         */
        int[] TimeDifferertInt = new int[Parameters.SensorNum];
        String[] dateString = new String[Parameters.SensorNum];
        
        try {
            for (int i = 0; i < Parameters.SensorNum; i++) {
                System.out.print(
                		"第个"+
                		printRunningParameters.formToChar(String.valueOf(i+1))+
                		"台站欲处理的文件为"+
                		printRunningParameters.formToChar(paths_original[i]));
                if(isMrMaEquipment(paths_original[i])) {
                	manager.isMrMa[i] = true;
                	dateString[i] = ReadDateFromHead.readDataSegmentHead_MrMa_String(paths_original[i]);
                	System.out.println("  时间为：" + printRunningParameters.formToChar(dateString[i]));
                }
                else {
                	manager.isMrMa[i] = false;
                	dateString[i] = ReadDateFromHead.readDataSegmentHead_MrLiu_String(paths_original[i]);
                	System.out.println("  时间为：" + printRunningParameters.formToChar(dateString[i]));
                }
                manager.setNFile1(i, paths_original[i]);
                manager.setNDateString(i, dateString[i]);
            }
        } catch (Exception e) {
            for (int k = 0; k < Parameters.SensorNum; k++) {
                AlignFile.align[k] = -1;
            }
            return AlignFile.align;//返回了-1的数组
        }
        
        DateArrayToIntArray aDateArrayToIntArray = new DateArrayToIntArray();
        //计算时间差。
        TimeDifferertInt = aDateArrayToIntArray.IntArray(dateString);
        //获得最大时间。
        Date DateMax = aDateArrayToIntArray.getDate();
        //设置最大时间对应的序号，后面对齐使用。
        manager.setMaxTimeSeries(aDateArrayToIntArray.getMaxSeries());
        System.out.println("当前组最大时间：" + Date2String.date2str(DateMax));
        System.out.print("当前组时间差（秒）：");
        
        for (int i = 0; i < Parameters.SensorNum; i++) {
            System.out.print(TimeDifferertInt[i] + " ");
        }
        System.out.println();
        return TimeDifferertInt;
    }
    
    @SuppressWarnings("unused")
	private void timecheck() throws ParseException {
    	//先更新各自的起止时间。
    	updatedStartEndTime_all();
        //再根据时间是否重叠决定继续读新文件。
    	while (!isTimeOverlap(time_sorted)) {//再次更新。
        	updatedNewFile();
        }
    }

    private void updatedNewFile() throws ParseException {
    	int id = time_sorted[0].getId();
        time_sorted[0].position += 1;
        int pos = (time_sorted[0].position);
        if(pos>=allfiles[id].length){
            System.out.println("该台站此目录下的所有文件已处理完毕");
            MainThread.exitVariable_visual = true;
            System.exit(0);
        }
        time_sorted[0].setFilename(allfiles[id][pos].getName());
        time_sorted[0].setFile(allfiles[id][pos]);
        Map map = extractTime(time_sorted[0].getFile());
        time_sorted[0].setBegintime((Long) map.get("begintime"));
        time_sorted[0].setEndtime((Long) map.get("endtime"));
    }
    
    private void updatedStartEndTime_all() throws ParseException {
    	//记录当前time_sorted中文件的起止时间。
    	for (int i = 0; i < time_sorted.length; i++) {
            Map map = extractTime(time_sorted[i].getFile());
            time_sorted[i].setBegintime((Long) map.get("begintime"));
            time_sorted[i].setEndtime((Long) map.get("endtime"));
        }
    }
    
    /**
     * extract the time from a file name
     * @param filename
     * @return
     * @throws ParseException
     */
    public Map extractTime(File file) throws ParseException {

        String timestr;
        Map map = new HashMap();
        long begintime = 0;
        long endtime = 0;
        //刘老师设备
        if (!isMrMaEquipment(file.getName())) {
            timestr = "20" + SubStrUtil.contentCut_liu(file.getName());//
            begintime = String2Date.str2Date2(timestr).getTime();
//            endtime = file.lastModified();
            if (Parameters.region.equals("datong")) {
                endtime = begintime + (1800 - 1 * 60) * 1000;
            }
            else if (Parameters.region.equals("pingdingshan") || Parameters.region.equals("hongyang")) {
                endtime = begintime + (3600 - 3 * 60) * 1000;
            }
            
        }
        //马老师设备，因为马老师设备的最后修改时间lastmodified并不是该文件的最后一条数据的时间，因此不能用lastmodified时间来判断
        //后续需要改进！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        else {
            timestr = SubStrUtil.contentCut_ma(file.getName());//
            begintime = String2Date.str2Date2(timestr).getTime();
            endtime = begintime + (7200 - 5 * 60) * 1000;
        }
        map.put("begintime", begintime);
        map.put("endtime", endtime);
        return map;
    }
    
    /**
     * Determine whether the time periods of multiple stations overlap,
     * if they overlap, they can be aligned
     * @param timeLine:
     * @return
     */
    public boolean isTimeOverlap(TimeLine timeLine[]) {
        Arrays.sort(timeLine, new ComparatorTimeLineTime());
        //Skip the first time period without judgment
        for (int i = 1; i < timeLine.length; i++) {
            for (int j = 0; j < i; j++) {
                if ((timeLine[j].getEndtime() - timeLine[i].getBegintime()) < 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * 判断是否是新设备文件
     * @param filename
     * @return
     */
     public boolean isMrMaEquipment(String filename){
         boolean flag = false;
         if(filePatternMatch.match_BIN(filename) && filename.endsWith(".bin")) {
        	 flag = true;
         }
         
         return flag;
     }
}
