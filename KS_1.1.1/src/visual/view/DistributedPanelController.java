package visual.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.Duration;
import visual.util.Tools_DataCommunication;
import visual_data.MatchRecords;
import visual_data.readTxt;

public class DistributedPanelController {

	private XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
	@FXML
	private LineChart<String, Number> mLineChart;

	@FXML
	private DatePicker mDate;

	@FXML
	void onClickPull(ActionEvent event) {
		System.out.println(mDate.getValue());
	}

	@FXML
	void initialize() {
		mLineChart.getData().add(series);
		mLineChart.setAnimated(false);
//		ArrayList<ArrayList<String>> data = readTxt.getData("Z:/test/hyl/201912");
//		for (int i = 0; i < data.size(); i++)
//			series.getData().add(new Data<String, Number>(readTxt.name.get(i).toString(), data.get(i).size()));

		// 每隔五分钟调用一次读取 .txt 的记录类
		if (Tools_DataCommunication.getCommunication().isRunTXT)
			return;
		Tools_DataCommunication.getCommunication().isRunTXT = true;
		this.timerTask_readTXT();
	}

	/***
	 * 每隔五分钟调用一次读取 .txt 的记录类
	 * 
	 * @param isRun true读取记录 false停止读取记录
	 */
	private void timerTask_readTXT() {
		/**
		 * Runnable：实现了Runnable接口，jdk就知道这个类是一个线程
		 */
		Runnable runnable = new Runnable() {
			// 创建 run 方法
			public void run() {
				if (!Tools_DataCommunication.getCommunication().isRunTXT)
					return;
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// 更新JavaFX的主线程的代码放在此处
						ArrayList<ArrayList<String>> data = readTxt.getData("Z:/test/hyl/201912");
						String x = null;
						int y = -1;
						for (int i = 0; i < data.size(); i++) {
							x = readTxt.name.get(i).toString();
							y = data.get(i).size();
//							System.out.println("[" + x + "," + y + "]");
							series.getData().add(new Data<String, Number>(x, y));
						}
						//进行记录匹配
						MatchRecords.matcher(data);
					}
				});
			}
		};
		// ScheduledExecutorService:是从Java SE5的java.util.concurrent里，
		// 做为并发工具类被引进的，这是最理想的定时任务实现方式。
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		// 第一次执行的时间为0秒，然后每隔五分钟执行一次
		service.scheduleAtFixedRate(runnable, 0, 300, TimeUnit.SECONDS);
	}

}