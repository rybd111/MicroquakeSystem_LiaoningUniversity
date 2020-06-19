/**
 * 
 */
package utils;

import java.util.Vector;

/**
 * MATLAB data transfer function.
 * @author Hanlin Zhang
 */
public class Data2Object_MATLAB {

	/**
	 * @param args
	 * @author Hanlin Zhang.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	/**
	 * transfer the normal array in java to the Object array for invoking matlab function.
	 * @param a
	 * @param channel
	 * @return an Object variable.
	 * @author Hanlin Zhang.
	 */
	public static Object data2Object(Vector<String> a,int channel) {
		double rowData[] = new double[a.size()];
		Object rowDataO = new Object();
		
		for(int i=0;i<a.size();i++) {
			rowData[i] =Double.parseDouble(a.get(i).split(" ")[channel]);
		}
		rowDataO=rowData;
		return rowDataO;
	}
}
