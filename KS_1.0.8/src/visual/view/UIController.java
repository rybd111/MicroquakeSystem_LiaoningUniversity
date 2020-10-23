package visual.view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mutiThread.MainThread;
import visual.Main;
import visual.MainFrame;
import visual.Preferences;
import visual.disasterCheck;
import visual.historyQuery;

public class UIController {
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
		Tools_DataCommunication.getCommunication().showandcloseMyConsole();
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
		stage.getIcons().add(new Image(new FileInputStream(System.getProperty("user.dir")+"\\resource\\lndx.png")));
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
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"\\resource\\需求文档.docx"));
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
		stage.getIcons().add(new Image(new FileInputStream(System.getProperty("user.dir")+"\\resource\\lndx.png")));
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
				"D:/data/ConstructionData/5data/25613 2020-05-01 09-33-15`05.csv", 0.0, "five",123);
		DbExcute aDbExcute = new DbExcute();
		aDbExcute.addElement(aQuackResults);
	}

	@FXML
	void onRestore(ActionEvent event) {// 还原
		mSlider_lower.setValue(0.0);
		mSlider_upper.setValue(90000.0);
		System.out.println("还原");
	}

	@FXML
	void onSaveP(ActionEvent event) {// 保存P波到时
		System.out.println("保存P波到时");
		if (Tools_DataCommunication.getCommunication().csvPath == null
				|| Tools_DataCommunication.getCommunication().getmChart().gettIndex() == 0)
			return;
		// 弹出对话框
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("警告");
		alert.setHeaderText("您确定要保存此时的P波到时吗？");
		alert.setContentText("该操作不可恢复！！！");
		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				Tools_DataCommunication.getCommunication().getmChart().saveP();
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
