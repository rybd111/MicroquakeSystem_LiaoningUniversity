package mutiThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Vector;

public class BackUpForInputEarthQuick {

	public static void cunChu(Vector<String> vector,int ii,int j) {

		if (vector == null || vector.size() == 0) {
			return;
		}
		File aFile = new File("D:/back");
		if (!aFile.exists()) {
			aFile.mkdir();// 创建文件夹
			// aFile.mkdirs();// 创建多层文件夹
		}
		File aFile2 = new File("D:/back/"+ii+j+".txt");
		if (!aFile2.exists()) {
			try {
				aFile2.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		OutputStream aOutputStream;

		try {
			/*
			 * FileWriter fw = new FileWriter(aFile2,false); BufferedWriter bw =
			 * new BufferedWriter(fw); bw.write(content); bw.close();
			 * fw.close();
			 */

			aOutputStream = new FileOutputStream(aFile2, true);
			Writer aWriter = new OutputStreamWriter(aOutputStream);
			for (int i = 0; i < vector.size(); i++) {
				aWriter.write(vector.get(i) + "\r\n");
			}
			aWriter.flush();
			aWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void cunChu(Vector<String>[][] sensorData) {
		// TODO Auto-generated method stub
		for (int i = 0; i < sensorData.length; i++) {		
				cunChu(sensorData[i],i);
			
		}
	}

	private static void cunChu(Vector<String>[] vectors, int ii) {
		// TODO Auto-generated method stub
		/*if (vector == null || vector.size() == 0) {
			return;
		}*/
		for (int i = 0; i < vectors.length; i++) {
			if(vectors[i]==null){
				return;
			}
		}
		File aFile = new File("D:/backforEarthQuick");
		if (!aFile.exists()) {
			aFile.mkdir();// 创建文件夹
			// aFile.mkdirs();// 创建多层文件夹
		}
		File aFile2 = new File("D:/backforEarthQuick/"+ii+".txt");
		if (!aFile2.exists()) {
			try {
				aFile2.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		OutputStream aOutputStream;

		try {
			/*
			 * FileWriter fw = new FileWriter(aFile2,false); BufferedWriter bw =
			 * new BufferedWriter(fw); bw.write(content); bw.close();
			 * fw.close();
			 */

			aOutputStream = new FileOutputStream(aFile2, true);
			Writer aWriter = new OutputStreamWriter(aOutputStream);
			for (int i = 0; i < vectors[0].size(); i++) {
				aWriter.write(vectors[0].get(i) + " "+vectors[1].get(i)+" "+vectors[2].get(i)+"\r\n");
			}
			aWriter.flush();
			aWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
