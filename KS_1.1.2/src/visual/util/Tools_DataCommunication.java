package visual.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import com.h2.constant.Parameters;
import com.teamdev.jxbrowser.chromium.Browser;
import visual.Main;
import visual.controller.MyCAD;
import visual.controller.MyLineChart;
import visual.controller.MyTableView;
import visual.model.MyPrintStream;
import visual.model.TableData;
import visual.util.Tools_DataCommunication.tableViewType;
import visual.view.DistributedPanelController;
import visual.view.RepositionPanelController_datong;
import visual.view.RepositionPanelController_hongyang;
import visual.view.RepositionPanelController_madaotou;
import visual.view.RepositionPanelController_pingdingshan;
import visual.view.UIController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

// ====================================================================================================================================
	/** 用于标识是否正在运行客户端程序 */
	public boolean isClient = false;
	/** 用于标记当前输出控制台的状态 */
	public boolean isShowConsole = false;
	// 主界面控制类
	private UIController controller = null;

	public UIController getController() {
		return controller;
	}

	public void setController(UIController controller) {
		this.controller = controller;
	}

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

	/** 获取控制播放提示音类 */
	private AudioPlayer audioPlayer = new AudioPlayer(
			new File(System.getProperty("user.dir") + "\\resource\\PromptTone.mp3"));

	/** 播放提示音 */
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}

	/** 生成报表类 */
	public GenerateReport getReport() {
		return report;
	}
	private GenerateReport report = new GenerateReport();
	
	/** 控制定时读取记录线程 */
	public boolean isRunTXT = false;
	/** 获取控制分布式面板控制类 */
	public DistributedPanelController distributedPanelController = null;
	// ===========================================CAD显示==============================================================================
	/** 获取JxBrowser的对象 */
	public Browser jxBrowser = null;
	/** 用于判断网页CAD是否已绘制完毕 */
	public boolean isFinishLoadCAD = false;
	/** CAD定位圆的半径 */
	public double circleRadius = 500.0;

	// CAD控制对象
	private MyCAD mCAD = null;

	public MyCAD getmCAD() {
		return mCAD;
	}

	/** 用于显示CAD */
	public void showCAD(Label label, BorderPane mBorderPane) {
		this.mCAD = new MyCAD(label, mBorderPane);
	}

//===========================================事件列表显示============================================================
	/** 用于TableView的数据传递 */
	public ObservableList dataList = FXCollections.observableArrayList();
	/** 用于查询面板的TableView数据传递 */
	public ObservableList dataList_QueryPanel = FXCollections.observableArrayList();

	// 事件列表控制对象
	private MyTableView mTableView = null;

	public MyTableView getmTableView() {
		return mTableView;
	}

	/** 用于显示TableView */
	public void ShowTableView(TableView<TableData> mTableView, ComboBox<String> mComboBox, Label mEventLabel) {

		this.mTableView = new MyTableView(mTableView, mComboBox, mEventLabel);
		this.mTableView.setTableViewData("select * from " + Parameters.DatabaseName5, tableViewType.Normal);

		mTableView.setItems(dataList);
	}

//===========================================波形图显示==============================================================
	/** 区分事件操作的不同 */
	public enum tableViewType {
		Normal, // 正常读取事件数据
		Query// 查询面板读取事件数据
	}

	/** 用于读取CSV文件的秒数 一秒5000条数据 */
	public int readTime = Parameters.startTime + Parameters.endTime;// s
	/*** 当前显示波形图的文件路径 */
	public String csvPath = null;
	public TableData reLocateData = null;
	/** 存放P波到时的位置 */
	public double[] P_array = null;
	/** 获取CSV文件的文件名台站 */
	public String fileSS = null;
	/** 用于波形图的数据传递 */
	public ArrayList<ArrayList<Integer>> list;
	/** 记录每个波形图表对应的最大Y值 */
	public double[] chartYmax = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };

	// 波形图控制对象
	private MyLineChart mChart = null;

	public MyLineChart getmChart() {
		return mChart;
	}

	/** 清空记录最大波形图Y值数组 */
	public void clearchartYmax() {
		for (int i = 0; i < chartYmax.length; i++)
			chartYmax[i] = 0.0;
	}

	/** 用于显示波形图 */
	public void ShowLineChart(Slider sliderP, TextField textP, Slider sliderlow, Slider sliderupper,
			TextField textlower, TextField textupper, VBox box, SplitPane mTimerSplitPane) {
		this.mChart = new MyLineChart(sliderP, textP, sliderlow, sliderupper, textlower, textupper, box,
				mTimerSplitPane);
	}

//	public boolean isInitWidthandHigth = true;
//============================================================================================================================
	/** 获取重定位控制类 */
//	public RepositionPanelController_pingdingshan repositionPanelController = null;
	public RepositionPanelController_hongyang controller_hongyang = null;
	public RepositionPanelController_datong controller_datong = null;
	public RepositionPanelController_pingdingshan controller_pingdingshan = null;
	public RepositionPanelController_madaotou controller_madaotou = null;
	public String[] reLocationResultString = null;
	/**
	 * =======================================屏幕自适应解决方案===========================================================================================
	 */
//	public void uiAdaptationMax() {
//		// 波形图
//		mChart.alterSplitPaneHight(93);
//		// 事件列表自带自适应:CONSTRAINED_RESIZE_POLICY
//	}
//
//	public void uiAdaptationMin() {
//		mChart.alterSplitPaneHight(83);
//		// 事件列表自带自适应:CONSTRAINED_RESIZE_POLICY
//	}

	/**
	 * =======================================内部类-输出控制台===========================================================================================
	 */
	class Mycontrol {
		private Stage stage;

		public void show() {

			TextArea console = new TextArea();
			Scene scene = new Scene(console, 1000, 600);
			stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("集中式运行面板");
			try {
				stage.getIcons()
						.add(new Image(new FileInputStream(System.getProperty("user.dir") + "\\resource\\lndx.png")));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("输出控制台没有找到窗口图片");
//				e.printStackTrace();
			}
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

	/**
	 * 暴力结束所有线程
	 */
	public void exitThreadAll() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				isClient = false;
				try {
					/** sleep时间低于50，可能会报跟线程相关的错误。因为我这里使用exit(0)，进行暴力结束 */
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		}).start();
	}
}
