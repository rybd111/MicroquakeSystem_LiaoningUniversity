package utils;

/**
 * 平顶山坐标转换函数
 * @author Boqiang Chen, Haiyou Yu.
 */
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
//		ConvertCoordinates c = new ConvertCoordinates(3745746.048,38423009.003);
		ConvertCoordinates c = new ConvertCoordinates(3746837.315,38424737.254);
		c.toString();
//		PSO	3746837.315	-38424737.254	-485.641	2021-01-02 16:59:33.49141500000000005	1.63	-0.908585	vzu	0	624040	D:/data/ConstructionData/3moti/vzu 2021-01-02 16-59-34`4.csv	0.5359	0.1175

	}
}
