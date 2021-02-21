/**
 * 
 */
package testKS;

import java.io.IOException;
import java.text.ParseException;

import com.h2.locate.ReLocation;
import com.mathworks.toolbox.javabuilder.MWException;

import utils.stringJoin;

/**
 * @author Hanlin Zhang
 */
public class relocation_test {

	/**
	 * 单独运行时，确保offline是true，并且注意盘符格式和地区region。
	 * @param args
	 * @author Hanlin Zhang.
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws MWException 
	 * @date revision 下午6:34:06
	 */
	public static void main(String[] args) throws IOException, MWException, ParseException {
		// TODO Auto-generated method stub
		double [][] coor = null;
		String kind = "PSO_Locate";//PSO_Locate, Five_Locate.
		
		int sensorNum = 4;
		double []daoshi = new double[sensorNum]; 
		String[] filePath = new String[sensorNum];

		
		//initial daoshi and filePath.
		daoshi[0] = 0;
		daoshi[1] = 0.14;
		daoshi[2] = 0.57;
		daoshi[3] = 0.07;
		
		filePath[0] = "Testv/";
		filePath[1] = "Testu/";
		filePath[2] = "Testw/";
		filePath[3] = "Testz/";
		
		coor = ReLocation.initialCoor(sensorNum, filePath, daoshi);
		
		String absolutetime="2021-01-22 06-11-50`63";
		String[] result=ReLocation.locate(coor, kind, absolutetime);
		
		System.out.println(stringJoin.SJoin_ArrayAsSpace(result));
	}

}
