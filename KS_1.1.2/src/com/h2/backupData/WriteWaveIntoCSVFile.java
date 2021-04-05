package com.h2.backupData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

import com.h2.constant.Parameters;
import DataExchange.Sensor;
import utils.one_dim_array_max_min;

public class WriteWaveIntoCSVFile {
	
	/**
	 * write a array to disk.
	 * @param filePath
	 * @param interSet
	 * @author Hanlin Zhang
	 * @throws IOException
	 * @date revision 2020年10月25日
	 */
	public void write_array(String filePath, Vector<String> A) throws IOException {
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
	public void preProcess(Sensor[] s1, int motiNum, String motiDate, String panfu) throws ParseException, IOException {
		
		//the name of write file - the most early time in all motivated sensors.
		motiDate=motiDate.replace(":","-");//替换掉时间中的:
		motiDate=motiDate.replace(".", "`");//替换掉时间中的.
		
		//set new motivation position.
		int[] lineInNewData = new int[s1.length];
		for(int i=0;i<s1.length;i++) {
			lineInNewData[i]=s1[i].getlineSeriesNew();
		}
		
		//get the motidata of each motivation sensor, but it is not align in time.
		@SuppressWarnings("unchecked")
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
				writeToCSV(MOTIDATA, Parameters.AbsolutePath_CSV5+panfu+motiDate+".csv", lineInNewData, s1);
				for(int i=0;i<s1.length;i++)
					s1[i].setFilename_S(Parameters.AbsolutePath_CSV5+panfu+motiDate+".csv");//文件名
			}

			if(motiNum==3){
				//write on the local disk.
				writeToCSV(MOTIDATA, Parameters.AbsolutePath_CSV3+panfu+motiDate+".csv", lineInNewData, s1);
				for(int i=0;i<s1.length;i++)
					s1[i].setFilename_S(Parameters.AbsolutePath_CSV3+panfu+motiDate+".csv");//文件名
			}
			
		}
		else {
			
			if (motiNum == 3) {
				writeToCSV(MOTIDATA, Parameters.AbsolutePath_CSV3+panfu+motiDate+".csv", lineInNewData, s1);
				for(int i=0;i<s1.length;i++)
					s1[i].setFilename_S(Parameters.AbsolutePath_CSV3+panfu+motiDate+".csv");//文件名
			}
			if (motiNum > 3) {
				writeToCSV(MOTIDATA, Parameters.AbsolutePath_CSV5+panfu+motiDate+".csv", lineInNewData, s1);
				for(int i=0;i<s1.length;i++)
					s1[i].setFilename_S(Parameters.AbsolutePath_CSV5+panfu+motiDate+".csv");//文件名
			}
			
		}
	}
	
	/**
	 * 删除给定的文件。
	 * @param filepath
	 *   绝对路径
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午10:04:43
	 */
	public void deleteWriteMotidata(String filepath) {
		System.out.println(filepath);
		File file = new File(filepath);
		if(file.exists())  file.delete();
	}
	
	/**
	 * Write the morivation data to a csv file.
	 * 20210325更新，新生成的同名波形文件，直接覆盖旧的，以减轻每次跑程序的操作压力。
	 * @param totalMotiData the data we will write to the disk.
	 * @param filePath the absolute path on our disk.
	 * @param line the motivation position in motivation data.
	 * @param sen used to pick up the number of the sensor or we cannot know which sensor is motivated.
	 * @throws ParseException
	 * @throws IOException
	 * @author Hanlin Zhang.
	 */
    @SuppressWarnings("unused")
	public void writeToCSV(Vector<String>[] totalMotiData, String filePath, int[] line, Sensor[] s1) throws ParseException, IOException {
    	//删除后再新建一个
    	File file=new File(filePath);
    	
    	if(file.exists()) {
    		System.out.println("文件是否成功删除？"+file.delete());
    		file.createNewFile();
    	}
    	else {
    		file.createNewFile();
    	}
    	
    	BufferedWriter out = null;
		String result="";//every line data ready to write.
		
		//get the min size among all motivation data vector.
		int minsize = 0;
		int[] totalMotiSize = new int[totalMotiData.length];
		
		for(int i=0;i<totalMotiData.length;i++)
			totalMotiSize[i] = totalMotiData[i].size();
		
		minsize=one_dim_array_max_min.minint(totalMotiSize);
		
		try {
        	out = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e1) {e1.printStackTrace();}
        try {
        	if(Parameters.offline==true) {
	        	for(int j=0;j<minsize;j++) {
	        	   
	        	   out.write(totalMotiData[0].get(j).split(" ")[6]+",");
	        	   
	        	   for(int i=0;i<totalMotiData.length;i++) {
	        		   result = result
	        				   +totalMotiData[i].get(j).split(" ")[0]+","
	        						   +totalMotiData[i].get(j).split(" ")[1]+","
	        								   +totalMotiData[i].get(j).split(" ")[2]+","
	        										   +totalMotiData[i].get(j).split(" ")[3]+","
	        												   +totalMotiData[i].get(j).split(" ")[4]+","
	        														   +totalMotiData[i].get(j).split(" ")[5]+","+line[i]+","
	        														   	       +String.valueOf(s1[i].getSensorNum()+1)+",";
	            	}
	        	   	out.write(result+"\r\t");
	        	   	result="";
	           }
        	}
        	else {
        		for(int j=0;j<minsize;j++) {
 	        	   
 	        	   out.write(totalMotiData[0].get(j).split(" ")[6]+",");
 	        	   
 	        	   for(int i=0;i<totalMotiData.length;i++) {
 	        		   result = result
 	        				   +totalMotiData[i].get(j).split(" ")[0]+","
 	        						   +totalMotiData[i].get(j).split(" ")[1]+","
 	        								   +totalMotiData[i].get(j).split(" ")[2]+","
 	        										   +totalMotiData[i].get(j).split(" ")[3]+","
 	        												   +totalMotiData[i].get(j).split(" ")[4]+","
 	        														   +totalMotiData[i].get(j).split(" ")[5]+","+line[i]+","
 	        														   	       +String.valueOf(s1[i].getSensorNum()+1)+",";
 	            	}
 	        	   	out.write(result+"\r\t");
 	        	   	result="";
 	           }
        	}
        	out.flush();
		}
        catch (IOException e1) {e1.printStackTrace();}
		finally{
			if(out != null){
				out.close();
			}
		}
    }
	
	/**
	 * 
	 * @param countNumber
	 * 	激发的传感器数量
	 * @param s1
	 * 	激发传感器对象
	 * @param panfu
	 * 	激发盘符
	 * @throws ParseException
	 * @throws IOException
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午10:05:27
	 */
    @SuppressWarnings("unused")
	public void saveAllMotivationSensors(int countNumber, Sensor [] s1, String panfu) throws ParseException, IOException {
			//the name of csv file is named by the first data's date in seconds.
			preProcess(s1, countNumber, s1[0].getAbsoluteTime(), panfu+" ");
	}
	
}
