package visual.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.h2.constant.Parameters;

import visual.util.Tools_DataCommunication;

/**
 * 已做代码优化-2020/09/25
 * 
 * @author Sunny-胡永亮
 *
 */
public class ReadCSV {
	protected File file = null;
	protected int senNum = 0;
	protected String fileSS = null;
//	protected String SenChannel[][] = new String[(Parameters.startTime + Parameters.endTime) * 5000][];
//	protected String SenChannelNum[][] = new String[(Parameters.startTime + Parameters.endTime) * 5000][];
//	protected String Time[] = new String[(Parameters.startTime + Parameters.endTime) * 5000];
	// the motivation position.
//	protected int motiPos[];

	public ReadCSV(String filePath) {
		this.file = new File(filePath);
		// determine the number of sensor.
		String fileS[] = filePath.split("/");
		// 解决文件名兼容性问题
//		mysqlit(fileS[fileS.length - 1]);
		fileSS = mysqlit(fileS[fileS.length - 1]);// filess="25613"
//		System.out.println("!111111111111111111   " + fileSS);
		if (fileSS == null)
			return;
//		System.out.println("文件名为：" + fileSS);
		this.senNum = fileSS.length();
		Tools_DataCommunication.getCommunication().fileSS = fileSS;
//		motiPos = new int[senNum];// the motivated position of each sensor.
//		SenChannel = new String[(Parameters.startTime + Parameters.endTime)
//				* (Parameters.FREQUENCY + 200)][this.senNum];// the channel of all sensors' z axis data.
//		SenChannelNum = new String[(Parameters.startTime + Parameters.endTime)
//				* (Parameters.FREQUENCY + 200)][this.senNum];
	}

	/***
	 * 解决文件名兼容性问题
	 * 
	 * @return
	 */
	private String mysqlit(String fileName) {
		// 按照空格分片
		String temp1[] = fileName.split(" ");
		// 分两种情况：
		char c = temp1[1].toCharArray()[0];
		if ('a' <= c && c <= 'z')
			return temp1[0] + temp1[1];

		return temp1[0];
	}

	public ArrayList<ArrayList<Integer>> readContents(int num) throws NumberFormatException, IOException {
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		double[] P_array = new double[this.senNum];
		for (int i = 0; i < 3 * this.senNum; i++)
			list.add(new ArrayList<>());
		int count = 0;
		// (文件完整路径),编码格式
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));// GBK
		String line = null;
		while ((line = reader.readLine()) != null) {
			String item[] = line.split(",");// CSV格式文件时候的分割符,我使用的是,号
			int length = item.length;
			if (item.length > 1 && count < num * 5000) {
				for (int i = 0; i < this.senNum; i++) {
					double max = compareMax(Math.abs(Integer.parseInt(item[4 + 8 * i])),
							Math.abs(Integer.parseInt(item[5 + 8 * i])), Math.abs(Integer.parseInt(item[6 + 8 * i])));
					if (max > Tools_DataCommunication.getCommunication().chartYmax[i])
						Tools_DataCommunication.getCommunication().chartYmax[i] = max;
					list.get(0 + 3 * i).add(Integer.parseInt(item[4 + 8 * i]));// x 4,12
					list.get(1 + 3 * i).add(Integer.parseInt(item[5 + 8 * i]));// y 5,13
					list.get(2 + 3 * i).add(Integer.parseInt(item[6 + 8 * i]));// z 6,14
					P_array[i] = Integer.parseInt(item[7 + 8 * i]);
				}
			}
			count++;
		}
		Tools_DataCommunication.getCommunication().P_array = P_array;
//		for (int i = 0; i < P_array.length; i++) {
//			System.out.println(P_array[i]);
//		}
		return list;
	}

	/**
	 * 三个数中取其最大值
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	private double compareMax(double a, double b, double c) {
		return a > b ? (a > c ? a : c) : (b > c ? b : c);
	}
}
