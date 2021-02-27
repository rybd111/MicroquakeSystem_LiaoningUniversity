/**
 * @author 韩百硕
 * 给传感器创建一个类
 */
package DataExchange;

import java.util.Vector;
import com.h2.constant.Parameters;
import com.h2.tool.CrestorTrough;

import controller.ADMINISTRATOR;

/**
 * 保存传感器状态信息。
 * @author Baishuo Han, Hanlin Zhang.
 *
 */
public class Sensor {
	
	public Sensor() {
		this.z = 0;
		this.y = 0;
		this.x = 0;
		/**排序相关*/
		this.sensorNum=0;
		this.sensorSeries = 0;
		/**激发相关*/
		this.sign = false;// 标识是否激发
		this.lineSeries=0;//记录5个台站激发位置
		this.lineSeriesNew=0;
		/**震级能量相关*/
		this.Max1 = 0;// 通道1的最大值
		this.Max2 = 0;// 通道2的最大值
		this.Max4 = 0;// 通道4的最大值
		this.Max5 = 0;// 通道5的最大值
		this.Bn = 0;// An中两个记录间的记录数，用于确定时间
		this.Be = 0;// Ae中两个记录间的记录数，用于确定时间
		this.time = "000000000000";// 激发的时间
		this.quackTime="000000000000";//发震时刻
		this.fudu = 0;// 最大幅度
		this.EarthClassFinal = 0;//M平均值之前的求和
		this.duringTime = 0.0;
		this.eachEnergy = 0.0;
		/**类型相关*/
		this.class1 = 0;
		/**定位相关*/
		this.crestortrough=null;//判断激发后对其进行赋值
		/**保存路径相关*/
		this.panfu = "";
		this.filename_S = "";
	}

	/**
	 * 
	 * @param manager used to get the base sensor's coordination.
	 * @return String a String join all attributes after calculating the location.
	 * @author Hanlin Zhang.
	 * @revision 2020年11月30日
	 */
	public String toString(ADMINISTRATOR manager) {
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		
		this.x = x+Parameters.SENSORINFO1[Parameters.diskNameNum][manager.getBaseCoordinate()][0];
		this.y = y+Parameters.SENSORINFO1[Parameters.diskNameNum][manager.getBaseCoordinate()][1];
		this.z = z+Parameters.SENSORINFO1[Parameters.diskNameNum][manager.getBaseCoordinate()][2];
		return nf.format(x) + " "
		     + nf.format(y) + " "
		     + nf.format(z);
	}
	
	/**
	 * This function are the same as the toString, return the coordination after calculating.
	 * But no adding base coordination.
	 * @return a String join all attributes after calculating the location.
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月27日上午10:39:16
	 */
	public String toString_NOadd() {
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		if(Parameters.offline == false) {
			return nf.format(x) + " "
			     + nf.format(y) + " "
			     + nf.format(z);
		}
		else if(Parameters.offline == true) {
			return nf.format(x) + " "
			     + nf.format(y) + " "
			     + nf.format(z);
		}
		return "Error";
	}
	
	/**coordination*/
	private double y;
	private double x;
	private double z;
	public double getx() {return x;}//get y.
	public void setx(double x) {this.x = x;}
	public double gety() {return y;}//get x.
	public void sety(double y) {this.y = y;}
	public double getz() {return z;}//get z.
	public void setz(double z) {this.z = z;}
	/**标识是否被激发*/
	private boolean sign;
	public boolean isSign() {return sign;}
	public void setSign(boolean sign) {this.sign = sign;}
	/**激发的时间，时间12位格式为yyMMddhhmmss*/
	private String time;
	public String getTime() {return time;	}
	public void setTime(String time) {this.time = time;}
	/**P波相对到时*/
	private double Sectime;
	public double getSecTime() {return Sectime;}
	public void setSecTime(double Sectime) { this.Sectime=Sectime;}
	/**P波绝对到时*/
	private String AbsoluteTime;
	public String getAbsoluteTime() {return AbsoluteTime;}
	public void setAbsoluteTime(String AbsoluteTime) {this.AbsoluteTime = AbsoluteTime;}
	/**激发的时间，时间12位格式为yyMMddhhmmss*/
	private String quackTime;
	public String getquackTime() {return quackTime;}
	public void setquackTime(String time) {this.quackTime=time;}
	/**传感器最大振幅，用于震级计算*/
	private double fudu;
	public double getFudu() {return fudu;}
	public void setFudu(double fudu) {this.fudu = fudu;}
	/**通道x的最大值*/
	private int Max1;
	private int Max2;
	private int Max4;
	private int Max5;
	public int getMax4() {return Max4;}
	public void setMax4(int max4) {Max4 = max4;}
	public int getMax5() {return Max5;}
	public void setMax5(int max5) {Max5 = max5;}
	public int getMax1() {return Max1;}
	public void setMax1(int max1) {Max1 = max1;}
	public int getMax2() {return Max2;}
	public void setMax2(int max2) {Max2 = max2;}
	/**东西方向的时间间隔*/
	private double Bn;
	private double Be;
	public double getBn() {return Bn;}
	public void setBn(double bn) {Bn = bn;}
	public double getBe() {return Be;}
	public void setBe(double be) {Be = be;}
	/**最后求得的每个传感器计算出来的震级*/
	private double EarthClassFinal;
	public double getEarthClassFinal() {return EarthClassFinal;}
	public void setEarthClassFinal(double earthClassFinal) {EarthClassFinal = earthClassFinal;}
	/**保存三台定位激发位置处的振幅*/
	private CrestorTrough crestortrough;
	public CrestorTrough getCrestortrough() {return crestortrough;}
	public void setCrestortrough(CrestorTrough crestortrough) {this.crestortrough = crestortrough;}
	/**激发位置，在now容器中*/
	private int lineSeries;
	public void setlineSeries(int lineSeries) {this.lineSeries = lineSeries;}
	public int getlineSeries() {return this.lineSeries;}
	/**motivation position in cutData of 18s.*/
	private int lineSeriesNew;
	public void setlineSeriesNew(int lineSeriesNew) {this.lineSeriesNew = lineSeriesNew;}
	public int getlineSeriesNew() {return this.lineSeriesNew;}
	/**持续时间震级*/
	private double duringTime;
	public double getDuring() {return duringTime;}
	public void setDuring(double duringTime) {this.duringTime = duringTime;}
	/**energy*/
	private double eachEnergy;
	public double getEnergy() {return eachEnergy;}
	public void setEnergy(double eachEnergy) {this.eachEnergy = eachEnergy;}
	/**class of data*/
	private int class1;
	public int getClass1() {return class1;}
	public void setClass1(int class1) {this.class1 = class1;}
	/**存储激发的盘符*/
	private String panfu;
	public String getpanfu() {return panfu;}
	public void setpanfu(String panfu) {this.panfu = panfu;}
	/**存储文件名*/
	private String filename_S;
	public String getFilename() {return filename_S;}
	public void setFilename_S(String filename_S) {this.filename_S = filename_S;}
	/**the number of the sensor, used to sort them.*/
	private int sensorNum;
	public void setSensorNum(int i) {this.sensorNum=i;}
	public int getSensorNum() {return sensorNum;}
	/**the series of the sensor.*/
	private int sensorSeries;
	public void SetSensorSeries(int i) {this.sensorSeries = i;}
	public int getSensorSeries() {return this.sensorSeries;}
	/**the motivation data of the current sensor.*/
	private Vector<String> motidata;
	public Vector<String> getCutVectorData() {return motidata;}
	public void setCutVectorData(Vector<String> motidata) {this.motidata = (Vector<String>) motidata.clone();}
	/**the size of calculation window.*/
	private Vector<String> tendata;
	public Vector<String> getTenVectorData() {return tendata;}
	public void setTenVectorData(Vector<String> tendata) {this.motidata = (Vector<String>) tendata.clone();}
	/**the initialization extreme-value*/
	private double Initialextremum;
	public void setInitialextremum(double Initialextremum) {this.Initialextremum=Initialextremum;}
	public double getInitialextremum() {return Initialextremum;}
}
