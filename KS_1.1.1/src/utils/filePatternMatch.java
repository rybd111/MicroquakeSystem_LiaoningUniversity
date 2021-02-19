/**
 * 
 */
package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfree.data.xy.VectorDataItem;

/**
 * @author Hanlin Zhang
 */
public class filePatternMatch {
		
	/**
	 * 匹配一种以"A-Za-z_"10位开头的文件，并以12位数字结尾的字符串。
	 * 此方法可以匹配含有HFMED文件的文件夹或HFMED文件，只能用于老仪器。
	 * @param str
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月19日下午12:14:40
	 */
	public static boolean match(String str) {
		
		String str1 =str;
		//该方法接受一个正则表达式作为它的第一个参数。
		String pattern = "^[A-Za-z_]{0,9}\\d{12}$";
		Pattern r = Pattern.compile(pattern);
		//对输入str进行解释和匹配操作
		Matcher m = r.matcher(str);
		if(m.find() ==true) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月19日上午11:58:28
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
