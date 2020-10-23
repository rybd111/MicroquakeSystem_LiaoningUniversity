package com.h2.backupData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * when the procedure appear a negative value in earthquake calculation, we will call this function.
 * @author Hanlin Zhang.
 */
public class recordNegativeGrade{
	public static void recordToTxt_negativegrade(String NegativeGrade) {
        File file = new File("D:\\环境+项目\\recordNegativeGrade.txt");
        BufferedWriter out = null;
        try {
			out = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e1) {e1.printStackTrace();}
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            out.write(NegativeGrade+"\r\n");//向文件中写内容
            out.flush();
            //System.out.println("写数据成功！");
        } catch (IOException e) {e.printStackTrace();}
        finally{
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }
	}
}