package mutiThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bean.JiZhi;

public class GetJiZhi {
	public static JiZhi test() {
		File afile = new File("E:\\back\\60.txt");
		JiZhi jizhi = readFile(afile, null);
		return jizhi;
	}

	public static JiZhi readFile(File file, String charset) {

		JiZhi ajizhi = new JiZhi();
		// 设置默认编码
		if (charset == null) {
			charset = "UTF-8";
		}
		int xMax = 0;
		List<String> xMinTime = new ArrayList<String>();
		List<String> yMinTime = new ArrayList<String>();
		List<String> zMinTime = new ArrayList<String>();

		List<String> xMaxTime = new ArrayList<String>();
		List<String> yMaxTime = new ArrayList<String>();
		List<String> zMaxTime = new ArrayList<String>();

		int yMax = 0;
		int zMax = 0;
		int xMin = 0;
		int yMin = 0;
		int zMin = 0;
		int x, y, z;
		String time = null;
		String text = null;
		if (file.isFile() && file.exists()) {
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(
						fileInputStream, charset);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);

				// StringBuffer sb = new StringBuffer();

				while ((text = bufferedReader.readLine()) != null) {
					String[] str = text.split(" ");
					x = Integer.valueOf(str[0]);
					y = Integer.valueOf(str[1]);
					z = Integer.valueOf(str[2]);

					if (x > xMax) {
						xMax = x;
					}
					if (y > yMax) {
						yMax = y;
					}
					if (z > yMax) {
						zMax = z;
					}
					if (x < xMin) {
						xMin = x;
					}
					if (y < yMin) {
						yMin = y;
					}
					if (z < zMin) {
						zMin = z;
					}

				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		if (file.isFile() && file.exists()) {
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(
						fileInputStream, charset);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);

				// StringBuffer sb = new StringBuffer();

				while ((text = bufferedReader.readLine()) != null) {
					String[] str = text.split(" ");
					x = Integer.valueOf(str[0]);
					y = Integer.valueOf(str[1]);
					z = Integer.valueOf(str[2]);
					/*time=*/
					if (x == xMax) {
						time = str[6];
						xMaxTime.add(time);
					}
					if (y == yMax) {
						time = str[6];
						yMaxTime.add(time);
					}
					if (z == yMax) {
						time = str[6];
						zMaxTime.add(time);
					}
					if (x == xMin) {
						time = str[6];
						xMinTime.add(time);
					}
					if (y == yMin) {
						time = str[6];
						yMinTime.add(time);
					}
					if (z == zMin) {
						time = str[6];
						zMinTime.add(time);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		ajizhi.setxMax(xMax);
		ajizhi.setxMaxTime(xMaxTime);
		ajizhi.setxMin(xMin);
		ajizhi.setxMinTime(xMinTime);
		
		ajizhi.setyMax(yMax);
		ajizhi.setyMaxTime(yMaxTime);
		ajizhi.setyMin(yMin);
		ajizhi.setyMinTime(yMinTime);
		
		ajizhi.setzMax(zMax);
		ajizhi.setzMaxTime(zMaxTime);
		ajizhi.setzMin(zMin);
		ajizhi.setzMinTime(zMinTime);
		
		return ajizhi;
	}
}
