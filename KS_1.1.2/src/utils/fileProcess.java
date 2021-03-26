/**
 * 
 */
package utils;

import java.io.File;
import java.io.IOException;

/**
 * @author Hanlin Zhang
 */
public class fileProcess {
	
	public static void existFileDelete(File file, String abPath) throws IOException {
		
		boolean result = false;
		
		if(file.exists()){
			File file1 = new File(abPath);
			file1.delete();
        }
		else {
			file.createNewFile();
		}
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月26日上午9:52:30
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
