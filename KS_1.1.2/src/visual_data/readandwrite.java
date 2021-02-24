package visual_data;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class readandwrite {
//    public static void main(String[] args) throws Exception {
//		 long start = System.currentTimeMillis();
//    	 ArrayList<String[]> subdataList = new ArrayList<String[]>();
//    	 ArrayList<ArrayList<String[]>> dataArrayList=new ArrayList<ArrayList<String[]>>();   	
//        // 读取路径
//    	 String[] readfilePath=new String[3];
//    	 readfilePath[0] = "Z:/test/data/s 2019-09-25 11-42-01`81.csv";
//    	 readfilePath[1] = "Z:/test/data/s 2019-09-25 11-51-15`61.csv";
//    	 readfilePath[2] = "Z:/test/data/s 2019-09-25 11-51-18`67.csv";
//    	for (int i = 0; i < readfilePath.length; i++)
//    	{
//    		subdataList=readCSV( readfilePath[i]);
//    		dataArrayList.add(subdataList);
//		}
////        String ReadfilePath = "D:/data/ConstructionData/2019-12-21_QuakeRecords.csv";
//        //生成路径
//        String WritiefilePath = "E:/TestData/a.csv";
//        // 读取CSV文件
//       
//        //生成CSV文件
//        createCSV(dataArrayList,WritiefilePath);
//		long end = System.currentTimeMillis();	
//		long timeDifference = end -start;
//		System.out.println("耗时："+timeDifference+"ms");
//    }

	/**
	 * 读取CSV文件
	 * 
	 * @param filePath:全路径名
	 */
	public static char separator = ',';

	public static ArrayList<String[]> readCSV(String filePath) throws Exception {
		CsvReader reader = null;
		ArrayList<String[]> dataList = new ArrayList<String[]>();
		try {
			reader = new CsvReader(filePath, separator, Charset.forName("UTF-8"));
			// 读取表头
			reader.readHeaders();
			String[] headArray = reader.getHeaders();// 获取标题
			dataList.add(0, headArray);// dataList第一个为表头
			String string = reader.getRawRecord();
			// 逐条读取记录，直至读完
			while (reader.readRecord()) {
				// 读一整行
				dataList.add(reader.getValues());
				// System.out.println(reader.getRawRecord());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				reader.close();
			}
		}

		return dataList;
	}
	
	/**
	 * 生成CSV文件
	 * 
	 * @param dataList:数据集
	 * @param filePath:全路径名
	 */
	public static boolean createCSV(ArrayList<ArrayList<String[]>> dataList, String filePath) throws Exception {
		boolean isSuccess = false;
		CsvWriter writer = null;
		FileOutputStream out = null;

		try {
			out = new FileOutputStream(filePath, true);
			writer = new CsvWriter(out, separator, Charset.forName("UTF-8"));
			for (ArrayList<String[]> ss : dataList) {
				for (String[] strs : ss) {
					writer.writeRecord(strs);
				}

			}

			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				writer.close();
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return isSuccess;
	}
	
	public static void pull(ArrayList<ArrayList<String>> list) throws Exception {
		// 生成路径
		String WritiefilePath = "E:/TestData/";
		// 接收一个事件的一个记录
		ArrayList<String[]> subdataList = new ArrayList<String[]>();
		// 接收一个事件的全部记录
		ArrayList<ArrayList<String[]>> dataArrayList = new ArrayList<ArrayList<String[]>>();
		for (ArrayList<String> s : list) {
			for (String ss : s) {
				System.out.println("s:" + s);
				String[] items = ss.split("\t");
//				for (int i = 0; i < items.length; i++) {
//					System.out.println("filePath" + items[i]);
//				}

				String filePath = items[2];

				subdataList = readCSV(filePath);
				String[] filename=filePath.split(" ");
				WritiefilePath+=filename[1];
				
			}
				
		}
		System.out.println("WritiefilePath" + WritiefilePath);
		dataArrayList.add(subdataList);
		createCSV(dataArrayList, WritiefilePath);

	}
}
