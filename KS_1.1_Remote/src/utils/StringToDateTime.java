package utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateTime {
	/**
	 * 返回当前日期，存入数据库datetime 类型
	 * @return
	 */
	public static Timestamp getDateSql(String adate){
	
	    Date date = new Date();
	    DateFormat formatDate=new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
	    try {
			date=formatDate.parse(adate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		DateFormat formatDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
        String time = formatDateTime.format(date);
        
        Timestamp timestamp =Timestamp.valueOf(time);
        
        return timestamp;
        
	}
}
