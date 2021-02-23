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
	 * 找到给定路径的下满足给定时间点的所有HFMED对应的File数组。
	 * @param parent 给定路径到数据的前一个路径。
	 * @param time date型变量，是一个时间点。
	 * @return files 是一个file数组，若找到文件，其长度为找到的文件个数，若没有找到文件，则其长度为0.
	 * @author Hanlin Zhang.
	 * @throws ParseException 
	 * @date revision 2021年2月19日下午3:25:06
	 */
	public static File[] TimeFilter(String parent, Date time) throws ParseException {
		//过滤器，过滤后缀为HFMED的文件。
    	HFMEDAccept hfmedAccept = new HFMEDAccept();
    	hfmedAccept.setExtendName("HFMED");
    	File file = new File(parent);
    	File[] file1 = file.listFiles(hfmedAccept);
    	//将时间转换为字符串，用于对比文件名的后12位。
    	String time1 = Date2String.date2str1(time);
    	//升序排序文件，尽管同一路径下的文件名可能相同，但为了保险起见，我们在比较器内部依然进行了文件名的截取。
    	Arrays.sort(file1,new ComparatorByCutName_liu());
		//保存所有符合时间点的文件，设置成数组，是因为方便以后进行时间范围的查找，不用重写逻辑。
		File[] files = new File[0];
		//取包含time的所有文件。
		for (File file2 : file1) {
            /** 找到所有的开始时间小于给定时间
             * 且结束时间大于给定时间的文件。后续加上时间带，以加强范围约束。
             */
            //5-17形式 ：190101124000
			//过滤掉那些文件名不符合的文件。
			if(filePatternMatch.match_HFMED(file2.getName())==false) {
				break;
			}
            if(("20"+SubStrUtil.contentCut_liu(file2.getName())).compareTo(time1) <=0 && 
            		file2.lastModified()-time.getTime() >= 0){
                files = Arrays.copyOf(files, files.length+1);
                files[files.length-1] = file2;
                break;
            }
        }
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
    	File file = new File(parent);
        File[] files = file.listFiles((dir, name) -> name.endsWith(".bin") || name.endsWith(".HFMED"));
        
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
     * 过滤当前路径有无HFMED结尾文件。
     * @author Hanlin Zhang.
     * @date revision 2021年2月19日上午11:08:47
     */
    public static boolean filter(String parent) {
    	File file = new File(parent);
    	//过滤器，过滤后缀为HFMED的文件。
    	HFMEDAccept hfmedAccept = new HFMEDAccept();
    	hfmedAccept.setExtendName("HFMED");
        File[] files = file.listFiles(hfmedAccept);
        if (files.length == 0) {
            return false;
        }
        return true;
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
