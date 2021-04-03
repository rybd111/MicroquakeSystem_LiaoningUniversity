/**
 * 
 */
package utils;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.jfree.ui.LengthAdjustmentType;

/**
 * @author Hanlin Zhang
 */
public class fileFilter {
	/**
	 * 找到给定路径的下满足给定时间点的所有HFMED对应的File数组，正常来说只能找到是一个给定时间对应的文件。
	 * @param parent 给定路径到数据的前一个路径。
	 * @param time date型变量，是一个时间点。
	 * @return files 是一个file数组，若找到文件，其长度为找到的文件个数，若没有找到文件，则其长度为0.
	 * @author Hanlin Zhang.
	 * @throws ParseException 
	 * @date revision 2021年2月19日下午3:25:06
	 */
	public static File[] TimeFilter(File[] file1, Date time) throws ParseException {
		//取包含time的所有文件。
		//保存所有符合时间点的文件，设置成数组，是因为方便以后进行时间范围的查找，不用重写逻辑。
		File files[] = useTimeFilter(file1, time);
		
		return files;
	}
	
	/**
     * 得到所有大于time的文件。
     * @param parent 给定路径到数据的前一个路径。
     * @param timeStr 一个时间点，字符型，
     * @return 所有大于timeStr的文件File[]。
     */
    @SuppressWarnings("unused")
	public static File [] DataFileFilter(
			String parent,
			String timeStr
			) {
    	
    	File[] files = useFileFilter(parent);
    	
        if (files == null || files.length < 1) {
        	System.out.println("没有能够对齐的文件！");
        	System.exit(0);
        }
        
        boolean isMrMa = false;
        ArrayList<File> filelist = new ArrayList<File>();
        //if the first file ends with .bin, then suppose all files end with .bin
        if(files[0].getName().endsWith(".bin")) {
        	isMrMa = true;
        	Arrays.sort(files, new ComparatorByCutName_ma());
        	for(File file1 : files) {
        		if((SubStrUtil.contentCut_ma(file1.getName())).compareTo(timeStr)>=0){
                    filelist.add(file1);
                }
        	}
        }
        else {
        	//排序根据文件名，此处排序只跟同目录下文件比较，因此不用考虑文件名不匹配的问题。
            Arrays.sort(files, new ComparatorByCutName_liu());
            for(File file1 : files) {
        		if((SubStrUtil.contentCut_liu(file1.getName())).compareTo(timeStr)>=0){
                    filelist.add(file1);
                }
        	}
        }
        
        return filelist.toArray(new File[filelist.size()]);
    }
    
    /**
     * 过滤当前路径下有无符合数据特征文件。
     * @author Hanlin Zhang.
     * @date revision 2021年2月19日上午11:08:47
     */
    public static boolean boolFileFilter(String parent) {
    	//过滤器，过滤后缀为HFMED的文件。
        File[] files = useSuffixFilter(parent); 
        files = useFilenameMatcher(files);
        
        if (files.length == 0) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 过滤当前路径下有无符合数据特征文件。
     * @author Hanlin Zhang.
     * @date revision 2021年2月19日上午11:08:47
     */
    public static boolean boolFileFilter(File file) {
    	//过滤器，过滤后缀为HFMED的文件。
        File[] files = useSufixFilter(file); 
        files = useFilenameMatcher(files);
        
        if (files.length == 0) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 既过滤后缀又过滤文件名，还过滤文件夹名。
     * 该函数使用时必须满足传入的parent是数据文件的上级目录
     * @param parent
     * @return
     * @author Hanlin Zhang.
     * @date revision 2021年2月28日上午11:53:49
     */
    public static File[] useFileFilter(String parent) {
    	String fileParentName = lastPartOfAbPath(parent);
    	
    	//检测文件名是否以字母开头。
    	if(filePatternMatch.isLetter(fileParentName) == false) {
    		return null;
    	}
    	
    	File files[] = useSuffixFilter(parent);
    	files = useFilenameMatcher(files);
    	
    	return files;
    }
    
    /**
     *只 过滤文件名，返回所有满足条件的文件数组。
     * @param parent
     * @return
     * @author Hanlin Zhang.
     * @date revision 2021年2月28日上午11:53:49
     */
    public static File[] useFileFilterFileArray(File[] f) {
    	File[] resultsFiles = new File[0];
    	
    	//载入每个传入文件的绝对路径，并获取绝对路径的最后部分，对于这个函数，最后部分为文件名。
    	for(int i=0;i<f.length;i++) {
    		String fileName = f[i].getName();
    		if(filePatternMatch.isHFMEDFile(fileName) || filePatternMatch.isBINFile(fileName) || filePatternMatch.isHDIFX(fileName)) {
    			resultsFiles = Arrays.copyOf(resultsFiles, resultsFiles.length+1);
    			resultsFiles[resultsFiles.length-1] = f[i];
    		}
    	}
    	
    	return resultsFiles;
    }
    
    /**
     * parent是各root根路径。
     * 进入parent后，再进行文件夹命名的过滤，是对文件夹的过滤。
     * 这里假设ma也是按照二级目录生成的数据盘，与liu设备存储的格式一样。
     * @param parent
     * @return
     */
//    public static File[] useFileFilterEnter(String parent) {
//    	File enter = new File(parent);
//    	String fileNameArray[] = enter.list();
//    	
//    	//在文件名数组中寻找满足条件的文件。
//    	for(int i=0;i<fileNameArray.length;i++) {
//    		
//    		//先检测文件名是否以字母开头。
//    		if(filePatternMatch.isLetter(fileNameArray[i]) == false) {
//        		return null;
//        	}
//    		
//    		//检测当前目录下的文件是否存在以bin或hfmed结尾的文件。
//    		File files[] = useSuffixFilter(fileNameArray[i]);
//    		
//    		//如果存在数据文件，则按照文件命名规则，检测是否符合bin或hfmed文件格式，files是过滤不符合命名特征的文件。
//        	files = useFilenameMatcher(files);
//    	}
//    	return files;
//    }
    
    /**
     * 只过滤后缀
     * 过滤当前路径下有无HFMED或bin结尾文件。
     * @author Hanlin Zhang.
     * @date revision 2021年2月19日上午11:08:47
     */
    public static File[] useSuffixFilter(String parent) {
    	
    	File file = new File(parent);
        File[] files = file.listFiles((dir, name) -> name.endsWith(".bin") || name.endsWith(".HFMED"));

        return files;
    }
    
    /**
     * 只过滤后缀
     * 过滤当前路径下有无HFMED或bin结尾文件。
     * @author Hanlin Zhang.
     * @date revision 2021年2月19日上午11:08:47
     */
    public static File[] useSufixFilter(File file) {
    	//过滤器，过滤后缀为HFMED的文件。
        File[] files = file.listFiles((dir, name) -> name.endsWith(".bin") || name.endsWith(".HFMED"));

        return files;
    }
    
    /**
     * 使用正则表达式命名特征过滤文件，不包括后缀的判断。
     * @param files
     * @return
     * @author Hanlin Zhang.
     * @date revision 2021年2月24日上午11:07:13
     */
    public static File[] useFilenameMatcher(File[] files) {
    	File[] temp = new File[0];
    	
    	for(File file2:files) {
	    	if(
	    			filePatternMatch.match_HFMED(file2.getName())==true ||
	    			filePatternMatch.match_BIN(file2.getName())==true
	    			) {
				temp = Arrays.copyOf(temp, temp.length+1);
				temp[temp.length-1] = file2;
	    	}
    	}
    	
    	return temp;
    }
    
    /**
     * 使用该函数的前提是已经过滤掉了不符合数据文件特征的文件，否则该函数可能会报错或失效。
     * @param file1
     * @param time
     * @return
     * @throws ParseException
     * @author Hanlin Zhang.
     * @date revision 2021年2月28日上午11:59:54
     */
    public static File[] useTimeFilter(File[] file1, Date time) throws ParseException {
    	//取包含time的所有文件。
		//保存所有符合时间点的文件，设置成数组，是因为方便以后进行时间范围的查找，不用重写逻辑。
		File[] files = new File[0];
		for (File file2 : file1) {
            /** 找到所有的开始时间小于给定时间
             * 且结束时间大于给定时间的文件。后续加上时间带，以加强范围约束。
             */
			//分为两种后缀，bin和HFMED
			if(file2.getName().endsWith(".bin")) {
				//将时间转换为字符串，用于对比文件名的后12位。
		    	String time1 = Date2String.date2str2(time);
		    	if(
	            		(SubStrUtil.contentCut_ma(file2.getName())).compareTo(time1) <=0 && file2.lastModified()-time.getTime() >= 0
		    			){
	                files = Arrays.copyOf(files, files.length+1);
	                files[files.length-1] = file2;
	            }
			}
			if(file2.getName().endsWith(".HFMED")) {
				//将时间转换为字符串，用于对比文件名的后12位。
		    	String time1 = Date2String.date2str1(time);
		    	if(
	            		("20"+SubStrUtil.contentCut_liu(file2.getName())).compareTo(time1) <=0 && file2.lastModified()-time.getTime() >= 0
	            		){
	                files = Arrays.copyOf(files, files.length+1);
	                files[files.length-1] = file2;
	            }
			}
        }
		
		return files;
    }
    
    /**
     * 获取文件路径中最后一个分隔符的内容，也就是上一级文件夹名。
     * @param AbPath
     * @return
     * @author Hanlin Zhang.
     * @date revision 2021年3月25日下午4:57:11
     */
    public static String lastPartOfAbPath(String AbPath) {
    	
    	String[] A = AbPath.split("\\\\");
    	
    	return A[A.length-1];
    }
    
    
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月19日下午3:13:58
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
