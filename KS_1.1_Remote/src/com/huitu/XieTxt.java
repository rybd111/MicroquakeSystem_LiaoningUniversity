package com.huitu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class XieTxt {

	/*public static void main(String[] args) {
		Save(452.26);
	}*/

	public static void Save(String txt) {
		String str = new String(); // 原有txt内容
		String s1 = new String();// 内容更新
		String fileName ="D:/Data/xData.txt";
		try {
			File f = new File(fileName);
			if (f.exists()) {
				//System.out.print("文件存在");
			} else {
				//System.out.print("文件不存在");
				f.createNewFile();// 不存在则创建
			}
			BufferedReader input = new BufferedReader(new FileReader(f));

			while ((str = txt) != null) {
				s1 += str + "\n";
			}
			//System.out.println(s1);
			input.close();			
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(s1);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
