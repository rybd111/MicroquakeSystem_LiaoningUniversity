package read.yang.readFile;


/**
 * find the hfmed file containing "Test".
 * @author Hanlin Zhang.
 */
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.io.File;
import com.h2.constant.Parameters;

import controller.ADMINISTRATOR;
import utils.filePatternMatch;
public class findNew {
	
	/**
	 * 查找最新文件，按照刘老师的名字截取进行排序。
	 * @param path
	 * @param manager
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午10:40:18
	 */
	@SuppressWarnings("unused")
	public static File find(String path,int th,ADMINISTRATOR manager) {
		int l=0;
		boolean flag=false;
		int count=1;
		File file = new File(path);
		File[] fs = file.listFiles();
		//fs为所有根目录下的文件数组。
		
		//我们只是用了两层for，也就是说只进入第二层目录。
		for(int j=0;j<fs.length;j++) {
			//按照最后修改时间排序。
			Arrays.sort(fs, new CompratorByLastModified());
			
			for (int i = 0; i < fs.length; i++) {
				//若文件是一个文件夹，且符合数据文件命名规范，则我们认为是带有数据的文件夹。
				//同时我们展开其文件，在下一次循环中继续排序判断。
				if(fs[i].isDirectory()) {
					file = new File(fs[i].getAbsolutePath());
					fs = file.listFiles();
					break;
				}
				
				//一下代码判断数据文件，我们使用fileFilter类来判断是否符合数据文件的命名特征。
				if(Parameters.readSecond==true){
					if(filePatternMatch.isBINFile(fs[i].getName())){
						if(count==2) {
							manager.isMrMa[th]=true;
							l=i;
							flag=true;
							break;
						}
						count++;
					}
					if(filePatternMatch.isHFMEDFile(fs[i].getName())){
						if(count==2){
							l=i;
							flag=true;
							break;
						}
						count++;
					}
				}
				else{
					if(filePatternMatch.isBINFile(fs[i].getName())){
						manager.isMrMa[th]=true;
						l=i;
						flag=true;
						break;
					}
					if(filePatternMatch.isHFMEDFile(fs[i].getName())){
						l=i;
						flag=true;
						break;
					}
				}
			}
			if(flag==true)
				break;
		}
		manager.setNNameF(th, fs[l].getName());
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
	
	
	public static class CompratorByLastModified implements Comparator<File> {
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
