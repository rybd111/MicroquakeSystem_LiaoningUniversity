package com.visual;

import com.visual.util.Tools_DataCommunication;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

//	public static ObservableList dataList = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
//		Parent root = FXMLLoader.load(getClass().getResource("GreenArea.fxml"));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/UI.fxml"));
//		//获得RootLayout对象
		AnchorPane root = (AnchorPane) loader.load();

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("view/lndx.png")));
		primaryStage.show();
		
		/**监听窗口关闭操作*/
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
		    	new Thread(new Runnable() {
		    		@Override
		    		public void run() {
		    			try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		    			System.exit(0);
		    		}
		    	}).start();
			}
		});
	}

	public static void main(String[] args) {
		//设置读取CSV文件的秒数
		Tools_DataCommunication.getCommunication().readTime=1;// s
		launch(args);
	}
}
