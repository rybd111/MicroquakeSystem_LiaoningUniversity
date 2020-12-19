package visual;
//从上述网址查询jfreechart的相关资料
//https://blog.csdn.net/danmo598/article/details/21541177

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second;
//import org.jfree.data.time.TimeSeries;
//import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

import com.h2.constant.Parameters;

import mutiThread.MainThread;

public class chartFrame extends Thread{
	
	public boolean exitVariable = false;
//	private TimeSeries timeSeries;
	private XYSeries timeSeries;
	private int number = 0;
	private JFrame Disaster;
	private Shell shlHistory;
	//设置Linechart表的位置
	public JPanel chartCon(int x, int y,int num, String chartTitle, String yaxisName) {
		//第一个参数为图例，第二个参数为表标题，第三个为y轴名称
		JFreeChart chart = createChart("", chartTitle, yaxisName);
		ChartPanel jpanel = new ChartPanel(chart);
		
		jpanel.setSize(Disaster.getSize().width-10, Disaster.getSize().height/(num)-10);//get the size of parent window.
		jpanel.setLocation(x, y);
		jpanel.setVisible(true);
		
		return jpanel;
	}
	
//	public JPanel chartConSWT(int x, int y,int num, String xaxisName, String chartTitle) {
	public JPanel chartConSWT(int x, int y,int num) {
		JFreeChart chart = createChart("", "", "range");
		ChartPanel jpanel = new ChartPanel(chart);
		
		jpanel.setSize(shlHistory.getSize().x, shlHistory.getSize().y/num);//get the size of parent window.
		jpanel.setLocation(x, y);
		jpanel.setVisible(true);
		
		return jpanel;
	}
	
	public chartFrame(){
		super();
	}
	
	public chartFrame(JFrame Disaster, int num){
		super();
		this.number = num;
		this.Disaster = Disaster;
	}
	
	public chartFrame(Shell shlHistory){
		super();
		this.shlHistory = shlHistory;
	}
	
	/**
	 * 
	 * @param chartContent
	 * @param title
	 * @param yaxisName
	 * @return
	 * @author Hanlin Zhang.
	 */
	@SuppressWarnings("deprecation")
	private JFreeChart createChart(String chartContent, String title, String yaxisName) {
		//创建时序图对象
//		timeSeries = new TimeSeries(chartContent, Second.class);
		timeSeries = new XYSeries(yaxisName);
		XYSeriesCollection timeseriescollection = new XYSeriesCollection(timeSeries);
//		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeSeries);
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title,"time(s)", yaxisName, timeseriescollection, true, true, false);
		XYPlot xyplot = jfreechart.getXYPlot();
		//纵坐标设定
		ValueAxis valueaxis = xyplot.getDomainAxis();
		//自动设置数据轴数据范围
		valueaxis.setAutoRange(true);
		//数据轴固定数据范围 30s
		valueaxis.setFixedAutoRange(5000D);
		valueaxis = xyplot.getRangeAxis();
		//valueaxis.setRange(0.0D,200D);  
		return jfreechart;
	}
	
	//update the time series data.
	public void run() {
		int count = 1;
		while (true) {
//			timeSeries.add(new Second(), randomNum());
			timeSeries.add(count, historyData(count));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
			if(exitVariable==true) {
	        	break;
	        }
		}
	}
	//random update.
//	private long randomNum() {
//		System.out.println((Math.random() * 20));
//		return (long) (Math.random() * 20);
//	}
	
	public int historyData(int series) {
		//get the data from history records.
		
		if(MainThread.aDataRec[number].afterVector!=null) {
			Vector<String> data = MainThread.aDataRec[number].afterVector;
			int dataItem = Integer.valueOf(data.get(series).split(" ")[5]);
//			System.out.println(data.get(series).split(" ")[6].substring(10));
//			System.out.println(dataItem);
			return dataItem;
		}
		else {
			return 0;
		}
	}
}
