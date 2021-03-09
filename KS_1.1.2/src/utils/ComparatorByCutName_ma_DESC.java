/**
 * 
 */
package utils;

import java.io.File;
import java.util.Comparator;

/**
 * @author Hanlin Zhang
 */
public class ComparatorByCutName_ma_DESC implements Comparator<File>{
	@SuppressWarnings("unused")
	public int compare(File f1, File f2) {
    	int diff=0;

    	diff = SubStrUtil.contentCut_ma(f1.getName()).compareTo(SubStrUtil.contentCut_ma(f2.getName()));
    	
        if (diff > 0)
            return -1;
        else if (diff == 0)
            return 0;
        else
            return 1;
    }
}
