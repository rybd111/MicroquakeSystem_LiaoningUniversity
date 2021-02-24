package visual.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.h2.constant.Parameters;
import com.teamdev.jxbrowser.chromium.TerminationStatus;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import mutiThread.MainThread;
import visual.chartFrame;

public class CurrentTimeLinechartController {

	private final int WINDOW_SIZE = 12;
	private Timeline animation;
	private int count = 0;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private String[] sensorName = { "杨\n甸\n子", "树\n碑\n子", "北\n青\n堆\n子", "车\n队", "工\n业\n广\n场", "火\n药\n库", "南\n风\n井",
			"蒿\n子\n屯", "李\n大\n人" };
	@FXML
	private VBox mVBox;

	/** 波形图表 */
	private ArrayList<LineChart<String, Number>> mChart_T = new ArrayList<>();
	public ArrayList<XYChart.Series<String, Number>> getT_seriesZ() {
		return T_seriesZ;
	}
	// 时间轴
	private LineChart<String, Number> mChart_xAxis = new LineChart<String, Number>(new CategoryAxis(),
			new NumberAxis());
	public XYChart.Series<String, Number> getTime_seriesZ() {
		return time_seriesZ;
	}
	
	/** 波形线 */
	private ArrayList<XYChart.Series<String, Number>> T_seriesZ = new ArrayList<XYChart.Series<String, Number>>();// 数组
	// 时间轴
	private XYChart.Series<String, Number> time_seriesZ = new XYChart.Series<String, Number>();

	@FXML
	void initialize() {
		animation = new Timeline();

		final String stockLineChartCss = UIController.class.getResource("Line.css").toExternalForm();
		// 创建LineChart表
		for (int i = 0; i < MainThread.fileStr.length; i++) {
			T_seriesZ.add(new XYChart.Series<String, Number>());
			LineChart<String, Number> chart = new LineChart<String, Number>(new CategoryAxis(), new NumberAxis());
			// 禁止动画播放
			chart.setAnimated(false);
			// 设置LineChart的样式
			chart.getStylesheets().add(stockLineChartCss);
			// 设置LineChart锚点
			setChartShow(i, chart, -70.0);
			// 把线放入LineChart表中
			chart.getData().addAll(T_seriesZ.get(i));
			mChart_T.add(chart);
		}
		mChart_xAxis.setAnimated(false);
		mChart_xAxis.getStylesheets().add(stockLineChartCss);
		setXAxisShow(mChart_xAxis);
		// 把线放入LineChart表中
		mChart_xAxis.getData().addAll(time_seriesZ);
		/***初始化波形图*/
		Date date = new Date();
		long tdate=date.getTime();
		for (int i = 0; i < 500; i++) {
			for (int j = 0; j < T_seriesZ.size(); j++)
				T_seriesZ.get(j).getData().add(new XYChart.Data(dateFormat.format(tdate + i * 1000), 0));
			time_seriesZ.getData().add(new XYChart.Data(dateFormat.format(tdate + i * 1000), 0));
		}
		/** 向波形图中添加数据 */
		chartFrame[] f = new chartFrame[Parameters.SensorNum];
		for (int i = 0; i < Parameters.SensorNum; i++)
			f[i] = new chartFrame(null, i);
		// 6 minutes data per frame

		final KeyFrame frame = new KeyFrame(Duration.millis(1000), (ActionEvent actionEvent) -> {
			Date date1 = new Date();
			for (int i = 0; i < T_seriesZ.size(); i++) {
				if (MainThread.aDataRec[i].afterVector == null)
					continue;
				if (count > MainThread.aDataRec[i].afterVector.size())
					count = 0;
				T_seriesZ.get(i).getData()
						.add(new XYChart.Data(MainThread.aDataRec[i].afterVector.get(count).split(" ")[6].substring(10),
								f[i].historyData(count)));
			}
			if (MainThread.aDataRec[0].afterVector != null)
				time_seriesZ.getData()
						.add(new XYChart.Data(MainThread.aDataRec[0].afterVector.get(count).split(" ")[6].substring(10),
								f[0].historyData(count)));
			count = count + Parameters.FREQUENCY + 200 - 1;
			/** 删除坐标 */
			if (T_seriesZ.size() != 0 && T_seriesZ.get(0).getData().size() > 500) {
				for (int i = 0; i < T_seriesZ.size(); i++)
					T_seriesZ.get(i).getData().remove(0);
				time_seriesZ.getData().remove(0);
			}
		});

		animation.getKeyFrames().add(frame);
		animation.setCycleCount(Animation.INDEFINITE);
		animation.play();
	}
public void ani_stop() {
	animation.stop();
}
	/**
	 * 设置波形图表在UI上的位置
	 * 
	 * @param index
	 * @param chart
	 */
	private void setChartShow(int index, LineChart<String, Number> chart, double bottom) {
		SplitPane splitpane = null;
		AnchorPane anchorpane = null;
		AnchorPane anchorpaneLabel = null;
		Label label=null;
		splitpane = (SplitPane) mVBox.getChildren().get(index);
		anchorpane = (AnchorPane) splitpane.getItems().get(1);
		// 设置波形图的锚点
		anchorpane.setTopAnchor(chart, -10.0);
		anchorpane.setBottomAnchor(chart, bottom);
		anchorpane.setLeftAnchor(chart, 0.0);
		anchorpane.setRightAnchor(chart, -10.0);
		anchorpane.getChildren().addAll(chart);
		//设置传感器名字
		anchorpaneLabel = (AnchorPane) splitpane.getItems().get(0);
		label=(Label)anchorpaneLabel.getChildren().get(0);
		String fileS[] = MainThread.fileStr[index].split("/");
//		String fileSS = fileS[fileS.length - 1].split(" ")[0];// filess="25613"
		char[] name=fileS[fileS.length - 1].split(" ")[0].toUpperCase().toCharArray();
//		System.out.println("111:    "+name[name.length-1]);
		switch (name[name.length-1]) {
		case 'T'://T 杨甸子
			label.setText(sensorName[0]);
			break;
		case 'U'://U 树碑子
			label.setText(sensorName[1]);
			break;
		case 'W'://W 北青堆子
			label.setText(sensorName[2]);
			break;
		case 'X'://X 车队
			label.setText(sensorName[3]);
			break;
		case 'Z'://Z 工业广场
			label.setText(sensorName[4]);
			break;
		case 'Y'://Y 火药库
			label.setText(sensorName[5]);
			break;
		case 'V'://V 南风井
			label.setText(sensorName[6]);
			break;
		case 'S'://S 蒿子屯
			label.setText(sensorName[7]);
			break;
		case 'R'://R 李大人
			label.setText(sensorName[8]);
			break;
		default:
			System.out.println(
					"======================Error：-传感器站台名称出错。-CurrentTimeLinechartController===================================");
			break;
		}
	}

	/**
	 * 设置X坐标轴在UI上的位置
	 * 
	 * @param chart
	 */
	private void setXAxisShow(LineChart<String, Number> chart) {
		SplitPane splitpane = null;
		AnchorPane anchorpane = null;
		splitpane = (SplitPane) mVBox.getChildren().get(mVBox.getChildren().size() - 1);
		anchorpane = (AnchorPane) splitpane.getItems().get(1);
		// 设置波形图的锚点
		anchorpane.setTopAnchor(chart, -20.0);
		anchorpane.setBottomAnchor(chart, 50.0);
		anchorpane.setLeftAnchor(chart, 0.0);
		anchorpane.setRightAnchor(chart, -10.0);
		anchorpane.getChildren().addAll(chart);
	}
}
