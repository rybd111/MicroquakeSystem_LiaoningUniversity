package com.yhy.getinformation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is designed to get the corresponding station information.
 * @author Haiyou Yu
 * @version 1.0 2020-11-14
 */

public class GetStationInformation {

	private static final String R = "R:";
	private static final String S = "S:";
	private static final String T = "T:";
	private static final String U = "U:";
	private static final String V = "V:";
	private static final String W = "W:";
	private static final String X = "X:";
	private static final String Y = "Y:";
	private static final String Z = "Z:";
	
	private static final String RNAME = "大棚";
	private static final String SNAME = "蒿子屯";
	private static final String TNAME = "杨甸子";
	private static final String UNAME = "树碑子";
	private static final String VNAME = "南风井";
	private static final String WNAME = "北青堆子";
	private static final String XNAME = "矿上车队";
	private static final String YNAME = "火药库";
	private static final String ZNAME = "工业广场";
	
	private static final double HONGYANG_SENSORINFO[][] = {
			{ 41517290.0374,4599537.3261,24.5649}, //R
		    { 41516836.655,4596627.472,21.545  },  //S
		    { 41518099.807,4595388.504,22.776  },  //T
		    { 41518060.298,4594304.927,21.926  },  //U
		    { 41516707.440,4593163.619,22.564  },  //V
		    { 41520207.356,4597983.404,22.661  },  //W
		    { 41520815.875,4597384.576,25.468  },  //X
		    { 41519926.476,4597275.978,20.705  },  //Y
		    { 41519304.125,4595913.485,23.921  }   //Z
		};
	
	private static Map<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
	private static ArrayList<String> panfuList;
	
	
	/**
	 * 
	 * @param date ,a Date
	 * @return all station information
	 */
	public static ArrayList<TableProperty> getAllStationsInformation(Date date) {
		initStationInformation();
		
		ArrayList<TableProperty> all = new ArrayList<TableProperty>();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String day = sf.format(date);
		//System.out.println("day:"+day);
		Set<String> setKey = map.keySet();
		for(String key:setKey) {
			TableProperty tp = new TableProperty();
			tp.setDay(day);
			tp.setPanfu(key);
			List<String> lists = map.get(key);
			//System.out.println(key+lists.toString());
			tp.setLocation(lists.get(0));
			tp.setxData(lists.get(1));
			tp.setyData(lists.get(2));
			tp.setzData(lists.get(3));
			
			ArrayList<String> information = null;
			try {
				information = getNetSpeedAndDiskInformation(key);
				//System.out.println("information:"+information.toString()+" size:"+information.size());
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//Why use information.get(0)==null? Because information is a ArrayList and it contains a NULL value.
				if(information.get(0)==null) {   //if information is null,then set its status to offline
					tp.setStatus("offline");
					tp.setNetspeed(null);
					tp.setUnused(null);
					tp.setUsed(null);
					tp.setTotal(null);
				}    
				else if(information.size()>1&&information.size()<=4){ //if information is not null,it should contains netspeed、unused、used、total
					tp.setStatus("online");
					tp.setNetspeed(information.get(0));
					tp.setUnused(information.get(1));
					tp.setUsed(information.get(2));
					tp.setTotal(information.get(3));
				}
				//System.out.println(tp.toString());
				all.add(tp);
			}
			
		}
		return all;
	}
	
	
	//----------------------------------------------------------------
	//This is the test code.
	public static void main(String[] args) {
		ArrayList<TableProperty> all = getAllStationsInformation(new Date());
		
		for(TableProperty t:all) {
			System.out.println(t.toString());
		}
		
//		Iterator<String> iter = map.keySet().iterator();
//		while (iter.hasNext()) {
//		    String key = iter.next();
//		    ArrayList<String> value = map.get(key);
//		    for(String v:value) {
//		    	System.out.println("value:"+v);
//		    }
//		}

	}
	//end test code.
	//---------------------------------------------------------------

	/**
	 * save the station's basic information to the map,including panfu,location,xData,yData,zData
	 */
	public static void initStationInformation() {
		panfuList = new ArrayList<>();
		panfuList.add(R);
		panfuList.add(S);
		panfuList.add(T);
		panfuList.add(U);
		panfuList.add(V);
		panfuList.add(W);
		panfuList.add(X);
		panfuList.add(Y);
		panfuList.add(Z);
		DecimalFormat df = new DecimalFormat("0.00000");
		for(String panfu:panfuList) {
			if(!map.containsKey(panfu)) {
				if(panfu.equals(R)) {
					ArrayList<String> values = new ArrayList<String>();
					values.add(RNAME);
					String x = df.format(HONGYANG_SENSORINFO[0][0]);
					String y = df.format(HONGYANG_SENSORINFO[0][1]);
					String z = df.format(HONGYANG_SENSORINFO[0][2]);
					values.add(x);
					values.add(y);
					values.add(z);
					map.put(R, values);
				}
				if(panfu.equals(S)) {
					ArrayList<String> values = new ArrayList<String>();
					values.add(SNAME);
					String x = df.format(HONGYANG_SENSORINFO[1][0]);
					String y = df.format(HONGYANG_SENSORINFO[1][1]);
					String z = df.format(HONGYANG_SENSORINFO[1][2]);
					values.add(x);
					values.add(y);
					values.add(z);
					map.put(S, values);
				}
				if(panfu.equals(T)) {
					ArrayList<String> values = new ArrayList<String>();
					values.add(TNAME);
					String x = df.format(HONGYANG_SENSORINFO[2][0]);
					String y = df.format(HONGYANG_SENSORINFO[2][1]);
					String z = df.format(HONGYANG_SENSORINFO[2][2]);
					values.add(x);
					values.add(y);
					values.add(z);
					map.put(T, values);
				}
				if(panfu.equals(U)) {
					ArrayList<String> values = new ArrayList<String>();
					values.add(UNAME);
					String x = df.format(HONGYANG_SENSORINFO[3][0]);
					String y = df.format(HONGYANG_SENSORINFO[3][1]);
					String z = df.format(HONGYANG_SENSORINFO[3][2]);
					values.add(x);
					values.add(y);
					values.add(z);
					map.put(U, values);
				}
				if(panfu.equals(V)) {
					ArrayList<String> values = new ArrayList<String>();
					values.add(VNAME);
					String x = df.format(HONGYANG_SENSORINFO[4][0]);
					String y = df.format(HONGYANG_SENSORINFO[4][1]);
					String z = df.format(HONGYANG_SENSORINFO[4][2]);
					values.add(x);
					values.add(y);
					values.add(z);
					map.put(V, values);
				}
				if(panfu.equals(W)) {
					ArrayList<String> values = new ArrayList<String>();
					values.add(WNAME);
					String x = df.format(HONGYANG_SENSORINFO[5][0]);
					String y = df.format(HONGYANG_SENSORINFO[5][1]);
					String z = df.format(HONGYANG_SENSORINFO[5][2]);
					values.add(x);
					values.add(y);
					values.add(z);
					map.put(W, values);
				}
				if(panfu.equals(X)) {
					ArrayList<String> values = new ArrayList<String>();
					values.add(XNAME);
					String x = df.format(HONGYANG_SENSORINFO[6][0]);
					String y = df.format(HONGYANG_SENSORINFO[6][1]);
					String z = df.format(HONGYANG_SENSORINFO[6][2]);
					values.add(x);
					values.add(y);
					values.add(z);
					map.put(X, values);
				}
				if(panfu.equals(Y)) {
					ArrayList<String> values = new ArrayList<String>();
					values.add(YNAME);
					String x = df.format(HONGYANG_SENSORINFO[7][0]);
					String y = df.format(HONGYANG_SENSORINFO[7][1]);
					String z = df.format(HONGYANG_SENSORINFO[7][2]);
					values.add(x);
					values.add(y);
					values.add(z);
					map.put(Y, values);
				}
				if(panfu.equals(Z)) {
					ArrayList<String> values = new ArrayList<String>();
					values.add(ZNAME);
					String x = df.format(HONGYANG_SENSORINFO[8][0]);
					String y = df.format(HONGYANG_SENSORINFO[8][1]);
					String z = df.format(HONGYANG_SENSORINFO[8][2]);
					values.add(x);
					values.add(y);
					values.add(z);
					map.put(Z, values);
				}
			}
		}
	}
	
	
	/**
	 * 
	 * @param diskName
	 * @return  a single station's information including netspeed,unused,used,total
	 */
	public static ArrayList<String> getNetSpeedAndDiskInformation(String diskName) {
		ArrayList<String> information = new ArrayList<String>();
		information.add(getNetWorkSpeed(diskName));
		information.addAll(getDiskInformation(diskName));
		return information;
	}
	
	
	@SuppressWarnings("resource")
	private static boolean copyFileUsingFileChannels(File source, File dest) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		boolean flag =false;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
			flag = true;
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
		return flag;
	}


	/**
	 * 
	 * @param diskNumber
	 * @return a single station's information including netspeed
	 */
	public static String getNetWorkSpeed(String diskNumber) {
		// Source file
		//Note: I put the test file (testnetspeed.txt) in the C disk root list,
		//you should change you path when the environment is different.
		File source = new File("C:" + File.separator + "testnetspeed.txt");
		long sourceFileSize = source.length();
		// destination path
		String filePath = diskNumber + File.separator + "testnetspeed";
		File dest = new File(filePath);
		
        if(!dest.exists()) { //if file does not exist,then create a new file.
        	try {
				dest.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
        }else {                // if file exists, then clear its contents.
        	try {
                FileWriter fileWriter =new FileWriter(dest);
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
		String networkSpeed = "";
		long start = System.currentTimeMillis();
		long end;
		float netSpeed = 0;
		boolean result = false;
		try {
			result = copyFileUsingFileChannels(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(result) {
			end = System.currentTimeMillis();
			long timeDifference = end -start;
			
			//change milliseconds to seconds
			BigDecimal a = new BigDecimal(timeDifference);
			BigDecimal b = new BigDecimal(1000);
			BigDecimal seconds = a.divide(b,2, BigDecimal.ROUND_HALF_UP);
		    
		    //change file's byte size to KB
		    BigDecimal fileSize = new BigDecimal(sourceFileSize/1024);
		    
		    //change BigDecimal to float
		    netSpeed =  fileSize.divide(seconds,2, BigDecimal.ROUND_HALF_UP).floatValue();
		    
		    // if network speed greater than 1000kbps, the suffix is Mbps
		    if(netSpeed > 1000) {
			    BigDecimal temp = new BigDecimal(netSpeed);
			    netSpeed = temp.divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP).floatValue();
			    networkSpeed = String.valueOf(netSpeed)+"Mbps";
			    //System.out.println("The network speed is : " + netSpeed+"Mbps");
		    }else{
			    networkSpeed = String.valueOf(netSpeed)+"kbps";
		    	//System.out.println("The network speed is : " + netSpeed+"kbps");
		    }
		}else {
			System.out.println("something wrong ");
		}
		return networkSpeed;
	}
	

	/**
	 * 
	 * @param diskNumber
	 * @return a single station's information including disk's unused,used,total information
	 */
	public static ArrayList<String> getDiskInformation(String diskNumber) {
		File[] roots = File.listRoots();
		ArrayList<String> list = new ArrayList<String>();
		String unused = "";
		String used = "";
		String total = "";
		for (File file : roots) {
			if(file.getPath().equals(diskNumber+File.separator)) {
				try {
					unused = String.valueOf(file.getFreeSpace() / 1024 / 1024 / 1024)+"G";
					used = String.valueOf((file.getTotalSpace() - file.getFreeSpace()) / 1024 / 1024 / 1024)+"G";
					total = String.valueOf(file.getTotalSpace() / 1024 / 1024 / 1024)+"G";
					list.add(unused);
					list.add(used);
					list.add(total);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return list;
	}

}
