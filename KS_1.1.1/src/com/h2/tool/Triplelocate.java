package com.h2.tool;

import DataExchange.Sensor;

/**
 * three location class.
 * @author Mingxin Sun.
 *
 */
public class Triplelocate {
	//三台定位，利用双台定位，每两个传感器形成一个交点，取三点的平均数
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
