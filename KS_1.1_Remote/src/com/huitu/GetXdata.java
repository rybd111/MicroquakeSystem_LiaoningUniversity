package com.huitu;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Vector;

import org.junit.Test;

import utils.Byte2Short;
import utils.FindByte;

public class GetXdata {

	public static void main(String[] args) throws IOException {
		getXdata();
	}
	
	public static void getXdata() throws IOException{

		// 第七通道赋值
		Vector<String> sensorData = new Vector<String>();
	    boolean flag3=true;// 用于跳出循环
		int StartDataTime = 10;
		int SaveDataTimeCount = 0; // 存储时间，单位为秒
		
		int lineNum =1;
		
		int num = 0;// 统计多少个高电平，就是多少时间
		int count = 0;// 统计数据总量
		int gaodianwei = 5000;// 第七通道数值
	    int countnum=0;
		boolean flag1 = false; // 两个flag，用于判断，高电平
		boolean flag2 = false;
		
		//write
		String fileNameSave ="D:/Data/xData.txt";
		String fileNameTime ="D:/Data/tData.txt";
		File  fileT=new File(fileNameTime);
		if (fileT.exists()) {
			System.out.println("exist");
		}else {
			fileT.createNewFile();
		}
		BufferedWriter writerT=new BufferedWriter(new FileWriter(fileT));
			File f = new File(fileNameSave);
			if (f.exists()) {
				System.out.print("文件存在");
			} else {
				//System.out.print("文件不存在");
				f.createNewFile();// 不存在则创建
			}
		
			BufferedReader input = new BufferedReader(new FileReader(f));
								
		// 用于存放数据
		byte[] dataByte = new byte[14];
		String fileName = "D:/kuangshandata/HYJ/GYGC/04/hyk_170524152917.HFMED";

		// String fileName =
		// "C:/Users/NiuNiu/Desktop/HYJ/GYGC/04/gygc_161126173902.HFMED" ;

		File destFile = new File("D:/kuangshandata/data.txt");
		// File destFile = new File("C:/Users/NiuNiu/Desktop/data.txt") ;
		BufferedInputStream buffered = new BufferedInputStream(
				new FileInputStream(new File(fileName)));

		BufferedWriter bufferedOut = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(destFile)));
		//shuchu
		BufferedWriter output = new BufferedWriter(new FileWriter(f));
		
		buffered.skip(284);
		System.out.println("x1" + " " + "y1" + " " + "z1" + " " + "x2" + " "
				+ "y2" + " " + "z2" + " " + "volt");

		
		/**
		 * 注意：你要的是数据部分，所以你需要跨过每个数据段的 数据段头
		 */

		for (int j = 0; j < 21093; j++) {
			short x1 = 0;
			short y1 = 0;
			short z1 = 0;
			short x2 = 0;
			short y2 = 0;
			short z2 = 0;
			short volt = 0;

			// 跳过数据段头
			buffered.skip(34);
			
			for (int i = 0; i < 2560; i++) {
				if (buffered.read(dataByte) == -1) {
					x1 = 0;
					y1 = 0;
					z1 = 0;
					x2 = 0;
					y2 = 0;
					z2 = 0;
					volt = 0;
					break;
				}

				byte[] x1Byte = FindByte.searchByteSeq(dataByte, 0, 1);
				byte[] y1Byte = FindByte.searchByteSeq(dataByte, 2, 3);
				byte[] z1Byte = FindByte.searchByteSeq(dataByte, 4, 5);
				byte[] x2Byte = FindByte.searchByteSeq(dataByte, 6, 7);
				byte[] y2Byte = FindByte.searchByteSeq(dataByte, 8, 9);
				byte[] z2Byte = FindByte.searchByteSeq(dataByte, 10, 11);
				byte[] voltByte = FindByte.searchByteSeq(dataByte, 12, 13);

				x1 = Byte2Short.byte2Short(x1Byte);
				y1 = Byte2Short.byte2Short(y1Byte);
				z1 = Byte2Short.byte2Short(z1Byte);
				x2 = Byte2Short.byte2Short(x2Byte);
				y2 = Byte2Short.byte2Short(y2Byte);
				z2 = Byte2Short.byte2Short(z2Byte);
				volt = Byte2Short.byte2Short(voltByte);

				String dataString = x1 + " " + y1 + " " + z1 + " " + x2 + " "
						+ y2 + " " + z2 + " " + volt;

				if (Math.abs(volt) > 5000) {
					flag1 = true;
				}
				if (Math.abs(volt) < 1000 && flag1) {
					flag2 = true;
				}
				if (flag1 && flag2) {
					num++;
					flag1 = false;
					flag2 = false;
				}

				count++;
				// 对齐后，开始向sensorData 赋值
             
				if(num>=StartDataTime&&num<StartDataTime+10){
					sensorData.add(dataString);
					countnum++;
				}else {
					if(num>=StartDataTime){
					flag3=false;
					break;
					}
				}
										
				Short xShort=x1;
				double temp=lineNum*0.0001;
				String sal =String.format("%.5f",temp);
				writerT.write(sal);
				lineNum++;
				writerT.newLine();
					output.write(xShort.toString());
					output.newLine();
					
				if (x1 == 0 && y1 == 0 && z1 == 0 && x2 == 0 && y2 == 0
						&& z2 == 0 && volt == 0)
					break;
				
				bufferedOut.write(dataString);
				bufferedOut.newLine();
			}// end inner for loop
			if(flag3==false){
				break;
			}
			if (x1 == 0 && y1 == 0 && z1 == 0 && x2 == 0 && y2 == 0 && z2 == 0
					&& volt == 0)
				break;
		}// end outer for loop
		writerT.close();
		output.close();
		buffered.close();
		bufferedOut.close();
		System.out.println("num="+num);
		System.out.println("countnum="+countnum);
		System.out.println("count="+count);
		System.out.print("sensorData.size()="+sensorData.size());
		
		
		}
}
