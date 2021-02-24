/**
 * 
 */
package utils;

import java.util.Comparator;

import DataExchange.Sensor;
import read.rqma.history.TimeLine;

/**
 * @author Hanlin Zhang
 */
public class ComparatorBySectime implements Comparator<Sensor> {
	
	public int compare(Sensor s1, Sensor s2) {
    	
        double diff = s1.getSecTime() - s2.getSecTime();
        
        if (diff > 0)
            return 1;
        else if (diff == 0)
            return 0;
        else
            return -1;
    }
}
