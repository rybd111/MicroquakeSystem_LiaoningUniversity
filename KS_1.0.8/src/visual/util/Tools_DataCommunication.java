package visual.util;

import java.io.IOException;
import java.util.ArrayList;
import com.teamdev.jxbrowser.chromium.Browser;
import visual.Main;
import visual.controller.MyCAD;
import visual.controller.MyLineChart;
import visual.controller.MyTableView;
import visual.model.MyPrintStream;
import visual.model.TableData;
import visual.view.MyConsoleController;
import visual.view.UIController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mutiThread.MainThread;

/***
 * 用于类与类之间的数据传输 饿汉式单例模式
 * 
 * @author Sunny-胡永亮
 */
public class Tools_DataCommunication {
	private static Tools_DataCommunication communication = new Tools_DataCommunication();

	private Tools_DataCommunication() {
	}

	public static Tools_DataCommunication getCommunication() {
		return communication;
	}

	// 波形图控制对象
	private MyLineChart mChart = null;

	public MyLineChart getmChart() {
		return mChart;
	}

	// 事件列表控制对象
	private MyTableView mTableView = null;

	public MyTableView getmTableView() {
		return mTableView;
	}

	// CAD控制对象
	private MyCAD mCAD = null;

	public MyCAD getmCAD() {
		return mCAD;
	}

	/** 获取JxBrowser的对象 */
	public Browser jxBrowser = null;
	/** 用于标识是否正在运行客户端程序 */
	public boolean isClient = false;
	/** 用于波形图的数据传递 */
	public ArrayList<ArrayList<Integer>> list;
	/** 用于判断网页CAD是否已绘制完毕 */
	public boolean isFinishLoadCAD = false;
	/** 用于读取CSV文件的秒数 一秒5000条数据 */
	public int readTime = 18;// s
	/** CAD圆的半径 */
	public double circleRadius = 500.0;
	/** 用于TableView的数据传递 */
	public ObservableList dataList = FXCollections.observableArrayList();
	/** 用于标记当前输出控制台的状态 */
	public boolean isShowConsole = false;
	// 内部类
	private Mycontrol mycontrol = new Mycontrol();

	/** 控制输出控制台的显示与关闭 */
	public void showandcloseMyConsole() {
		if (isShowConsole) {// 显示了console
			isShowConsole = false;
			mycontrol.close();
		} else {// 没有显示console
			isShowConsole = true;
			mycontrol.show();
		}
	}

	/** 用于显示CAD */
	public void showCAD(BorderPane mBorderPane) {
		this.mCAD = new MyCAD(mBorderPane);
	}

	/** 将TableColumn与TableData每一个属性连接起来 */
	public void InitTableViewData(TableColumn<TableData, String> eventIndex, TableColumn<TableData, String> eventTime,
			TableColumn<TableData, String> eventLoca, TableColumn<TableData, String> eventPos,
			TableColumn<TableData, String> energy, TableColumn<TableData, String> grade) {
		eventIndex.setCellValueFactory(new PropertyValueFactory<>("eventIndex"));
		eventTime.setCellValueFactory(new PropertyValueFactory<>("eventTime"));
		eventLoca.setCellValueFactory(new PropertyValueFactory<>("eventLoca"));
		eventPos.setCellValueFactory(new PropertyValueFactory<>("eventPos"));
		energy.setCellValueFactory(new PropertyValueFactory<>("energy"));
		grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
	}

	/** 用于显示TableView */
	public void ShowTableView(TableView<TableData> mTableView) {

		this.mTableView = new MyTableView(mTableView);
		this.mTableView.setTableViewData();

		mTableView.setItems(dataList);
	}

	/** 用于显示波形图 */
	public void ShowLineChart(LineChart<Number, Number> T1, LineChart<Number, Number> T2, LineChart<Number, Number> T3,
			LineChart<Number, Number> T4, LineChart<Number, Number> T5, LineChart<Number, Number> T6,
			LineChart<Number, Number> T7, LineChart<Number, Number> T8, LineChart<Number, Number> T9) {
		InitLineChart(T1, T2, T3, T4, T5, T6, T7, T8, T9);
		this.mChart = new MyLineChart(T1, T2, T3, T4, T5, T6, T7, T8, T9);
//		mChart.setLineChart(readTime, "C:/Users/Sunny/Desktop/5moti/25613 2020-05-01 09-33-15`05.csv");// 显示波形图
//		mChart.setLineChart(readTime, "D:/data/ConstructionData/5data/25613 2020-05-01 09-33-15`05.csv");
//		mChart.setLineChart(readTime, "D:/data/ConstructionData/5data/124637 2020-05-20 13-52-55`13.csv");
//		mChart.setLineChart(readTime, "D:/data/ConstructionData/5data/162534 2020-05-01 09-33-12`3.csv");

	}

	/**
	 * 用于初始化坐标轴 如果不执行这个方法，会报 ：java.lang.OutOfMemoryError: GC overhead limit
	 * exceeded错误
	 */
	private void InitLineChart(LineChart<Number, Number> T1, LineChart<Number, Number> T2, LineChart<Number, Number> T3,
			LineChart<Number, Number> T4, LineChart<Number, Number> T5, LineChart<Number, Number> T6,
			LineChart<Number, Number> T7, LineChart<Number, Number> T8, LineChart<Number, Number> T9) {

		final String stockLineChartCss = UIController.class.getResource("Line.css").toExternalForm();
		T1.getStylesheets().add(stockLineChartCss);
		T2.getStylesheets().add(stockLineChartCss);
		T3.getStylesheets().add(stockLineChartCss);
		T4.getStylesheets().add(stockLineChartCss);
		T5.getStylesheets().add(stockLineChartCss);
		T6.getStylesheets().add(stockLineChartCss);
		T7.getStylesheets().add(stockLineChartCss);
		T8.getStylesheets().add(stockLineChartCss);
		T9.getStylesheets().add(stockLineChartCss);

		// T1
		T1.setCreateSymbols(false);
		NumberAxis T1_xAxis = (NumberAxis) T1.getXAxis();
		T1_xAxis.setLowerBound(0);
		T1_xAxis.setTickUnit(5000);
		T1_xAxis.setUpperBound(18 * 5000);
		NumberAxis T1_yAxis = (NumberAxis) T1.getYAxis();
		T1_yAxis.setLowerBound(-20000);
		T1_yAxis.setTickUnit(20000);
		T1_yAxis.setUpperBound(20000);

		// T2
		T2.setCreateSymbols(false);
		NumberAxis T2_xAxis = (NumberAxis) T2.getXAxis();
		T2_xAxis.setLowerBound(0);
		T2_xAxis.setTickUnit(5000);
		T2_xAxis.setUpperBound(18 * 5000);
		NumberAxis T2_yAxis = (NumberAxis) T2.getYAxis();
		T2_yAxis.setLowerBound(-20000);
		T2_yAxis.setTickUnit(20000);
		T2_yAxis.setUpperBound(20000);

		// T3
		T3.setCreateSymbols(false);
		NumberAxis T3_xAxis = (NumberAxis) T3.getXAxis();
		T3_xAxis.setLowerBound(0);
		T3_xAxis.setTickUnit(5000);
		T3_xAxis.setUpperBound(18 * 5000);
		NumberAxis T3_yAxis = (NumberAxis) T2.getYAxis();
		T3_yAxis.setLowerBound(-20000);
		T3_yAxis.setTickUnit(20000);
		T3_yAxis.setUpperBound(20000);

		// T4
		T4.setCreateSymbols(false);
		NumberAxis T4_xAxis = (NumberAxis) T4.getXAxis();
		T4_xAxis.setLowerBound(0);
		T4_xAxis.setTickUnit(5000);
		T4_xAxis.setUpperBound(18 * 5000);
		NumberAxis T4_yAxis = (NumberAxis) T4.getYAxis();
		T4_yAxis.setLowerBound(-20000);
		T4_yAxis.setTickUnit(20000);
		T4_yAxis.setUpperBound(20000);

		// T5
		T5.setCreateSymbols(false);
		NumberAxis T5_xAxis = (NumberAxis) T5.getXAxis();
		T5_xAxis.setLowerBound(0);
		T5_xAxis.setTickUnit(5000);
		T5_xAxis.setUpperBound(18 * 5000);
		NumberAxis T5_yAxis = (NumberAxis) T5.getYAxis();
		T5_yAxis.setLowerBound(-20000);
		T5_yAxis.setTickUnit(20000);
		T5_yAxis.setUpperBound(20000);

		// T6
		T6.setCreateSymbols(false);
		NumberAxis T6_xAxis = (NumberAxis) T6.getXAxis();
		T6_xAxis.setLowerBound(0);
		T6_xAxis.setTickUnit(5000);
		T6_xAxis.setUpperBound(18 * 5000);
		NumberAxis T6_yAxis = (NumberAxis) T6.getYAxis();
		T6_yAxis.setLowerBound(-20000);
		T6_yAxis.setTickUnit(20000);
		T6_yAxis.setUpperBound(20000);

		// T7
		T7.setCreateSymbols(false);
		NumberAxis T7_xAxis = (NumberAxis) T7.getXAxis();
		T7_xAxis.setLowerBound(0);
		T7_xAxis.setTickUnit(5000);
		T7_xAxis.setUpperBound(18 * 5000);
		NumberAxis T7_yAxis = (NumberAxis) T7.getYAxis();
		T7_yAxis.setLowerBound(-20000);
		T7_yAxis.setTickUnit(20000);
		T7_yAxis.setUpperBound(20000);

		// T8
		T8.setCreateSymbols(false);
		NumberAxis T8_xAxis = (NumberAxis) T8.getXAxis();
		T8_xAxis.setLowerBound(0);
		T8_xAxis.setTickUnit(5000);
		T8_xAxis.setUpperBound(18 * 5000);
		NumberAxis T8_yAxis = (NumberAxis) T8.getYAxis();
		T8_yAxis.setLowerBound(-20000);
		T8_yAxis.setTickUnit(20000);
		T8_yAxis.setUpperBound(20000);

		// T9
		T9.setCreateSymbols(false);
		NumberAxis T9_xAxis = (NumberAxis) T9.getXAxis();
		T9_xAxis.setLowerBound(0);
		T9_xAxis.setTickUnit(5000);
		T9_xAxis.setUpperBound(18 * 5000);
		NumberAxis T9_yAxis = (NumberAxis) T9.getYAxis();
		T9_yAxis.setLowerBound(-20000);
		T9_yAxis.setTickUnit(20000);
		T9_yAxis.setUpperBound(20000);
	}

	class Mycontrol {
		private Stage stage;

		public void show() {

			TextArea console = new TextArea();
			Scene scene = new Scene(console, 1000, 600);
			stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("输出控制台");
			stage.getIcons().add(new Image(Main.class.getResourceAsStream("view/lndx.png")));
			stage.show();
			/** 绑定输出流 */
			MyPrintStream mps = new MyPrintStream(System.out, console);
			System.setOut(mps);
			System.setErr(mps);
			/** 开启后台线程 */
			if (MainThread.exitVariable_visual == true) {
				MainThread.exitVariable_visual = false;
				MainThread main = new MainThread();
				main.start();
			}
			/** 监听窗口关闭操作 */
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					isShowConsole = false;
				}
			});
		}

		public void close() {
			stage.close();
		}
	}
}
