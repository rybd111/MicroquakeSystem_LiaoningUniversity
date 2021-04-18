package com.h2.locate;

import java.io.IOException;
import java.nio.file.WatchEvent.Kind;
import java.text.ParseException;
import java.util.Arrays;

import com.h2.constant.Parameters;
import com.h2.tool.Doublelocate;
import com.h2.tool.FiveLocation;
import com.h2.tool.QuakeClass;
import com.h2.tool.SensorTool;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mysql.fabric.xmlrpc.base.Struct;

import DataExchange.Sensor;
import cn.hutool.core.lang.Tuple;
import controller.ADMINISTRATOR;
import read.yang.unity.SensorProperties;
import utils.ConvertCoordination_base;
import utils.ConvertCoordination_pingdingshan;
import utils.CoorProcess;
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
	public static String[] locate(double[][] sensorInformation,String kind,String absolutetime) throws MWException, ParseException {
		String[] result = new String[5];
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		
		if(sensorInformation.length<3)
		{
			System.out.println("激发传感器个数小于3，无法计算");
		}
		else {
			//先求相对坐标
			double[][] newCoor = relativeCoor(sensorInformation);
			//保存相对坐标
			Sensor[] sensors1 = new Sensor[sensorInformation.length];
			
			for(int i=0;i<newCoor.length;i++) {
				sensors1[i]=new Sensor();
				sensors1[i].setSecTime(newCoor[i][0]);//到时
				sensors1[i].setx(newCoor[i][1]);//x
				sensors1[i].sety(newCoor[i][2]);//y
				sensors1[i].setz(newCoor[i][3]);//z
			}
			sensors1[0].setAbsoluteTime(absolutetime);//绝对时间
			
			if(kind=="Five_Locate")	{
				Sensor location_refine = FiveLocation.getLocation(sensors1);
				location_refine.toString();
				double xdata=Double.parseDouble(nf.format(location_refine.getx()));//事件x坐标
				double ydata=Double.parseDouble(nf.format(location_refine.gety()));//事件坐标
				double zdata=Double.parseDouble(nf.format(location_refine.getz()));//事件z坐标
				location_refine.setSecTime(Doublelocate.quakeTime(sensors1[0], location_refine));
				double parrival=location_refine.getSecTime();//到时
				String intequackTime =TimeTransform.TimeDistance(sensors1[0].getAbsoluteTime(), location_refine.getSecTime()); //发震时刻
				
				//绝对坐标
				double xyz[] = absoluteCoor(xdata, ydata, zdata, sensorInformation[0]);
				
				//转换坐标
				double xy[] = CoorProcess.coorProcess(xyz[0], xyz[1]);
				
				result[0] = String.valueOf(xy[0]);
				result[1] = String.valueOf(xy[1]);
				result[2] = String.valueOf(xyz[2]);
				result[3] = String.valueOf(parrival);
				result[4] = String.valueOf(intequackTime);
				
				return result;
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
			else if(kind=="PSO_Locate")	{
				//calculate the two dimensional matrix.
		 		double coor[][] = new double[sensors1.length][4];
		 		for(int i=0;i<sensors1.length;i++)
		 		{
	 				coor[i][0]=sensors1[i].getx();
	 				coor[i][1]=sensors1[i].gety();
	 				coor[i][2]=sensors1[i].getz();
	 				coor[i][3]=sensors1[i].getSecTime();
		 		}
				Sensor location_refine = new Sensor();
				location_refine=QuakeClass.PSO(coor);
				location_refine.toString();
				double xdata=Double.parseDouble(nf.format(location_refine.getx()));//事件x坐标
				double ydata=Double.parseDouble(nf.format(location_refine.gety()));//事件坐标
				double zdata=Double.parseDouble(nf.format(location_refine.getz()));//事件z坐标
				location_refine.setSecTime(Doublelocate.quakeTime(sensors1[0], location_refine));
				double parrival=location_refine.getSecTime();//到时
				String intequackTime =TimeTransform.TimeDistance(sensors1[0].getAbsoluteTime(), location_refine.getSecTime()); //发震时刻
				
				//绝对坐标
				double xyz[] = absoluteCoor(xdata, ydata, zdata, sensorInformation[0]);
				
				//转换坐标
				double xy[] = CoorProcess.coorProcess(xyz[0], xyz[1]);
				
				result[0] = String.valueOf(xy[0]);
				result[1] = String.valueOf(xy[1]);
				result[2] = String.valueOf(xyz[2]);
				result[3] = String.valueOf(parrival);
				result[4] = String.valueOf(intequackTime);
				
				return result;
			}
			else {
				System.out.println("kind is error");
				return null;
			}
		}
		return null;
	}
	
	//---------------------------------------------------------------------------
	/**
	 * 手动重定位使用。
	 * @param count
	 * @param filePath
	 * @param daoshi
	 * @return
	 * @throws IOException
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月12日下午7:34:54
	 */
	public static double[][] initialCoor(int count, String[] filePath, double[] daoshi) throws IOException {
		double coor[][] = new double[count][4];
		//---------------------------------------------------------------------
		System.out.println("请确认定位运行基本信息：");
		if(Parameters.offline == true) {
			System.out.println("当前运行模式为："+"离线");
		}
		else {
			System.out.println("当前运行模式为："+"在线");
		}
		System.out.print("当前激发盘符设置： ");
		for(int i=0;i<filePath.length;i++) {
			String s = filePath[i].substring(filePath[i].length()-2, filePath[i].length()-1);
			System.out.print(s+" ");
		}
		System.out.println();
		System.out.println("当前矿区为："+Parameters.region);
//		System.out.println("按任意键继续。。。");
//		System.in.read(); // 用这个就行了，获取输入流，会等待键盘按键
		//---------------------------------------------------------------------
		ADMINISTRATOR manager = new ADMINISTRATOR();
		Sensor[] s = SensorTool.initSensorInfo(count, filePath, manager);
		
		for(int i=0;i<count;i++) {
			coor[i][1] = s[i].getx();
			coor[i][2] = s[i].gety();
			coor[i][3] = s[i].getz();
			coor[i][0] = daoshi[i];
		}
		
		return coor;
	}
	
	/**
	 * 求取相对坐标、相对时间。
	 * @param sensorInformation
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年4月18日上午10:39:39
	 */
	public static double[][] relativeCoor(double[][] sensorInformation) {
		//深拷贝，防止篡改。
		double[][] newInfo = new double[sensorInformation.length][sensorInformation[0].length];
		
		for(int i=0;i<sensorInformation.length;i++) {
			newInfo[i][0] = sensorInformation[i][0];
			newInfo[i][1] = sensorInformation[i][1];
			newInfo[i][2] = sensorInformation[i][2];
			newInfo[i][3] = sensorInformation[i][3];
		}
		
		for(int i=1;i<newInfo.length;i++) {
			newInfo[i][0] = newInfo[i][0] - newInfo[0][0];
			newInfo[i][1] = newInfo[i][1] - newInfo[0][1];
			newInfo[i][2] = newInfo[i][2] - newInfo[0][2];
			newInfo[i][3] = newInfo[i][3] - newInfo[0][3];
		}
		
		newInfo[0][0] = 0;
		newInfo[0][1] = 0;
		newInfo[0][2] = 0;
		newInfo[0][3] = 0;
		
		return newInfo; 
	}
	
	/**
	 * 注意sensorInformation里面的数据顺序
	 * @param x
	 * @param y
	 * @param z
	 * @param firstCoor
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年4月18日上午10:39:17
	 */
	public static double[] absoluteCoor(double x, double y, double z, double[] firstCoor){
		double newx = x + firstCoor[1];
		double newy = y + firstCoor[2];
		double newz = z + firstCoor[3];
		
		double[] result = {newx, newy, newz};
		
		return result;
	}
	
	
//	public static void main(String[] args) throws MWException, ParseException {
//		 double[][] senserInformation= {{-1.1432,41516836.655,4596627.472,21.545},//S Test1
//					{-1.1432,41518099.807,4595388.504,22.776 },//T Test2
//					{-1.1432,41518060.298,4594304.927,21.926  },//U Test3
//					{-1.1432,41520207.356,4597983.404,22.661  },//W Test4
//					{-1.1432,41520815.875,4597384.576,25.468  },//X Test5
//					{-1.1432,41519926.476,4597275.978,20.705  },//Y Test6
//					{-1.1432,41519304.125,4595913.485,23.921  },//Z Test7
//					{-1.1432,41516707.440,4593163.619,22.564  },//V Test8
//					{-1.1432,41517290.0374,4599537.3261,24.5649 }//R Test9
//		 };
//		 String absolutetime="2020-03-01 16-24-47`87";
//		 String kind="PSO_Locate";
//		 String[] result=locate(senserInformation, kind,absolutetime);
//		System.out.println("x= "+Double.parseDouble(result[0])+"\n"+"y= "+Double.parseDouble(result[1])
//		+"\n"+"z= "+Double.parseDouble(result[2])+"\n"+"parrival= "+result[3]+"\n"+"intequackTime= "+result[4]);
//		
//	}
	
}
