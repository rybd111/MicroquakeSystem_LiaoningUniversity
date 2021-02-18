package read.rqma.history;

import java.io.File;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import utils.Date2String;

/**
 * @Description:
 * @Auther: RQMA
 * @Date: 4/24/2019 10:29 AM
 */
public class FindHistoryFile {

    public FindHistoryFile() {
    	
    }

    /**
     * 找到给定时刻对应的文件，该文件包含该时刻。
     * 没有返回空。
     * @param path
     * @param time ：文件名前缀(给定时间点)
     * @return
     * @throws ParseException 
     */

    @SuppressWarnings("unused")
	public File getFile(String parent, Date time) throws ParseException {
        String time1 = Date2String.date2str1(time);
    	
    	File file = new File(parent);
    	//过滤器，过滤后缀为HFMED的文件。
    	FileAccept fileAccept = new FileAccept();
        fileAccept.setExtendName("HFMED");
    	
        File[] files = file.listFiles(fileAccept);
        
        if (files == null)
            return null;
        
        Arrays.sort(files,new ComparatorByFileName());
        
        for (File file2 : files) {
            /** 找到开始时间小于给定时间
             * 且结束时间大于给定时间的文件。
             */
            //5-17形式 ：190101124000
            if(contentCut(file2.getName()).compareTo(time1) <=0 && file2.lastModified()-time.getTime() >= 0){
                System.out.println(file2.getName());
                return file2;//返回该文件
            }
        }
        return null;
    }

    private String contentCut(String parent) {
        String []str=new String[2];
        str = parent.split("_");
        String results = str[1].split("\\u002E")[0];
        return  results;
    }
    
    public static void main(String[] args) {
        String fileStr= "E:/CoalMine/data/Test1/";
        String timeStr = "190101143901";

       /* File f= getFile(fileStr,1,timeStr);
        System.out.println(f.getPath());
        TimeLine  timeLine=new TimeLine();

        timeLine.setId(111);*/
//        String []s= get(fileStr);
//        System.out.println(s.length);
    }

}
