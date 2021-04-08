package visual.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.events.PaintEvent;

import com.db.DbExcute;
import com.h2.constant.Parameters;
import visual.model.ReadCSV;
import visual.model.saveCSV;
import visual.util.Tools_DataCommunication;
import visual.view.UIController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * 已做代码优化-2020/09/25
 * 
 * @author Sunny-胡永亮
 *
 */
public class MyLineChart {
	/** CSV文件路径 */
	private String filePath = null;

	private Slider mSlider_P;
	private Slider mSlider_lower;
	private Slider mSlider_upper;

	private TextField mText_P;
	private TextField mText_lower;
	private TextField mText_upper;

	private SplitPane mTimersplitpane;

	private VBox mVBoxLineChart;

	private int tIndex = 0;

	public int gettIndex() {
		return tIndex;
	}

	/** 鼠标悬停Label数组 */
	private ArrayList<Label> T_label = new ArrayList<Label>();

	/** 波形图表数组 */
	private ArrayList<LineChart<Number, Number>> mChart_T = new ArrayList<>();

	/** 波形图表的X坐标轴数组 */
	private ArrayList<NumberAxis> T_xAxis = new ArrayList<>();

	/** 波形图表的Y坐标轴数组 */
	private ArrayList<NumberAxis> T_yAxis = new ArrayList<>();
	/** 传感器站台名字 */
	// 红阳三矿
	private String[] sensorName_hongyanng = { "杨\n甸\n子", "树\n碑\n子", "北\n青\n堆\n子", "车\n队", "工\n业\n广\n场", "火\n药\n库",
			"南\n风\n井", "蒿\n子\n屯", "李\n大\n人" };
	// 大同
	private String[] sensorName_datong = { "3\n号", "4\n号", "5\n号", "6\n号", "7\n号", "2\n号", "1\n号" };
	// 平顶山
	private String[] sensorName_pingdingshan = { "牛家村", "洗煤厂", "香山矿", "王家村", "十一矿工业广场老办公楼西南角花坛", "西风井", "打钻工区" };
	// 马道头
	private String[] sensorName_madaotou = { "sel", "nhy", "wmz ", "tbz" };
	/** 波形线 数组 */
	private ArrayList<XYChart.Series<Number, Number>> T_seriesZ = new ArrayList<XYChart.Series<Number, Number>>();
	/** P波到时 */
	private ArrayList<XYChart.Series<Number, Number>> pArray = new ArrayList<XYChart.Series<Number, Number>>();

	public ArrayList<XYChart.Series<Number, Number>> getpArray() {
		return pArray;
	}

	/** 时间坐标轴 */
	private NumberAxis timerXaxis = new NumberAxis(0, 18 * 5000, 5000);
	private NumberAxis timerYaxis = new NumberAxis();
	private LineChart<Number, Number> timerChart = new LineChart<Number, Number>(timerXaxis, timerYaxis);
	private XYChart.Series<Number, Number> timerSeries = new XYChart.Series<Number, Number>();

	public MyLineChart(Slider sliderP, TextField textP, Slider sliderlow, Slider sliderupper, TextField textlower,
			TextField textupper, VBox box, SplitPane mTimersplitpane) {
		final String stockLineChartCss = UIController.class.getResource("Line.css").toExternalForm();
		this.mVBoxLineChart = box;
		this.mSlider_P = sliderP;
		this.mSlider_lower = sliderlow;
		this.mSlider_upper = sliderupper;
		this.mText_P = textP;
		this.mText_lower = textlower;
		this.mText_upper = textupper;
		this.mTimersplitpane = mTimersplitpane;
		/** 创建线条 */
		for (int i = 0; i < 3 * 9; i++) {
			T_seriesZ.add(new XYChart.Series<Number, Number>());
		}
		/** 创建鼠标悬停Label、XY轴、波形图表、设置波形图表锚点 */

		for (int i = 0; i < 9; i++) {
			// 创建鼠标悬停Label
			Label label = new Label("T" + (i + 1));
			// 初始化Label不可见
			label.setVisible(false);
			T_label.add(label);
			/** P波到时 */
			XYChart.Series<Number, Number> p = new XYChart.Series<Number, Number>();
			pArray.add(p);
			// 创建X坐标轴
			T_xAxis.add(new NumberAxis(0, 18 * 5000, 500));
			// 创建Y坐标轴
			T_yAxis.add(new NumberAxis());
			// 创建波形图表
			LineChart<Number, Number> chart = new LineChart<Number, Number>(T_xAxis.get(i), T_yAxis.get(i));
			mChart_T.add(chart);
			// 设置波形图表样式
			chart.getStylesheets().add(stockLineChartCss);
			// 禁止播放动画
			chart.setAnimated(false);
			// 设置LineChart锚点
			setChartAnchor(i, chart, label);
			// 把线放入波形图表中
			chart.getData().addAll(T_seriesZ.get(0 + 3 * i), T_seriesZ.get(1 + 3 * i), T_seriesZ.get(2 + 3 * i), p);
		}
		// 时间坐标轴
		timerChart.getData().addAll(timerSeries);
		timerChart.getStylesheets().add(stockLineChartCss);
		AnchorPane anpane = null;
		anpane = (AnchorPane) this.mTimersplitpane.getItems().get(1);
		anpane.setTopAnchor(timerChart, -15.0);
		anpane.setBottomAnchor(timerChart, 0.0);
		anpane.setLeftAnchor(timerChart, -45.0);
		anpane.setRightAnchor(timerChart, -10.0);
		anpane.getChildren().addAll(timerChart);
		initZoomSize();
	}

	public void setLineChart(int time, String path) {

		// 清空波形图最大Y值数组
		Tools_DataCommunication.getCommunication().clearchartYmax();
		// 放大缩小波形图还原到初始值
		this.mSlider_lower.setValue(0.00);
		this.mSlider_upper.setValue(90000.00);
		System.out.println("读取CSV文件路径为：" + path);
		// if (path == null || path == "" || path == " " || path.length() <= 0) {
		// System.out.println("路径为空，绘制波形图失败！");
		// return;
		// }
		this.filePath = path;
		ReadCSV r = new ReadCSV(this.filePath);
		try {
			Tools_DataCommunication.getCommunication().list = r.readContents(time);// s
		} catch (NumberFormatException | IOException e) {
			System.out.println("路径为空，绘制波形图失败！");
			return;
		}

		clearSeries();
		setSensorName();
		setSeries();//
		System.out.println("波形图绘制完毕");
		System.out.println("==================================================");
	}

	public void setSensorName() {

		SplitPane splitpane = null;
		AnchorPane anchoranpane = null;
		Label label = null;
		for (int i = 0; i < mVBoxLineChart.getChildren().size(); i++) {
			splitpane = (SplitPane) mVBoxLineChart.getChildren().get(i);
			anchoranpane = (AnchorPane) splitpane.getItems().get(0);
			label = (Label) anchoranpane.getChildren().get(0);
			label.setText("T" + (i + 1));
		}
		/** 判断是哪个矿区 */
		switch (Parameters.diskNameNum) {
		case 0:// 红阳三矿
			setSensorName_hongyang();
			break;
		case 1:// 大同
			setSensorName_datong();
			break;
		case 2:// 平顶山
			setSensorName_pingdingshan();
			break;
		case 3:// 马道头
			setSensorName_madaotou();
			break;
		default:
			System.out.println("未能识别属于哪个矿区-----MyLineChart");
			break;
		}
	}

	/**
	 * 红阳三矿
	 */
	private void setSensorName_hongyang() {
		SplitPane splitpane = null;
		AnchorPane anchoranpane = null;
		Label label = null;
		char[] name = Tools_DataCommunication.getCommunication().fileSS.toUpperCase().toCharArray();
		for (int i = 0; i < name.length; i++) {
			splitpane = (SplitPane) mVBoxLineChart.getChildren().get(i);
			anchoranpane = (AnchorPane) splitpane.getItems().get(0);
			label = (Label) anchoranpane.getChildren().get(0);
			switch (name[i]) {
			case 'T':
				label.setText(sensorName_hongyanng[0]);
				break;
			case 'U':
				label.setText(sensorName_hongyanng[1]);
				break;
			case 'W':
				label.setText(sensorName_hongyanng[2]);
				break;
			case 'X':
				label.setText(sensorName_hongyanng[3]);
				break;
			case 'Z':
				label.setText(sensorName_hongyanng[4]);
				break;
			case 'Y':
				label.setText(sensorName_hongyanng[5]);
				break;
			case 'V':
				label.setText(sensorName_hongyanng[6]);
				break;
			case 'S':
				label.setText(sensorName_hongyanng[7]);
				break;
			case 'R':
				label.setText(sensorName_hongyanng[8]);
				break;
			default:
				System.out.println(
						"======================Error：-红阳三矿传感器站台名称出错。-MyLineChart===================================");
				break;
			}
		}
	}

	/**
	 * 大同
	 */
	private void setSensorName_datong() {
		SplitPane splitpane = null;
		AnchorPane anchoranpane = null;
		Label label = null;
		char[] name = Tools_DataCommunication.getCommunication().fileSS.toUpperCase().toCharArray();
		for (int i = 0; i < name.length; i++) {
			splitpane = (SplitPane) mVBoxLineChart.getChildren().get(i);
			anchoranpane = (AnchorPane) splitpane.getItems().get(0);
			label = (Label) anchoranpane.getChildren().get(0);
			switch (name[i]) {
			case 'V':// 3号
				label.setText(sensorName_datong[0]);
				break;
			case 'W':// 4号
				label.setText(sensorName_datong[1]);
				break;
			case 'X':// 5号
				label.setText(sensorName_datong[2]);
				break;
			case 'Y':// 6号
				label.setText(sensorName_datong[3]);
				break;
			case 'Z':// 7号
				label.setText(sensorName_datong[4]);
				break;
			case 'U':// 2号
				label.setText(sensorName_datong[5]);
				break;
			case 'T':// 1号
				label.setText(sensorName_datong[6]);
				break;
			default:
				System.out.println(
						"======================Error：-大同传感器站台名称出错。-MyLineChart===================================");
				break;
			}
		}
	}

	/**
	 * 平顶山
	 */
	private void setSensorName_pingdingshan() {
		SplitPane splitpane = null;
		AnchorPane anchoranpane = null;
		Label label = null;
		char[] name = Tools_DataCommunication.getCommunication().fileSS.toUpperCase().toCharArray();
		for (int i = 0; i < name.length; i++) {
			splitpane = (SplitPane) mVBoxLineChart.getChildren().get(i);
			anchoranpane = (AnchorPane) splitpane.getItems().get(0);
			label = (Label) anchoranpane.getChildren().get(0);
			switch (name[i]) {
			case 'Z':// Z 牛家村
				label.setText(sensorName_pingdingshan[0]);
				break;
			case 'T':// T 洗煤厂
				label.setText(sensorName_pingdingshan[1]);
				break;
			case 'Y':// Y 香山矿
				label.setText(sensorName_pingdingshan[2]);
				break;
			case 'V':// V 王家村
				label.setText(sensorName_pingdingshan[3]);
				break;
			case 'X':// X 十一矿工业广场老办公楼西南角花坛
				label.setText(sensorName_pingdingshan[4]);
				break;
			case 'W':// W 西风井
				label.setText(sensorName_pingdingshan[5]);
				break;
			case 'U':// U 打钻工区
				label.setText(sensorName_pingdingshan[6]);
				break;
			default:
				System.out.println(
						"======================Error：-平顶山传感器站台名称出错。-MyLineChart===================================");
				break;
			}
		}
	}

	/**
	 * 马道头
	 */
	private void setSensorName_madaotou() {
		SplitPane splitpane = null;
		AnchorPane anchoranpane = null;
		Label label = null;
		char[] name = Tools_DataCommunication.getCommunication().fileSS.toUpperCase().toCharArray();
		for (int i = 0; i < name.length; i++) {
			splitpane = (SplitPane) mVBoxLineChart.getChildren().get(i);
			anchoranpane = (AnchorPane) splitpane.getItems().get(0);
			label = (Label) anchoranpane.getChildren().get(0);
			switch (name[i]) {
			case 'O':// O sel
				label.setText(sensorName_pingdingshan[0]);
				break;
			case 'P':// P nhy
				label.setText(sensorName_pingdingshan[1]);
				break;
			case 'Q':// Q wmz
				label.setText(sensorName_pingdingshan[2]);
				break;
			case 'Z':// Z tbz
				label.setText(sensorName_pingdingshan[3]);
				break;
			default:
				System.out.println(
						"======================Error：-马道头传感器站台名称出错。-MyLineChart===================================");
				break;
			}
		}
	}

	private void setSeries() {
		clearP();
		double tempt = 0.0;
		for (int i = 0; i < Tools_DataCommunication.getCommunication().fileSS.length(); i++) {

			tempt = Tools_DataCommunication.getCommunication().chartYmax[i];
			for (int j = 0; j < Tools_DataCommunication.getCommunication().list.get(0 + 3 * i).size(); j++) {
				if (j == Tools_DataCommunication.getCommunication().P_array[i]) {
					XYChart.Data<Number, Number> temp1 = new XYChart.Data<>(j, 4 * tempt);
					XYChart.Data<Number, Number> temp2 = new XYChart.Data<>(j, 6 * tempt);
					pArray.get(i).getData().addAll(temp1, temp2);
				}

				/** 数据过滤 */
				if (j % 100 != 0)
					continue;
				XYChart.Data<Number, Number> data1 = new XYChart.Data<>(j,
						Tools_DataCommunication.getCommunication().list.get(0 + 3 * i).get(j) + 1 * tempt);// X
				XYChart.Data<Number, Number> data2 = new XYChart.Data<>(j,
						Tools_DataCommunication.getCommunication().list.get(1 + 3 * i).get(j) + 3 * tempt);// Y
				XYChart.Data<Number, Number> data3 = new XYChart.Data<>(j,
						Tools_DataCommunication.getCommunication().list.get(2 + 3 * i).get(j) + 5 * tempt);// Z
				T_seriesZ.get(0 + 3 * i).getData().add(data1);// x
				T_seriesZ.get(1 + 3 * i).getData().add(data2);// y
				T_seriesZ.get(2 + 3 * i).getData().add(data3);// z
				/** setShowDataLabel必须在getData().add之后，不然会报空指针 */
				setShowDataLabel(data1, T_label.get(i));
				setShowDataLabel(data2, T_label.get(i));
				setShowDataLabel(data3, T_label.get(i));

			}

		}
	}

	public void clearSeries() {
		for (int i = 0; i < T_seriesZ.size(); i++)
			T_seriesZ.get(i).getData().clear();
	}

	public void clearP() {
		// 清空P波到时
		for (int i = 0; i < 9; i++)
			pArray.get(i).getData().clear();
	}
	public void clearName() {
		SplitPane splitpane = null;
		AnchorPane anchoranpane = null;
		Label label = null;
		for (int i = 0; i < mVBoxLineChart.getChildren().size(); i++) {
			splitpane = (SplitPane) mVBoxLineChart.getChildren().get(i);
			anchoranpane = (AnchorPane) splitpane.getItems().get(0);
			label = (Label) anchoranpane.getChildren().get(0);
			label.setText("T" + (i + 1));
		}
	}
	/** 改变LineChart的高度 */
	public void alterSplitPaneHight(double hight) {
		SplitPane pane = null;
		AnchorPane anchorpane = null;
		VBox box = null;
		Label label = null;
		for (int i = 0; i < mVBoxLineChart.getChildren().size(); i++) {
			pane = (SplitPane) mVBoxLineChart.getChildren().get(i);
			pane.setPrefHeight(hight);
			anchorpane = (AnchorPane) pane.getItems().get(0);
			box = (VBox) anchorpane.getChildren().get(1);
			for (int j = 0; j < box.getChildren().size(); j++) {
				label = (Label) box.getChildren().get(j);
				label.setPrefHeight(hight / 3);
			}

		}
	}

	/**
	 * 初始化放大缩小波形图及P波调整功能
	 */
	public void initZoomSize() {
		mText_P.setText(Double.toString(mSlider_P.getValue()));
		mText_lower.setText(Double.toString(mSlider_lower.getValue()));
		mText_upper.setText(Double.toString(mSlider_upper.getValue()));
		// 监听Slider的值是否发生改变
		mSlider_P.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				mText_P.setText(String.format("%.2f", newValue));
				if (tIndex == 0)
					return;
				if (tIndex - 1 >= Tools_DataCommunication.getCommunication().fileSS.length())
					return;
				double temp = pArray.get(tIndex - 1).getData().get(1).getYValue().doubleValue();
				pArray.get(tIndex - 1).getData().clear();
				XYChart.Data<Number, Number> data1 = new XYChart.Data<Number, Number>(newValue, temp / 1.5);
				XYChart.Data<Number, Number> data2 = new XYChart.Data<Number, Number>(newValue, temp);
				pArray.get(tIndex - 1).getData().addAll(data1, data2);
			}
		});
		mSlider_lower.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// 设置X坐标轴的下界不能超过X坐标轴的上界
				if (newValue.doubleValue() >= mSlider_upper.getValue() - 5000) {
					mSlider_lower.setValue(mSlider_upper.getValue() - 5000);
					return;
				}
				// 将SLider的值显示出来
				mText_lower.setText(String.format("%.2f", newValue));
				// 设置X坐标轴的low值
				setXlowerBound(newValue.doubleValue());

			}
		});
		mSlider_upper.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// 设置X坐标轴的下界不能超过X坐标轴的上界
				if (newValue.doubleValue() <= mSlider_lower.getValue() + 5000) {
					mSlider_upper.setValue(mSlider_lower.getValue() + 5000);
					return;
				}
				// 将SLider的值显示出来
				mText_upper.setText(String.format("%.2f", newValue));
				// 设置X坐标轴的Up值
				setXupperrBound(newValue.doubleValue());
			}
		});
		// 监听用户是否按下回车按键
		mText_P.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				// 防止非法输入
				if (Double.parseDouble(mText_P.getText()) >= 90000.0)
					mText_P.setText("90000.0");
				if (Double.parseDouble(mText_P.getText()) <= 0.0)
					mText_P.setText("0.0");
				mSlider_P.setValue(Double.parseDouble(mText_P.getText()));
			}
		});
		mText_lower.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				if (Double.parseDouble(mText_lower.getText()) >= 90000.0)
					mText_lower.setText("85000.0");
				if (Double.parseDouble(mText_lower.getText()) <= 0.0)
					mText_lower.setText("0.0");
				mSlider_lower.setValue(Double.parseDouble(mText_lower.getText()));
			}
		});
		mText_upper.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				if (Double.parseDouble(mText_upper.getText()) >= 90000.0)
					mText_upper.setText("90000.0");
				if (Double.parseDouble(mText_upper.getText()) <= 5000.0)
					mText_upper.setText("5000.0");
				mSlider_upper.setValue(Double.parseDouble(mText_upper.getText()));
			}
		});
	}

	/**
	 * 设置X坐标轴的下界
	 * 
	 * @param lower
	 */
	private void setXlowerBound(double lower) {
		for (int i = 0; i < T_xAxis.size(); i++)
			T_xAxis.get(i).setLowerBound(lower);
		timerXaxis.setLowerBound(lower);
	}

	/**
	 * 设置X坐标轴的上界
	 * 
	 * @param upper
	 */
	private void setXupperrBound(double upper) {
		for (int i = 0; i < T_xAxis.size(); i++)
			T_xAxis.get(i).setUpperBound(upper);
		timerXaxis.setUpperBound(upper);
	}

	/**
	 * 设置波形图表的锚点
	 * 
	 * @param i
	 * @param child
	 * @param label
	 */
	private void setChartAnchor(int i, Node child, Label label) {
		SplitPane pane = null;
		AnchorPane anpane = null;
		AnchorPane anpaneLeft = null;
		pane = (SplitPane) mVBoxLineChart.getChildren().get(i);
		anpane = (AnchorPane) pane.getItems().get(1);
		final StackPane stackpane = (StackPane) anpane.getChildren().get(0);
		anpane = (AnchorPane) stackpane.getChildren().get(0);
		anpane.setTopAnchor(child, -15.0);
		anpane.setBottomAnchor(child, -39.0);
		anpane.setLeftAnchor(child, -65.0);
		anpane.setRightAnchor(child, -10.0);
		anpane.getChildren().addAll(child);
		stackpane.getChildren().addAll(label);
		stackpane.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				label.setVisible(false);
			}
		});
		/** 设置StackPhone孩子的对齐方式 */
		// stackpane.setAlignment(Pos.TOP_LEFT);
		stackpane.setAlignment(label, Pos.TOP_LEFT);

		/** 对显示XY坐标的Label进行鼠标跟随操作 */
		stackpane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				if (e.getX() >= stackpane.getWidth() / 2)
					label.setTranslateX(e.getX() - 110);
				else
					label.setTranslateX(e.getX());

				if (e.getY() >= stackpane.getHeight() / 2)
					label.setTranslateY(e.getY() - 20);
				else
					label.setTranslateY(e.getY());
			}
		});
		anpaneLeft = (AnchorPane) pane.getItems().get(0);
		anpaneLeft.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (Tools_DataCommunication.getCommunication().P_array == null)
					return;
				tIndex = i + 1;
				if (i < Tools_DataCommunication.getCommunication().fileSS.length())
					mSlider_P.setValue(Tools_DataCommunication.getCommunication().P_array[i]);

			}
		});
	}

	/**
	 * 监听鼠标悬停事件 更新Label中XY的坐标信息
	 * 
	 * @param data
	 * @param index
	 */
	private void setShowDataLabel(XYChart.Data<Number, Number> data, Label label) {
		data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				/** 这里不对显示XY坐标的Label的位置进行修改，因为这里是Data的监听事件 */
				label.setVisible(true);
				label.setText("(" + String.valueOf(data.getXValue()) + "," + String.valueOf(data.getYValue()) + ")");
			}
		});
	}

	/** 保存更改过后的P波到时位置CSV */
	public void saveP() {
		saveCSV sa = new saveCSV(this.filePath, pArray);
		try {
			sa.save();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updata(double x, double y, double z, double parrival, String quackTime, int whereID) {
		// System.out.println(
		// "----------：x=" + x + ", y=" + y + ", z=" + z + ", parrival=" + parrival + ",
		// quackTime=" + quackTime);
		DbExcute aDbExcute = new DbExcute();
		String sql = "UPDATE `" + Parameters.DatabaseName5 + "` SET xData='" + x + "', yData='" + y + "', zData='" + z
				+ "', parrival='" + parrival + "',quackTime='" + quackTime + "' where id='" + whereID + "';";
		System.out.println(sql);
		aDbExcute.update(sql);
	}
}
