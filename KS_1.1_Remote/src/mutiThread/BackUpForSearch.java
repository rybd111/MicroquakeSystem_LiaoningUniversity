package mutiThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Vector;

public class BackUpForSearch {
	
	
	public static void cunChu(Vector<String> vector, int a) {
		
		if(vector==null || vector.size()==0){
			return;
		}
		File aFile = new File("E:/back");
		if (!aFile.exists()) {
			aFile.mkdir();// 创建文件夹
			// aFile.mkdirs();// 创建多层文件夹
		}
		File aFile2 = new File("E:/back/60.txt");
		if (!aFile2.exists()) {
			try {
				aFile2.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		OutputStream aOutputStream;
		if (a < 10) {
			try {
				/*
				 * FileWriter fw = new FileWriter(aFile2,false); BufferedWriter
				 * bw = new BufferedWriter(fw); bw.write(content); bw.close();
				 * fw.close();
				 */

				aOutputStream = new FileOutputStream(aFile2, true);
				Writer aWriter = new OutputStreamWriter(aOutputStream);
				for (int i = 0; i < vector.size(); i++) {
					aWriter.write(vector.get(i)+"\r\n");
				}
				aWriter.flush();
				aWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				aOutputStream = new FileOutputStream(aFile2, false);
				Writer aWriter = new OutputStreamWriter(aOutputStream);
				for (int i = 0; i < vector.size(); i++) {			
					aWriter.write(vector.get(i)+"\r\n");
				}
				aWriter.flush();
				aWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
