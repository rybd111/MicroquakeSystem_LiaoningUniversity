package com.visual.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.visual.model.TableData;
import com.visual.util.Tools_DataCommunication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
	void onOperationButtonClick(ActionEvent event) {
		System.out.println("用户按下运行ToggleButton按钮");
	}

	@FXML
	void onDayScheduleClick(ActionEvent event) {
		System.out.println("用户按下日报表ToggleButton按钮");
	}

	@FXML
	void onMonthScheduleClick(ActionEvent event) {
		System.out.println("用户按下月报表ToggleButton按钮");
	}

	@FXML
	void onHelpClick(ActionEvent event) {
		System.out.println("用户按下帮助ToggleButton按钮");
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
				mChart_T6, mChart_T7, mChart_T8, mChart_T9);//显示波形图
		System.out.println("已初始化到UI界面上");
	}
}
