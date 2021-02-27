/**
 * 
 */
package utils;

import com.h2.constant.Parameters;

/**
 * @author Hanlin Zhang
 */
public class CoorProcess {

	/**
	 * 处理各矿区的坐标转换问题，主要是在存入数据库前和重定位使用(存入数据库)，只用于在矿区图中显示使用，不用于控制台显示。
	 * @param xdata
	 * @param ydata
	 * @return x and y after converting.
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月27日上午10:20:49
	 */
	public static double[] coorProcess(
			double xdata, 
			double ydata
			) {
		//平顶山需要转换坐标
		if(Parameters.region.equals("pingdingshan")) {
			ConvertCoordination_base c = new ConvertCoordination_pingdingshan(xdata, ydata);
			System.out.println("坐标转换完毕！！！");
			return c.getCoordination();
		}
		//其他矿区目前，不需要转换
		else {
			ConvertCoordination_base c = new ConvertCoordination_base(xdata, ydata);
			System.out.println("当前矿区坐标无需转换！！！");
			return c.getCoordination();
		}
	}
	
	
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月27日上午10:02:52
	 */
	public static void main(String[] args) {

	}

}
