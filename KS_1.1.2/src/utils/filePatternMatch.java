/**
 * 
 */
package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfree.ui.LengthAdjustmentType;

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
	
	/**
	 * 去除后缀。
	 * @param fileName
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午11:36:50
	 */
	public static String delSuffix(String fileName) {
		return fileName.split("\\.")[0];
	}
	
	/**
	 * 
	 * @param filename
	 *  文件的全名。
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日下午4:12:18
	 */
	private static boolean checkSuffix(String filename, String suffixName) {
		boolean flag = false;
		//检测到有bin，则可以执行
		String suffix[] = filename.split("\\.");
		if(suffix.length>1) {
			if(suffix[1].equals(suffixName)) {
				flag = true;
			}
		}
		
		
		return flag;
	}
	
	/**
	 * 判断是否满足HFMED文件的特征。
	 * findNew类使用。
	 * @param fileName
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日下午4:15:13
	 */
	public static boolean isHFMEDFile(String fileName) {
		return (checkSuffix(fileName, "HFMED") & match_HFMED(fileName));
	}
	
	/**
	 * HFMED文件的索引文件，否则可能无法用HFMED的波形文件打开。
	 * @param fileName
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年4月3日下午3:18:18
	 */
	public static boolean isHDIFX(String fileName) {
		return (checkSuffix(fileName, "HFIDX") & match_HFMED(fileName));
	}
	
	/**
	 * 判断是否满足bin文件的特征。
	 * findNew类使用。
	 * @param fileName
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日下午4:15:13
	 */
	public static boolean isBINFile(String fileName) {
		return (checkSuffix(fileName, "bin") & match_HFMED(fileName));
	}
	
	/**
	 * 检测文件名是否以字母开头，若是，则认为当前文件夹是有效的，算作考虑范围，否则不考虑
	 * @param fileName
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月25日下午4:42:42
	 */
	public static boolean isLetter(String fileName) {
		//该方法接受一个正则表达式作为它的第一个参数。
		String pattern = "^[A-Z a-z]{1}";
		Pattern r = Pattern.compile(pattern);
		//对输入fileName进行解释和匹配操作
		Matcher m = r.matcher(fileName);
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
