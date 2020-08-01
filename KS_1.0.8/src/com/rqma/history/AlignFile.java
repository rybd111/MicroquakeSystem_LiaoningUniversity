package com.rqma.history;

import com.h2.constant.Parameters;
import com.yang.readFile.ReadDataSegmentHead;

import mutiThread.MainThread;
import utils.DateArrayToIntArray;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

/**
 * @Description:
 * @Auther: RQMA
 * @Date: 4/25/2019 5:54 PM
 */
public class AlignFile {
    String str_oldest;
    String str_newest;
    /**
     * 五个台站所读文件中的最老时间
     */
    long oldest_date;
    /**
     * 五个台站所读文件中的最最新时间
     */
    long newest_date;

    private String dateStr;

    public TimeLine time_sorted[] = new TimeLine[Parameters.SensorNum];

    /**
     * 五个台站的全路径
     */
    public String[] paths_original = new String[Parameters.SensorNum];

    /**
     * 存放所有文件名和所在文件夹名
     */
    private String[][] allfilesname = new String[Parameters.SensorNum][24 * 360];
    /**
     * 第一次读，需要根据输入的目录名读文件，
     */
    private boolean first = true;
    /**
     * 第一次以后，从数组读，速度快
     */
    private boolean notfirst = false;

    public static int align[] = new int[Parameters.SensorNum];
    

    public AlignFile() {
        super();
    }

    public int[] getAlign(String fileName[], String time) throws Exception {
    	for(int i=0;i<Parameters.SensorNum;i++) {
    		align[i] = -1;
    	}
        if (first) {
            for (int i = 0; i < Parameters.SensorNum; i++) {

                this.allfilesname[i] = FindHistoryFile.get(fileName[i], i, time);//得到输入目录下的所有文件名

                time_sorted[i] = new TimeLine();//定义
                time_sorted[i].setId(i);
                time_sorted[i].setPosition(0);
                time_sorted[i].setFilename(allfilesname[i][0]);
                time_sorted[i].setFilepath(fileName[i]);
            }
            timecheck(time_sorted);
            first = false;
        }
        if (notfirst) {
            int id = time_sorted[0].getId();
            time_sorted[0].position += 1;
            int pos = (time_sorted[0].position);
            if(pos>=allfilesname[id].length){
                System.out.println("该台站此目录下的所有文件已处理完毕");
                MainThread.exitVariable_visual = true;
                System.exit(0);
            }

            time_sorted[0].setFilename(allfilesname[id][pos]);

            timecheck(time_sorted);

        }
        notfirst = true;

   /*     System.out.println("timeLine数组当前值");
        for (int i = 0; i < Parameters.SensorNum; i++) {
           System.out.println(time_sorted[i].getId() + " " + time_sorted[i].getFilename() + " " + time_sorted[i].getFilepath());
        }*/

        /**
         * for循环用于路径还原
         */
        for (int i = 0; i < Parameters.SensorNum; i++) {
           // System.out.println(path);
            for(int j=0;j<time_sorted.length;j++) {
            	if(time_sorted[j].id==i) {
            		String path = time_sorted[j].getFilepath() + time_sorted[j].getFilename();
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
                System.out.println("第个"+(i+1)+"台站欲处理的文件为"+paths_original[i]);
                dateString[i] = ReadDataSegmentHead.readDataSegmentHeadall(paths_original[i]);
            }

        } catch (Exception e) {
            for (int k = 0; k < Parameters.SensorNum; k++) {
                AlignFile.align[k] = -1;
            }
            return AlignFile.align;//返回了-1的数组
        }
        System.out.println("文件第一个数据段段头时间：");

        for (int i = 0; i < Parameters.SensorNum; i++) {
            System.out.println(dateString[i]);
        }
        DateArrayToIntArray aDateArrayToIntArray = new DateArrayToIntArray();
        TimeDifferertInt = aDateArrayToIntArray.IntArray(dateString);

        Date DateMax = aDateArrayToIntArray.getDateStr();

        this.dateStr = DateUtil.toString(DateMax);
        System.out.println("文件第一个数据段段头最大时间：" + this.dateStr);
        System.out.print("文件时间差：");
        for (int i = 0; i < Parameters.SensorNum; i++) {
            System.out.print(TimeDifferertInt[i] + " ");
        }
        System.out.println();
        return TimeDifferertInt;
    }

    @SuppressWarnings("unused")
	private void timecheck(TimeLine timeLine[]) throws ParseException {

        Arrays.sort(timeLine, new ComparatorTimeLine());//巨坑，平顶山和大同硬件生成的文件命名就不能统一吗?????????
        
        if(Parameters.region_offline=="datong") {
	        str_oldest = "20" + SubStrUtil.getSubParentPackage(timeLine[0].getFilename());//最老时间字符串
	        str_newest = "20" + SubStrUtil.getSubParentPackage(timeLine[Parameters.SensorNum-1].getFilename());//最新时间字符串
        }
        else if(Parameters.region_offline=="pingdingshan"||Parameters.region_offline=="hongyang") {
        	str_oldest = "20" + timeLine[0].getFilename().substring(5, 17);//最老时间字符串
            str_newest = "20" + timeLine[Parameters.SensorNum-1].getFilename().substring(5, 17);//最新时间字符串
        }
        	
        oldest_date = DateUtil.toDate(str_oldest).getTime(); //最老时间
        newest_date = DateUtil.toDate(str_newest).getTime(); ///最新时间
        /**
         * 必须保证需要处理的一组文件的时间差在一小时内，所以  (newest_date - oldest_date) >= 3600 * 1000
         * 后来跑程序发现，一个台站的下一个文件与上一个文件相隔不是严格的一小时，可能57，58分钟左右，如果按照一小时算，当五台站中有几个是同一个台站时，会导致一个台站的上一个文件与下一个文件在同一组计算
           因此,时间差取 57分钟  (3600-3*60) * 1000
           注意，以后若观察到下一个文件与上一个文件相差更少，需要再调整时间差
         */

        
        /**
         *意想不到是，大同的文件半小时产生一份，少年，这个bug找累了吗，哈哈哈...... 2019.06.19
         */
        if(Parameters.region_offline=="datong") {
	        while ((newest_date - oldest_date) >= (1800-1*60) * 1000) {
	            int id = timeLine[0].getId();
	            timeLine[0].position += 1;
	            int pos = (timeLine[0].position);
	            if(pos>=allfilesname[id].length){
	                System.out.println("该台站此目录下的所有文件已处理完毕(---没有能够对齐的文件---)");
	                MainThread.exitVariable_visual = true;
	                System.exit(0);
	            }
	
	            timeLine[0].setFilename(allfilesname[id][pos]);
	            Arrays.sort(timeLine, new ComparatorTimeLine());
	            str_oldest = "20" + SubStrUtil.getSubParentPackage(timeLine[0].getFilename());
	            str_newest = "20" + SubStrUtil.getSubParentPackage(timeLine[Parameters.SensorNum-1].getFilename());
	
	            oldest_date = DateUtil.toDate(str_oldest).getTime();
	            newest_date = DateUtil.toDate(str_newest).getTime();
	        }
        }
        else if(Parameters.region_offline=="pingdingshan"||Parameters.region_offline=="hongyang") {
        	while ((newest_date - oldest_date) >= (3600-3*60) * 1000) {
                int id = timeLine[0].getId();
                timeLine[0].position += 1;
                int pos = (timeLine[0].position);
                if(pos>=allfilesname[id].length){
                    System.out.println("该台站此目录下的所有文件已处理完毕(---没有能够对齐的文件---)");
                    MainThread.exitVariable_visual = true;
                    System.exit(0);
                }

                timeLine[0].setFilename(allfilesname[id][pos]);
                Arrays.sort(timeLine, new ComparatorTimeLine());
                str_oldest = "20" + timeLine[0].filename.substring(5, 17);
                str_newest = "20" + timeLine[Parameters.SensorNum-1].filename.substring(5, 17);

                oldest_date = DateUtil.toDate(str_oldest).getTime();
                newest_date = DateUtil.toDate(str_newest).getTime();
            }
        }


    }

    /**
     * 这是之前的想法，一次读一个文件，一小时处理之后，再去找文件，费时间，不采用
     *
     * @param fileName
     * @param time
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
	public int[] AlignMain(String fileName[], String time) throws Exception {

        String str_oldest;
        String str_newest;
        long oldest_date;
        long newest_date;
        for (int i = 0; i < Parameters.SensorNum; i++) {
            time_sorted[i] = new TimeLine();
            time_sorted[i].setId(i);
            time_sorted[i].setFilename(FindHistoryFile.getFile(fileName[i], i, time).getName());
            time_sorted[i].setFilepath(FindHistoryFile.getFile(fileName[i], i, time).getParent());

        }
        Arrays.sort(time_sorted, new ComparatorTimeLine()); //五个台站时间排序

        for (int i = 0; i < Parameters.SensorNum; i++) {
            System.out.println(time_sorted[i].getId() + " " + time_sorted[i].getFilename() + " " + time_sorted[i].getFilepath());

        }

        str_oldest = "20" + time_sorted[0].filename.substring(5, 17);//最老时间字符串
        str_newest = "20" + time_sorted[Parameters.SensorNum-1].filename.substring(5, 17);//最新时间字符串

        oldest_date = DateUtil.toDate(str_oldest).getTime(); //最老时间
        newest_date = DateUtil.toDate(str_newest).getTime(); ///最新时间
        if(Parameters.region_offline=="datong") {
        	while ((newest_date - oldest_date) > 1800 * 1000) {
                String next_time = DateUtil.toString(new Date(oldest_date + 1800 * 1000)).substring(2);

                time_sorted[0].setFilename(FindHistoryFile.getFile(time_sorted[0].getFilepath(), time_sorted[0].getId(), next_time).getName());

                Arrays.sort(time_sorted, new ComparatorTimeLine());

                str_oldest = "20" + time_sorted[0].filename.substring(5, 17);
                str_newest = "20" + time_sorted[Parameters.SensorNum-1].filename.substring(5, 17);

                oldest_date = DateUtil.toDate(str_oldest).getTime();
                newest_date = DateUtil.toDate(str_newest).getTime();
            }
        }
        else if(Parameters.region_offline=="pingdingshan") {
        	while ((newest_date - oldest_date) > 3600 * 1000) {
                String next_time = DateUtil.toString(new Date(oldest_date + 3600 * 1000)).substring(2);

                time_sorted[0].setFilename(FindHistoryFile.getFile(time_sorted[0].getFilepath(), time_sorted[0].getId(), next_time).getName());

                Arrays.sort(time_sorted, new ComparatorTimeLine());

                str_oldest = "20" + time_sorted[0].filename.substring(5, 17);
                str_newest = "20" + time_sorted[Parameters.SensorNum-1].filename.substring(5, 17);

                oldest_date = DateUtil.toDate(str_oldest).getTime();
                newest_date = DateUtil.toDate(str_newest).getTime();
            }
        }

        for (int i = 0; i < Parameters.SensorNum; i++) {
            System.out.println(time_sorted[i].filename);
        }


        return null;
    }

}
