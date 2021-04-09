package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Hanlin Zhang
 */
public class currentDate {
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年4月9日上午9:15:21
	 */
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
		Date date = new Date();// 获取当前时间
		System.out.println("现在时间：" + sdf.format(date));// 输出已经格式化的现在时间(24小时制)
	}
	
	public static String currentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
		Date date = new Date();// 获取当前时间
		return sdf.format(date);
	}
}
