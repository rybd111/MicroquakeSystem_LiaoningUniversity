/**
 * 
 */
package utils;

/**
 * @author Hanlin Zhang
 */
public class ArrayMatch {

	/**
	 * 匹配值并返回序号
	 * @param A
	 * @param elementMatch
	 * @return match series
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月31日下午12:09:49
	 */
	public static int match_String(String[] A, String elementMatch) {
		for(int i=0;i<A.length;i++) {
			if(A[i].equals(elementMatch)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月31日下午12:05:49
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
