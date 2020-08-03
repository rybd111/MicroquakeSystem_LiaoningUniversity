package com.h2.backupData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.h2.constant.Parameters;
import com.h2.constant.Sensor;
import com.h2.tool.getRelativeMotiPOS;

import cn.hutool.core.text.csv.CsvWriter;
import mutiThread.MainThread;
import utils.one_dim_array_max_min;

public class WriteMotiData {
	/**
	 * Write the one minute long data in a txt file respectively.
	 * @param motidata
	 * @param filepath
	 * @param Pos
	 * @author Hanlin Zhang
	 */
	public static void writemotidata(Vector<String> motidata,String filepath,int[] Pos) {
        
		File file = new File(filepath);
        BufferedWriter out = null;
        Vector<String> motidata1 = new Vector<String>();
        int count=1;
        motidata1 = motidata;
    	
        try {
        	out = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e1) {e1.printStackTrace();}
        try {
        	
        
        	if(!file.exists())file.createNewFile(); 
           
            for(int i =0;i<motidata1.size();i++) {
            	if(Pos[count]==i && count<1000){
		            out.write(motidata1.get(i)+" "+"1"+"\r\n");
		            out.flush();
		            count++;
            	}else{
            		out.write(motidata1.get(i)+" "+"0"+"\r\n");
		            out.flush();
            	}
            }
            
        } catch (IOException e) {e.printStackTrace();}
        finally{
            if(out != null){
                try {
                    out.close();
                    //file.delete();
                } catch (IOException e) {e.printStackTrace();}
            }
        }
	}
	public static void deleteWritemotidata(String filepath) {
		System.out.println(filepath);
		File file = new File(filepath);
		if(file.exists())  file.delete();
	}
	public static void writemotiDate(String motiDate,String filepath) {
		File file=new File(filepath);
		
		BufferedWriter out = null;
		try {
        	out = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e1) {e1.printStackTrace();}
        try {
        	
        	if(!file.exists())file.createNewFile(); 
            
            out.write(motiDate+"\r\n");
            out.flush();
           
        } catch (IOException e) {e.printStackTrace();}
        finally{
            if(out != null){
                try {
                    out.close();
                   
                } catch (IOException e) {e.printStackTrace();}
            }
        }
	}
	/**
	 * 将时间列转换为空格分隔的形式，便于后续用matlab进行处理
	 * @param motidata 激发数据，长度在前文中进行了设置
	 * @param filepath 文件存储的绝对路径，通过parameters文件进行设置
	 * @param line 
	 * @param Pos 
	 * @throws ParseException
	 * @author Hanlin Zhang
	 */
	public static void writemotiData(Vector<String> motidata,String filePath, int line) throws ParseException {
		File file=new File(filePath);
		BufferedWriter out = null;
		
		try {
        	out = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e1) {e1.printStackTrace();}
        try {
        	//System.out.println(motidata1.size());
        	if(!file.exists())file.createNewFile(); 
            	///System.out.println(motidata1.get(i));
        	for(int i =0;i<motidata.size();i++) {
        		
        		DateFormat format1 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        		Date startDate = format1.parse(motidata.get(i).split(" ")[6]);
        		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd HHmmss");
        		Calendar calendar = Calendar.getInstance(); //内存溢出的出错位置。~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
	   			calendar.setTime(startDate);
	   			
	   			Date startDate1 = calendar.getTime();
	   			String date1 = format2.format(startDate1);
	   			//存储激发位置为开始时间长度处
        		out.write(motidata.get(i).split(" ")[0]+" "+motidata.get(i).split(" ")[1]+" "+motidata.get(i).split(" ")[2]+" "+motidata.get(i).split(" ")[3]+" "+motidata.get(i).split(" ")[4]+" "+motidata.get(i).split(" ")[5]+" "+date1+" "+line+" "+"\r\n");
        		out.flush();
        	}
		} catch (IOException e) {e.printStackTrace();}
		finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {e.printStackTrace();}
			}
		}
	}
	
	/**
	 * Write the morivation data to a csv file.
	 * @param totalMotiData the data we will write to the disk.
	 * @param filePath the absolute path on our disk.
	 * @param line the motivation position in motivation data.
	 * @param sen used to pick up the number of the sensor or we cannot know which sensor is motivated.
	 * @throws ParseException
	 * @throws IOException
	 * @author Hanlin Zhang.
	 */
    @SuppressWarnings("unused")
	public static void writeToCSV(Vector<String>[] totalMotiData, String filePath, int[] line, Sensor[] s1) throws ParseException, IOException {
    	File file=new File(filePath);
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
        	if(!file.exists())file.createNewFile();
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
}
	
