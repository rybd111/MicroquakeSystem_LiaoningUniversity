package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Date2String {
	/*
	 * 字符串转化为日期
	 */	
	public static String date2str(Date d) throws ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		
		String date = simpleDateFormat.format(d);
		
		return date;
	}
	
	public static String date2str1(Date d) throws ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss") ;
		
		String date = simpleDateFormat.format(d);
		
		return date;
	}
	
	public static String date2str2(Date d) throws ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
		
		String date = simpleDateFormat.format(d);
		
		return date;
	}
	
	public static String date2str3(Date d) throws ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
		
		String date = simpleDateFormat.format(d);
		
		return date;
	}
	
	public static String date2str4(Date d) throws ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss") ;
		
		String date = simpleDateFormat.format(d);
		
		return date;
	}
}
