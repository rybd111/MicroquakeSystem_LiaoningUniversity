package com.yang.readFile;
import java.io.*;

public class FindNewHFMED {
	//public static String[] nameF = new String[5];//保存5个台站上次的最新文件
	public static File searchN(String path, int th){
		File file = new File(path);
		String pathOri = path;
		boolean HavHF = false;
		int k = 0, j = 0, p = 0;
		File [] files= new File[100];
		files = file.listFiles();//获取当前目录下的所有文件
		long  timeMax = 0,timeMaxF = 0;
		timeMax = files[k].lastModified();
		timeMaxF = files[k].lastModified();
			
		for(int i = 0;i < 10; i++){//表示10级目录
			HavHF = false;//重置
			long [] time = new long[files.length];
			for(p = 0;p < time.length;p ++) time[p]=0;
			p=0;timeMax=0;timeMaxF=0;
			for (File f1 : files) {
				if(f1.isDirectory()){
					HavHF = true;//表示有文件夹
					time[p] = f1.lastModified();
					if(time[p]>timeMax){
						k = p;//若当前时间大于之前的最大值，则更新k，否则不更新
						timeMax = time[p];//更新max
					}
				}
				else{
					if(f1.getPath().endsWith(".HFMED")){
						time[p] = f1.lastModified();
						if(time[p]>timeMaxF){
							j = p;	//若当前时间大于之前的最大值，则更新文件最新时间j，否则不更新
							timeMaxF = time[p];
						}
					}
				}
				p++;
			}
			//files[k].length();//获取文件夹大小，为了刷新文件夹内的文件
			if(HavHF == true){//有文件夹才更新
				//System.out.println(files[k].getName());
				pathOri = pathOri +"/"+ files[k].getName()+"/";
				//System.out.println(pathOri);
				file = new File(pathOri);
				files = file.listFiles();//列出下一级目录中的所有文件
			}
			else break;
		}//取出最大文件，进入文件夹搜索
		findNew.nameF[th] = files[j].getName();//保存上次文件名
		return files[j];
	}
}
