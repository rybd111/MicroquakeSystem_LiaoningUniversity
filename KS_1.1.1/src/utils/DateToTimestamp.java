package utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToTimestamp {
	public static Timestamp dateToTimestampSQl(String dateStr) {
			String adate="20"+dateStr;
		    Date date = new Date();
		    DateFormat formatDate=new SimpleDateFormat("yyyyMMddHHmmss");
		    try {
				date=formatDate.parse(adate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     //format的格式可以任意
			DateFormat formatDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
	        String time = formatDateTime.format(date);
	        Timestamp timestamp =Timestamp.valueOf(time);
	        return timestamp;
	}
}
