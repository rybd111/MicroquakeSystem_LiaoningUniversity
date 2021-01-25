package visual_data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/***
 * 读取测点 .txt 中的记录类
 * 
 * @author Sunny-胡永亮
 *
 */
public class readTxt {

	private static File file = null;
	private static ArrayList<ArrayList<String>> data = new ArrayList<>();
	private static ArrayList<Integer> lastLine = new ArrayList<>();
	public static ArrayList<Character> name = new ArrayList<>();

	/***
	 * 获取该目录下所有 .txt 文件中的记录
	 * 
	 * @return
	 */
	public static ArrayList<ArrayList<String>> getData(String path) {
		file = new File(path);
		// 清空list
		data.clear();
		// 初始化文件指针
		if (lastLine.size() <= 0) {
			for (int i = 0; i < file.list().length; i++)
				lastLine.add(-1);
		}
		String line = null;
		for (int i = 0; i < file.list().length; i++) {
			name.add(file.list()[i].toCharArray()[0]);
//			System.out.println(file.list()[i].toCharArray()[0]);
			BufferedReader reader;
			try {
				reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(path + "/" + file.list()[i]), "utf-8"));
				ArrayList<String> tempData = new ArrayList<String>();
				int tempLine = 0;
				while ((line = reader.readLine()) != null) {
					if (++tempLine <= lastLine.get(i))
						continue;
					tempData.add(line);
				}
				lastLine.remove(i);
				lastLine.add(i, tempLine);
				data.add(tempData);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	public static void main(String[] args) throws Exception {
		ArrayList<ArrayList<String>> s = readTxt.getData("Z:/test/hyl/201912");
//		readandwrite.pull(MatchRecords.matcher(data));
	}
}
