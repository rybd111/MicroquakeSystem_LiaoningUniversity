/**
 * 
 */
package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hanlin Zhang
 */
public class one_dim_array_max_min {
	//find max
	public static int minint(int [] array){
		int min=array[0];
		int i=0;
		for(i=0;i<array.length;i++){
			if(array[i]<min){
				min=array[i];
			}
		}
		return min;
	}
	
	//find min
	public static int maxint(int [] array){
		int max=0;
		int i=0;
		for(i=0;i<array.length;i++){
			if(array[i]>max){
				max=array[i];
			}
		}
		return max;
	}
	//find max
	public static double mindouble(double [] array){
		double min=array[0];
		int i=0;
		for(i=0;i<array.length;i++){
			if(array[i]<min){
				min=array[i];
			}
		}
		return min;
	}
	
	//find min
	public static double maxdouble(double [] array){
		double max=0;
		int i=0;
		for(i=0;i<array.length;i++){
			if(array[i]>max){
				max=array[i];
			}
		}
		return max;
	}
	
	/**
	 * we should determine this attribute according to the distance of these sensors to the earthquake.
	 * @param array
	 * @return
	 * @author Hanlin Zhang.
	 */
	public static int getMethod_4(int[] array){
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        int count_2 = 0;
        int temp = 0;
        for(int i=0;i<array.length;i=i+count){
            if(i==array.length-1){
                temp =1;
                break;
            }
            for(int j=i+1;j<array.length;j++){
                if(array[i]==array[j]){
                    count++;
                }
                continue;
            }
            if(count>count_2){
            count_2 = count;
            map.put(count_2, array[i]);
            }
                        
        }
        return map.get(count_2);
    }

}
