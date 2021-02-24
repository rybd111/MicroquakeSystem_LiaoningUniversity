package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.h2.constant.Parameters;

import read.yang.readFile.ReadData;

//日期数组，转化为整型数组，整型数组为日期数组 与最大值得差
public class DateArrayToIntArray {

	private Date dateMax;
	private int maxSeries;
	
	public int getMaxSeries() {
		return maxSeries;
	}
	public Date getDate() {
		return dateMax;
	}
	public String getDateStr() throws ParseException {
		return Date2String.date2str(dateMax);
	}
	

	public int[] IntArray(String[] dateStr) throws ParseException{//处理完变为3列0了
		
		//this format is as follows for the ReadDataSegmentHead.java class convert the time to the this format,
		//so we create the DateFormat class variable to address this time format.
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		
		//we create a variable of date class to address date conversion problem.
		Date [] startDate = new Date[Parameters.SensorNum];
		
		for(int i=0; i<Parameters.SensorNum; i++){
			startDate[i] = format1.parse(dateStr[i]);//变为Date类型
		}
		
		Date DateMax = FindMaxDate(startDate);
		this.dateMax =DateMax;
		//时间距离，单位s
		int[] DateDifferntInt =new int[startDate.length];
		
		for (int i = 0; i < DateDifferntInt.length; i++) {
			DateDifferntInt[i]=Math.abs((int) ((startDate[i].getTime()-DateMax.getTime())/1000 ));
		}
        return DateDifferntInt;
	}

	private Date FindMaxDate(Date[] aDates) {
		Date MaxDate = aDates[0];

		for (int i = 0; i < aDates.length; i++) {

			int cha = (int) ((MaxDate.getTime() - aDates[i].getTime()) / 1000);

			if (cha <= 0) {
				MaxDate = aDates[i];
				maxSeries = i;
			}
		}
		return MaxDate;
	}
	
	
	//用于将最大日期文件的前6个字节变为16进制字符串，对齐使用
	public static String FindSixByte(String fileName) throws IOException {
		byte[] dataSegmentHeadByte = new byte[6];
		File file = new File(fileName);
		@SuppressWarnings("resource")
		FileInputStream fs = new FileInputStream(file);
		fs.read(dataSegmentHeadByte);
//		fs.close();
		String st = FindByte.bytesToHexString(dataSegmentHeadByte);
		return st;
		
	}
	//用于将最大日期文件的前4个字节变为16进制字符串
	public static String FindFourByte(String fileName) throws IOException {
		byte[] dataSegmentHeadByte = new byte[4];
		File file = new File(fileName);
		@SuppressWarnings("resource")
		FileInputStream fs = new FileInputStream(file);
		fs.read(dataSegmentHeadByte);
//		fs.close();
		String st = FindByte.bytesToHexString(dataSegmentHeadByte);
		return st;
		
	}
	//用于将最大日期文件的第五和第六字节变为16进制字符串
	public static String FindTwoByte(String fileName) throws IOException {
		byte[] dataSegmentHeadByte = new byte[2];
		File file = new File(fileName);
		@SuppressWarnings("resource")
		FileInputStream fs = new FileInputStream(file);
		for(int i=0;i<2;i++) {
			fs.read(dataSegmentHeadByte);
		}
		fs.read(dataSegmentHeadByte);
//		fs.close();
		String st = FindByte.bytesToHexString(dataSegmentHeadByte);
		return st;
	}
}
