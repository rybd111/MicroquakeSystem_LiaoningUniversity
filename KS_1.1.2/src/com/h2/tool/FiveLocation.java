package com.h2.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jblas.DoubleMatrix;
import org.jblas.Solve;

import com.h2.constant.Parameters;

import DataExchange.Sensor;

public class FiveLocation
{
	/**
	 * 计算震源的位置
	 * 
	 * @param Token
	 *            全部传感器
	 * @return 存储有震源坐标和时间的sensor对象
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	public static Sensor getLocation(Sensor[] Token)
	{
		Sensor sensor = new Sensor();

		DoubleMatrix B = DoubleMatrix.zeros(4, 1);//4行1列

		DoubleMatrix A = getA(Token);//A是一个二维数组，存储第一个传感器与其余传感器的时间差、坐标差4行4列

		DoubleMatrix C = getC(Token);//C存储两传感器间的差值4行1列

		B = Solve.pinv(A).mmul(C);//表示要进行矩阵乘法运算的两个数组
		// 通过B给sensor赋值
		sensor.setSecTime(B.get(0));//t
		sensor.setx(B.get(1));//x
		sensor.sety(B.get(2));//y
		sensor.setz(B.get(3));//z
		
		return sensor;
	}

	private static DoubleMatrix getC(Sensor[] sensors)
	{
		DoubleMatrix C = DoubleMatrix.zeros(4, 1);//4行1列二维数组
		C.put(0, getLinearAlgebraT(sensors[1], sensors[0]));//C存储第一个传感器与其余传感器getT差值的一半
		C.put(1, getLinearAlgebraT(sensors[2], sensors[0]));
		C.put(2, getLinearAlgebraT(sensors[3], sensors[0]));
		C.put(3, getLinearAlgebraT(sensors[4], sensors[0]));
		//System.out.println("C:"+C);
		return C;
	}

	/**
	 * 计算C中的值
	 * 
	 * @param sensor1
	 * @param sensor2
	 * @return
	 */
	private static double getLinearAlgebraT(Sensor sensor1, Sensor sensor2)
	{
		return (getT(sensor1) - getT(sensor2)) / 2;
	}

	/**
	 * 通过传感器信息计算c2t2-(x2+y2+z2) 2为幂
	 * 
	 * @param sensor
	 * @return C*C*t*t-(x*x+y*y+z*z)
	 */
	private static double getT(Sensor sensor)//返回C*C*t*t-(x*x+y*y+z*z)
	{
		//System.out.println("getT:"+getTime(sensor));
		return Math.pow(Parameters.C, 2) * Math.pow(sensor.getSecTime(), 2)
			- (Math.pow(sensor.getz(), 2) + Math.pow(sensor.getx(), 2) + Math.pow(sensor.gety(), 2));
		//C*C*t*t-(x*x+y*y+z*z)
	}

	/**
	 * 得到A矩阵
	 * 
	 * @return
	 */
	private static DoubleMatrix getA(Sensor[] sensors)//A是一个二维数组，存储第一个传感器与其余传感器的时间差、坐标差
	{
		DoubleMatrix A = DoubleMatrix.zeros(4, 4);
		for (int i = 0; i < 4; i++)
		{
			A.putRow(i, getRow(i + 1, sensors));
		}
		return A;
	}

	/**
	 * 
	 * @param i
	 * @param sensors
	 * @return
	 */
	private static DoubleMatrix getRow(int i, Sensor[] sensors)
	{
		DoubleMatrix v = DoubleMatrix.zeros(1, 4);//1行4列一维数组
		v.put(0, Math.pow(Parameters.C, 2) * (sensors[i].getSecTime()-sensors[0].getSecTime()));//c*c*第一个传感器与其余传感器的时间差
		v.put(1, sensors[0].getx() - sensors[i].getx());//第一个传感器与其余传感器的坐标差值y
		v.put(2, sensors[0].gety() - sensors[i].gety());//第一个传感器与其余传感器的坐标差值x
		v.put(3, sensors[0].getz() - sensors[i].getz());//第一个传感器与其余传感器的坐标差值
		
		return v;
	}

	/**
	 * 计算时间   
	 * 
	 * @param sensor
	 *            传递进来的传感器
	 * @return 秒
	 */
	private static long getTime(Sensor sensor)
	{
		// 时间的格式yyyyMMddhhmmss
		String time = sensor.getTime();//获得激发时间，从now容器中第7列中得到的，且第7列时间为年月日时分秒 180712180613 12位
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		Date motiDate = new Date();
		
		try {
			motiDate = format1.parse(time);
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		int sec = motiDate.getHours()*3600+motiDate.getMinutes()*60+motiDate.getSeconds();
		
		//System.out.println(sec);
		return sec;//转换为时间，这个时间好像不对，因为按照十进制+1，而时间是60进制+1
	}
	
	/**
	 * 得到5个传感器总的激发时间
	 * 
	 * @param sensor
	 *            随便一个传感器，目的为了获得年月日
	 * @param time
	 *            单位是秒数
	 * @return
	 */
	private static String getSetTime(Sensor sensor, double inte)
	{
		int time = Math.abs((int) inte);
		String hour1,min1,sec1;
		//System.out.println(inte);
		String st1 = sensor.getTime().substring(0, 10);// 年月日
		//System.out.println(inte);
		// 计算时间
		//System.out.println("time:"+time);
		int hour = time / 3600;
		//System.out.println("hour:"+hour);
		if(hour/10>0)	hour1 = hour+":";
		else	hour1 = "0"+String.valueOf(hour)+":";
		
		int min = (time - hour*3600) / 60;
		if(min/10>0)	min1 = min+":";
		else	min1 = "0"+String.valueOf(min)+":";
		
		int sec = time - hour * 3600 - min * 60;
		if(sec/10>0)	sec1 = String.valueOf(sec);
		else	sec1 = "0"+sec;
		
		//System.out.println(st1 + hour1 + min1 + sec1);
		return st1 + hour1 + min1 + sec1;
	}
}
