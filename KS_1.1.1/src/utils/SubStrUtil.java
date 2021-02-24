package utils;

import com.h2.constant.Parameters;

public class SubStrUtil {
	
	public static String [] getFileParentPackage(String[] path) {
        String[] SubStr = new String[Parameters.SensorNum];//记录 线下跑时 文件所在父目录
        for (int i = 0; i < Parameters.SensorNum; i++) {
            int strcount = 0;
            for (int j = path[i].length() - 2; j >= 0; j--) {
                if (path[i].charAt(j) == '/') {
                    break;
                }
                strcount++;
            }
            SubStr[i] = path[i].substring(path[i].length() - 1 - strcount, path[i].length() - 1);
        }
        return SubStr;
    }
	
	/**
     * 截取下划线后面，与.前面的字符串。
     * 获取刘老师仪器文件名中的时间字符串
     * 使用该函数前，必须使用正则表达式判断是马老师文件，否则会报错。
     * @param parent
     * @return
     * @author Hanlin Zhang.
     * @date revision 2021年2月19日上午10:42:45
     */
    public static String contentCut_liu(String parent) {
        String []str = parent.split("_");
        String results = str[1].split("\\u002E")[0];
        return  results;
    }
    
    /**
     * 新设备文件名中的时间截取 ；如 NO4_2020-01-02 19-39-24.bin 截取为 20200102193924
     * 使用该函数前，必须使用正则表达式判断是马老师文件，否则会报错。
     * @param fileName
     * @return
     */
    public static String contentCut_ma(String fileName) {

        String []str1= fileName.split("_");  //分割“_”
        String []str2= str1[1].split("\\."); //分割“.”
        String time= str2[0].replace("-","");
        time=time.replace(" ","");
        return time;
    }
    
    /**
     * 截取路径中带有：的部分，我们认为根目录是文件所在的传感器名。
     * @param parent
     * @return
     * @author Hanlin Zhang.
     * @date revision 2021年2月19日上午10:06:21
     */
    public static String contentCut_root(String parent) {
    	String []str = parent.split("\\\\");
        int series = 0;
    	
        for(int i=0;i<str.length;i++) {
        	if(str[i].contains(":")) {
        		series = i;
        		break;
        	}
        }
        String results = str[series].replaceAll(":", "");
        
        return results;
    }
    
}
