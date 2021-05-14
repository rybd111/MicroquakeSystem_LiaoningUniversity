/**
 * 
 */
package com.h2.constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import Entrance.MainTest;
import mutiThread.MainThread;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

/**
 * config the constant parameters of procedure from the beginning.
 * @author Hanlin Zhang
 */
public class ConfigToParameters {

	public String filePath = "";
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @throws IOException
	 * @throws NumberFormatException 
	 * @author Hanlin Zhang
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		ConfigToParameters c = new ConfigToParameters();
	}
	
	public ConfigToParameters() throws NumberFormatException, IOException {
		String j = System.getProperty("user.dir");//get the procedure absolute path.
		this.filePath = j+"/resource/Config.ini";//get the config file.
		System.out.println(filePath);
		Load(this.filePath);//get the variables in config file.
	}
	
	public ConfigToParameters(String path) throws NumberFormatException, IOException {
		this.filePath = path;
		System.out.println(filePath);
		Load(this.filePath);// get the variables in config file.
	}
	
	/**
	 * read the csv file's specific contents in coal mine files.
	 * we need to know csv file must have different format, so this function only adapt for our current csv file.
	 * @return TimeSeries in jfreeChart type.
	 * @author Hanlin Zhang.
	 * @throws IOException
	 * @throws NumberFormatException 
	 */
	private void Load(String Path) throws NumberFormatException, IOException {
		File file = new File(Path);
		
		//(文件完整路径),编码格式
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));//GBK
        
        String line = null;
        while((line=reader.readLine())!=null){//when the procedure read the last line in csv file, the length of it will become 1.
            if(line.length()>1) { //this line has content.
	            if(!line.substring(0, 1).equals("#")) {//this line is an annotation.
	            	String item[] = line.split("=");
	            	//输出item长度。
//	            	System.out.println(item.length);
	            	//save different data from config file.
	            	item[0] = item[0].replaceAll(" ", "");
	            	item[1] = item[1].replaceAll(" ", "");
		            //save new value to Parameters.
		            /**distanceToSquareWave*/
	            	if(item[0].equals("FREQUENCY")) {
		            	Parameters.FREQUENCY = Integer.parseInt(item[1]);
		            }
	            	if(item[0].equals("distanceToSquareWave")) {
		            	Parameters.distanceToSquareWave = Double.parseDouble(item[1]);
		            }
		            /**ShortCompareLong*/
		            if(item[0].equals("ShortCompareLong")) {
		            	Parameters.ShortCompareLong = Double.parseDouble(item[1]);
		            }
		            /**ShortCompareLongAdjust*/
		            if(item[0].equals("ShortCompareLongAdjust")) {
		            	Parameters.ShortCompareLongAdjust = Double.parseDouble(item[1]);
		            }
		            /**afterRange_ThresholdMin*/
		            if(item[0].equals("afterRange_ThresholdMin")) {
		            	Parameters.afterRange_ThresholdMin = Double.parseDouble(item[1]);
		            }
		            /**afterRange_ThresholdMax*/
		            if(item[0].equals("afterRange_ThresholdMax")) {
		            	Parameters.afterRange_ThresholdMax = Double.parseDouble(item[1]);
		            }
		            /**refineRange_ThresholdMin*/
		            if(item[0].equals("refineRange_ThresholdMin")) {
		            	Parameters.refineRange_ThresholdMin = Double.parseDouble(item[1]);
		            }
		            /**refineRange_ThresholdMax*/
		            if(item[0].equals("refineRange_ThresholdMax")) {
		            	Parameters.refineRange_ThresholdMax = Double.parseDouble(item[1]);
		            }
		            /**IntervalToOtherSensors*/
		            if(item[0].equals("IntervalToOtherSensors")) {
		            	Parameters.IntervalToOtherSensors = Integer.parseInt(item[1]);
		            }
		            /**SSIntervalToOtherSensors*/
		            if(item[0].equals("SSIntervalToOtherSensors")) {
		            	if(item[1].equals("true"))
		            		Parameters.SSIntervalToOtherSensors = true;
		            	if(item[1].equals("false"))
		            		Parameters.SSIntervalToOtherSensors = false;
		            }
		            /**INTERVAL*/
		            if(item[0].equals("INTERVAL")) {
		            	Parameters.INTERVAL = Integer.parseInt(item[1]);
		            }
		            /**C*/
		            if(item[0].equals("C")) {
		            	Parameters.C = Integer.parseInt(item[1]);
		            }
		            /**SensorNum*/
		            if(item[0].equals("SensorNum")) {
		            	Parameters.SensorNum = Integer.parseInt(item[1]);
		            }
		            int i=1;
	            	/**fileStr*/
		            if(item[0].equals("fileStr")) {
		            	MainThread.fileStr[0] = item[1];
		            	
		            	while((line=reader.readLine())!=null) {
		            		item = line.split("=");
		            		if(!item[0].equals("")) {
				            	item[0] = item[0].replaceAll(" ", "");
				            	item[1] = item[1].replaceAll(" ", "");
				            	try {
					            	if(item[0].equals("fileStr") && i<Parameters.SensorNum) {
						            	MainThread.fileStr[i] = item[1];
						            	i++;
					            	}
				            	}
				            	catch(Exception e){
				            		System.out.println("主路径个数超限错误！");
				            	}
		            		}
		            		else
		            			break;
			            }
		            }
		            if(item[0].equals("WenJianTou")) {
		            	Parameters.WenJianTou = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("ShuJuTou1")) {
		            	Parameters.ShuJuTou1 = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("ShuJu")) {
		            	Parameters.ShuJu = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("Shi")) {
		            	Parameters.Shi = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("Yizhen")) {
		            	Parameters.Yizhen = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("YIMiao")) {
		            	Parameters.YIMiao = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("San")) {
		            	Parameters.San = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("YIMiao")) {
		            	Parameters.YIMiao = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("startTime")) {
		            	Parameters.startTime = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("endTime ")) {
		            	Parameters.endTime  = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("TongDaoDiagnose")) {
		            	Parameters.TongDaoDiagnose = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("motivationDiagnose")) {
		            	Parameters.motivationDiagnose = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("isStorageDatabase ")) {
		            	Parameters.isStorageDatabase = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("isStorageAllMotivationCSV")) {
		            	Parameters.isStorageAllMotivationCSV = Integer.parseInt(item[1]);
		            }
		            if(item[0].equals("isStorageEventRecord")) {
		            	Parameters.isStorageEventRecord = Integer.parseInt(item[1]);
		            }
		            
		            if(item[0].equals("AbsolutePath_CSV3")) {
		            	Parameters.AbsolutePath_CSV3 = item[1];
		            }
		            if(item[0].equals("AbsolutePath_CSV5")) {
		            	Parameters.AbsolutePath_CSV5 = item[1];
		            }
		            if(item[0].equals("AbsolutePath5_record")) {
		            	Parameters.AbsolutePath5_record = item[1];
		            }
		            if(item[0].equals("AbsolutePath_allMotiTime_record")) {
		            	Parameters.AbsolutePath_allMotiTime_record = item[1];
		            }
		            
		            if(item[0].equals("DatabaseName5")) {
		            	Parameters.DatabaseName5 = item[1];
		            }
		            /**Adjust*/
		            if(item[0].equals("Adjust")) {
		            	if(item[1].equals("true"))
		            		Parameters.Adjust = true;
		            	if(item[1].equals("false"))
		            		Parameters.Adjust = false;
		            }
		            if(item[0].equals("MinusAFixedOnMagtitude")) {
		            	if(item[1].equals("true"))
		            		Parameters.MinusAFixedOnMagtitude = true;
		            	if(item[1].equals("false"))
		            		Parameters.MinusAFixedOnMagtitude = false;
		            }
		            if(item[0].equals("readSecond")) {
		            	if(item[1].equals("true"))
		            		Parameters.readSecond = true;
		            	if(item[1].equals("false"))
		            		Parameters.readSecond = false;
		            }
		            if(item[0].equals("MinusAFixedValue")) {
		            	Parameters.MinusAFixedValue = Double.parseDouble(item[1]);
		            }
		            if(item[0].equals("offline")) {
		            	if(item[1].equals("true"))
		            		Parameters.offline = true;
		            	if(item[1].equals("false"))
		            		Parameters.offline = false;
		            }
		            if(item[0].equals("StartTimeStr")) {
		            	Parameters.StartTimeStr = item[1];
		            }
		            if(item[0].equals("region")) {
		            	Parameters.region = item[1];
		            }
		            if(item[0].equals("runningModel")) {
		            	MainTest.runningModel = Integer.valueOf(item[1]);
		            }
		            if(item[0].equals("prePath")) {
		            	MainTest.prePath = item[1];
		            }
		            
	            }
            }
        }
//        System.out.println(Parameters.distanceToSquareWave);
//        System.out.println(Parameters.ShortCompareLong);
//        System.out.println(Parameters.ShortCompareLongAdjust);
//        System.out.println(Parameters.afterRange_Threshold456);
//        System.out.println(Parameters.refineRange_Threshold456);
//        System.out.println(Parameters.IntervalToOtherSensors);
//        System.out.println(Parameters.SSIntervalToOtherSensors);
//        System.out.println(Parameters.INTERVAL);
//        System.out.println(Parameters.C);
//        System.out.println(Parameters.SensorNum);
//        for(int i=0;i<MainThread.fileStr.length;i++) {
//        	System.out.println(MainThread.fileStr[i]);
//        }
//     	System.out.println(Parameters.WenJianTou);
//    	System.out.println(Parameters.ShuJuTou1);
//    	System.out.println(Parameters.ShuJu);
//    	System.out.println(Parameters.Shi);
//    	System.out.println(Parameters.Yizhen);
//    	System.out.println(Parameters.YIMiaoG);
//    	System.out.println(Parameters.YIMiao);
//    	System.out.println(Parameters.San);
//    	System.out.println(Parameters.YIMiao);
//    	System.out.println(Parameters.startTime);
//    	System.out.println(Parameters.endTime);
//    	System.out.println(Parameters.TongDaoDiagnose);
//    	System.out.println(Parameters.motivationDiagnose);
//    	for(int i=0;i<Parameters.diskName.length;i++) {
//    		System.out.print(Parameters.diskName[i]);
//    	}
//    	System.out.println();
//    	System.out.println(Parameters.isStorageOneMinuteData);
//    	System.out.println(Parameters.isStorageDatabase);
//    	System.out.println(Parameters.isStorageEachMotivationCSV);
//    	System.out.println(Parameters.isStorageAllMotivationCSV);
//    	System.out.println(Parameters.isStorageEventRecord);
//    	System.out.println(Parameters.AbsolutePath5_record);
//    	System.out.println(Parameters.AbsolutePath_CSV3);
//    	System.out.println(Parameters.AbsolutePath_CSV5);
//    	System.out.println(Parameters.AbsolutePath_allMotiTime_record);
//    	System.out.println(Parameters.AbsolutePath_recordsOfOneSensor);
//    	System.out.println(Parameters.AbsolutePath_wave);
//    	System.out.println(Parameters.Adjust);
//    	System.out.println(Parameters.MinusAFixedOnMagtitude);
//    	System.out.println(Parameters.readSecond);
//    	System.out.println(Parameters.MinusAFixedValue);
//    	System.out.println(Parameters.plusSingle_coefficient);
//    	System.out.println(Parameters.plusDouble_coefficient_45);
//    	System.out.println(Parameters.plusDouble_coefficient_12);
//    	System.out.println(Parameters.offline);
	}
}
