/**
 * 
 */
package utils;

import java.util.Vector;

import com.h2.constant.Parameters;

/**
 * 判断数据量是否足够？
 * 用于计算模块的数据判断
 * @author Hanlin Zhang
 */
public class diagDataNum {
	
	public static boolean diagnose(Vector<String>[][] ssen) {
		
		//the number of data must enough to calculate which satisfied to 10s, or it will appear mistake consequence for the current data.
		for (Vector<String>[] vectors : ssen) {
			for (Vector<String> vector : vectors) {
				if (vector.size() < Parameters.FREQUENCY * Parameters.readLen)	
					return false;//this function promise enough volume of data.
			}
		}
		
		return true;
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月12日下午7:44:08
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
