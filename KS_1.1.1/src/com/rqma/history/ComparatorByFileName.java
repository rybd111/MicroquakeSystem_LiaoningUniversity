package com.rqma.history;

import java.io.File;
import java.util.Comparator;

import com.h2.constant.Parameters;

public class ComparatorByFileName implements Comparator<File> {
    private TimeLine timeLine;
    @SuppressWarnings("unused")
	public int compare(File f1, File f2) {
    	
    	
    	int diff=0;
    	if(Parameters.region_offline=="datong")
    		diff = SubStrUtil.getSubParentPackage(f1.getName()).compareTo(SubStrUtil.getSubParentPackage(f2.getName()));
    	else if(Parameters.region_offline=="pingdingshan")
    		diff = f1.getName().compareTo(f2.getName());
    	
        if (diff > 0)
            return 1;
        else if (diff == 0)
            return 0;
        else
            return -1;
    }


}