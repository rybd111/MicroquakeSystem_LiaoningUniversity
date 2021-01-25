package visual.view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.swt.widgets.Display;
import com.db.DbExcute;
import visual.model.TableData;
import visual.util.Tools_DataCommunication;
import DataExchange.QuackResults;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mutiThread.MainThread;
import visual.Main;
import visual.Preferences;
import visual.controller.MyLineChart;

public class UIController {
	public Stage myStage = null;
	@FXML
	private SplitPane mSplitpaneSum;

	public SplitPane getmSplitpaneSum() {
		return mSplitpaneSum;
	}

	@FXML
	private SplitPane mSplitpane;

	public SplitPane getmSplitpane() {
		return mSplitpane;
	}

	// -----------------------------CAD------------------
	@FXML
	private AnchorPane mGreenPane;

	@FXML
	private BorderPane mBorderPane;
	@FXML
	private Label mLabel_neweve;
//--------------------------表格-------------------
	@FXML
	private AnchorPane mYellowPane;

	@FXML
	private ComboBox<String> mComboBox;
	@FXML
	private Label mEventLabel;

	@FXML
	private TableView<TableData> mTableView;

//------------------------波形图-------------------
	@FXML
	private AnchorPane mBluePane;

	@FXML
	private VBox mVBoxLineChart;

	public VBox getmVBoxLineChart() {
		return mVBoxLineChart;
	}

//---------------------------其他窗口对象-------------
	public Stage RepositionPanelStage = null;
	public Stage DistributedPanelStage = null;

	@FXML
	private Slider mSlider_P;
	@FXML
	private Slider mSlider_lower;
	@FXML
	private Slider mSlider_upper;
	@FXML
	private TextField mText_P;
	@FXML
	private TextField mText_lower;
	@FXML
	private TextField mText_upper;

	@FXML
	private SplitPane mTimerSplitPane;

//------------------------------------------------------------------
//--------------------------按钮绑定的事件--------------------------
//------------------------------------------------------------------
	@FXML
	void onClickStart(ActionEvent event) {// 运行
		if (Tools_DataCommunication.getCommunication().isRunTXT) {// 分布式在运行
			// 弹出对话框
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText("请先点击停止按钮");
			alert.setContentText("分布式程序停止后，才能启动集中式程序运行！");
			alert.showAndWait();
			return;
		}
		Tools_DataCommunication.getCommunication().showandcloseMyConsole();
		System.out.println("按下运行按钮");
	}

	@FXML
	void onClickStop(ActionEvent event) {// 停止
		// 停止集中式运行
		MainThread.exitVariable_visual = true;
		// 停止分布式运行
		Tools_DataCommunication.getCommunication().isRunTXT = false;
		System.out.println("按下停止按钮");
	}

	@FXML
	void onClickExit(ActionEvent event) {// 退出
		System.exit(0);
		System.out.println("按下退出按钮");
	}

	@FXML
	void onClickHistoryQuery(ActionEvent event) throws IOException {// 实时监测
		if (MainThread.exitVariable_visual) {
			// 弹出对话框
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText("请先点击运行按钮");
			alert.setContentText("计算程序运行后，方可查看实时数据");
			alert.showAndWait();
			return;
		}
		System.out.println("按下实时监测按钮");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/CurrentTimeLinechart.fxml"));
//		//获得RootLayout对象
		AnchorPane root = (AnchorPane) loader.load();
		CurrentTimeLinechartController control = loader.getController();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("实时监测");
		stage.getIcons().add(new Image(new FileInputStream(System.getProperty("user.dir") + "\\resource\\lndx.png")));
		stage.setResizable(false);// 禁止对窗口进行拉伸操作！
		stage.show();
		/** 监听窗口关闭操作 */
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				for (int i = 0; i < control.getT_seriesZ().size(); i++)
					control.getT_seriesZ().get(i).getData().clear();
				control.getTime_seriesZ().getData().clear();
				control.ani_stop();
//				System.exit(0);
			}
		});
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
			Desktop.getDesktop().open(new File(System.getProperty("user.dir") + "\\resource\\需求文档.docx"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("按下帮助按钮");
	}

	@FXML
	void onClickQuery(ActionEvent event) throws IOException {// 查询
		System.out.println("按下查询按钮");

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/QueryPanel.fxml"));
//		//获得RootLayout对象
		AnchorPane root = (AnchorPane) loader.load();
		QueryPanelController controller = loader.getController();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("查询面板");
		stage.getIcons().add(new Image(new FileInputStream(System.getProperty("user.dir") + "\\resource\\lndx.png")));
		stage.setResizable(false);// 禁止对窗口进行拉伸操作！
		stage.show();

		/** 监听窗口关闭操作 */
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
//				if(controller.getMyThread()!=null)
//					controller.getMyThread().interrupt();
//				Tools_DataCommunication.getCommunication().getmCAD().getController().deleteALL();
				Tools_DataCommunication.getCommunication().dataList_QueryPanel.clear();
				mTableView.setItems(Tools_DataCommunication.getCommunication().dataList);
				System.out.println("查询界面关闭");
				Tools_DataCommunication.getCommunication().getmCAD().getController().deleteALL();
			}
		});
	}

	@FXML
	void onClickTest(ActionEvent event) {// Test
		System.out.println("按下Test按钮");

		DateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = formatDateTime.format(new Date());

		QuackResults aQuackResults = new QuackResults(1, 1, 1, time, 5.2, 0.2, " ", 0.0, 0.0,
				"D:/data/ConstructionData/5data/25613 2020-05-01 09-33-15`05.csv", 0.0, "five", 123);
		DbExcute aDbExcute = new DbExcute();
		aDbExcute.addElement(aQuackResults);
	}

	@FXML
	void onClickDistributed(ActionEvent event) throws IOException {// 分布式运行
		System.out.println("按下分布式运行按钮");
		if (DistributedPanelStage != null)
			DistributedPanelStage.close();
		if (!MainThread.exitVariable_visual) {// 集中式运行
			// 弹出对话框
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText("请先点击停止按钮");
			alert.setContentText("集中式程序停止后，才能启动分布式程序运行！");
			alert.showAndWait();
			return;
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/DistributedPanel.fxml"));
//		//获得RootLayout对象
		AnchorPane root = (AnchorPane) loader.load();
		DistributedPanelController controller = loader.getController();
		Tools_DataCommunication.getCommunication().distributedPanelController = controller;
		Scene scene = new Scene(root);
		DistributedPanelStage = new Stage();
		DistributedPanelStage.setScene(scene);
		DistributedPanelStage.setTitle("分布式工作状态监控面板");
		DistributedPanelStage.getIcons()
				.add(new Image(new FileInputStream(System.getProperty("user.dir") + "\\resource\\lndx.png")));
//		DistributedPanelStage.setResizable(false);// 禁止对窗口进行拉伸操作！
		DistributedPanelStage.show();

		/** 监听窗口关闭操作 */
		DistributedPanelStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {

			}
		});
	}

	@FXML
	void onRestore(ActionEvent event) {// 还原
		mSlider_lower.setValue(0.0);
		mSlider_upper.setValue(90000.0);
		System.out.println("还原");
	}

	@FXML
	void onCalculate(ActionEvent event) throws IOException {// 重新定位
		System.out.println("点击重新定位功能");
		if (RepositionPanelStage != null) {
			RepositionPanelStage.close();
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/RepositionPanel.fxml"));
//		//获得RootLayout对象
		AnchorPane root = (AnchorPane) loader.load();
		RepositionPanelController controller = loader.getController();
		Tools_DataCommunication.getCommunication().repositionPanelController = controller;
		Scene scene = new Scene(root);
		RepositionPanelStage = new Stage();
		RepositionPanelStage.setScene(scene);
		RepositionPanelStage.setTitle("重定位面板");
		RepositionPanelStage.getIcons()
				.add(new Image(new FileInputStream(System.getProperty("user.dir") + "\\resource\\lndx.png")));
		RepositionPanelStage.setResizable(false);// 禁止对窗口进行拉伸操作！
//		if (myStage != null) {
//			RepositionPanelStage.setX(myStage.getX() - 295);
//			RepositionPanelStage.setY(myStage.getY());
//		}
		RepositionPanelStage.show();
		controller.setMystage(RepositionPanelStage);
		/** 监听窗口关闭操作 */
		RepositionPanelStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				controller.close();
			}
		});
	}

	@FXML
	void onSaveP(ActionEvent event) {// 保存
		System.out.println("保存");
		if (Tools_DataCommunication.getCommunication().csvPath == null
				|| Tools_DataCommunication.getCommunication().getmChart().gettIndex() == 0
				|| Tools_DataCommunication.getCommunication().reLocateData == null)
			return;
		// 弹出对话框
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("警告");
		alert.setHeaderText("您确定要保存此时的P波到时吗？");
		alert.setContentText("该操作不可恢复！！！");
		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				MyLineChart myLineChart = Tools_DataCommunication.getCommunication().getmChart();
				QuackResults data = Tools_DataCommunication.getCommunication().reLocateData.getQuackResults();
				myLineChart.updata(data.getxData(), data.getyData(), data.getzData(), data.getParrival(),
						data.getQuackTime(),
						Integer.parseInt(Tools_DataCommunication.getCommunication().reLocateData.getEventIndex()));
				myLineChart.saveP();
				System.out.println("保存成功");
			}
		});

	}

	// 在Main程序加载fxml文件时候执行
	@FXML
	void initialize() {
		// 获取当前UI主界面控制类
		Tools_DataCommunication.getCommunication().setController(this);
		// 显示CAD
		Tools_DataCommunication.getCommunication().showCAD(mLabel_neweve, mBorderPane);
		// 显示TableView
		Tools_DataCommunication.getCommunication().ShowTableView(mTableView, mComboBox, mEventLabel);
		// 显示波形图
		Tools_DataCommunication.getCommunication().ShowLineChart(mSlider_P, mText_P, mSlider_lower, mSlider_upper,
				mText_lower, mText_upper, mVBoxLineChart, mTimerSplitPane);
		mVBoxLineChart.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				Tools_DataCommunication.getCommunication().getmChart()
						.alterSplitPaneHight(newValue.doubleValue() / mVBoxLineChart.getChildren().size());
			}
		});
	}
}
