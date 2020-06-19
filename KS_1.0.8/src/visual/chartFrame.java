package visual;

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
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

public class chartFrame extends Thread{
	
	public boolean exitVariable = false;
	private TimeSeries timeSeries;
	private JFrame Disaster;
	private Shell shlHistory;
	
	public JPanel chartCon(int x, int y,int num) {
		JFreeChart chart = createChart("", "", "range");
		ChartPanel jpanel = new ChartPanel(chart);
		
		jpanel.setSize(Disaster.getSize().width, Disaster.getSize().height/num);//get the size of parent window.
		jpanel.setLocation(x, y);
		jpanel.setVisible(true);
		
		return jpanel;
	}
	
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
	
	public chartFrame(JFrame Disaster){
		super();
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
		timeSeries = new TimeSeries(chartContent, Millisecond.class);
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeSeries);
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title,"时间(秒)", yaxisName, timeseriescollection, true, true, false);
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
		while (true) {
			try {
				timeSeries.add(new Millisecond(), randomNum());
				Thread.sleep(10);
			} catch (InterruptedException e) {			}
			if(exitVariable==true) {
	        	break;
	        }
		}
	}
	
	//random update.
	private long randomNum() {
		System.out.println((Math.random() * 20));
		return (long) (Math.random() * 20);
	}
}
