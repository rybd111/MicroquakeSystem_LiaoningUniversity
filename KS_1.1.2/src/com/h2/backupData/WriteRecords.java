package com.h2.backupData;

import com.h2.constant.Parameters;

import DataExchange.Sensor;
import mutiThread.MainThread;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @revision 2019-12-21 
 * @Description:record the P arrival time of each senosr and the consequence the procedure calculated.
 * @Auther: RQMA, Hanlin Zhang.
 * @Date: 5/10/2019 12:28 PM
 */
public class WriteRecords{

    /**
     * It's data same as the database.
     * write the info. of the motivation of all sensors to a csv file.
     * @param sensors
     * @param result
     * @param filepath
     * @author RQMa, Hanlin Zhang
     */
    @SuppressWarnings("unused")
	public void WriteCalculationResults(
			Sensor[]sensors,
			String earlestTime,
			Sensor result,
			String filepath,
			String quakeGrade,
			double finalEnergy,
			String kindOfCalculation,
			double tensor,
			double b_value) {
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
            	for(int i=0;i<Parameters.diskName[Parameters.diskNameNum].length;i++) {
	            	out.write(Parameters.diskName[Parameters.diskNameNum][i]+",");
	            }
        		out.write("x,y,z,P波到时,震级,能量,定位算法,发震时刻,张量,b值");
            	out.write("\r");
        	}
        	
            int lastk=0;
            record = earlestTime+"\t,";
            String i1 = null;
            for (int i = 0; i < Parameters.diskName[Parameters.diskNameNum].length; i++) {
            	for(int j=0;j<sensors.length;j++) {
            		if(Parameters.offline == true) {
            			i1=MainThread.fileParentPackage[sensors[j].getSensorNum()].replace("Test", "").toLowerCase();
            		}
            		else {
            			i1 = MainThread.fileStr[sensors[j].getSensorNum()].replace(":/", "");
            		}
            		if(i1.equals(Parameters.diskName[Parameters.diskNameNum][i])) {
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
            if(record.split(",").length<=(Parameters.diskName[Parameters.diskNameNum].length+1)) {
            	for(int i=record.split(",").length;i<Parameters.diskName[Parameters.diskNameNum].length+1;i++) {
            		record = record+",";
            	}
            }
            record = record + String.valueOf(result.getx())+","+String.valueOf(result.gety())+","+String.valueOf(result.getz())+","
            		+String.valueOf(result.getSecTime())+","+String.valueOf(quakeGrade)+","
            		+String.valueOf(finalEnergy)+","+kindOfCalculation+","+String.valueOf(result.getquackTime())+"\t,"
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
	public void WriteSeveralMotiTime(Sensor[]Motisensors, String filepath) {
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
            	for(int i=0;i<Parameters.diskName[Parameters.diskNameNum].length;i++) {
	            	out.write(Parameters.diskName[Parameters.diskNameNum][i]+",");
	            }
            	out.write("\r\n");
        	}
        	//record the quack time of each event.
            int lastk=0;
            record = Motisensors[0].getAbsoluteTime()+"\t,";
            String i1 = null;
            for (int i = 0; i < Parameters.diskName[Parameters.diskNameNum].length; i++) {
            	for(int j=0;j<Motisensors.length;j++) {
            		if(Parameters.offline == true) {
            			i1=MainThread.fileParentPackage[Motisensors[j].getSensorNum()].replace("Test", "");
            		}
            		else {
            			i1 = MainThread.fileStr[Motisensors[j].getSensorNum()].replace(":/", "");
            		}
            		if(i1.equals(Parameters.diskName[Parameters.diskNameNum][i]) && Motisensors[j].isSign()) {
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
            			record = record+String.valueOf(Motisensors[j].getSecTime())+",";
            		}
            	}
            }
            //count the number of commas.
            if(record.split(",").length<=(Parameters.diskName[Parameters.diskNameNum].length+1)) {
            	for(int i=record.split(",").length;i<Parameters.diskName[Parameters.diskNameNum].length+1;i++) {
            		record = record+",";
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
     * insert a null line in csv file.
     * @param filepath
     */
    public void insertALine(String filepath) {
    	File file = new File(filepath);
        BufferedWriter out = null;
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
