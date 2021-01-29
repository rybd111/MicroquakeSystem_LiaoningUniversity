package visual.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.ArrayList;

import com.h2.constant.Parameters;

import javafx.scene.chart.XYChart;
import visual.util.Tools_DataCommunication;
/**
 * 已做代码优化-2020/09/25
 * @author Sunny-胡永亮
 *
 */
public class saveCSV {
	private File file = null;
	private int senNum = 0;
	private String fileSS = null;
	private StringBuffer bufAll = new StringBuffer(); // 保存修改过后的所有内容，不断增加
	/** P波到时 */
	private ArrayList<XYChart.Series<Number, Number>> pArray = null;
	
	public saveCSV(String filePath,ArrayList<XYChart.Series<Number, Number>> pArray) {
		this.file = new File(filePath);
		String fileS[] = filePath.split("/");
		fileSS = mysqlit(fileS[fileS.length - 1]);// filess="25613"
		this.senNum = fileSS.length();
		this.pArray=pArray;
	}
	/***
	 * 解决文件名兼容性问题
	 * 
	 * @return
	 */
	private String mysqlit(String fileName) {
		String temp1[] = fileName.split(" ");
		char c = temp1[1].toCharArray()[0];
		if ('a' <= c && c <= 'z')
			return temp1[0] + temp1[1];
		else
			return temp1[0];
	}
	public void save() throws NumberFormatException, IOException {
		//保存P波到时到CSV文件中
		readContents(Tools_DataCommunication.getCommunication().readTime);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));// GBK
		writer.write(bufAll.toString());
		if (writer != null)
			writer.close();
		//将
	}
	private void readContents(int num) throws NumberFormatException, IOException {
		 NumberFormat nf = NumberFormat.getNumberInstance();
	     nf.setMaximumFractionDigits(7);
		int count = 0;
		// (文件完整路径),编码格式
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));// GBK
		String line = null;
		while ((line = reader.readLine()) != null) {
			StringBuffer buf = new StringBuffer();
			String item[] = line.split(",");// CSV格式文件时候的分割符,我使用的是,号
			int length = item.length;
			if (item.length > 1 && count < num * (Parameters.FREQUENCY+200)) {
				buf.append(item[0]+",");
				for (int i = 0; i < this.senNum; i++) {
					buf.append(item[1 + 8 * i]+",");
					buf.append(item[2 + 8 * i]+",");
					buf.append(item[3 + 8 * i]+",");
					buf.append(item[4 + 8 * i]+",");
					buf.append(item[5 + 8 * i]+",");
					buf.append(item[6 + 8 * i]+",");
					//修改P波到时
					buf.append( pArray.get(i).getData().get(0).getXValue().intValue()+",");
					buf.append(item[8 + 8 * i]+",");
				}
//			buf.append(item[length-1]);
			}
			count++;
			if (buf.length() != 0)
				bufAll.append(buf + "\n");
		}
	}

}
