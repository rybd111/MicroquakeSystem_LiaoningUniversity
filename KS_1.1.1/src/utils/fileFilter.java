/**
 * 
 */
package utils;

import java.awt.List;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import controller.ADMINISTRATOR;

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
	public static File[] TimeFilter(String parent, Date time) throws ParseException {
		//过滤器，先过滤后缀为HFMED与bin的文件。
		File file1[] = usefilefilter(parent);
		//先过滤符合命名特征的数据文件，bin HFMED。
		file1 = useMatcher(file1);
		//取包含time的所有文件。
		//保存所有符合时间点的文件，设置成数组，是因为方便以后进行时间范围的查找，不用重写逻辑。
		File files[] = useTimeFilt(file1, time);
		
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
			String timeStr) {
    	
    	File[] files = usefilefilter(parent);
    	
    	files = useMatcher(files);
    	
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
     * 过滤当前路径下有无HFMED或bin结尾文件。
     * @author Hanlin Zhang.
     * @date revision 2021年2月19日上午11:08:47
     */
    public static boolean boolfilter(String parent) {
    	File file = new File(parent);
    	//过滤器，过滤后缀为HFMED的文件。
//    	HFMEDAccept hfmedAccept = new HFMEDAccept();
//    	hfmedAccept.setExtendName("HFMED");
        File[] files = file.listFiles((dir, name) -> name.endsWith(".bin") || name.endsWith(".HFMED"));
        
        if (files.length == 0) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 过滤当前路径下有无HFMED或bin结尾文件。
     * @author Hanlin Zhang.
     * @date revision 2021年2月19日上午11:08:47
     */
    public static File[] usefilefilter(String parent) {
    	File file = new File(parent);
    	//过滤器，过滤后缀为HFMED的文件。
        File[] files = file.listFiles((dir, name) -> name.endsWith(".bin") || name.endsWith(".HFMED"));
        
        return files;
    }
    
    /**
     * 使用正则表达式命名特征过滤文件。
     * @param files
     * @return
     * @author Hanlin Zhang.
     * @date revision 2021年2月24日上午11:07:13
     */
    public static File[] useMatcher(File[] files) {
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
    
    public static File[] useTimeFilt(File[] file1, Date time) throws ParseException {
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
	            		(SubStrUtil.contentCut_ma(file2.getName())).compareTo(time1) <=0 && 
	            		file2.lastModified()-time.getTime() >= 0
	            		){
	                files = Arrays.copyOf(files, files.length+1);
	                files[files.length-1] = file2;
	            }
			}
			if(file2.getName().endsWith(".HFMED")) {
				//将时间转换为字符串，用于对比文件名的后12位。
		    	String time1 = Date2String.date2str1(time);
		    	if(
	            		("20"+SubStrUtil.contentCut_liu(file2.getName())).compareTo(time1) <=0 && 
	            		file2.lastModified()-time.getTime() >= 0
	            		){
	                files = Arrays.copyOf(files, files.length+1);
	                files[files.length-1] = file2;
	            }
			}
        }
		
		return files;
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
