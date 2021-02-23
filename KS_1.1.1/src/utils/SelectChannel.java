/**
 * 
 */
package utils;

import com.h2.constant.Parameters;

/**
 * @author Hanlin Zhang
 */
public class SelectChannel {

	/**
	 * Diagnose the channel exceed the scope or not according to the hardware parameters.
	 * @param s the corresponding of the channel value distilled from vector which is String type.
	 * @return A flag which is true indicates exceed the max scope or not.
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	public static boolean testValue(String s)
	{
		int a = Integer.parseInt(s);
		if (a >= Parameters.HEAD || a <= Parameters.TAIL){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月23日下午2:08:08
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
