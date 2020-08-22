package visual.model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.h2.constant.Parameters;
public class ReadCSV {
	protected File file = null;
	protected int senNum = 0;
	protected String fileSS = null;
	protected String SenChannel[][] = new String[(Parameters.startTime + Parameters.endTime) * 5000][];
	protected String SenChannelNum[][] = new String[(Parameters.startTime + Parameters.endTime) * 5000][];
	protected String Time[] = new String[(Parameters.startTime + Parameters.endTime) * 5000];
	// the motivation position.
	protected int motiPos[];

	public ReadCSV(String filePath) {
		this.file = new File(filePath);
		// determine the number of sensor.
		String fileS[] = filePath.split("/");
		fileSS = fileS[fileS.length - 1].split(" ")[0];// filess="25613"
		this.senNum = fileSS.length();
		motiPos = new int[senNum];// the motivated position of each sensor.
		SenChannel = new String[(Parameters.startTime + Parameters.endTime)
				* (Parameters.FREQUENCY + 200)][this.senNum];// the channel of all sensors' z axis data.
		SenChannelNum = new String[(Parameters.startTime + Parameters.endTime)
				* (Parameters.FREQUENCY + 200)][this.senNum];
	}

	public ArrayList<ArrayList<Integer>> readContents(int num) throws NumberFormatException, IOException {
		ArrayList<Integer> number1 = new ArrayList<>();
		ArrayList<Integer> number2 = new ArrayList<>();
		ArrayList<Integer> number3 = new ArrayList<>();
		ArrayList<ArrayList<Integer>> list=new ArrayList<ArrayList<Integer>>();
		int count = 0;
		// (文件完整路径),编码格式
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));// GBK
		String line = null;
		while ((line = reader.readLine()) != null) {
			String item[] = line.split(",");// CSV格式文件时候的分割符,我使用的是,号
			if (item.length > 1 && count < num*5000) {
				number1.add(Integer.parseInt(item[4]));
				number2.add(Integer.parseInt(item[5]));
				number3.add(Integer.parseInt(item[6]));
				count++;
			}
		}
		list.add(number1);
		list.add(number2);
		list.add(number3);
		return list;
	}
}
