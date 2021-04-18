/**
 * 
 */
package testKS;

import java.io.IOException;
import java.text.ParseException;

import com.h2.locate.ReLocation;
import com.mathworks.toolbox.javabuilder.MWException;

import utils.CoorProcess;
import utils.stringJoin;

/**
 * @author Hanlin Zhang
 */
public class relocation_test {

	/**
	 * 单独运行时，确保offline是true，并且注意盘符格式和地区region。
	 * 当前使用时，如果是自定义的到时和坐标，则需要手动输入，如果是region内的矿区，则不需要。
	 * 使用时，需要注意传感器数量、传感器采样频率、传感器坐标要使用相对坐标，否则PSO算法不能生效。
	 * @param args
	 * @author Hanlin Zhang.
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws MWException 
	 * @date revision 下午6:34:06
	 */
	public static void main(String[] args) throws IOException, MWException, ParseException {
		double [][] coor = null;
		String kind = "PSO_Locate";//PSO_Locate, Five_Locate.
		//确定传感器数量。
		int sensorNum = 4;
		double []daoshi = new double[sensorNum]; 
		String[] filePath = new String[sensorNum];

		//initial daoshi and filePath.
//		daoshi[0] = 0;
//		daoshi[1] = 0.14;
//		daoshi[2] = 0.57;
//		daoshi[3] = 0.07;
		
		filePath[0] = "Testv/";
		filePath[1] = "Testu/";
		filePath[2] = "Testw/";
		filePath[3] = "Testz/";
		coor = new double[4][4];
//		coor = ReLocation.initialCoor(sensorNum, filePath, daoshi);
		
		coor[0][1] = 384321.5012;
		coor[0][2] = 4349462.1852;
		coor[0][3] = 675.1000;
		coor[0][0] = 2760/500.0;
		
		coor[1][1] = 384406.7260;
		coor[1][2] = 4349173.9024;
		coor[1][3] = 676.2000;
		coor[1][0] = 2770/500.0;
		
//		coor[2][1] = 384523.7502;
//		coor[2][2] = 4349263.0437;
//		coor[2][3] = 681.3720;
//		coor[2][0] = 2800/500.0;
		
		coor[2][1] = 384523.8828;
		coor[2][2] = 4349608.9562;
		coor[2][3] = 681.7700;
		coor[2][0] = 2820/500.0;
		
//		coor[4][1] = 385121.9614;
//		coor[4][2] = 4349697.6698;
//		coor[4][3] = 676.2500;
//		coor[4][0] = 2890/500.0;
		
		coor[3][1] = 383570.4640;
		coor[3][2] = 4348672.0280;
		coor[3][3] = 1401.1460;
		coor[3][0] = 2990/500.0;
		
		String absolutetime="2021-01-22 06-11-50`63";
		String[] result=ReLocation.locate(coor, kind, absolutetime);

		result[0] = String.valueOf(Double.parseDouble(result[0]));
		result[1] = String.valueOf(Double.parseDouble(result[1]));
		result[2] = String.valueOf(Double.parseDouble(result[2]));
		result[3] = String.valueOf(Double.parseDouble(result[3]));
		
		System.out.println(stringJoin.SJoin_ArrayAsSpace(result));
	}

}
