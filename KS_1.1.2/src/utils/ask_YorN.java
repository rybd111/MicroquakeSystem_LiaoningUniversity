/**
 * 
 */
package utils;

import java.io.IOException;
import java.util.Scanner;

import com.ibm.icu.util.InitialTimeZoneRule;

import ch.qos.logback.core.boolex.Matcher;
import mutiThread.MainThread;

/**
 * @author Hanlin Zhang
 */
public class ask_YorN {

	/**
	 * 询问是还是否。
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月28日下午1:46:30
	 */
	public static boolean askYesOrNo() {

	    String yesOrNo = null;
	
	    boolean validInput = false;

	    while (!validInput) {
	    	System.out.println("是否手动选择盘符(y/n)?手动选择直接输入盘符，中间不用加入任何区分，用回车结束。");

			yesOrNo = getConsoleInput();
			
			validInput = yesOrNo.toUpperCase().equals("Y") || yesOrNo.toUpperCase().equals("N");
			
			if (!validInput) {
			
			    System.out.println("Invalid input! Please enter /'Y/' or /'N/'");
			
			}
		}

	    return yesOrNo.toUpperCase().equals("Y");

	}
	
	/**
	 * 盘符较少时询问?
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月20日下午12:16:27
	 */
	public static void askWhenHasLess(String fileStr[]) {
		// 我们认为小于2个盘符的路径并不符合，数量不够
		if(fileStr.length<=2) {
			System.out.println("扫描到的盘符有：");
			for(int i=0;i<fileStr.length;i++) {
				System.out.println(fileStr[i]);
			}
			System.out.println("盘符数量较少<2，是否继续？");
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String[] determineDisk(String[] fileStr) {
		
		String inputFileStr = null;
		
		boolean validInput = false;
		
		while(!validInput) {
			System.out.println("请选择盘符：");
			
			inputFileStr = getConsoleInput();
			
			validInput = Match(inputFileStr.toLowerCase(), fileStr);
			
			if(!validInput) {
				System.out.println("Invalid input! Please enter a string contained in fileStr");
			}
		}
		
		String[] result = String2Array.string2array(inputFileStr);
		
		//加入:/可以完成盘符的组合。
		for (int i = 0; i < result.length; i++) {
			result[i] += ":/";
		}
		
		return result;
	}
	 
	
    private static String getConsoleInput() {

        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();

    }
    
    private static boolean Match(String inputFileStr, String[] fileStr) {
    	
    	String inteFileStr = stringJoin.SJoin_Array(fileStr).replace(":\\", "");
    	
    	String[] A = String2Array.string2array(inputFileStr);
    	
    	for( int i=0;i<A.length;i++) {
    		if(inteFileStr.toLowerCase().contains(A[i]) == false) {
        		return false;
        	}
    	}
    	
    	return true;
    }
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月20日上午9:13:46
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String query = "nowjava.com";

        System.out.println(askYesOrNo());
	}

}
