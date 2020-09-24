package bean;

import java.util.List;

/**
 * get extreme value of list type.
 * @author unknown
 *
 */
public class JiZhi {
	
	int xMin = 0;
	int yMin = 0;
	int zMin = 0;
	int xMax = 0;
	int yMax = 0;
	int zMax = 0;
	
	List<String> xMinTime;
	List<String> yMinTime;
	List<String> zMinTime;
	List<String> xMaxTime;
	List<String> yMaxTime;
	List<String> zMaxTime;
	
	
	public JiZhi(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax,
			List<String> xMinTime, List<String> yMinTime,
			List<String> zMinTime, List<String> xMaxTime,
			List<String> yMaxTime, List<String> zMaxTime) {
		super();
		this.xMin = xMin;
		this.yMin = yMin;
		this.zMin = zMin;
		this.xMax = xMax;
		this.yMax = yMax;
		this.zMax = zMax;
		this.xMinTime = xMinTime;
		this.yMinTime = yMinTime;
		this.zMinTime = zMinTime;
		this.xMaxTime = xMaxTime;
		this.yMaxTime = yMaxTime;
		this.zMaxTime = zMaxTime;
	}
	public JiZhi() {
		// TODO Auto-generated constructor stub
	}
	public int getxMin() {
		return xMin;
	}
	public void setxMin(int xMin) {
		this.xMin = xMin;
	}
	public int getyMin() {
		return yMin;
	}
	public void setyMin(int yMin) {
		this.yMin = yMin;
	}
	public int getzMin() {
		return zMin;
	}
	public void setzMin(int zMin) {
		this.zMin = zMin;
	}
	public int getxMax() {
		return xMax;
	}
	public void setxMax(int xMax) {
		this.xMax = xMax;
	}
	public int getyMax() {
		return yMax;
	}
	public void setyMax(int yMax) {
		this.yMax = yMax;
	}
	public int getzMax() {
		return zMax;
	}
	public void setzMax(int zMax) {
		this.zMax = zMax;
	}
	public List<String> getxMinTime() {
		return xMinTime;
	}
	public void setxMinTime(List<String> xMinTime) {
		this.xMinTime = xMinTime;
	}
	public List<String> getyMinTime() {
		return yMinTime;
	}
	public void setyMinTime(List<String> yMinTime) {
		this.yMinTime = yMinTime;
	}
	public List<String> getzMinTime() {
		return zMinTime;
	}
	public void setzMinTime(List<String> zMinTime) {
		this.zMinTime = zMinTime;
	}
	public List<String> getxMaxTime() {
		return xMaxTime;
	}
	public void setxMaxTime(List<String> xMaxTime) {
		this.xMaxTime = xMaxTime;
	}
	public List<String> getyMaxTime() {
		return yMaxTime;
	}
	public void setyMaxTime(List<String> yMaxTime) {
		this.yMaxTime = yMaxTime;
	}
	public List<String> getzMaxTime() {
		return zMaxTime;
	}
	public void setzMaxTime(List<String> zMaxTime) {
		this.zMaxTime = zMaxTime;
	}
	
	
	

}
