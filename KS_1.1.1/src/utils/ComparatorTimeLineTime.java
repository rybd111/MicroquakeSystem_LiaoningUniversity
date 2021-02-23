package utils;

import read.rqma.history.TimeLine;

import java.util.Comparator;

/**
 * @Description:
 * @Auther: RQMA
 * @Date: 2020/5/31 22:10
 */
public class ComparatorTimeLineTime implements Comparator<TimeLine> {
	
    @Override
    public int compare(TimeLine o1, TimeLine o2) {
    	
        long diff = o1.getBegintime() - o2.getBegintime();
        if (diff > 0)
            return 1;
        else if (diff == 0)
            return 0;
        else
            return -1;
    }
}
