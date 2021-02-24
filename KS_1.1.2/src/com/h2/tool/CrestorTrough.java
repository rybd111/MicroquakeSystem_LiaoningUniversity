package com.h2.tool;

import com.h2.constant.Parameters;

import DataExchange.Sensor;

/**
 * save the three location status.
 * @author Mingxin Sun.
 */
public class CrestorTrough {
	public final int VP=Parameters.C;//p波速
	public final double VS=Parameters.S;//s波速
	private double Ae;//波峰或者波谷点三分量
	private double An;
	private double Az;
	private double e1;//视出射角
	private double e;//真出射角
	private double a;
	private double l;//运算中间量
	private double m;
	private double n;
    
	public  CrestorTrough(Double x,Double y,Double z) {

		Ae=x;//三分量
		An=y;
		Az=z;
		e1=Math.atan(Math.abs(Az)/(Math.sqrt(Math.pow(Ae, 2)+Math.pow(An, 2))));
		if(e1<0){
			e1=Math.PI+e1;
		}
		e=Math.acos((VP/VS)*Math.cos((Math.PI/2+e1)*1/2));
		a=Math.atan(Math.abs(An)/Math.abs(Ae));
		l=Math.cos(e)*Math.cos(a);
		m=Math.cos(e)*Math.sin(a);
		n=Math.sin(e);
	}
	public double getE() {
		return e;
	}
	public  double getE1() {
		return e1;
	}
	public double getL() {
		return l;
	}	
	public double getM() {
		return m;
	}	
	public double getN() {
		return n;
	}
	

}
