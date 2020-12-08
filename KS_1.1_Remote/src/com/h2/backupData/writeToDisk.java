package com.h2.backupData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
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
	@SuppressWarnings("unused")
	public static void writeToTxt(Sensor sen, int motiNum, String panfu, int th, Vector<String> motiPreLa) throws ParseException {
		//获取激发时间，以秒为单位
		String motiDate = sen.getAbsoluteTime();
		
		//the name of write file.
		motiDate=motiDate.replace(":","");//替换掉时间中的:
		motiDate=motiDate.replace(".", " ");//替换掉时间中的.
		
		if(Parameters.offline==false) {
			
			String[] panfu1 = new String[Parameters.SensorNum];//替换掉所有盘符中的:/，用于作为文件名
			for(int i=0;i<Parameters.SensorNum;i++) {
				panfu1[i] = MainThread.fileStr[i];
				panfu1[i]=panfu1[i].replace(":/", "");
			}
			//控制存储模块的开启与关闭
			if(Parameters.isStorageToTxt == 1){
				//存储5台站或更多激发数据至txt
				if(motiNum>3){
					for(int i=0;i<Parameters.diskName.length;i++){
						if(MainThread.fileStr[th].equals(Parameters.diskName[i]) && Parameters.initPanfu[i]==0){
							WriteMotiData.writemotiData(motiPreLa,Parameters.AbsolutePath5+panfu1[th]+"/"+panfu+motiDate+".txt",Parameters.FREQUENCY * Parameters.startTime);
							Parameters.initPanfu[i]=1;
							sen.setFilename_S(Parameters.AbsolutePath5+panfu1[th]+"/"+panfu+motiDate);//文件名
						}
					}
				}
				
				//存储3台站激发数据至txt
				if(motiNum==3){
					for(int i=0;i<Parameters.diskName.length;i++){
						if(MainThread.fileStr[th].equals(Parameters.diskName[i]) && Parameters.initPanfu[i]==0){
							WriteMotiData.writemotiData(motiPreLa,Parameters.AbsolutePath3+panfu1[th]+"/"+panfu+motiDate+".txt",Parameters.FREQUENCY * Parameters.startTime);
							Parameters.initPanfu[i]=1;
							sen.setFilename_S(Parameters.AbsolutePath3+panfu1[th]+"/"+panfu+motiDate);
						}
					}
				}
			}
		}
		else {
			
			String parent="";
			switch (MainThread.fileParentPackage[sen.getSensorNum()]) {
				case "Test1": { parent="Test1";break; }
				case "Test2": { parent="Test2";break; }
				case "Test3": { parent="Test3";break; }
				case "Test4": { parent="Test4";break; }
				case "Test5": { parent="Test5";break; }
				case "Test6": { parent="Test6";break; }
				case "Test7": { parent="Test7";break; }
				default: break;
			}
			if(Parameters.isStorageToTxt == 1){
				if (motiNum == 3) {
					WriteMotiData.writemotiData(motiPreLa, Parameters.AbsolutePath3_offline+parent+"/" + panfu+motiDate+".txt", Parameters.FREQUENCY * Parameters.startTime);//写入10s的数据
					sen.setFilename_S(Parameters.AbsolutePath3_offline+parent+"//"+panfu+motiDate);//文件名
				}
				if (motiNum > 3) {
					WriteMotiData.writemotiData(motiPreLa, Parameters.AbsolutePath5_offline+parent+"/" +panfu+motiDate+".txt", Parameters.FREQUENCY * Parameters.startTime);//写入10s的数据
					sen.setFilename_S(Parameters.AbsolutePath5_offline+parent+"//"+panfu+motiDate);//文件名
				}
			}
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
	public static void writeToCSV(Sensor[] sen, int motiNum, String motiDate) throws ParseException, IOException {
		
		//the name of write file - the most early time in all motivated sensors.
		motiDate=motiDate.replace(":","-");//替换掉时间中的:
		motiDate=motiDate.replace(".", "`");//替换掉时间中的.
		
		String writePath = Parameters.AbsolutePath_wave+Parameters.panfuName+" "+motiDate+".csv";
		//set new motivation position.
		int[] lineInNewData = new int[sen.length];
		for(int i=0;i<sen.length;i++) {
			lineInNewData[i]=sen[i].getlineSeriesNew();
		}
		
		//get the motidata of each motivation sensor, but it is not align in time.
		Vector<String> [] MOTIDATA = new Vector[sen.length];
		for(int i=0;i<sen.length;i++) {
			MOTIDATA[i] = new Vector<String>();
		}
		for(int i=0;i<sen.length;i++) {
			MOTIDATA[i].addAll(sen[i].getCutVectorData());
		}
		
		if(Parameters.offline==false) {
			
//			if(motiNum>3){
				WriteMotiData.writeToCSV(MOTIDATA, writePath, lineInNewData, sen);
				for(int i=0;i<sen.length;i++)
					sen[i].setFilename_S(writePath);//文件名
//			}
			if(motiNum==3){
				WriteMotiData.writeToCSV(MOTIDATA, writePath, lineInNewData, sen);
				for(int i=0;i<sen.length;i++)
					sen[i].setFilename_S(writePath);//文件名
			}
			
		}
		else {
			
//			if (motiNum == 3) {
				WriteMotiData.writeToCSV(MOTIDATA, writePath, lineInNewData, sen);
				for(int i=0;i<sen.length;i++)
					sen[i].setFilename_S(writePath);//文件名
//			}
			if (motiNum > 3) {
				WriteMotiData.writeToCSV(MOTIDATA, writePath, lineInNewData, sen);
				for(int i=0;i<sen.length;i++)
					sen[i].setFilename_S(writePath);//文件名
			}
		}
	}
	
	@SuppressWarnings("unused")
	public static void saveAllMotivationSensors(int countNumber, Sensor [] sensors) throws ParseException, IOException {
			//the name of csv file is named by the first data's date in seconds.
			writeToDisk.writeToCSV(sensors, countNumber, sensors[0].getAbsoluteTime());
	}
	
}
