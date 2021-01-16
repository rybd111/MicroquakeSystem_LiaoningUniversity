package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAddStr {
	/*
	 * 日期字符串，时间增加
	 */
	public static String DateAddSecondsStr(String dateString) throws Exception {
		
		/*String fileName = "B:/Users/lemo/Documents/Tencent Files/1159639005/FileRecv/gygc_161126173902.HFMED";

        ReadDataSegmentHead readDateSegmentHead = new ReadDataSegmentHead();
		
		String dateString = readDateSegmentHead.readDataSegmentHead(fileName);
		System.out.println(dateString);*/
		dateString=20+dateString;
		Date date3 = (new SimpleDateFormat("yyyyMMddHHmmss")).parse(dateString);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date3);	
		cal.add(Calendar.SECOND,1);
		
		
		Date dateTmp =cal.getTime();
		String returnDateStr = (new SimpleDateFormat("yyyyMMddHHmmss")).format(dateTmp);//加一天后结果
		
		
		//System.out.println(dateTmp.toString());
		//System.out.println(returnDateStr+" returnDateStr");

		Date calendarTdate=cal.getTime();
		System.out.println(calendarTdate);
		
		returnDateStr=returnDateStr.substring(2);
		System.out.println(returnDateStr);
		
		return returnDateStr;		
	}
	
	/*public static void main(String[] args) throws Exception {
		DateAddStr.DateAddSecondsStr("Sss");
		
	}*/
}
