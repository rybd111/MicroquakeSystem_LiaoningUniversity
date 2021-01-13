package com.h2.backupData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

import com.h2.constant.Parameters;
import com.h2.constant.Sensor;
import com.h2.tool.QuakeClass;
import com.h2.tool.getRelativeMotiPOS;
import com.h2.tool.relativeStatus;
import com.h2.tool.stringJoin;

import mutiThread.MainThread;

public class writeToDisk {
	/**
	 * write a array to disk.
	 * @param filePath
	 * @param interSet
	 * @author Hanlin Zhang
	 * @throws IOException 
	 * @date revision 2020年10月25日
	 */
	public static void write_array(String filePath, Vector<String> A) throws IOException {
		File file=new File(filePath);
		BufferedWriter out = null;
		if(!file.exists())file.createNewFile();
		
		try {
        	out = new BufferedWriter(new FileWriter(filePath, true));
		} catch (IOException e1) {e1.printStackTrace();}
		
		for(int i=0;i<A.size();i++) {
			try {
	        	out.write(i+" ");
	        	out.write(A.get(i).split(" ")[5]);
	        	out.write("\n");
			}
			catch (IOException e1) {e1.printStackTrace();}
		}
		
		if(out != null){
			out.close();
		}
	}
	/**
	 * write motivation data to a csv file.
	 * @param selected sensors
	 * @param motiNum motivation number
	 * @param motiDate quack time
	 * @param panfu motivation sensors
	 * @author Hanlin Zhang
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("unused")
//	public static void writeToCSV(Sensor[] s1, Sensor[] s2, int motiNum, String motiDate, String panfu) throws ParseException, IOException {
	public static void writeToCSV(Sensor[] s1, int motiNum, String motiDate, String panfu) throws ParseException, IOException {
		
		//the name of write file - the most early time in all motivated sensors.
		motiDate=motiDate.replace(":","-");//替换掉时间中的:
		motiDate=motiDate.replace(".", "`");//替换掉时间中的.
		
		//set new motivation position.
		int[] lineInNewData = new int[s1.length];
		for(int i=0;i<s1.length;i++) {
			lineInNewData[i]=s1[i].getlineSeriesNew();
		}
		
		//get the motidata of each motivation sensor, but it is not align in time.
		Vector<String> [] MOTIDATA = new Vector[s1.length];
		for(int i=0;i<s1.length;i++) {
			MOTIDATA[i] = new Vector<String>();
		}
		for(int i=0;i<s1.length;i++) {
			MOTIDATA[i].addAll(s1[i].getCutVectorData());
		}
		
		
		if(Parameters.offline==false) {
			
			if(motiNum>3){
				//write on the local disk.
				WriteMotiData.writeToCSV(MOTIDATA, Parameters.AbsolutePath_CSV5+panfu+motiDate+".csv", lineInNewData, s1);
				//write on the data center.
				WriteMotiData.writeToCSV(MOTIDATA, Parameters.AbsolutePath_CSV5_dataCenter+panfu+motiDate+".csv", lineInNewData, s1);
				for(int i=0;i<s1.length;i++)
					s1[i].setFilename_S(Parameters.AbsolutePath_CSV5+panfu+motiDate+".csv");//文件名
			}

			if(motiNum==3){
				//write on the local disk.
				WriteMotiData.writeToCSV(MOTIDATA, Parameters.AbsolutePath_CSV3+panfu+motiDate+".csv", lineInNewData, s1);
				//write on the data center.
				WriteMotiData.writeToCSV(MOTIDATA, Parameters.AbsolutePath_CSV3_dataCenter+panfu+motiDate+".csv", lineInNewData, s1);
				for(int i=0;i<s1.length;i++)
					s1[i].setFilename_S(Parameters.AbsolutePath_CSV3+panfu+motiDate+".csv");//文件名
			}
			
		}
		else {
			
			if (motiNum == 3) {
				WriteMotiData.writeToCSV(MOTIDATA, Parameters.AbsolutePath_CSV3+panfu+motiDate+".csv", lineInNewData, s1);
				for(int i=0;i<s1.length;i++)
					s1[i].setFilename_S(Parameters.AbsolutePath_CSV3+panfu+motiDate+".csv");//文件名
			}
			if (motiNum > 3) {
				WriteMotiData.writeToCSV(MOTIDATA, Parameters.AbsolutePath_CSV5+panfu+motiDate+".csv", lineInNewData, s1);
				for(int i=0;i<s1.length;i++)
					s1[i].setFilename_S(Parameters.AbsolutePath_CSV5+panfu+motiDate+".csv");//文件名
			}
			
		}
	}
	
	@SuppressWarnings("unused")
//	public static void saveAllMotivationSensors(int countNumber, Sensor [] s1, Sensor[] s2, String panfu) throws ParseException, IOException {
	public static void saveAllMotivationSensors(int countNumber, Sensor [] s1, String panfu) throws ParseException, IOException {
			//the name of csv file is named by the first data's date in seconds.
			writeToDisk.writeToCSV(s1, countNumber, s1[0].getAbsoluteTime(), panfu+" ");
	}
	
}
