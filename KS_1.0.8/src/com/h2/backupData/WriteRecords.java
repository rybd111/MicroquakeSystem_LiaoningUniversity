package com.h2.backupData;

import com.h2.constant.Parameters;
import com.h2.constant.Sensor;
import mutiThread.MainThread;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;



/**
 * @revision 2019-12-21 
 * @Description:record the P arrival time of each senosr and the consequence the procedure calculated.
 * @Auther: RQMA, Hanlin Zhang.
 * @Date: 5/10/2019 12:28 PM
 */
public class WriteRecords {
	
	public static String lastDate = "2019-1-13 02:05:03";//update at the creating ReadData function's object to make sure this variable is the last day all the way.

    /**
     * write the info. of the motivation of all sensors to a csv file.
     * @param sensors
     * @param result
     * @param filepath
     * @author RQMa, Hanlin Zhang
     */
    @SuppressWarnings("unused")
	public static void Write(Sensor[]sensors , Sensor sensor_latest, Sensor result, String filepath, 
			String quakeGrade, double finalEnergy, String kindOfCalculation, double tensor, double b_value) {
        File file = new File(filepath);
        BufferedWriter out = null;
        BufferedWriter out1 = null;
        String record="";//every line data ready to write.;
        boolean flag1 = true;
        
        try {
        	out = new BufferedWriter(new FileWriter(file,true));
        	if (!file.exists()||file.length()==0) {
        		
            	file.createNewFile();
            	out.write("最早到时,");
            	if(Parameters.offline==true) {
	            	for(int i=0;i<Parameters.diskName_offline.length;i++) {
		            	out.write(Parameters.diskName_offline[i]+",");
		            }
            		out.write("x,y,z,P波到时,震级,能量,定位算法,发震时刻,张量,b值");
            	}
            	else {
            		for(int i=0;i<Parameters.diskName.length;i++) {
            			out.write((Parameters.diskName[i].replace(":/", "")+","));
		            }
            		out.write("x,y,z,P波到时,震级,能量,定位算法,发震时刻,张量,b值");
            	}
            	out.write("\r");
        	}
        	
            int lastk=0;
            record = sensor_latest.getAbsoluteTime()+"\t,";
            
            if(Parameters.offline==true) {
	            for (int i = 0; i < Parameters.diskName_offline.length; i++) {
	            	for(int j=0;j<sensors.length;j++) {
	            		String i1=MainThread.fileParentPackage[sensors[j].getSensorNum()].replace("Test", "");
	            		if(i1.equals(Parameters.diskName_offline[i])) {
	            			if(flag1==true) {
		            			for(int k=0;k<i;k++) {
		            				record = record+",";
		            				lastk=k+1;
		            			}
		            			flag1=false;
	            			}
	            			else {
	            				if(lastk+1<i)
		            				for(int k=lastk+1;k<i;k++) {
			            				record = record+",";
			            				lastk=k+1;
			            			}
	            				else
	            					lastk++;
	            			}
	            			record = record+String.valueOf(sensors[j].getSecTime())+",";
	            		}
	            	}
	            }
	            //count the number of commas.
	            if(record.split(",").length<=(Parameters.diskName_offline.length+1)) {
	            	for(int i=record.split(",").length;i<Parameters.diskName_offline.length+1;i++) {
	            		record = record+",";
	            	}
	            }
            }
            else {
            	for (int i = 0; i < Parameters.diskName.length; i++) {
	            	for(int j=0;j<sensors.length;j++) {
	            		
	            		if(MainThread.fileStr[sensors[j].getSensorNum()].equals(Parameters.diskName[i])) {
	            			if(flag1==true) {
		            			for(int k=0;k<i;k++) {
		            				record = record+",";
		            				lastk=k+1;
		            			}
		            			flag1=false;
	            			}
	            			else {
	            				if(lastk+1<i)
		            				for(int k=lastk+1;k<i;k++) {
			            				record = record+",";
			            				lastk=k+1;
			            			}
		            			else
		            				lastk++;
	            			}
	            			record = record+String.valueOf(sensors[j].getSecTime())+",";
	            		}
	            	}
	            }
            	//count the number of commas.
	            if(record.split(",").length<=(Parameters.diskName.length+1)) {
	            	for(int i=record.split(",").length;i<Parameters.diskName.length+1;i++) {
	            		record = record+",";
	            	}
	            }
            }
            record = record + String.valueOf(result.getLatitude())+","+String.valueOf(result.getLongtitude())+","+String.valueOf(result.getAltitude())+","
            		+String.valueOf(result.getSecTime())+","+String.valueOf(quakeGrade)+","
            		+String.valueOf(finalEnergy)+","+kindOfCalculation+","+String.valueOf(result.getquackTime())+","
            		+String.valueOf(tensor)+","+String.valueOf(b_value)+",";
            out.write(record+"\r");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * Write all motivation sensors to a txt file.
     * @param sensors all sensors initialize at beginning. 
     * @param sensor_latest the standard sensor.
     * @param filepath the absolute path write on our disk.
     * @author RQMa, Hanlin Zhang.
     */
    @SuppressWarnings("unused")
	public static void WriteSeveralMotiTime(Sensor[]sensors, String filepath) {
        File file = new File(filepath);
        BufferedWriter out = null;
        String record="";
        boolean flag1 = true;
        try {
            out = new BufferedWriter(new FileWriter(file, true));
            if (!file.exists()||file.length()==0) {
        		
            	file.createNewFile();
            	//write the head of the excel.
            	out.write("最早到时,");
            	if(Parameters.offline==true) {
	            	for(int i=0;i<Parameters.diskName_offline.length;i++) {
		            	out.write(Parameters.diskName_offline[i]+",");
		            }
//	            	out.write("发震时刻,");
            	}
            	else {
            		for(int i=0;i<Parameters.diskName.length;i++) {
		            	out.write((Parameters.diskName[i].replace(":/", "")+","));
		            }
//            		out.write("发震时刻,");
            	}
            	out.write("\r\n");
        	}
        	//record the quack time of each event.
            int lastk=0;
            record = sensors[0].getAbsoluteTime()+"\t,";

            if(Parameters.offline==true) {
	            for (int i = 0; i < Parameters.diskName_offline.length; i++) {
	            	for(int j=0;j<sensors.length;j++) {
	            		String i1=MainThread.fileParentPackage[sensors[j].getSensorNum()].replace("Test", "");
	            		if(i1.equals(Parameters.diskName_offline[i]) && sensors[j].isSign()) {
	            			if(flag1==true) {
		            			for(int k=0;k<i;k++) {
		            				record = record+",";
		            				lastk=k+1;
		            			}
		            			flag1=false;
	            			}
	            			else {
	            				if(lastk+1<i)
		            				for(int k=lastk+1;k<i;k++) {
			            				record = record+",";
			            				lastk=k+1;
			            			}
	            				else
	            					lastk++;
	            			}
	            			record = record+String.valueOf(sensors[j].getSecTime())+",";
	            		}
	            	}
	            }
	            //count the number of commas.
	            if(record.split(",").length<=(Parameters.diskName_offline.length+1)) {
	            	for(int i=record.split(",").length;i<Parameters.diskName_offline.length+1;i++) {
	            		record = record+",";
	            	}
	            }
            }
            else {
            	for (int i = 0; i < Parameters.diskName.length; i++) {
	            	for(int j=0;j<sensors.length;j++) {
	            		if(MainThread.fileStr[sensors[j].getSensorNum()].equals(Parameters.diskName[i])&&sensors[j].isSign()) {
	            			if(flag1==true) {
		            			for(int k=0;k<i;k++) {
		            				record = record+",";
		            				lastk=k+1;
		            			}
		            			flag1=false;
	            			}
	            			else {
	            				if(lastk+1<i)
		            				for(int k=lastk+1;k<i;k++) {
			            				record = record+",";
			            				lastk=k+1;
			            			}
	            				else
	            					lastk++;
	            			}
	            			record = record+String.valueOf(sensors[j].getSecTime())+",";
	            		}
	            	}
	            }
            	//count the number of commas.
	            if(record.split(",").length<=(Parameters.diskName.length+1)) {
	            	for(int i=record.split(",").length;i<Parameters.diskName.length+1;i++) {
	            		record = record+",";
	            	}
	            }
            }
            out.write(record+"\r\n");
            out.flush();
       } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * insert a null line in xlxs file.
     * @param filepath
     */
    public static void insertALine(String filepath) {
    	File file = new File(filepath);
        BufferedWriter out = null;
        String record="";
        boolean flag1 = true;
        try {
            out = new BufferedWriter(new FileWriter(file, true));
            
        	//insert a blank line in excel for distinguishing every event.
        	out.write("\n\r");
        	out.flush();
        	
    	}catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
