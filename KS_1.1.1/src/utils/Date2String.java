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
}
