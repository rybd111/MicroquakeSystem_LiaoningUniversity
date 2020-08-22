package visual.controller;

import java.io.IOException;

import visual.model.ReadCSV;
import visual.util.Tools_DataCommunication;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class MyLineChart {

	private LineChart<Number, Number> mChart_T1;
	private LineChart<Number, Number> mChart_T2;
	private LineChart<Number, Number> mChart_T3;
	private LineChart<Number, Number> mChart_T4;
	private LineChart<Number, Number> mChart_T5;
	private LineChart<Number, Number> mChart_T6;
	private LineChart<Number, Number> mChart_T7;
	private LineChart<Number, Number> mChart_T8;
	private LineChart<Number, Number> mChart_T9;
	// T1
	private XYChart.Series<Number, Number> T1_series1 = new XYChart.Series<Number, Number>();// x
	private XYChart.Series<Number, Number> T1_series2 = new XYChart.Series<Number, Number>();// y
	private XYChart.Series<Number, Number> T1_series3 = new XYChart.Series<Number, Number>();// z
	// T2
	private XYChart.Series<Number, Number> T2_series1 = new XYChart.Series<Number, Number>();// x
	private XYChart.Series<Number, Number> T2_series2 = new XYChart.Series<Number, Number>();// y
	private XYChart.Series<Number, Number> T2_series3 = new XYChart.Series<Number, Number>();// z
	// T3
	private XYChart.Series<Number, Number> T3_series1 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T3_series2 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T3_series3 = new XYChart.Series<Number, Number>();
	// T4
	private XYChart.Series<Number, Number> T4_series1 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T4_series2 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T4_series3 = new XYChart.Series<Number, Number>();
	// T5
	private XYChart.Series<Number, Number> T5_series1 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T5_series2 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T5_series3 = new XYChart.Series<Number, Number>();
	// T6
	private XYChart.Series<Number, Number> T6_series1 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T6_series2 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T6_series3 = new XYChart.Series<Number, Number>();
	// T7
	private XYChart.Series<Number, Number> T7_series1 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T7_series2 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T7_series3 = new XYChart.Series<Number, Number>();
	// T8
	private XYChart.Series<Number, Number> T8_series1 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T8_series2 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T8_series3 = new XYChart.Series<Number, Number>();
	// T9
	private XYChart.Series<Number, Number> T9_series1 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T9_series2 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> T9_series3 = new XYChart.Series<Number, Number>();

	public MyLineChart(LineChart<Number, Number> T1, LineChart<Number, Number> T2, LineChart<Number, Number> T3,
			LineChart<Number, Number> T4, LineChart<Number, Number> T5, LineChart<Number, Number> T6,
			LineChart<Number, Number> T7, LineChart<Number, Number> T8, LineChart<Number, Number> T9) {
		this.mChart_T1 = T1;
		this.mChart_T2 = T2;
		this.mChart_T3 = T3;
		this.mChart_T4 = T4;
		this.mChart_T5 = T5;
		this.mChart_T6 = T6;
		this.mChart_T7 = T7;
		this.mChart_T8 = T8;
		this.mChart_T9 = T9;
		// 把线放入LineChart表中
		mChart_T1.getData().addAll(T1_series1, T1_series2, T1_series3);
		mChart_T2.getData().addAll(T2_series1, T2_series2, T2_series3);
		mChart_T3.getData().addAll(T3_series1, T3_series2, T3_series3);
		mChart_T4.getData().addAll(T4_series1, T4_series2, T4_series3);
		mChart_T5.getData().addAll(T5_series1, T5_series2, T5_series3);
		mChart_T6.getData().addAll(T6_series1, T6_series2, T6_series3);
		mChart_T7.getData().addAll(T7_series1, T7_series2, T7_series3);
		mChart_T8.getData().addAll(T8_series1, T8_series2, T8_series3);
		mChart_T9.getData().addAll(T9_series1, T9_series2, T9_series3);
	}

	public void setLineChart(int time, String path) {
		System.out.println("读取CSV文件路径为：" + path);
		if (path == null || path == "" || path == " " || path.length() <= 0) {
			System.out.println("路径为空，绘制波形图失败！");
			return;
		}

		ReadCSV r = new ReadCSV(path);
		try {
			Tools_DataCommunication.getCommunication().list = r.readContents(time);// s
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		clearSeries();
		setSeries();//
		System.out.println("波形图绘制完毕");
		System.out.println("==================================================");
	}

	private void setSeries() {
		if (3 != Tools_DataCommunication.getCommunication().list.size())
			return;
		System.out.println(Tools_DataCommunication.getCommunication().list.get(0).size());
		for (int i = 0; i < Tools_DataCommunication.getCommunication().list.get(0).size(); i++) {
			// 数据过滤。
			if (i % 5 == 0)
				continue;
			// T1
			/** 这里+20000，+60000，+100000的目的是平移X轴。因为要在一个表里画三条线 */
			T1_series1.getData()
					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(0).get(i) + 20000));// x
			T1_series2.getData()
					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(1).get(i) + 60000));// y
			T1_series3.getData()
					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(2).get(i) + 100000));// z
			// T2
//			T2_series1.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(0).get(i) + 20000));
//			T2_series2.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(1).get(i) + 60000));
//			T2_series3.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(2).get(i) + 100000));
//
//			T3_series1.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(0).get(i) + 20000));
//			T3_series2.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(1).get(i) + 60000));
//			T3_series3.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(2).get(i) + 100000));
//
//			T4_series1.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(0).get(i) + 20000));
//			T4_series2.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(1).get(i) + 60000));
//			T4_series3.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(2).get(i) + 100000));
//
//			T5_series1.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(0).get(i) + 20000));
//			T5_series2.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(1).get(i) + 60000));
//			T5_series3.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(2).get(i) + 100000));
//
//			T6_series1.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(0).get(i) + 20000));
//			T6_series2.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(1).get(i) + 60000));
//			T6_series3.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(2).get(i) + 100000));
//
//			T7_series1.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(0).get(i) + 20000));
//			T7_series2.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(1).get(i) + 60000));
//			T7_series3.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(2).get(i) + 100000));
//
//			T8_series1.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(0).get(i) + 20000));
//			T8_series2.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(1).get(i) + 60000));
//			T8_series3.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(2).get(i) + 100000));
//
//			T9_series1.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(0).get(i) + 20000));
//			T9_series2.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(1).get(i) + 60000));
//			T9_series3.getData()
//					.add(new XYChart.Data<>(i, Tools_DataCommunication.getCommunication().list.get(2).get(i) + 100000));
		}

//		setSeries1();//能运行

//		setSeries2();//能运行
	}

	private void setSeries1() {

		T2_series1.getData().addAll(T1_series1.getData());
		T2_series2.getData().addAll(T1_series2.getData());
		T2_series3.getData().addAll(T1_series3.getData());

		T3_series1.getData().addAll(T1_series1.getData());
		T3_series2.getData().addAll(T1_series2.getData());
		T3_series3.getData().addAll(T1_series3.getData());

		T4_series1.getData().addAll(T1_series1.getData());
		T4_series2.getData().addAll(T1_series2.getData());
		T4_series3.getData().addAll(T1_series3.getData());

		T5_series1.getData().addAll(T1_series1.getData());
		T5_series2.getData().addAll(T1_series2.getData());
		T5_series3.getData().addAll(T1_series3.getData());

		T6_series1.getData().addAll(T1_series1.getData());
		T6_series2.getData().addAll(T1_series2.getData());
		T6_series3.getData().addAll(T1_series3.getData());

		T7_series1.getData().addAll(T1_series1.getData());
		T7_series2.getData().addAll(T1_series2.getData());
		T7_series3.getData().addAll(T1_series3.getData());

		T8_series1.getData().addAll(T1_series1.getData());
		T8_series2.getData().addAll(T1_series2.getData());
		T8_series3.getData().addAll(T1_series3.getData());

		T9_series1.getData().addAll(T1_series1.getData());
		T9_series2.getData().addAll(T1_series2.getData());
		T9_series3.getData().addAll(T1_series3.getData());
	}

	private void setSeries2() {

		T2_series1.setData(T1_series1.getData());
		T2_series2.setData(T1_series2.getData());
		T2_series3.setData(T1_series3.getData());

		T3_series1.setData(T1_series1.getData());
		T3_series2.setData(T1_series2.getData());
		T3_series3.setData(T1_series3.getData());

		T4_series1.setData(T1_series1.getData());
		T4_series2.setData(T1_series2.getData());
		T4_series3.setData(T1_series3.getData());

		T5_series1.setData(T1_series1.getData());
		T5_series2.setData(T1_series2.getData());
		T5_series3.setData(T1_series3.getData());

		T6_series1.setData(T1_series1.getData());
		T6_series2.setData(T1_series2.getData());
		T6_series3.setData(T1_series3.getData());

		T7_series1.setData(T1_series1.getData());
		T7_series2.setData(T1_series2.getData());
		T7_series3.setData(T1_series3.getData());

		T8_series1.setData(T1_series1.getData());
		T8_series2.setData(T1_series2.getData());
		T8_series3.setData(T1_series3.getData());

		T9_series1.setData(T1_series1.getData());
		T9_series2.setData(T1_series2.getData());
		T9_series3.setData(T1_series3.getData());
	}

	private void clearSeries() {
		T1_series1.getData().clear();
		T1_series2.getData().clear();
		T1_series3.getData().clear();

		T2_series1.getData().clear();
		T2_series2.getData().clear();
		T2_series3.getData().clear();

		T3_series1.getData().clear();
		T3_series2.getData().clear();
		T3_series3.getData().clear();

		T4_series1.getData().clear();
		T4_series2.getData().clear();
		T4_series3.getData().clear();

		T5_series1.getData().clear();
		T5_series2.getData().clear();
		T5_series3.getData().clear();

		T6_series1.getData().clear();
		T6_series2.getData().clear();
		T6_series3.getData().clear();

		T7_series1.getData().clear();
		T7_series2.getData().clear();
		T7_series3.getData().clear();

		T8_series1.getData().clear();
		T8_series2.getData().clear();
		T8_series3.getData().clear();

		T9_series1.getData().clear();
		T9_series2.getData().clear();
		T9_series3.getData().clear();
	}
}
