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
		for (int i = 0; i < Parameters.SensorNum; i++) {
			T_seriesZ.add(new XYChart.Series<String, Number>());
			LineChart<String, Number> chart = new LineChart<String, Number>(new CategoryAxis(), new NumberAxis());
			// 禁止动画播放
			chart.setAnimated(false);
			// 设置LineChart的样式
			chart.getStylesheets().add(stockLineChartCss);
			// 设置LineChart锚点
			setChartShow(i, chart, -30.0);
			// 把线放入LineChart表中
			chart.getData().addAll(T_seriesZ.get(i));
			mChart_T.add(chart);
		}
		mChart_xAxis.setAnimated(false);
		mChart_xAxis.getStylesheets().add(stockLineChartCss);
		setXAxisShow(mChart_xAxis);
		// 把线放入LineChart表中
		mChart_xAxis.getData().addAll(time_seriesZ);
		/** 向波形图中添加数据 */
		chartFrame[] f = new chartFrame[Parameters.SensorNum];
		for (int i = 0; i < Parameters.SensorNum; i++)
			f[i] = new chartFrame(null, i);
		// 6 minutes data per frame

		final KeyFrame frame = new KeyFrame(Duration.millis(1000), (ActionEvent actionEvent) -> {
			Date date = new Date();
			for (int i = 0; i < T_seriesZ.size(); i++) {
				if (MainThread.aDataRec[i].afterVector == null)
					continue;
				if (count > MainThread.aDataRec[i].afterVector.size())
					count = 0;
//				T_seriesZ.get(i).getData().add(new XYChart.Data(dateFormat.format(date), f[i].historyData(count)));
				T_seriesZ.get(i).getData()
						.add(new XYChart.Data(MainThread.aDataRec[i].afterVector.get(count).split(" ")[6].substring(10),
								f[i].historyData(count)));
			}
			if (MainThread.aDataRec[0].afterVector != null)
				time_seriesZ.getData()
						.add(new XYChart.Data(MainThread.aDataRec[0].afterVector.get(count).split(" ")[6].substring(10),
								f[0].historyData(count)));
//			time_seriesZ.getData().add(new XYChart.Data(dateFormat.format(date), f[0].historyData(count)));
			count = count + Parameters.FREQUENCY + 200 - 1;
			/** 删除坐标 */
			if (T_seriesZ.size() != 0 && T_seriesZ.get(0).getData().size() > 500) {
				for (int i = 0; i < T_seriesZ.size(); i++)
					T_seriesZ.get(i).getData().remove(0);
				time_seriesZ.getData().remove(0);
			}
			if (time_seriesZ.getData().size() == 15) {
				for (int i = 0; i < mChart_T.size(); i++) {
					SplitPane splitpane = null;
					AnchorPane anchorpane = null;
					splitpane = (SplitPane) mVBox.getChildren().get(i);
					anchorpane = (AnchorPane) splitpane.getItems().get(1);
					anchorpane.setBottomAnchor(mChart_T.get(i), -70.0);
				}
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
		splitpane = (SplitPane) mVBox.getChildren().get(index);
		anchorpane = (AnchorPane) splitpane.getItems().get(1);
		// 设置波形图的锚点
		anchorpane.setTopAnchor(chart, -10.0);
		anchorpane.setBottomAnchor(chart, bottom);
		anchorpane.setLeftAnchor(chart, 0.0);
		anchorpane.setRightAnchor(chart, -10.0);
		anchorpane.getChildren().addAll(chart);
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
