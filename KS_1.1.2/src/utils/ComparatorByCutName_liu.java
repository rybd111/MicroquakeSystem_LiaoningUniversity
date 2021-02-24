package utils;

import java.io.File;
import java.util.Comparator;

import read.rqma.history.TimeLine;

public class ComparatorByCutName_liu implements Comparator<File> {
    @SuppressWarnings("unused")
	public int compare(File f1, File f2) {
    	int diff=0;

    	diff = SubStrUtil.contentCut_liu(f1.getName()).compareTo(SubStrUtil.contentCut_liu(f2.getName()));
    	
        if (diff > 0)
            return 1;
        else if (diff == 0)
            return 0;
        else
            return -1;
    }
}
