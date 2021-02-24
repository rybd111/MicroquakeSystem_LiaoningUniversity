/**
 * 
 */
package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.Process;
//import org.apache.commons.lang3.text.StrBuilder;
/**
 * @author Hanlin Zhang
 */
public class PrintInfoINCMD {

	public void runCommand() throws IOException {
		String[] cmd = {"cmd","/k","Start Echo Hello My Friend"};
		//执行API Runtime获取系统运行环bai境并执行
		Runtime.getRuntime().exec(cmd);
     }
 
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @throws IOException 
	 * @date revision 2021年2月24日下午4:25:25
	 */
	public static void main(String[] args) throws IOException {
		
		PrintInfoINCMD pri = new PrintInfoINCMD();
		pri.runCommand();
	}

}
