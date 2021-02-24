/**
 * 
 */
package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfree.data.xy.VectorDataItem;

import javafx.scene.transform.Scale;

/**
 * 只比较前缀，不比较后面。
 * @author Hanlin Zhang
 */
public class filePatternMatch {
		
	/**
	 * 匹配一种以"A-Za-z_"10位开头的文件，并以12位数字结尾的字符串。
	 * 此方法可以匹配含有HFMED文件的文件夹或HFMED文件，只能用于老仪器。
	 * 此方法没有判断后缀，是因为装有HFMED的文件夹也是此种命名特征。
	 * @param str
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月19日下午12:14:40
	 */
	public static boolean match_HFMED(String str) {
		//去除后缀。
		str = delSuffix(str);
		//该方法接受一个正则表达式作为它的第一个参数。
		String pattern = "[A-Za-z_]{0,9}\\d{12}$";
		Pattern r = Pattern.compile(pattern);
		//对输入str进行解释和匹配操作
		Matcher m = r.matcher(str);
		if(m.find() ==true) {
			return true;
		}
		return false;
	}
	
	/**
	 * 匹配一种以"NO\d*_"4位开头的文件，并以"年-月-日 时-分-秒"结尾的字符串。
	 * NO4_2020-01-02 19-39-24.bin
	 * 此方法可以匹配bin文件，只能用于新仪器，没有匹配后缀，是因为马老师仪器的路径下没有二级目录。
	 * @param str
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月19日下午12:14:40
	 */
	public static boolean match_BIN(String str) {
		//去除后缀。
		str = delSuffix(str);
		//该方法接受一个正则表达式作为它的第一个参数。
		String pattern = "NO\\d*\\_(\\d{2}|\\d{4})\\-(0\\d|1[012])\\-([0-2]\\d|3[01])\\s([01]\\d|2[0-3])\\-[0-5]\\d\\-[0-5]\\d";
		Pattern r = Pattern.compile(pattern);
		//对输入str进行解释和匹配操作
		Matcher m = r.matcher(str);
		if(m.find() ==true) {
			return true;
		}
		return false;
	}
	
	public static String delSuffix(String fileName) {
		return fileName.split("\\.")[0];
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
