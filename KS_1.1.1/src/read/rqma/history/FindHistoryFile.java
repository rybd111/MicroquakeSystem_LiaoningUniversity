package read.rqma.history;

import java.io.File;
import java.util.Arrays;

import com.h2.constant.Parameters;

/**
 * @Description:
 * @Auther: RQMA
 * @Date: 4/24/2019 10:29 AM
 */
public class FindHistoryFile {

    public FindHistoryFile() {

    }

    /**
     *
     * @param path
     * @param sid
     * @param time ：文件名前缀(给定时间点)
     * @return
     */

    @SuppressWarnings("unused")
	public static File getFile(String path, int sid,String time) {
        File file = new File(path);
        File[] files = file.listFiles();//文件夹下的所有文件或文件夹
        if (files == null)
            return null;
        Arrays.sort(files,new ComparatorByFileName());
        for (File file2 : files) {
            //找到后缀为HFMED的并且时间大于给定时间的第一个文件
            //5-17形式 ：190101124000

        	if(Parameters.region=="datong") {
	            if(file2.getPath().endsWith(".HFMED") && SubStrUtil.getSubParentPackage(file2.getName()).compareTo(time)>=0){
	                System.out.println(file2.getName());
	                return file2;//返回该文件
	            }
        	}
        	else if(Parameters.region=="pingdingshan"||Parameters.region=="hongyang") {
        		if(file2.getPath().endsWith(".HFMED") && file2.getName().substring(5,17).compareTo(time)>=0){
                    System.out.println(file2.getName());
                    return file2;
                }
        	}
        }
        return null;
    }
    public static File getFile(String path, int sid) {
        File file = new File(path);
        if (file == null)
            return null;
        return file;
    }


    /**
     * 得到所有文件名
     * @param path
     * @param sid
     * @param time
     * @return 所有文件名
     */
    @SuppressWarnings("unused")
	public static String [] get(String path, int sid,String time) {
        File file = new File(path);
        FileAccept fileAccept = new FileAccept();
        fileAccept.setExtendName("HFMED");
        String[] files = file.list(fileAccept);//文件夹下的所有文件或文件夹
        if (files == null)
            return null;
        Arrays.sort(files);
        int i=0;
        if(Parameters.region=="datong") {
	        for (; i <  files.length; i++) {
	            if(SubStrUtil.getSubParentPackage(files[i]).compareTo(time)>=0){
	                break;
	            }
	        }
        }
        else if(Parameters.region=="pingdingshan"||Parameters.region=="hongyang") {
        	for (; i <  files.length; i++) {
                if(files[i].substring(5,17).compareTo(time)>=0){
                    break;
                }
            }
        }
        String filename[]=Arrays.copyOfRange(files,i,files.length);
        return filename;
    }

    public static void main(String[] args) {
        String fileStr= "E:/CoalMine/data/Test1/";
        String timeStr = "190101143901";

       /* File f= getFile(fileStr,1,timeStr);
        System.out.println(f.getPath());
        TimeLine  timeLine=new TimeLine();

        timeLine.setId(111);*/
        String []s=  get(fileStr,1,timeStr);
        System.out.println(s.length);
    }

}
