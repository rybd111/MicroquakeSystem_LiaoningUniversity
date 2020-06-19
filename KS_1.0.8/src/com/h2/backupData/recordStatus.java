package com.h2.backupData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * the log records the network break down, but it's not using any more for we had taken the log mechanism.
 * @author Hanlin Zhang.
 */
public class recordStatus {
	public static void recordToTxt_status(String StatusOfDisc,int CountOfNetError,String StartTime, String EndTime,String [] fileStr) {
        File file = new File("D:\\环境+项目\\recordNetErStatus.txt");
        BufferedWriter out = null;
        char[] Statusofprocedure = StatusOfDisc.toCharArray();//char数组
        try {
			out = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e1) {e1.printStackTrace();}
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
           // for(int i =0;i<StatusOfDisc.length();i++){
            	out.write("当前盘符为："+fileStr[0]+"、"+fileStr[1]+"、"+fileStr[2]+"、"+fileStr[3]+"、"+fileStr[4]+"  ");
            	out.write("出现网络错误的盘符："+StatusOfDisc+"有");//保存出现网络错误的盘符
            	out.write(CountOfNetError+"次  ");//保存该盘符出现网络错误的次数
            	out.write("程序启动时间为："+StartTime+"···网络错误发生时间为："+EndTime+"\r\n");//保存出现网络错误的盘符
            //}
            out.flush();
            System.out.println("写网络错误状态成功！");
        } catch (IOException e) {e.printStackTrace();}
        finally{
            if(out != null){
                try {out.close();} catch (IOException e) {e.printStackTrace();}
            }
        }
	}
}
