package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class String2Date {
	
	/*
	 * 字符串转化为日期
	 */	
	public static Date str2Date(String dateStr) throws ParseException{
		
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss") ;
	
	Date date = simpleDateFormat.parse(dateStr) ;
	
	return date;	
	}
}