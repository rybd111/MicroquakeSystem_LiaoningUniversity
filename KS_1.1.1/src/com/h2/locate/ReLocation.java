package com.h2.locate;

import java.nio.file.WatchEvent.Kind;
import java.text.ParseException;

import com.h2.constant.Sensor;
import com.h2.tool.Doublelocate;
import com.h2.tool.Location2;
import com.h2.tool.QuakeClass;
import com.h2.tool.Triplelocate;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mysql.fabric.xmlrpc.base.Struct;
import com.yang.unity.SensorProperties;

import cn.hutool.core.lang.Tuple;
import utils.TimeDifferent;
import utils.TimeTransform;

public class ReLocation {
	
	/**
	 * @param senserInformation 传感器信息（到时,x,y,z）.
	 * @param kind 算法类型.("Five_Locate","PSO_Locate")
	 * @param absolutetime  绝对时间，为波形文件名中的绝对时间.
	 * @return String[x,y,z,parrival,intequackTime]
	 * @throws MWException.
	 * @throws ParseException.
	 * @author 张港
	 */
	public static String[] locate(double[][] senserInformation,String kind,String absolutetime) throws MWException, ParseException {
		String[] resultString=new String[5];
		if(senserInformation.length<3)
		{
			System.out.println("激发传感器个数小于3，无法计算");
		}
		else {
				Sensor[] sensors1 = new Sensor[senserInformation.length];
				
				for(int i=0;i<senserInformation.length;i++)
				{
					sensors1[i]=new Sensor();	
					sensors1[i].setSecTime(senserInformation[i][0]);//到时
					sensors1[i].setLatitude(senserInformation[i][1]);//x
					sensors1[i].setLongtitude(senserInformation[i][2]);//y
					sensors1[i].setAltitude(senserInformation[i][3]);//z
				}
				sensors1[0].setAbsoluteTime(absolutetime);//绝对时间
				if(kind=="Five_Locate")
					{
						Sensor location_refine = Location2.getLocation(sensors1);
						java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
						nf.setGroupingUsed(false);
						double xdata=Double.parseDouble(nf.format(location_refine.getLatitude()));//事件x坐标
						resultString[0]=String.valueOf(xdata);
//						System.out.println("x+"+xdata);
//						System.out.println("x+"+resultString[0]);
						double ydata=Double.parseDouble(nf.format(location_refine.getLongtitude()));//事件y坐标
//						System.out.println("y+"+ydata);
						resultString[1]=String.valueOf(ydata);
						double zdata=Double.parseDouble(nf.format(location_refine.getAltitude()));//事件z坐标
//						System.out.println("z+"+zdata);
						resultString[2]=String.valueOf(zdata);
						location_refine.setSecTime(Doublelocate.quakeTime(sensors1[0], location_refine));
						double parrival=location_refine.getSecTime();//到时
						resultString[3]=String.valueOf(parrival);
//						System.out.println("parrival+"+parrival);
						String intequackTime = TimeTransform.TimeDistance(sensors1[0].getAbsoluteTime(), location_refine.getSecTime());//发震时刻
						resultString[4]=String.valueOf(intequackTime);
//						System.out.println("intequackTime+"+intequackTime);				
						return resultString;
				 		
					}
				/*
			else if(kind=="Three_Locate")
					{
						Sensor location_refine=Triplelocate.tripleStationLocate(sensors1);
						java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
						nf.setGroupingUsed(false);
						double xdata=Double.parseDouble(nf.format(location_refine.getLatitude()));
						System.out.println("x+"+xdata);
						double ydata=Double.parseDouble(nf.format(location_refine.getLongtitude()));
						System.out.println("y+"+ydata);
						double zdata=Double.parseDouble(nf.format(location_refine.getAltitude()));
						System.out.println("z+"+zdata);
						location_refine.setSecTime(Doublelocate.quakeTime(sensors1[0], location_refine));
						double parrival=location_refine.getSecTime();
						System.out.println("parrival+"+parrival);
						System.out.println("三台站+++++++++++："+location_refine.toString()+"\n");//在控制台输出结果
						return location_refine;
					}
					*/
			else if(kind=="PSO_Locate")
					{
							//calculate the two dimensional matrix.
					 		double coor[][] = new double[sensors1.length][4];
					 		for(int i=0;i<sensors1.length;i++)
					 		{
				 				coor[i][0]=sensors1[i].getLatitude();
				 				coor[i][1]=sensors1[i].getLongtitude();
				 				coor[i][2]=sensors1[i].getAltitude();
				 				coor[i][3]=sensors1[i].getSecTime();
					 		}
						Sensor location_refine = new Sensor();
						location_refine=QuakeClass.PSO(coor);
						java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
						nf.setGroupingUsed(false);
						double xdata=Double.parseDouble(nf.format(location_refine.getLatitude()));//事件x坐标
						resultString[0]=String.valueOf(xdata);
//						System.out.println("x+"+resultString[0]);
						double ydata=Double.parseDouble(nf.format(location_refine.getLongtitude()));//事件y坐标
//						System.out.println("y+"+ydata);
						resultString[1]=String.valueOf(ydata);
						double zdata=Double.parseDouble(nf.format(location_refine.getAltitude()));//事件z坐标
//						System.out.println("z+"+zdata);
						resultString[2]=String.valueOf(zdata);
						location_refine.setSecTime(Doublelocate.quakeTime(sensors1[0], location_refine));
						double parrival=location_refine.getSecTime();//到时
						resultString[3]=String.valueOf(parrival);
//						System.out.println("parrival+"+parrival);
						String intequackTime =TimeTransform.TimeDistance(sensors1[0].getAbsoluteTime(), location_refine.getSecTime()); //发震时刻
						resultString[4]=String.valueOf(intequackTime);
//						System.out.println("intequackTime+"+intequackTime);				
						return resultString;
					}
			else 	{
						System.out.println("kind is error");
						return null;
					}
			}
		return resultString;
	}
	
	
	
	
	/*
	public static void main(String[] args) throws MWException, ParseException {
		 double[][] senserInformation= {{-1.1432,41516836.655,4596627.472,21.545},//S Test1
					{-1.1432,41518099.807,4595388.504,22.776 },//T Test2
					{-1.1432,41518060.298,4594304.927,21.926  },//U Test3
					{-1.1432,41520207.356,4597983.404,22.661  },//W Test4
					{-1.1432,41520815.875,4597384.576,25.468  },//X Test5
					{-1.1432,41519926.476,4597275.978,20.705  },//Y Test6
					{-1.1432,41519304.125,4595913.485,23.921  },//Z Test7
					{-1.1432,41516707.440,4593163.619,22.564  },//V Test8
					{-1.1432,41517290.0374,4599537.3261,24.5649 }//R Test9
		 };
		 String absolutetime="2020-03-01 16-24-47`87";
		 String kind="PSO_Locate";
		 String[] result=locate(senserInformation, kind,absolutetime);
		System.out.println("x= "+Double.parseDouble(result[0])+"\n"+"y= "+Double.parseDouble(result[1])
		+"\n"+"z= "+Double.parseDouble(result[2])+"\n"+"parrival= "+result[3]+"\n"+"intequackTime= "+result[4]);
		
	}
	*/
}
