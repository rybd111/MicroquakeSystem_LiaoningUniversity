package com.h2.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.h2.constant.Parameters;

import DataExchange.Sensor;

/**
 * this function is used to calculate the two location.
 * @author Mingxin Sun.
 */
public class Doublelocate {
	//双台定位
		public static Sensor doubleStationLocate(Sensor[] triggersensor){
			double x1,y1,z1,x2,y2,z2;//取出触发的两个传感器的坐标
			x1=triggersensor[0].getLatitude();
			y1=triggersensor[0].getLongtitude();
			z1=triggersensor[0].getAltitude();
			x2=triggersensor[1].getLatitude();
			y2=triggersensor[1].getLongtitude();
			z2=triggersensor[1].getAltitude();
			//取出波峰或波谷
			CrestorTrough crestortrough1=triggersensor[0].getCrestortrough();
			CrestorTrough crestortrough2=triggersensor[1].getCrestortrough();
			Sensor epicenter=new Sensor();
			if(isSamePlane(triggersensor)){
				//共面（两直线交点）
				double t1=((crestortrough2.getM()*(x1-x2)-crestortrough2.getL()*(y1-y2))/
						(crestortrough2.getL()*crestortrough1.getM()-crestortrough1.getL()*crestortrough2.getM()));
				//double t2=((crestortrough1.getM()*(sensor2.getX()-sensor1.getX())-crestortrough1.getL()*(sensor2.getY()-sensor1.getY()))/(crestortrough1.getL()*crestortrough2.getM()-crestortrough2.getL()*crestortrough1.getM()));
				
				epicenter.setLatitude(x1+crestortrough1.getL()*t1);
				epicenter.setLongtitude(y1+crestortrough1.getM()*t1);
				epicenter.setAltitude(z1+crestortrough1.getN()*t1);
			}else{
				//异面
				double gc1=crestortrough1.getN()*crestortrough2.getM()-crestortrough2.getN()*crestortrough1.getM();//公垂线方向向量三分量
				double gc2=crestortrough2.getN()*crestortrough1.getL()-crestortrough1.getN()*crestortrough2.getL();
				double gc3=crestortrough2.getL()*crestortrough1.getM()-crestortrough1.getL()*crestortrough2.getM();
				//中间变量
				double t1=((x2-x1)*(crestortrough1.getM()*gc3-crestortrough1.getN()*gc2)-(y2-y1)*(crestortrough1.getL()*gc3-crestortrough1.getN()*gc1)+(z2-z1)*(crestortrough1.getL()*gc2-crestortrough1.getM()*gc1))/
						(crestortrough2.getL()*(crestortrough1.getM()*gc3-crestortrough1.getN()*gc2)-crestortrough2.getM()*(crestortrough1.getL()*gc3-crestortrough1.getN()*gc1)+crestortrough2.getN()*(crestortrough1.getL()*gc2-crestortrough1.getM()*gc1))*(-1);
				double t2=((x1-x2)*(crestortrough2.getM()*gc3-crestortrough2.getN()*gc2)-(y1-y2)*(crestortrough2.getL()*gc3-crestortrough2.getN()*gc1)+(z1-z2)*(crestortrough2.getL()*gc2-crestortrough2.getM()*gc1))/
						(crestortrough1.getL()*(crestortrough2.getM()*gc3-crestortrough2.getN()*gc2)-crestortrough1.getM()*(crestortrough2.getL()*gc3-crestortrough2.getN()*gc1)+crestortrough1.getN()*(crestortrough2.getL()*gc2-crestortrough2.getM()*gc1))*(-1);
				//System.out.println(t1);
				//System.out.println(t2);
				//两个交点
				Sensor intersection1=new Sensor();
				Sensor intersection2=new Sensor();
				intersection1.setLatitude(x2+crestortrough2.getL()*t1);
				intersection1.setLongtitude(y2+crestortrough2.getM()*t1);
				intersection1.setAltitude(z2+crestortrough2.getN()*t1);
				//System.out.println(intersection1);
				//System.out.println(sensor2.getZ()+"  "+crestortrough2.getN()*t1);
				//System.out.println(sensor2.getZ()+crestortrough2.getN()*t1);
				
				intersection2.setLatitude(x1+crestortrough1.getL()*t2);
				intersection2.setLongtitude(y1+crestortrough1.getM()*t2);
				intersection2.setAltitude(z1+crestortrough1.getN()*t2);
				//System.out.println(intersection2);
				//System.out.println(sensor1.getY()+"  "+crestortrough1.getM()*t2);
				//两个交点的中点
				epicenter.setLatitude((intersection1.getLatitude()+intersection2.getLatitude())/2);
				epicenter.setLongtitude((intersection1.getLongtitude()+intersection2.getLongtitude())/2);
				epicenter.setAltitude((intersection1.getAltitude()+intersection2.getAltitude())/2);
				
			}
			return epicenter;
		}
		//判断是否在同一平面
		public static boolean isSamePlane(Sensor[] triggersensor){
			double x1,y1,z1,x2,y2,z2;//取出触发的两个传感器的坐标
			x1=triggersensor[0].getLatitude();
			y1=triggersensor[0].getLongtitude();
			z1=triggersensor[0].getAltitude();
			x2=triggersensor[1].getLatitude();
			y2=triggersensor[1].getLongtitude();
			z2=triggersensor[1].getAltitude();
			//取出波峰或波谷
			CrestorTrough crestortrough1=triggersensor[0].getCrestortrough();
			CrestorTrough crestortrough2=triggersensor[1].getCrestortrough();
			//判定行列式的值
			double decide=(x2-x1)*(crestortrough1.getM()*crestortrough2.getN()-crestortrough2.getM()*crestortrough1.getN())
						 -(y2-y1)*(crestortrough1.getL()*crestortrough2.getN()-crestortrough2.getL()*crestortrough1.getN())
					     +(z2-z1)*(crestortrough1.getL()*crestortrough2.getM()-crestortrough2.getL()*crestortrough1.getM());
			if(decide==0){
				return true;
			}else{
				return false;
			}
		}
		//发震时刻=触发时间-传播用时（其中涉及到类型转换）
		public static double quakeTime(Sensor sensor,Sensor location) {
			double distance=QuakeClass.getDistance(location, sensor);//震源与传感器之间的距离
			double t0=distance/Parameters.C;
			double time=0.0;
			time = sensor.getSecTime()-t0;

			return time;
		}
		//取出传感器触发时间并转化为秒
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
		
		//修改时间
		private static String getSetTime(Sensor sensor, double t)
		{
			int time = Math.abs((int) t);
			String hour1,min1,sec1;
			//System.out.println(t);
			String st1 = sensor.getTime().substring(0, 10);// 年月日 yyyy-MM-ddHH:mm:ss
			//System.out.println(t);
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
		
		//三台定位
		public static Sensor tripleStationLocate(Sensor[] triggersensor) {
			Sensor epicenter=new Sensor();
			Sensor[] sen1={triggersensor[0],triggersensor[1]};
			Sensor[] sen2={triggersensor[1],triggersensor[2]};
			Sensor[] sen3={triggersensor[0],triggersensor[2]};
			//三个利用两点定位确定的点
			Sensor point1=new Sensor();
			Sensor point2=new Sensor();
			Sensor point3=new Sensor();
			point1=Doublelocate.doubleStationLocate(sen1);
			point2=Doublelocate.doubleStationLocate(sen2);
			point3=Doublelocate.doubleStationLocate(sen3);
			epicenter.setLatitude((point1.getLatitude()+point2.getLatitude()+point3.getLatitude())/3);
			epicenter.setLongtitude((point1.getLongtitude()+point2.getLongtitude()+point3.getLongtitude())/3);
			epicenter.setAltitude((point1.getAltitude()+point2.getAltitude()+point3.getAltitude())/3);
			return epicenter;
		}

}
