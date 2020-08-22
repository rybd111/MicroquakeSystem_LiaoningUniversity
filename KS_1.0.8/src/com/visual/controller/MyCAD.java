package com.visual.controller;

import java.io.IOException;

import com.visual.util.Tools_DataCommunication;
import com.visual.view.ShowCADController;
import com.visual.view.UIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class MyCAD {
	private ShowCADController controller = null;

	public MyCAD(BorderPane root) {
		BorderPane pane;
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(UIController.class.getResource("ShowCAD.fxml"));
			pane = (BorderPane) loader.load();
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					try {
//						Thread.sleep(8000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println(pane.getWidth());
//					System.out.println(pane.getHeight());
//				}
//			}).start();

			root.setCenter(pane);

			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 对CAD画园进行封装 */
	public void exeJS(double x, double y, double r) {
		if (Tools_DataCommunication.getCommunication().isFinishLoadCAD)
			controller.exeJs(x, y, r);
	}
}
