package utils;

import com.h2.constant.Parameters;

import DataExchange.Sensor;
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
	 * Join the motivated sensor's name, and return a string.
	 * @param S
	 * @param sensors
	 * This is the sorted sensor array.
	 * @return 
	 * A string of motivated sensors. 
	 * @author Hanlin Zhang.
	 */
	public static String SJoin_Array(String[] S, Sensor[] sensors) {
		
		String joinS="";
		
		for(int i=0;i<sensors.length;i++) {
			joinS=joinS.concat(S[sensors[i].getSensorNum()]);//extract the correspond sensor's number motivated.
		}
		return joinS;
	}
	
	/**
	 * transfer the String array to String.
	 * @param A
	 * @return String
	 * @author Hanlin Zhang.
	 * @date revision 下午7:31:43
	 */
	public static String SJoin_ArrayAsSpace(String[] A) {
		String result = null;
		
		for(int i=0;i<A.length;i++) {
			result += " "+A[i];
		}
		
		return result;
	}
	
	public static String SJoin_Array(String[] A) {
		String result = "";
		
		for(int i=0;i<A.length;i++) {
			result +=A[i];
		}
		
		return result;
	}
	
	
	
}
