package com.h2.tool;

import java.util.Vector;

import com.h2.constant.Sensor;

import utils.one_dim_array_max_min;

/**
 * this class used to compute the distance of motivated position among all motivated sensors, and contain two functions used to obtain the number of max value or min value in a array.
 * @author Hanlin Zhang.
 */
public class getRelativeMotiPOS {
	public static int[] getReMotiPOS(Sensor[] sensors) {
		
		int[] motiline = new int[sensors.length];
		
		for(int i=0; i < sensors.length; i++) {
			motiline[i]=sensors[i].getlineSeries();
		}
		
		int maxLine = one_dim_array_max_min.maxint(motiline);
		int[] lineDifferent = new int[sensors.length];
		
		//save each sensor's line from the earlest sensor to record the moving distance.
		for(int i=0;i<sensors.length;i++) {
			lineDifferent[i] = motiline[i]-maxLine;
		}
		return lineDifferent;
	}
	
//	public static Vector<String>[] UpdateMOTIDATA(Vector<String> motiArray,int[] lineDifferent){
//		Vector<String>[] motiArr = new Vector[motiArray.size()];
//		Vector<String> temp = new Vector<String>();
//		for(int i=0;i<motiArr.length;i++) motiArr[i] = new Vector<String>();
//		int h=0;
//		
//		for(int i=0;i<motiArr.length;i++) {//in motiArr
//			for(int j=0;j<lineDifferent.length;j++) {//in lineDifferent
//				if(lineDifferent[j]>0) {
//					for(int k=0;k<motiArr[i].size()-lineDifferent[j];k++) {//in motiArr size.
//						temp.addElement(motiArr[i].get(lineDifferent[j]+k));
//						h=k;
//					}
//					for(int k=h;k<motiArr[i].size();k++) {
//						temp.addElement();
//					}
//					
//				}
//			}
//			
//		}
//		
//		
//		return motiArr; 
//		
//	}
	
}
