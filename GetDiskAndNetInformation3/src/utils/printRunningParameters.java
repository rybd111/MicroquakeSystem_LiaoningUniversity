/**
 * 
 */
package utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;


/**
 * @author Hanlin Zhang
 */
public class printRunningParameters {

	public static void printpullParameters(String[] fileStr) throws IOException {
		
		System.out.println("自动配置的主路径为： ");
		for(int i=0;i<fileStr.length;i++) {
			System.out.println(formToChar(fileStr[i]));
		}
		
		System.out.println("自动配置完毕——————————");
	}
	
	/**
	 * 输出特定格式。
	 * @param out 
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月20日下午2:59:00
	 */
	public static String formToChar(String out) {
		return "["+out+"]";
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月23日下午10:34:02
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
