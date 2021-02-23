/**
 * 
 */
package utils;

import java.util.Arrays;

/**
 * @author Hanlin Zhang
 */
public class String2Array {

	public static String[] string2array(String A) {
		String[] results = new String[0];
		
		for(int i=0;i<A.length();i++) {
			results = Arrays.copyOf(results, results.length+1);
			results[results.length-1] = String.valueOf(A.charAt(i));
		}
		
		return results;
		
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月20日上午11:18:52
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
