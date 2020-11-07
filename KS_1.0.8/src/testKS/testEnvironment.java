/**
 * @revision 2020年10月26日
 * @author Hanlin Zhang
 */
package testKS;
import com.mathworks.toolbox.javabuilder.MWException;

import testEnvironment.SUM;

/**
 * 
 * @author Hanlin Zhang
 * @date revision 2020年10月26日
 */
public class testEnvironment {

	/**
	 * @param args
	 * @author Hanlin Zhang
	 * @throws MWException 
	 * @date revision 2020年10月26日
	 */
	public static void main(String[] args) throws MWException {
		int a = 1,b = 1;
		
		SUM s = new SUM();
		Object[] conse = s.testEnvironment(1, a, b);
		System.out.println("consequence:" + conse[0].toString());
	}

}
