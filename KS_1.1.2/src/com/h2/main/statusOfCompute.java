/**
 * 
 */
package com.h2.main;

import DataExchange.Sensor;

/**
 * @author Hanlin Zhang
 */
public class statusOfCompute {
	private Sensor[] sensors1 = null;
	private String panfu = null;
	
	statusOfCompute(){
		
	}
	
	public void setPanfu(String panfu) {
		this.panfu = panfu;
	}
	public String getPanfu() {
		return panfu;
	}
	
	public void setSensors1(Sensor[] s) {
		this.sensors1 = s;
	}
	public Sensor[] getSensors1() {
		return sensors1;
	}
}
