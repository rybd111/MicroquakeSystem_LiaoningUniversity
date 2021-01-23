package read.rqma.history;

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
	public static String  getSubParentPackage(String parent) {

        String []str=new String[2];
        str = parent.split("_");
       // System.out.println(str[1]);
        return  str[1].split("\\u002E")[0];

    }
}
