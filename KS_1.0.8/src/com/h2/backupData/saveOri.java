package com.h2.backupData;

import java.util.Vector;

import com.h2.constant.Parameters;
import com.h2.tool.stringJoin;

import mutiThread.MainThread;

/**
 * save one minute long data.
 * @author Hanlin Zhang.
 */
public class saveOri {
	public String []panfu = new String[Parameters.SensorNum];
	public String panfu1 = "";
	@SuppressWarnings("unused")
	public saveOri() {
		if(Parameters.offline==false) {
			for(int i=0;i<Parameters.SensorNum;i++) {
				panfu[i] = MainThread.fileStr[i];
				panfu1 = panfu1+panfu[i];
			}
			for(int i = 0; i<panfu.length; i++) {
				panfu[i] = panfu[i].replace(":/","");
			}
			panfu1 = panfu1.replace(":/", "");
		}
		else {
			for(int i=0;i<Parameters.SensorNum;i++) {
				panfu[i] = MainThread.fileParentPackage[i];
				panfu1 = panfu1+panfu[i];
			}
			for(int i = 0; i<panfu.length; i++) {
				panfu[i] = panfu[i].replace("Test","");
			}
			panfu1 = panfu1.replace("Test", "");
		}
	}
	/**
	 * 将容器数据写入记事本,写入60s的数据
	 * @param data 传入的容器数据
	 * @param path 存储实际盘符
	 * @param motiPos 当该行数据为激发位置时，记录为1，否则为0
	 */
	@SuppressWarnings("unused")
	public void saveOrii(Vector<String> data,String panfu,int[] motiPos,int sensorNum){
		
		String da = data.get(0).split(" ")[6];//将日期中的：替换为空格，作为文件名
		
		da=da.replace(":","");
		if(Parameters.offline==false) {
			String panfu1=panfu.replace(":/", "");
			for(int i=0;i<Parameters.diskName.length;i++)	
				if(panfu.equals(Parameters.diskName[i]) && Parameters.initPanfu[i]==0) {
					WriteMotiData.writemotidata(data,Parameters.AbsolutePathMinute+panfu1+"/"+this.panfu1+da+".txt",motiPos); 
					Parameters.initPanfu[i]=1;
				}
		}
		else {
			String parent="";
//			String panfu1 = stringJoin.SJoin_Array(MainThread.fileParentPackage);
//			String panfu1 = MainThread.fileParentPackage[sensorNum].replaceAll("Test", "");
			
			String parent1 = panfu.replace("Test", "");
			
			for(int i=0;i<Parameters.diskName_offline.length;i++) {	
				if(parent1.equals(Parameters.diskName_offline[i]) && Parameters.initPanfu[i]==0) {
//					System.out.println("写入路径为："+Parameters.AbsolutePathMinute+parent+"/");
					WriteMotiData.writemotidata(data,Parameters.AbsolutePathMinute+panfu+"/"+this.panfu1+da+".txt",motiPos); 
					Parameters.initPanfu[i]=1;
				}
			}
		}

		//清空Vector
		data.clear();		
	}
}
