package visual.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.h2.constant.Parameters;
import mutiThread.MainThread;

/***
 * 将设置里的参数保存到配置文件中
 * 
 * @author Sunny
 *
 */
public class ParametersToConfig {
	private String ss[] = new String[7];
	private String filePath = "";
	private StringBuffer bufAll = new StringBuffer(); // 保存修改过后的所有内容，不断增加

	public ParametersToConfig() throws IOException {
		ss[0] = "D:/data/ConstructionData/20200711/Testr/";
		ss[1] = "D:/data/ConstructionData/20200711/Testt/";
		ss[2] = "D:/data/ConstructionData/20200711/Testu/";
		ss[3] = "D:/data/ConstructionData/20200711/Testw/";
		ss[4] = "D:/data/ConstructionData/20200711/Testx/";
		ss[5] = "D:/data/ConstructionData/20200711/Testy/";
		ss[6] = "D:/data/ConstructionData/20200711/Testz/";
		String j = System.getProperty("user.dir");// get the procedure absolute path.
		this.filePath = j + "/resource/config.ini";// get the config file.
		System.out.println("111111111111111111111111111111111"+filePath);
		save(this.filePath);
	}

	private void save(String path) throws IOException {
		readFile(path);
		File file = new File(path);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));// GBK
		writer.write(bufAll.toString());
		if (writer != null)
			writer.close();

	}

	private void readFile(String Path) throws NumberFormatException, IOException {
		File file = new File(Path);
		// (文件完整路径),编码格式
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));// GBK
		String line = reader.readLine();
		
		while ((line = reader.readLine()) != null) {// when the procedure read the last line in csv file, the length of
													// it will become 1.
			if (line.length() > 1) { // this line has content.
				if (!line.substring(0, 1).equals("#")) {// this line is an annotation.
					StringBuffer buf = new StringBuffer();
					String item[] = line.split("=");
					// save different data from config file.
					item[0] = item[0].replaceAll(" ", "");
					item[1] = item[1].replaceAll(" ", "");
					// save new value to Parameters.
					/** distanceToSquareWave */
					if (item[0].equals("FREQUENCY")) {
						buf.append("FREQUENCY = " + Parameters.FREQUENCY + "\n");
					}
					if (item[0].equals("distanceToSquareWave")) {
						buf.append("distanceToSquareWave = " + Parameters.distanceToSquareWave + "\n");
					}
					/** ShortCompareLong */
					if (item[0].equals("ShortCompareLong")) {
						buf.append("ShortCompareLong = " + Parameters.ShortCompareLong + "\n");
					}
					/** ShortCompareLongAdjust */
					if (item[0].equals("ShortCompareLongAdjust")) {
						buf.append("ShortCompareLongAdjust = " + Parameters.ShortCompareLongAdjust + "\n");
					}
					/** afterRange_Threshold456 */
					if (item[0].equals("afterRange_Threshold456")) {
//						buf.append("afterRange_Threshold456 = " + Parameters.afterRange_Threshold456);
					}
					/** afterRange_ThresholdMin */
					if (item[0].equals("afterRange_ThresholdMin")) {
						buf.append("afterRange_ThresholdMin = " + Parameters.afterRange_ThresholdMin);
					}
					/** afterRange_ThresholdMax */
					if (item[0].equals("afterRange_ThresholdMax")) {
						buf.append("afterRange_ThresholdMax = " + Parameters.afterRange_ThresholdMax + "\n");
					}
					/** refineRange_ThresholdMin */
					if (item[0].equals("refineRange_ThresholdMin")) {
						buf.append("refineRange_ThresholdMin = " + Parameters.refineRange_ThresholdMin);
					}
					/** refineRange_ThresholdMax */
					if (item[0].equals("refineRange_ThresholdMax")) {
						buf.append("refineRange_ThresholdMax = " + Parameters.refineRange_ThresholdMax + "\n");
					}
					/** IntervalToOtherSensors */
					if (item[0].equals("IntervalToOtherSensors")) {
						buf.append("IntervalToOtherSensors = " + Parameters.IntervalToOtherSensors + "\n");
					}
					/** SSIntervalToOtherSensors */
					if (item[0].equals("SSIntervalToOtherSensors")) {
						if (Parameters.SSIntervalToOtherSensors == true)
							buf.append("SSIntervalToOtherSensors = true\n");
						if (Parameters.SSIntervalToOtherSensors == false)
							buf.append("SSIntervalToOtherSensors = false\n");
					}
					/** INTERVAL */
					if (item[0].equals("INTERVAL")) {
						buf.append("INTERVAL = " + Parameters.INTERVAL + "\n");
					}
					/** C */
					if (item[0].equals("C")) {
						buf.append("C = " + Parameters.C + "\n");
					}
					/** SensorNum */
					if (item[0].equals("SensorNum")) {
						buf.append("SensorNum = " + Parameters.SensorNum + "\n");
					}
					int i = 1;
					/** fileStr */
					if (item[0].equals("fileStr")) {
//						MainThread.fileStr[0] = item[1];
						buf.append("fileStr = " + ss[0] + "\n");
						while (i < this.ss.length) {
							buf.append("fileStr = " + this.ss[i] + "\n");
							i++;
						}
						buf.append("\n");
					}
					if (item[0].equals("WenJianTou")) {
						buf.append("WenJianTou = " + Parameters.WenJianTou + "\n");
					}
					if (item[0].equals("ShuJuTou1")) {
						buf.append("ShuJuTou1 = " + Parameters.ShuJuTou1);
					}
					if (item[0].equals("ShuJu")) {
						buf.append("ShuJu = " + Parameters.ShuJu);
					}
					if (item[0].equals("Shi")) {
						buf.append("Shi = " + Parameters.Shi);
					}
					if (item[0].equals("Yizhen")) {
						buf.append("Yizhen = " + Parameters.Yizhen);
					}
//					if (item[0].equals("YIMiaoG")) {
//						buf.append("YIMiaoG = " + Parameters.YIMiaoG);
//					}
					if (item[0].equals("YIMiao")) {
						buf.append("YIMiao = " + Parameters.YIMiao);
					}
					if (item[0].equals("San")) {
						buf.append("San = " + Parameters.San + "\n");
					}
					if (item[0].equals("startTime")) {
						buf.append("startTime = " + Parameters.startTime + "\n");
					}
					if (item[0].equals("endTime ")) {
						buf.append("endTime = " + Parameters.endTime + "\n");
					}
					if (item[0].equals("TongDaoDiagnose")) {
						buf.append("TongDaoDiagnose = " + Parameters.TongDaoDiagnose + "\n");
					}
					if (item[0].equals("motivationDiagnose")) {
						buf.append("motivationDiagnose = " + Parameters.motivationDiagnose + "\n");
					}
					if (item[0].equals("isStorageDatabase")) {
						buf.append("isStorageDatabase = " + Parameters.isStorageDatabase + "\n");
					}
					if (item[0].equals("isStorageAllMotivationCSV")) {
						buf.append("isStorageAllMotivationCSV = " + Parameters.isStorageAllMotivationCSV + "\n");
					}
					if (item[0].equals("isStorageEventRecord")) {
						buf.append("isStorageEventRecord = " + Parameters.isStorageEventRecord + "\n");
					}

					if (item[0].equals("AbsolutePath_CSV3")) {
						buf.append("AbsolutePath_CSV3 = " + Parameters.AbsolutePath_CSV3 + "\n");
					}
					if (item[0].equals("AbsolutePath_CSV5")) {
						buf.append("AbsolutePath_CSV5 = " + Parameters.AbsolutePath_CSV5 + "\n");
					}
					if (item[0].equals("AbsolutePath5_record")) {
						buf.append("AbsolutePath5_record = " + Parameters.AbsolutePath5_record + "\n");
					}
					if (item[0].equals("AbsolutePath_allMotiTime_record")) {
						buf.append("AbsolutePath_allMotiTime_record = " + Parameters.AbsolutePath_allMotiTime_record
								+ "\n");
					}
					if (item[0].equals("DatabaseName5")) {
						buf.append("DatabaseName5 = " + Parameters.DatabaseName5);
					}
					if (item[0].equals("DatabaseName4")) {
//						buf.append("DatabaseName4 = " + Parameters.DatabaseName4);
					}
					if (item[0].equals("DatabaseName3")) {
//						buf.append("DatabaseName3 = " + Parameters.DatabaseName3 + "\n");
					}
					/** Adjust */
					if (item[0].equals("Adjust")) {
						if (Parameters.Adjust == true)
							buf.append("Adjust = true\n");
						if (Parameters.Adjust == false)
							buf.append("Adjust = false\n");
					}
					if (item[0].equals("MinusAFixedOnMagtitude")) {
						if (Parameters.MinusAFixedOnMagtitude == true)
							buf.append("MinusAFixedOnMagtitude = true");
						if (Parameters.MinusAFixedOnMagtitude == false)
							buf.append("MinusAFixedOnMagtitude = false");
					}
					if (item[0].equals("readSecond")) {
						if (Parameters.readSecond == true)
							buf.append("readSecond = true\n");
						if (Parameters.readSecond == false)
							buf.append("readSecond = false\n");
					}
					if (item[0].equals("MinusAFixedValue")) {
						buf.append("MinusAFixedValue = " + Parameters.MinusAFixedValue + "\n");
					}
					if (item[0].equals("plusSingle_coefficient")) {
						buf.append("plusSingle_coefficient = " + Parameters.plusSingle_coefficient);
					}
					if (item[0].equals("plusDouble_coefficient_45")) {
						buf.append("plusDouble_coefficient_45 = " + Parameters.plusDouble_coefficient_45);
					}
					if (item[0].equals("plusDouble_coefficient_12")) {
						buf.append("plusDouble_coefficient_12 = " + Parameters.plusDouble_coefficient_12 + "\n");
					}
					if (item[0].equals("offline")) {
						if (Parameters.offline == true)
							buf.append("offline = true\n");
						if (Parameters.offline == false)
							buf.append("offline = false\n");
					}
					if (item[0].equals("timeStr")) {
						buf.append("timeStr = " + Parameters.StartTimeStr + "\n");
					}
					if (buf.length() != 0)
						bufAll.append(buf + "\n");
				} else {
					bufAll.append(line + "\n");
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			ParametersToConfig config = new ParametersToConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String stringArrayToString(String[] s) {
		String temp = "";
		for (int i = 0; i < s.length; i++) {
			temp += s[i].toString().replace(" ", ",");
		}
		System.out.print("11:    " + temp);
		return temp;
	}
}
