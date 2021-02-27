package utils;

/**
 * 坐标转换类，如果存在新的坐标转换矿区，则使用该类继承。
 * @author Boqiang Chen, Haiyou Yu, Hanlin Zhang.
 */
public class ConvertCoordination_base {

	private  double x = 0.0;
	private  double y = 0.0;
	
	public ConvertCoordination_base() {
		
	}
	
	public ConvertCoordination_base(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	//父类没有转换。
	public double[] getCoordination() {
		double[] results = new double[2];
		
		results[0] = this.x;
		results[1] = this.y;
		
		return results;
	}

	@Override
	public String toString() {
		String result = "ConvertCoordinates [x=" + x + ", y=" + y + "]";
		System.out.println(result);
		return result;
	}

	public static void main(String[] args) {
		ConvertCoordination_base c = new ConvertCoordination_base(3745746.048,38423009.003);
		c.getCoordination();
		c.toString();
	}
}
