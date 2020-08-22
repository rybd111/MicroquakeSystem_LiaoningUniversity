package visual;

import java.io.IOException;
import visual.util.Tools_DataCommunication;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
/***
 * 
 * @author Sunny
 *
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/UI.fxml"));
//		//获得RootLayout对象
		AnchorPane root = (AnchorPane) loader.load();

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("UI界面-辽宁大学");
		primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("view/lndx.png")));
		primaryStage.show();

		/** 监听窗口关闭操作 */
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Tools_DataCommunication.getCommunication().isClient = false;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.exit(0);
					}
				}).start();
			}
		});
	}

	// 6326  	空
	//6307 		有
	public static void main(String[] args) {
		// 设置读取CSV文件的秒数
		Tools_DataCommunication.getCommunication().readTime = 1;// s
		// 设置显示CAD定位点圆的半径
		Tools_DataCommunication.getCommunication().circleRadius = 5.0;
		Tools_DataCommunication.getCommunication().isClient = true;
		launch(args);
	}
}
