package com.visual.util;

import java.util.ArrayList;

import com.teamdev.jxbrowser.chromium.ch;
import com.visual.Main;
import com.visual.controller.MyCAD;
import com.visual.controller.MyLineChart;
import com.visual.controller.MyTableView;
import com.visual.model.TableData;
import com.visual.view.UIController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/***
 * 用于类与类之间的数据传输
 * 
 * @author Sunny 饿汉式单例模式
 */
public class Tools_DataCommunication {
	private static Tools_DataCommunication communication = new Tools_DataCommunication();

	private Tools_DataCommunication() {
	}

	public static Tools_DataCommunication getCommunication() {
		return communication;
	}

	/** 用于波形图的数据传递 */
	public ArrayList<ArrayList<Integer>> list;
	/** 用于读取CSV文件的秒数 一秒5000条数据 */
	public int readTime = 1;// s
	/** 用于TableView的数据传递 */
	public ObservableList dataList = FXCollections.observableArrayList();

	/** 用于显示CAD */
	public void showCAD(BorderPane mBorderPane) {
		MyCAD mycad = new MyCAD(mBorderPane);
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
	public void ShowTableView(TableView mTableView) {
		MyTableView view = new MyTableView();
		view.setTableViewData();
		mTableView.setItems(dataList);
	}

	/** 用于初始化坐标轴 
	 * 如果不执行这个方法，会报 ：java.lang.OutOfMemoryError: GC overhead limit exceeded错误
	 * */
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

	/** 用于显示波形图 */
	public void ShowLineChart(LineChart<Number, Number> T1, LineChart<Number, Number> T2, LineChart<Number, Number> T3,
			LineChart<Number, Number> T4, LineChart<Number, Number> T5, LineChart<Number, Number> T6,
			LineChart<Number, Number> T7, LineChart<Number, Number> T8, LineChart<Number, Number> T9) {
		InitLineChart(T1, T2, T3, T4, T5, T6, T7, T8, T9); 
		MyLineChart chart = new MyLineChart(T1, T2, T3, T4, T5, T6, T7, T8, T9);
		chart.setLineChart(readTime);
	}

}
