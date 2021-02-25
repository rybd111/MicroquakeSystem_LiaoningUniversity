/**
 * 
 */
package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Hanlin Zhang
 */
public class Long2Date {

	public static String translateLong(Long time) throws Exception {
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			format.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
			String dateTime = format.format(time);
			Date date = format.parse(dateTime);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			Calendar orgin = Calendar.getInstance();
			orgin.setTimeInMillis(0);

			int year = calendar.get(Calendar.YEAR) - orgin.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) - orgin.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DATE) - orgin.get(Calendar.DATE);
			int hour = calendar.get(Calendar.HOUR_OF_DAY) - orgin.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE) - orgin.get(Calendar.MINUTE);
			int second = calendar.get(Calendar.SECOND) - orgin.get(Calendar.SECOND);
			int millisecond = calendar.get(Calendar.MILLISECOND) - orgin.get(Calendar.MILLISECOND);

			StringBuilder builder = new StringBuilder();
			if(year!=0){
				builder.append(year).append("年");
			}
			if(month!=0){
				builder.append(month).append("月");
			}
			if(day!=0){
				builder.append(day).append("天");
			}
			if(hour!=0){
				builder.append(hour).append("小时");
			}
			if(minute!=0){
				builder.append(minute).append("分");
			}
			if(second!=0){
				builder.append(second).append("秒");
			}
			if(millisecond!=0){
				builder.append(millisecond).append("毫秒");
			}

			return builder.toString();
		}catch (Exception e){
			throw new Exception("格式化日期出错："+e.getMessage());
		}
	}
	
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @throws Exception 
	 * @date revision 2021年2月25日上午11:01:12
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String duration = translateLong(17920L);
		System.out.println(duration);
	}

}
