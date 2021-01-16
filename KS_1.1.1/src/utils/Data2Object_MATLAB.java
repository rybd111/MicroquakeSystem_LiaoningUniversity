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
	
	public static Object data2Object_65000(Vector<String> a,int channel) {
		double rowData[] = new double[65000];
		Object rowDataO = new Object();
		
		for(int i=0;i<65000;i++) {
			rowData[i] =Double.parseDouble(a.get(i).split(" ")[channel]);
		}
		rowDataO=rowData;
		return rowDataO;
	}
	
	public static Object array2Object(double[][] a) {
//		double[] raw = new double[a[0].length];
		Object rawdata[] = new Object[a.length];
		
//		for(int i=0;i<a.length;i++) {
//			for(int j=0;j<a[0].length;j++) {
//				raw[j] = a[i][j];
//			}
//			
//		}
//		for(int i=0;i<a.length;i++) {
//			rawdata[i] = a[i]; 
//		}
		rawdata = a;
		return rawdata;
	}
	
	public static Object num2Object(int a) {
		Object o = new Object();
		double a1 = a*1.0;
		o=a1;
		return o;
	}
}
