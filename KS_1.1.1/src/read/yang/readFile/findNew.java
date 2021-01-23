package read.yang.readFile;

import java.io.File;

/**
 * find the hfmed file containing "Test".
 * @author Hanlin Zhang.
 */
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import com.h2.constant.Parameters;

import controller.ADMINISTRATOR;
public class findNew {
	public static String[] nameF = new String[Parameters.SensorNum];
	@SuppressWarnings("unused")
	public static File find(String path,int th,ADMINISTRATOR manager) {
		
		int l=0;
		boolean flag=false;
		int count=1;
		File file = new File(path);
		File[] fs = file.listFiles();
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		for(int j=0;j<fs.length;j++) {
			Arrays.sort(fs, new CompratorByLastModified());
			
			for (int i = 0; i < fs.length; i++) {
				if(fs[i].getPath().length()>8) {
					if(fs[i].isDirectory()&&fs[i].getPath().substring(3, 8).compareTo("Test_")==0) {
						file = new File(fs[i].getAbsolutePath());
						fs = file.listFiles();
						//只提取今天的文件
	//					fs=cut(df,fs);
						break;
					}
					if(Parameters.readSecond==true){
						if(fs[i].getPath().endsWith(".bin")){
							if(count==2) {
								manager.isMrMa[th]=true;
								l=i;
								flag=true;
								break;
							}
							count++;
						}
						if(fs[i].getPath().endsWith(".HFMED")){
							if(count==2){
	//							manager.isMrLiu[th]=true;
								l=i;
								flag=true;
								break;
							}
							count++;
						}
					}
					else{
						if(fs[i].getPath().endsWith(".bin")){
							manager.isMrMa[th]=true;
							l=i;
							flag=true;
							break;
						}
						if(fs[i].getPath().endsWith(".HFMED")){
	//						manager.isMrLiu[th]=true;
							l=i;
							flag=true;
							break;
						}
					}
					//System.out.println(fs[i].getName()+" "+new Date(fs[i].lastModified()).toLocaleString());
				}
			}
			if(flag==true)
				break;
		}
//		System.out.println(fs[l].getAbsolutePath()+"_绝对路径");
		nameF[th] = fs[l].getName();
		return fs[l];
	}
	@SuppressWarnings("null")
	public static File[] cut(SimpleDateFormat df,File[] fs) {
		
		String SystemDate = df.format(new Date());//获取系统时间
		int[] l=new int[fs.length];
		File[] fs0=fs;
		File[] fs1=null;
		int n=0;
		System.out.println("处理前fs的长度"+fs.length);
		for(int i=0;i<fs0.length;i++) {
			String dateTime=df.format(new Date(fs0[i].lastModified()));
			if(dateTime.substring(5, 10).compareTo(SystemDate.substring(5, 10))==0){
				l[n]=i;n++;
			}
		}
		n=0;
		for(int i=0;i<l.length;i++) {
			if(l[i]!=0) {
				fs1[n]=fs0[l[i]];
				n++;
			}
		}
		System.out.println("处理后fs的长度"+fs1.length);
		return fs1;
	}
	static class CompratorByLastModified implements Comparator<File> {
		@Override
        public int compare(File lFile, File rFile) {
            boolean lInValid = (lFile == null || !lFile.exists());
            boolean rInValid = (rFile == null || !rFile.exists());
            boolean bothInValid = lInValid && rInValid;
            if (bothInValid) {
                return 0;
            }

            if (lInValid) {
                return -1;
            }

            if (rInValid) {
                return 1;
            }

            long lModified = lFile.lastModified();
            long rModified = rFile.lastModified();
            long diff = lModified - rModified;
            if (diff > 0)
				return -1;//倒序正序控制
			else if (diff == 0)
				return 0;
			else
				return 1;//倒序正序控制
        }
		public boolean equals(Object obj) {
			return true;
		}
	}
}
