/**
 * 
 */
package utils;

import DataExchange.Sensor;

/**
 * @author Hanlin Zhang
 */
public class DistanceAroundSensors {

	/** the earth radius.*/
	private static final double EARTH_RADIUS = 6378137;
	
	/**
	 * Calculate the distance between two sensors by spherical distance of the earth.
	 * @param s1 sensor1.
	 * @param s2 sensor2.
	 * @return The distance among each sensor to the others.
	 * @author Baishuo Han.
	 */
	public static double getDistance(Sensor s1, Sensor s2)
	{
		// http://www.jianshu.com/p/18efaabab98e
		double radLat1 = rad(s1.getx() / 100);
		double radLat2 = rad(s2.getx() / 100);
		double a = radLat1 - radLat2;
		double b = rad(s1.gety() / 100) - rad(s2.gety() / 100);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return Math.abs(s / 1000);
	}
	
	/**
	 * The angle transfer to the radian.
	 * @param d the angle.
	 * @return the radian.
	 * @author Baishuo Han.
	 */
	private static double rad(double d)
	{
		return d * Math.PI / 180.0;
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月23日下午3:03:32
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
