/**
 * 
 */
package utils;

/**
 * @author Hanlin Zhang
 */
public class outArray {
	/**
	 * 输出一维数组中的每个元素。
	 * @param A
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月31日上午11:19:16
	 */
	public static void outArray_String(String[] A) {
		for(int i=0;i<A.length;i++) {
			System.out.print(A[i]+" ");
		}
	}
	/**
	 * 输出三维数组的每个元素。
	 * @param A
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月31日上午11:19:30
	 */
	public static void outArray_double(double[][][] A, int th) {
		for(int i=0;i<A.length;i++) {
			if(i == th) {
				for(int j=0;j<A[i].length;j++) {
					for(int h=0;h<A[i][j].length;h++) {
						System.out.print(A[i][j][h]+", ");
					}
					System.out.println();
				}
			}
		}
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月30日下午10:49:02
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
