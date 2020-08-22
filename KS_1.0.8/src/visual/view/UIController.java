package visual.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.eclipse.swt.widgets.Display;

import com.db.DbExcute;
import visual.model.TableData;
import visual.util.Tools_DataCommunication;

import bean.QuackResults;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import mutiThread.MainThread;
import visual.MainFrame;
import visual.Preferences;
import visual.historyQuery;

public class UIController {

//--------------------------CAD绘制--------------------------
	@FXML
	private AnchorPane mGreenPane;
	@FXML
	private BorderPane mBorderPane;
//--------------------------表格-------------------
	@FXML
	private AnchorPane mYellowPane;
	@FXML
	private TableView<TableData> mTableView;
	@FXML
	private TableColumn<TableData, String> eventIndex;// 触发事件序号
	@FXML
	private TableColumn<TableData, String> eventTime;// 触发时间
	@FXML
	private TableColumn<TableData, String> eventLoca;// 触发台站
	@FXML
	private TableColumn<TableData, String> eventPos;// 定位坐标
	@FXML
	private TableColumn<TableData, String> energy;// 能量
	@FXML
	private TableColumn<TableData, String> grade;// 震级
//--------------------------波形图-------------------
	@FXML
	private AnchorPane mBluePane;
	@FXML
	private LineChart<Number, Number> mChart_T1;
	@FXML
	private LineChart<Number, Number> mChart_T2;
	@FXML
	private LineChart<Number, Number> mChart_T3;
	@FXML
	private LineChart<Number, Number> mChart_T4;
	@FXML
	private LineChart<Number, Number> mChart_T5;
	@FXML
	private LineChart<Number, Number> mChart_T6;
	@FXML
	private LineChart<Number, Number> mChart_T7;
	@FXML
	private LineChart<Number, Number> mChart_T8;
	@FXML
	private LineChart<Number, Number> mChart_T9;

//------------------------------------------------------------------
//--------------------------按钮绑定的事件--------------------------
//------------------------------------------------------------------
	@FXML
	void onClickStart(ActionEvent event) {// 运行
		Tools_DataCommunication.getCommunication().showandcloseMyConsole();
//		if (MainThread.exitVariable_visual == true) {
//			MainThread.exitVariable_visual = false;
//			MainThread main = new MainThread();
//			main.start();
//		}
		System.out.println("按下运行按钮");
	}

	@FXML
	void onClickStop(ActionEvent event) {// 停止
		MainThread.exitVariable_visual = true;
		System.out.println("按下停止按钮");
	}

	@FXML
	void onClickExit(ActionEvent event) {// 退出
		System.exit(0);
		System.out.println("按下退出按钮");
	}

	@FXML
	void onClickHistoryQuery(ActionEvent event) {// 历史查询
		System.out.println("按下历史查询按钮");
		try {
			historyQuery window = new historyQuery();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void oClickPreferences(ActionEvent event) {// 设置
		System.out.println("按下设置按钮");
		try {
			Preferences window = new Preferences();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickHelp(ActionEvent event) {// 帮助

		try {
			// 打开当前文件
			Desktop.getDesktop().open(new File("C:\\Users\\Sunny\\Desktop\\说明"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("按下帮助按钮");
	}

	@FXML
	void onClickTest(ActionEvent event) {// Test
		System.out.println("按下Test按钮");
		String adate = "20" + "170524151342";
		Date date = new Date();
		DateFormat formatDate = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			date = formatDate.parse(adate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		DateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 三台站：[x=4595388.504 # y=41518099.807 # z=22.776 # Time=2019-11-26 12:38:27 #
		// Grade=-0.22#Parrival0.0--------------------------------------------------------------------------]

		String time = formatDateTime.format(date);
		Timestamp timestamp = Timestamp.valueOf(time);
		QuackResults aQuackResults3 = new QuackResults(1, 1, 1, time, 5.2, 0.2, " ", 0.0, 0.0,
				"D:/data/ConstructionData/5data/25613 2020-05-01 09-33-15`05.csv",0.0,"");
		DbExcute aDbExcute = new DbExcute();
		aDbExcute.addElement3(aQuackResults3);
	}

	@FXML
	void onZoomIN(ActionEvent event) {
		System.out.println("放大");
	}

	@FXML
	void onZoomOut(ActionEvent event) {
		System.out.println("缩小");
	}

	@FXML
	void onRestore(ActionEvent event) {
		System.out.println("还原");
	}

	// 在Main程序加载fxml文件时候执行
	@FXML
	void initialize() {
		Tools_DataCommunication.getCommunication().showCAD(mBorderPane);// 显示CAD
		// 将TableColumn与TableData每一个属性连接起来
		Tools_DataCommunication.getCommunication().InitTableViewData(eventIndex, eventTime, eventLoca, eventPos, energy,
				grade);
		Tools_DataCommunication.getCommunication().ShowTableView(mTableView);// 显示TableView
		Tools_DataCommunication.getCommunication().ShowLineChart(mChart_T1, mChart_T2, mChart_T3, mChart_T4, mChart_T5,
				mChart_T6, mChart_T7, mChart_T8, mChart_T9);// 显示波形图
	}
}
