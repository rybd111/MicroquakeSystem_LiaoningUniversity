//package com.yang.readFile;
//
//import java.io.File;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeMap;
//
//public class FindNewFile {
//	public static String[] nameF = new String[5];
//	public static File researchFile(String path, int th) {
//		//查找最新.HFMED文件
//		File  filePath = null;
//		File file = new File(path);
//		Map<Long,File> fileMap = new TreeMap<Long, File>();
//		File[] files = file.listFiles();//文件夹下的所有文件或文件夹
//		if(files==null)
//			return null;
//		else {
//			for (File file2 : files) {
//				if(file2.isDirectory()){
//					long time=file2.lastModified(); //得到文件最后修改日期
//					file2.length();//更新文件夹的大小属性
//					fileMap.put(time, file2);//放进map中	
//				}
//				else{
//					if(file2.getPath().endsWith(".HFMED")){//后缀为HFMED
//					//if(file2.getPath().endsWith(".txt")){//后缀为HFMED
//						long time=file2.lastModified(); //得到以HFMED为后缀的文件的修改日期
//						//System.out.println(time);
//						fileMap.put(time, file2);
//					}
//				}
//			}
//			
//			filePath =fileMap.get(((TreeMap<Long, File>) fileMap).lastKey());
//
//			if(filePath.isDirectory()){
//				return researchFile(filePath.getPath(),th);
//			}
//			else{
//				filePath=fileMap.get(((TreeMap<Long, File>) fileMap).lowerKey(((TreeMap<Long, File>) fileMap).lastKey()));
//			}
//			
////			Set<Long> set = fileMap.keySet();
////			Iterator<Long> it = set.iterator();
////			int num = fileMap.size();//	
////			
////			while(it.hasNext()){
////				Long key = (Long) it.next();
////				if(num == 1){
////					filePath = fileMap.get(key);
////					if(filePath.isDirectory()){
////						return researchFile(filePath.getPath(),th);
////					}
////				}
////				num --;
////			}
//		}
//		FindNewFile.nameF[th] = filePath.getName();
//		//if(th==4)
//		//System.out.println("输出各台站最新HFMED文件的文件名："+FindNewFile.nameF[th]);
//		return filePath;
//	}
//}


package read.yang.readFile;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class FindNewFile {
	
	public static File researchFile(String path, int th) {
		//��������.HFMED�ļ�
		File  filePath = null;
		File file = new File(path);
		Map<Long,File> fileMap = new TreeMap<Long, File>();
		File[] files = file.listFiles();//�ļ����µ������ļ����ļ���
		if(files==null)
			return null;
		else {
			for (File file2 : files) {
//				System.out.println(file2.getPath());
//				System.out.println(file2.isDirectory());
//				System.out.println(file2.getPath().substring(3, 7));
				if(file2.isDirectory()&&file2.getPath().substring(3, 7).compareTo("Test")==0){
					long time=file2.lastModified(); //�õ��ļ�����޸�����
					file2.length();//�����ļ��еĴ�С����
					fileMap.put(time, file2);//�Ž�map��	
				}
				else{
					if(file2.getPath().endsWith(".HFMED")){//��׺ΪHFMED
					//if(file2.getPath().endsWith(".txt")){//��׺ΪHFMED
						long time=file2.lastModified(); //�õ���HFMEDΪ��׺���ļ����޸�����
						//System.out.println(time);
						fileMap.put(time, file2);
					}
				}
			}
			Set<Long> set = fileMap.keySet();
			Iterator<Long> it = set.iterator();
			int num = fileMap.size();//	
			
			while(it.hasNext()){
				Long key = (Long) it.next();
				if(num == 1){
					filePath = fileMap.get(key);
					if(filePath.isDirectory()){
						return researchFile(filePath.getPath(),th);
					}
				}
				num --;
			}
		}
		
		//if(th==4)
		//System.out.println("�����̨վ����HFMED�ļ����ļ�����"+FindNewFile.nameF[th]);
		return filePath;
	}
}