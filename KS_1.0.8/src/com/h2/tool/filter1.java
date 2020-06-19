package com.h2.tool;
import java.util.Vector;

/**
 * A sliding filter used to filter the spines in wave since our read process and the writing of the sensor process is conflicted.
 * @author Hanlin Zhang.
 */
public class filter1 {
//		private double max3=0;
//		private double max4=0;
//		private double max5=0;
		
//		private double min3=0;
//		private double min4=0;
//		private double min5=0;
		public static double []filt(double []a){
			
			for(int i =0;i<a.length-20;i++)
			{//初始化最小值，否则最小值将一直是0
				//System.out.println(data.get(i).split(" ")[3]);
//				this.min3=Double.parseDouble(data.get(i).split(" ")[3]);
//				this.min4=Double.parseDouble(data.get(i).split(" ")[4]);
//				this.min5=Double.parseDouble(data.get(i).split(" ")[5]);
				double max5=0;
				
//				private double min3=0;
//				private double min4=0;
				double min5=0;
				min5 = a[i];
				for(int j=i;j<i+20;j++){//找出5个数据中的最大值与最小值
//					String str[] = data.get(j).split(" ");//获取每列数据
//					if(Math.abs(Double.parseDouble(str[3]))>this.max3)
//						this.max3 = Double.parseDouble(str[3]);
//					if(Math.abs(Double.parseDouble(str[4]))>this.max4)
//						this.max4 = Double.parseDouble(str[4]);
					if(Math.abs(a[j])>max5)
						max5 = a[j];
					
//					if(Math.abs(Double.parseDouble(str[3]))<this.min3)
//						this.min3 = Double.parseDouble(str[3]);
//					if(Math.abs(Double.parseDouble(str[4]))<this.min4)
//						this.min4 = Double.parseDouble(str[4]);
					if(a[j]<min5)
						min5 = a[j];
				}//将最大最小值求出
				double sum3=0;double sum4=0;double sum5=0;
				//找出剩余点的和
				//System.out.println("3:"+max3+" "+min3);
				//System.out.println("4:"+max4+" "+min4);
				//System.out.println("5:"+max5+" "+min5);
				for(int j=i;j<i+20;j++){
//					String str[] = data.get(j).split(" ");//获取每列数据
//					if(Double.parseDouble(str[3])!=this.max3&&Double.parseDouble(str[3])!=this.min3)
//						sum3=sum3+Double.parseDouble(str[3]);
//					if(Double.parseDouble(str[4])!=this.max4&&Double.parseDouble(str[4])!=this.min4)
//						sum4=sum4+Double.parseDouble(str[4]);
					if(a[j]!=max5&&a[j]!=min5)
						sum5=sum5+a[j];
				}//将所有不是最大值的数据全部加和，这样三个通道再分别取平均值，然后得出滤波后的值
				//System.out.println(sum3);
//				String a[]=data.get(i).split(" ");
//				String aA = a[0]+" "+a[1]+" "+a[2]+" "+String.valueOf(sum3/3)+
//						" "+String.valueOf(sum4/3)+" "+String.valueOf(sum5/3)+" "+
//						a[6];
				//System.out.println("111"+aA);
//				data.set(i, aA);//将滤波后的数据替换到原先的位置
//				this.max3=0;this.max4=0;
				a[i] = (int)sum5/20;
				max5=0;
			}
			return a;
		}
	}


