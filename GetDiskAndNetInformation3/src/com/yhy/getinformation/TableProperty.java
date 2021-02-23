package com.yhy.getinformation;

/**
 * Table structure
 * @author Haiyou Yu
 * @version 1.0 2020-11-14
 */

public class TableProperty {

	/**
	 * such as 2020-11-15 or 2020-11-16...
	 */
	private String day;
	
	/**
	 * such as Z:
	 */
	private String panfu;
	
	/**
	 * such as 蒿子屯
	 */
	private String location;
	
	/**
	 * x coordination of location
	 */
	private String xData;
	
	/**
	 * y coordination of location
	 */
	private String yData;
	
	/**
	 * z coordination of location
	 */
	private String zData;
	
	/**
	 * online or offline of location
	 */
	private String status;
	
	/**
	 * the unused disk capacity
	 */
	private String unused;
	
	/**
	 * the used disk capacity
	 */
	private String used;
	
	/**
	 * the total disk capacity
	 */
	private String total;
	
	/**
	 * the network speed of station
	 */
	private String netspeed;
	
	public TableProperty() {
	}

	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getPanfu() {
		return panfu;
	}
	public void setPanfu(String panfu) {
		this.panfu = panfu;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getxData() {
		return xData;
	}
	public void setxData(String xData) {
		this.xData = xData;
	}
	public String getyData() {
		return yData;
	}
	public void setyData(String yData) {
		this.yData = yData;
	}
	public String getzData() {
		return zData;
	}
	public void setzData(String zData) {
		this.zData = zData;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUnused() {
		return unused;
	}
	public void setUnused(String unused) {
		this.unused = unused;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getNetspeed() {
		return netspeed;
	}
	public void setNetspeed(String netspeed) {
		this.netspeed = netspeed;
	}

	@Override
	public String toString() {
		return "TableProperty [day=" + day + ", panfu=" + panfu + ", location=" + location + ", xData=" + xData
				+ ", yData=" + yData + ", zData=" + zData + ", status=" + status + ", unused=" + unused + ", used="
				+ used + ", total=" + total + ", netspeed=" + netspeed + "]";
	}

    public String[] getStringArray() {
    	String[] str = {getDay(),getPanfu(),getLocation(),
    			getxData(),getyData(),getzData(),getStatus(),
    			getUnused(),getUsed(),getTotal(),getNetspeed()};
    	return str;
    }
}
