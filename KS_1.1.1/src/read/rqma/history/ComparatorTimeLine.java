package read.rqma.history;

import java.util.Comparator;

import com.h2.constant.Parameters;

/**
 * @Description:
 * @Auther: RQMA
 * @Date: 4/26/2019 2:04 PM
 */
public class  ComparatorTimeLine implements Comparator<TimeLine> {
    @SuppressWarnings("unused")
	@Override
    public int compare(TimeLine o1, TimeLine o2) {
    	
    	
    	int diff=0;
    	if(Parameters.region=="datong")
    		diff = SubStrUtil.getSubParentPackage(o1.filename).compareTo(SubStrUtil.getSubParentPackage(o2.filename));
    	else if(Parameters.region=="pingdingshan"||Parameters.region=="hongyang") {
    		diff = o1.filename.compareTo(o2.filename);
    	}
        if (diff > 0)
            return 1;
        else if (diff == 0)
            return 0;
        else
            return -1;

    }
}
