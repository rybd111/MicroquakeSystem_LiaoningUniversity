package utils;


public class ConvertCoordinates {

	private  double x = 0.0;
	private  double y = 0.0;
	
	public ConvertCoordinates() {
		
	}

	public ConvertCoordinates(double x,double y) {
		int angle = 33;
		double radian = angle*Math.PI/180;
		double tempx = y-38420000;
		double tempy = x-3744500;
		double newY = tempy*Math.cos(radian)+tempx*Math.sin(radian);
		double newX = tempx*Math.cos(radian)-tempy*Math.sin(radian);
		this.setX(newX);
		this.setY(newY);
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

	@Override
	public String toString() {
		String result = "ConvertCoordinates [x=" + x + ", y=" + y + "]";
		System.out.println(result);
		return result;
	}


	public static void main(String[] args) {
		ConvertCoordinates c = new ConvertCoordinates(3745746.048,38423009.003);
		c.toString();
	}
}
