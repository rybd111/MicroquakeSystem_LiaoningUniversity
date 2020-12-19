/**
 * 
 */
package utils;

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
}
