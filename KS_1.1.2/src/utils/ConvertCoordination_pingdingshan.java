package utils;

/**
 * 平顶山坐标转换函数
 * @author Boqiang Chen, Haiyou Yu, Hanlin Zhang.
 */
public class ConvertCoordination_pingdingshan extends ConvertCoordination_base{

	private  double x = 0.0;
	private  double y = 0.0;
	
	public ConvertCoordination_pingdingshan(
			double x,
			double y
			) {
		int angle = 33;
		double radian = angle*Math.PI/180;
		double tempx = y-38420000;
		double tempy = x-3744500;
		double newY = tempy*Math.cos(radian)+tempx*Math.sin(radian);
		double newX = tempx*Math.cos(radian)-tempy*Math.sin(radian);
		this.x = newX;
		this.y = newY;
	}
	
	/**
	 * 返回转换后的坐标结果。
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月27日上午9:56:22
	 */
	public double[] getCoordination() {
		double[] results = new double[2];
		
		results[0] = this.x;
		results[1] = this.y;
		
		return results;
	}
	
	public static void main(String[] args) {
//		ConvertCoordination_base c = new ConvertCoordination_base(3745746.048,38423009.003);
		ConvertCoordination_base c = new ConvertCoordination_pingdingshan(3746837.315,38424737.254);
		
		c.getCoordination();
		c.toString();
//		PSO	3746837.315	-38424737.254	-485.641	2021-01-02 16:59:33.49141500000000005	1.63	-0.908585	vzu	0	624040	D:/data/ConstructionData/3moti/vzu 2021-01-02 16-59-34`4.csv	0.5359	0.1175

	}
}
