/**
 * 
 */
package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import DataExchange.Sensor;

/**
 * @author Hanlin Zhang
 */
public class CutData {

	/**
	 * Cut a period of time data modified by artificial setting.
	 * @param data   the 30s data to be prepared to cut.
	 * @param s   array of sensors.
	 * @param PreSeconds  time before P arrival.
	 * @param AfterSeconds   time after P arrival.
	 * @param sen   the sensor we need to process.
	 * @return   Return the data after cutting. "Vector<String>"
	 * @author Hanlin Zhang.
	 */
	public static Vector<String> cutOdata(Vector<String> data, Sensor[] s, int PreSeconds, int AfterSeconds, Sensor sen) {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		Date motiDate = new Date();
		Date initmotiDate = new Date();
		Date t1 = new Date();
		
		long timeDiff = 0;
		boolean flag1 = true;
		//diagnose the sensors' P arrival time is exceed 1 second, procedure will cut one more second before the motivating line, and cut one less second after the motivating line.
				
		try {
			motiDate = String2Date.str2Date(sen.getTime());
			initmotiDate = String2Date.str2Date(s[0].getTime());
		}
		catch (ParseException e1) {e1.printStackTrace();}
		
		if(motiDate.getTime()-initmotiDate.getTime()>1) {
			PreSeconds++;
			AfterSeconds--;
		}
		
		Vector<String> resultSet = new Vector<String>();
		
		for(int i = 0; i < data.size(); i++){
			try {t1 = String2Date.str2Date(data.get(i).split(" ")[6]);}
			catch (ParseException e) {e.printStackTrace();}
			timeDiff = (motiDate.getTime()-t1.getTime())/1000;
			if(timeDiff < PreSeconds && timeDiff >= -AfterSeconds){
				resultSet.add(data.get(i));
				if(flag1==true) {
					flag1=false;
					sen.setlineSeriesNew(sen.getlineSeries()-i);//the motivation position in CSV file.
				}
			}
		}
		
		return resultSet;
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月23日下午2:12:36
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
