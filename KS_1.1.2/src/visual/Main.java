package visual;

import java.io.FileInputStream;
import java.io.IOException;
import com.h2.constant.ConfigToParameters;
import com.h2.constant.Parameters;

import Entrance.MainTest;
import Entrance.InitialConfig;
import Entrance.RunningSceneConfig;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mutiThread.MainThread;
import utils.printRunningParameters;
import visual.util.Tools_DataCommunication;
import visual.view.UIController;

/***
 * 作为系统UI界面的启动程序
 * 
 * @author Sunny-胡永亮
 *
 */

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/UI.fxml"));
//		//获得RootLayout对象
		AnchorPane root = (AnchorPane) loader.load();
		UIController controller = (UIController) loader.getController();
		controller.myStage = primaryStage;
		Tools_DataCommunication.getCommunication().setController(controller);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("CS界面-辽宁大学");
		primaryStage.setMinHeight(914);
		primaryStage.getIcons()
				.add(new Image(new FileInputStream(System.getProperty("user.dir") + "\\resource\\lndx.png")));
		primaryStage.show();
		primaryStage.setMaximized(true);// 设置最大化
		primaryStage.setResizable(false);// 禁止对窗口进行拉伸操作！
//		System.out.println(primaryStage.getX());
//		System.out.println(primaryStage.getY());
		/** 监听窗口关闭操作 */
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				// 弹出对话框
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("警告");
				alert.setHeaderText("您确定要关闭主窗口吗？");
				alert.setContentText("关闭主窗口后，程序将停止运行!");
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK)
						Tools_DataCommunication.getCommunication().exitThreadAll();
					else
						event.consume();
				});
			}
		});
		/** 监听Stage的位置XY */
		primaryStage.xProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (controller.RepositionPanelStage == null)
					return;
//				System.out.println("x=" + primaryStage.getX() + ",  y=" + primaryStage.getY());
				controller.RepositionPanelStage.setX(primaryStage.getX() - 295);
				controller.RepositionPanelStage.setY(primaryStage.getY());
			}
		});
		/** 监听窗口最大化及还原窗口操作 */
		primaryStage.maximizedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				controller.getmSplitpaneSum().setDividerPositions(0.51);
				controller.getmSplitpane().setDividerPositions(0.5);
				if (!newValue) {// 还原操作，窗口恢复到初始状态
					primaryStage.setWidth(1077);
					primaryStage.setHeight(914);
				}
			}
		});
	}

	public static void main(String[] args) throws NumberFormatException, IOException {

		// 设置读取CSV文件的秒数
		Tools_DataCommunication.getCommunication().readTime = com.h2.constant.Parameters.READTIMER;// s
		// 设置显示CAD定位点圆的半径
		Tools_DataCommunication.getCommunication().circleRadius = 200.0;
		// 标记已启动UI界面
		Tools_DataCommunication.getCommunication().isClient = true;
		
		/** 载入配置文件*/
		new ConfigToParameters();
		
		/** 情景配置，共有8中模式，每一种对应一个整形数值，在MainTest类中定义。*/
		new RunningSceneConfig(MainTest.LOCAL_OFFLINE_STORAGE);
		
		/** 输出参数*/
		printRunningParameters.printAllParameters();
		
//		Tools_DataCommunication.getCommunication().showandcloseMyConsole();

		// 启动JavaFX程序
		launch(args);
	}
}
