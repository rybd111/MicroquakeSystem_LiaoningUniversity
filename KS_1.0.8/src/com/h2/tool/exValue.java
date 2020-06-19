package com.h2.tool;

/**
 * this class is used to find the series of extreme value in a wave.
 * @author Hanlin Zhang.
 */
public class exValue {
	public static int exV(double [] a){
		boolean flag1 = false;//同时满足两个标志位均为true时，该点为极值点
		boolean flag2 = false;
		int k = 0 ;
		for(int i=0;i<a.length-1;i++){
			//计算a中的相邻的两个点的斜率
			if((a[i]-a[i+1])<0){//有可能大于0有可能小于0
				flag1=true;
				if(flag1==true && flag2==true){
					k=i;
					break;
				}
			}
			if(a[i]-a[i+1]>0){
				flag2=true;
				if(flag1==true && flag2==true){
					k=i;
					break;
				}
			}
		}
		
		return k;//返回数组中的位置
	}
}
