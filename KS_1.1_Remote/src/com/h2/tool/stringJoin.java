package com.h2.tool;

import com.h2.constant.Parameters;
import com.h2.constant.Sensor;

import mutiThread.MainThread;

/**
 * we can use this function to join a String type array.
 * @author Hanlin Zhang.
 */
public class stringJoin {
	/**
	 * @param S The String array will integrate.
	 * @return The integrated String.
	 */
	@SuppressWarnings("unused")
	public static String SJoin_Array(String[] S, int [] l, int num) {
		
		String joinS="";

		for(int i=0;i<num;i++) {
			if(l[i]==0&&i==0) {
				joinS=joinS.concat(S[l[i]]);
			}
			else if(l[i]!=0) {
				joinS=joinS.concat(S[l[i]]);
			}
		}
		return joinS;
	}
	
	/**
	 * 
	 * @param S
	 * @param sensors
	 * @return
	 * @author Hanlin Zhang
	 * @date revision 2020年11月30日
	 */
	public static String SJoin_Array(String[] S, Sensor[] sensors) {
		
		String joinS="";
		for(int i=0;i<sensors.length;i++) {
			joinS=joinS.concat(S[sensors[i].getSensorNum()]);//extract the correspond sensor's number motivated.
		}

		return joinS;
	}
	
}
