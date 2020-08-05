/**
 * @author 韩百硕
 * 给传感器创建一个类
 */
package com.h2.constant;

import java.util.Vector;
import com.h2.tool.CrestorTrough;
import com.h2.tool.SensorTool;

/**
 * 
 * @author Baishuo Han, Hanlin Zhang.
 *
 */
public class Sensor {
	public Sensor() {
		this.sensorNum=0;
		this.sign = false;// 标识是否激发
		this.time = "000000000000";// 激发的时间
		this.quackTime="000000000000";//发震时刻
		this.fudu = 0;// 最大幅度
		this.EarthClassFinal = 0;//M平均值之前的求和
		this.duringTime = 0.0;
		this.eachEnergy = 0.0;
		this.class1 = 0;
		
		this.Altitude = 0;// 海拔z
		this.Longtitude = 0;// 经度y
		this.Latitude = 0;// 维度x
		this.lineSeries=0;//记录5个台站激发位置
		this.lineSeriesNew=0;
		this.panfu = "";
		this.filename_S = "";
		// 用于震级的计算
		Max1 = 0;// 通道1的最大值
		Max2 = 0;// 通道2的最大值
		Max4 = 0;// 通道4的最大值
		Max5 = 0;// 通道5的最大值
		Bn = 0;// An中两个记录间的记录数，用于确定时间
		Be = 0;// Ae中两个记录间的记录数，用于确定时间
		crestortrough=null;//判断激发后对其进行赋值
	}

	public void AddCoor() {
		if(Parameters.offline == false) {
			this.Latitude = Latitude+Parameters.SENSORINFO[SensorTool.baseCoordinate][0];
			this.Longtitude = Longtitude+Parameters.SENSORINFO[SensorTool.baseCoordinate][1];
			this.Altitude = Altitude+Parameters.SENSORINFO[SensorTool.baseCoordinate][2];
		}
		else if(Parameters.offline == true) {
			if(Parameters.region_offline=="hongyang") {
				this.Latitude = Latitude + Parameters.SENSORINFO_offline_hongyang[SensorTool.baseCoordinate][0];
				this.Longtitude = Longtitude + Parameters.SENSORINFO_offline_hongyang[SensorTool.baseCoordinate][1];
				this.Altitude = Altitude+Parameters.SENSORINFO_offline_hongyang[SensorTool.baseCoordinate][2];
			}
			if(Parameters.region_offline=="datong") {
				this.Latitude = Latitude + Parameters.SENSORINFO_offline_datong[SensorTool.baseCoordinate][0];
				this.Longtitude = Longtitude + Parameters.SENSORINFO_offline_datong[SensorTool.baseCoordinate][1];
				this.Altitude = Altitude+Parameters.SENSORINFO_offline_datong[SensorTool.baseCoordinate][2];
			}
			if(Parameters.region_offline=="pingdingshan") {
				this.Latitude = Latitude + Parameters.SENSORINFO_offline_pingdingshan[SensorTool.baseCoordinate][0];
				this.Longtitude = Longtitude + Parameters.SENSORINFO_offline_pingdingshan[SensorTool.baseCoordinate][1];
				this.Altitude = Altitude+Parameters.SENSORINFO_offline_pingdingshan[SensorTool.baseCoordinate][2];
			}
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	/**
	 * return a String join all attributes after calculating the location.
	 */
	public String toString() {
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		if(Parameters.offline == false) {
			this.Latitude = Latitude+Parameters.SENSORINFO[SensorTool.baseCoordinate][0];
			this.Longtitude = Longtitude+Parameters.SENSORINFO[SensorTool.baseCoordinate][1];
			this.Altitude = Altitude+Parameters.SENSORINFO[SensorTool.baseCoordinate][2];
			return nf.format(Latitude) + " "
			     + nf.format(Longtitude) + " "
			     + nf.format(Altitude);
		}
		else if(Parameters.offline == true) {
			if(Parameters.region_offline=="hongyang") {
				this.Latitude = Latitude + Parameters.SENSORINFO_offline_hongyang[SensorTool.baseCoordinate][0];
				this.Longtitude = Longtitude + Parameters.SENSORINFO_offline_hongyang[SensorTool.baseCoordinate][1];
				this.Altitude = Altitude+Parameters.SENSORINFO_offline_hongyang[SensorTool.baseCoordinate][2];
				return nf.format(Latitude) + " "
				     + nf.format(Longtitude) + " "
				     + nf.format(Altitude);
			}
			if(Parameters.region_offline=="datong") {
				this.Latitude = Latitude + Parameters.SENSORINFO_offline_datong[SensorTool.baseCoordinate][0];
				this.Longtitude = Longtitude + Parameters.SENSORINFO_offline_datong[SensorTool.baseCoordinate][1];
				this.Altitude = Altitude+Parameters.SENSORINFO_offline_datong[SensorTool.baseCoordinate][2];
				return nf.format(Latitude) + " "
				     + nf.format(Longtitude) + " "
				     + nf.format(Altitude);
			}
			if(Parameters.region_offline=="pingdingshan") {
				this.Latitude = Latitude + Parameters.SENSORINFO_offline_pingdingshan[SensorTool.baseCoordinate][0];
				this.Longtitude = Longtitude + Parameters.SENSORINFO_offline_pingdingshan[SensorTool.baseCoordinate][1];
				this.Altitude = Altitude+Parameters.SENSORINFO_offline_pingdingshan[SensorTool.baseCoordinate][2];
				return nf.format(Latitude) + " "
				     + nf.format(Longtitude) + " "
				     + nf.format(Altitude);
			}
		}
		return "Error";
	}
	
	public String toString_NOadd() {
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		if(Parameters.offline == false) {
			return nf.format(Latitude) + " "
			     + nf.format(Longtitude) + " "
			     + nf.format(Altitude);
		}
		else if(Parameters.offline == true) {
			if(Parameters.region_offline=="hongyang") {
				return nf.format(Latitude) + " "
				     + nf.format(Longtitude) + " "
				     + nf.format(Altitude);
			}
			if(Parameters.region_offline=="datong") {
				return nf.format(Latitude) + " "
				     + nf.format(Longtitude) + " "
				     + nf.format(Altitude);
			}
			if(Parameters.region_offline=="pingdingshan") {
				return nf.format(Latitude) + " "
				     + nf.format(Longtitude) + " "
				     + nf.format(Altitude);
			}
		}
		return "Error";
	}
	
	//get each sensor's series number to mark them.
	public void setSensorNum(int i) {this.sensorNum=i;}
	public int getSensorNum() {return sensorNum;}
	
	//get near quake grade of each sensor.
	public double getEarthClassFinal() {return EarthClassFinal;}
	public void setEarthClassFinal(double earthClassFinal) {EarthClassFinal = earthClassFinal;}

	//get during time qucke grade of each sensor.
	public double getDuring() {return duringTime;}
	public void setDuring(double duringTime) {this.duringTime = duringTime;}
	
	//get energy of each sensor.
	public double getEnergy() {return eachEnergy;}
	public void setEnergy(double eachEnergy) {this.eachEnergy = eachEnergy;}
	
	public int getClass1() {return class1;}
	public void setClass1(int class1) {this.class1 = class1;}
	
	//sign this sensor is not motivation.
	public boolean isSign() {return sign;}
	public void setSign(boolean sign) {this.sign = sign;}

	//the motivation time of every sensor.
	public String getTime() {return time;	}
	public void setTime(String time) {this.time = time;}
	
	//the P arrival time in millisecond.
	public double getSecTime() {return Sectime;}
	public void setSecTime(double Sectime) { this.Sectime=Sectime;}

	//the P arrival time, which is absolute time.
	public String getAbsoluteTime() {return AbsoluteTime;}
	public void setAbsoluteTime(String AbsoluteTime) {this.AbsoluteTime = AbsoluteTime;}
	
	//get the time of quack in seconds.
	public String getquackTime() {return quackTime;}
	public void setquackTime(String time) {this.quackTime=time;}

	//get x.
	public double getLongtitude() {return Longtitude;}
	public void setLongtitude(double longtitude) {Longtitude = longtitude;}

	//get y.
	public double getLatitude() {return Latitude;}
	public void setLatitude(double latitude) {Latitude = latitude;}

	//get z.
	public double getAltitude() {return Altitude;}
	public void setAltitude(double altitude) {Altitude = altitude;}

	//15s data.
	public Vector<String> getCutVectorData() {return motidata;}
	public void setCutVectorData(Vector<String> motidata) {this.motidata = (Vector<String>) motidata.clone();}
	
	//10s data.
	public Vector<String> getTenVectorData() {return tendata;}
	public void setTenVectorData(Vector<String> tendata) {this.motidata = (Vector<String>) tendata.clone();}

	//获取幅度le
	public double getFudu() {return fudu;}
	public void setFudu(double fudu) {this.fudu = fudu;}
	
	public void setlineSeries(int lineSeries) {this.lineSeries = lineSeries;}
	public int getlineSeries() {return this.lineSeries;}

	public void setlineSeriesNew(int lineSeriesNew) {this.lineSeriesNew = lineSeriesNew;}
	public int getlineSeriesNew() {return this.lineSeriesNew;}
	
	public int getMax4() {return Max4;}
	public void setMax4(int max4) {Max4 = max4;}

	public int getMax5() {return Max5;}
	public void setMax5(int max5) {Max5 = max5;}

	public int getMax1() {return Max1;}
	public void setMax1(int max1) {Max1 = max1;}

	public int getMax2() {return Max2;}
	public void setMax2(int max2) {Max2 = max2;}

	public double getBn() {return Bn;}
	public void setBn(double bn) {Bn = bn;}

	public double getBe() {return Be;}
	public void setBe(double be) {Be = be;}
	
	//获取盘符
	public String getpanfu() {return panfu;}
	public void setpanfu(String panfu) {this.panfu = panfu;}

	public String getFilename() {return filename_S;}
	public void setFilename_S(String filename_S) {this.filename_S = filename_S;}
	public CrestorTrough getCrestortrough() {return crestortrough;}
	public void setCrestortrough(CrestorTrough crestortrough) {this.crestortrough = crestortrough;}
	
	
	/**
	 * 标识是否被激发
	 */
	private boolean sign;
	/**
	 * 激发的时间，时间12位格式为yyMMddhhmmss
	 */
	private String time;
	/**
	 * P波相对到时
	 */
	private double Sectime;
	/**
	 * P波绝对到时
	 */
	private String AbsoluteTime;
	/**
	 * 激发的时间，时间12位格式为yyMMddhhmmss
	 */
	private String quackTime;
	/**
	 * 传感器最大振幅，用于震级计算
	 */
	private double fudu;
	/**
	 * 经线
	 */
	private double Longtitude;
	/**
	 * 纬线
	 */
	private double Latitude;
	/**
	 * 海拔
	 */
	private double Altitude;

	// 最大震级公式修改后增加的字段
	/**
	 * 通道1的最大值
	 */
	private int Max1;
	/**
	 * 通道2的最大值
	 */
	private int Max2;
	/**
	 * 通道4的最大值
	 */
	private int Max4;
	/**
	 * 通道5的最大值
	 */
	private int Max5;
	/**
	 * An中的记录数，用于震级计算中获取时间
	 */
	private double Bn;
	/**
	 * Ae中的记录数，用于震级计算中获取时间
	 */
	private double Be;
	/**
	 * 最后求得的每个传感器计算出来的震级
	 */
	private double EarthClassFinal;
	private CrestorTrough crestortrough;
	/**
	 * 激发位置，在now容器中
	 */
	private int lineSeries;
	/**
	 * motivation position in cutData.
	 */
	private int lineSeriesNew;
	/**
	 * 持续时间震级
	 */
	private double duringTime;
	/**energy*/
	private double eachEnergy;
	/**class of data*/
	private int class1;
	/**
	 * 存储激发的盘符
	 */
	private String panfu;
	/**
	 * 存储文件名
	 */
	private String filename_S;
	/**
	 * the number of the sensor.
	 */
	private int sensorNum;
	/**
	 * the motivation data of the current sensor.
	 */
	private Vector<String> motidata;
	/**the size of calculation window.*/
	private Vector<String> tendata;
}
