package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.fabric.xmlrpc.base.Data;

public class TimeTransform {
	
	/**
	 * @param date1  传入日期格式为"yy-mm-dd hh-mm-ss`s"
	 * @param double2  到时 location_refine.getSecTime()
	 * @return consequence 日期格式为"yyyy-MM-dd HH:mm:ss.s"
	 * @throws ParseException
	 */
	public static String TimeDistance(String date1, double double2) throws ParseException {
		String consequence="";
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		String yearmonthday="";
		String prePartion = "";
		String hms = "";
		String h = "";
		String m = "";
		String afterPartion = "";
		String s = "";
		Date d = new Date();
		prePartion=date1.split("\\`")[0];
		yearmonthday=prePartion.split("\\ ")[0];
		hms=prePartion.split("\\ ")[1];
			h=hms.split("\\-")[0]+":";
			m=hms.split("\\-")[1]+":";
			s=hms.split("\\-")[2];
		
		hms=" "+h+m+s;
		prePartion=yearmonthday+hms;
		afterPartion="0."+date1.split("\\`")[1];
		
		d = simpleDateFormat.parse(prePartion);
		
		double distanceB = Double.valueOf(afterPartion)+double2;
		
		//there are four situations. 1 greater than 0 or greater than 1. 2 smaller than 0 or smaller than -1.
		if(distanceB<-1 || distanceB>1) {
//				d.setTime(d.getTime()+(int)distanceB*1000);
			if(distanceB<-1 ) {
				int distanceBint=(int)distanceB;
				double distancefloat=distanceB-distanceBint;
				d.setTime(d.getTime()+distanceBint*1000);
				distancefloat=distancefloat+1;
				d.setTime(d.getTime()-1*1000);
				consequence = simpleDateFormat.format(d)+"."+String.valueOf(distancefloat).split("\\.")[1];
			}
			else {
				int distanceBint=(int)distanceB;
				double distancefloat=distanceB-distanceBint;
				d.setTime(d.getTime()+distanceBint*1000);
				consequence = simpleDateFormat.format(d)+"."+String.valueOf(distancefloat).split("\\.")[1];
			}
			
		}
		else if(distanceB>0){
			consequence = simpleDateFormat.format(d)+"."+String.valueOf(distanceB).split("\\.")[1];
		}
		else if(distanceB<0) {
			distanceB = distanceB+1;
			d.setTime(d.getTime()-1*1000);
			consequence = simpleDateFormat.format(d)+"."+String.valueOf(distanceB).split("\\.")[1];
		}
		
		return consequence;
	}
		
	
	public static void dataTransform(String formDate) throws ParseException {
		Date d = String2Date.str2Date(formDate);
		
	} 
}
