package utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Compute the distance among different time type.
 * @author Hanlin Zhang.
 *
 */
public class TimeDifferent {

	/*
	 * compute the two date's distance in day.
	 */
	public static int  DateDifferent(String date1Str,String date2Str) throws ParseException{
		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		
		Date date1 = simpleDateFormat.parse(date1Str) ;
		
		Date date2 = simpleDateFormat.parse(date2Str) ;
		
		//get the time different by the day.
		int interval = (int)((date1.getTime() - date2.getTime())/(1000*3600*24));
		
		return interval;	
	}
	
	/**
	 * calculate the 
	 * @param date1
	 * @param double2
	 * @return
	 * @throws ParseException
	 */
	public static String TimeDistance(String date1, double double2) throws ParseException {
		String consequence="";
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		String prePartion = "";
		String afterPartion = "";
		Date d = new Date();
		
		prePartion=date1.split("\\.")[0];
		afterPartion="0."+date1.split("\\.")[1];
		
		d = simpleDateFormat.parse(prePartion);
		
		double distanceB = Double.valueOf(afterPartion)+double2;
		
		
		//there are four situations. 1 greater than 0 or greater than 1. 2 smaller than 0 or smaller than -1.
		if(distanceB<-1 || distanceB>1) {
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
}
