package visual.controller;

import java.io.IOException;

import visual.util.Tools_DataCommunication;
import visual.view.ShowCADController;
import visual.view.UIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
public class MyCAD {
	private ShowCADController controller = null;
	private Label mLabel_neweve;

	public ShowCADController getController() {
		return controller;
	}

	public MyCAD(Label label,BorderPane root) {
		this.mLabel_neweve=label;
		BorderPane pane;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(UIController.class.getResource("ShowCAD.fxml"));
			pane = (BorderPane) loader.load();
			root.setCenter(pane);

			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 绘制定位图形 */
	public void exeJS(double x, double y, double r,String kind) {
		if (Tools_DataCommunication.getCommunication().isFinishLoadCAD)
			controller.exeJs(x, y, r,kind);
	}

	/** 当出现新事件时，将新事件显示在Label上 */
	public void updataNewLabel(String str) {
		String s = "最新事件：" + str;
		mLabel_neweve.setText(s);
	}
}
