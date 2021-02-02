/**
 * 
 */
package utils;

import java.util.Scanner;

/**
 * @author Hanlin Zhang
 */
public class ScanInNum {

	/**
	 * 输入数字，返回数字数组，用于确定选择哪个盘符
	 */
	public static int[] ScanInNum(){
		Scanner in = new Scanner(System.in);
		
		String s = in.nextLine();
				
		//处理s为数字，并返回序列数组。
		char[] temp = s.toCharArray();
		
		int[] result = new int[temp.length];
		for(int i=0;i<result.length;i++) {
			result[i] = Integer.valueOf(String.valueOf(temp[i]))-1;
		}
		
		return result;
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月2日下午10:06:49
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
