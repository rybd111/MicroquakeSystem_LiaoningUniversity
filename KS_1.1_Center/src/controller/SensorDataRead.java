package controller;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

import bean.Location;

import utils.Byte2Short;
import utils.DateAddStr;
import utils.FindByte;

/*
 * 读取传感器10s 数据
 */
public class SensorDataRead {

	//private Vector<String> sensorData; // 10s 传感器数据
	private Location aLocation; // 获取位置
	private String dateStr;
    private int startTime=0;
		
	
    //该构造方法，用于读取10s 数据 
	public SensorDataRead( Location aLocation) {
		super();
		//this.sensorData = senorData;
		this.aLocation = aLocation;
	}

	public SensorDataRead(Location aLocation, String dateStr) {
	super();
	this.aLocation = aLocation;
	dateStr=dateStr.substring(2);
	this.dateStr = dateStr;
}
	
	

	//该构造方法用于同步
	/*public SensorDataRead(Location aLocation, int startTime) {
		super();
		this.aLocation = aLocation;
		StartTime = startTime;
	}*/
	
	public SensorDataRead(String dateStr, int startTime) {
		super();
		this.dateStr = dateStr;
		this.startTime = startTime;
	}

	/*
	 * 读取10s 数据 返回Vector
	 */
	public Vector<String> GetDataTenM() throws Exception {
        
		Vector<String> sensorData = new Vector<String>();
		String voltStr=this.dateStr;
		boolean flag3 = true;
		int num = 0;// 统计多少个高电平，就是多少时间
		int count = 0;// 统计数据总量
		// int gaodianwei = 5000;// 第七通道数值
		int countnum = 0;
		boolean flag1 = false; // 两个flag，用于判断，高电平
		boolean flag2 = false;

		// 用于存放数据
		byte[] dataByte = new byte[14];
		String fileName = "B:/Users/lemo/Documents/Tencent Files/1159639005/FileRecv/gygc_161126173902.HFMED";

		// String fileName =
		// "C:/Users/NiuNiu/Desktop/HYJ/GYGC/04/gygc_161126173902.HFMED" ;

		File destFile = new File("D:/kuangshandata/data.txt");
		// File destFile = new File("C:/Users/NiuNiu/Desktop/data.txt") ;

		BufferedInputStream buffered = new BufferedInputStream(
				new FileInputStream(new File(fileName)));

		BufferedWriter bufferedOut = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(destFile)));

		buffered.skip(284);
		//System.out.println("x1" + " " + "y1" + " " + "z1" + " " + "x2" + " "
		//		+ "y2" + " " + "z2" + " " + "volt");

		// buffered.mark(readlimit);
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
				
				
				//用于判断高电平
				if (Math.abs(volt) > 5000) {
					flag1 = true;
				}
				if (Math.abs(volt) < 1000 && flag1) {
					flag2 = true;
				}
				if (flag1 && flag2) {
					num++;
					voltStr=DateAddStr.DateAddSecondsStr(voltStr);
					flag1 = false;
					flag2 = false;
				}
				
				
				String dataString = x1 + " " + y1 + " " + z1 + " " + x2 + " "
						+ y2 + " " + z2 + " " + voltStr;
				//System.out.println(dataString);
				count++;
				// 对齐后，开始向sensorData 赋值
				
				
				if (num >=0 && num < 10) {

					sensorData.add(dataString);
				
					countnum++;
				} else {
					if (num >=0) {
						flag3 = false;
						aLocation.setSite(100); //设置location，位置
						break;
					}
				}

				if (x1 == 0 && y1 == 0 && z1 == 0 && x2 == 0 && y2 == 0
						&& z2 == 0 && volt == 0)
					break;

				bufferedOut.write(dataString);
				bufferedOut.newLine();
			}// end inner for loop
			if (flag3 == false) {
				break;
			}
			if (x1 == 0 && y1 == 0 && z1 == 0 && x2 == 0 && y2 == 0 && z2 == 0
					&& volt == 0)
				break;
		}// end outer for loop
		buffered.close();
		bufferedOut.close();
		System.out.println("读取传感器数据");
		System.out.println("num=" + num);
		System.out.println("countnum=" + countnum);
		System.out.println("count=" + count);
		System.out.print("sensorData.size()=" + sensorData.size());

		return sensorData;
	}

	
}
