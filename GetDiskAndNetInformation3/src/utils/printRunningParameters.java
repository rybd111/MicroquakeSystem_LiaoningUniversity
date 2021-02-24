/**
 * 
 */
package utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;


/**
 * 输出远程盘符信息，没有则返回空
 * @author Hanlin Zhang
 */
public class printRunningParameters {

	public static void printScanPath(String[] fileStr) throws IOException {
		System.out.println("扫描到的包含数据文件的远程路径：  ");
		
		if(fileStr == null) {
			System.out.println("没有远程路径！");
		}
		else {
			for(int i=0;i<fileStr.length;i++) {
				System.out.print(formToChar(fileStr[i]));
			}
			System.out.println();
		}
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
