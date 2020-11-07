package visual.view;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RepositionPanelController {

	private Stage mystage = null;
	private String arithmetic = "PSO";
	private ArrayList<String> numbername = new ArrayList<String>();

	public void setMystage(Stage mystage) {
		this.mystage = mystage;
	}

	@FXML
	private VBox mRadioBVox;
	private ToggleGroup radiogroup = new ToggleGroup();
	@FXML
	private VBox mCheckVBox;

	@FXML
	void onSure(ActionEvent event) {
		RadioButton button = null;
		for (int i = 1; i < mRadioBVox.getChildren().size(); i++) {
			button = (RadioButton) mRadioBVox.getChildren().get(i);
			if (button.isSelected()) {
				this.arithmetic = button.getText();
				break;
			}
		}

		if (numbername.size() < 3) {
			// 弹出对话框
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText("请选择参与定位的台站。");
			alert.setContentText("需要三个或三个以上的台站参与定位过程");
			alert.showAndWait();
			return;
		}
		/** 调用后台函数，开始重定位 */
		
		System.out.println("您选择的定位算法是：  " + this.arithmetic + ",  您选择参与定位的台站为：  ");
		for (int i = 0; i < numbername.size(); i++)
			System.out.println(numbername.get(i));
	}

	@FXML
	void onCancel(ActionEvent event) {
		close();
		this.mystage.close();
	}

	@FXML
	void initialize() {
		((RadioButton) mRadioBVox.getChildren().get(1)).setToggleGroup(radiogroup);// 3
		((RadioButton) mRadioBVox.getChildren().get(2)).setToggleGroup(radiogroup);// 5
		RadioButton PSObutton = (RadioButton) mRadioBVox.getChildren().get(3);// PSO
		PSObutton.setToggleGroup(radiogroup);
		radiogroup.selectToggle(PSObutton);
		CheckBox box = null;
		for (int i = 1; i < mCheckVBox.getChildren().size(); i++) {
			box = (CheckBox) mCheckVBox.getChildren().get(i);
			String str = box.getText();
			box.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (oldValue == false && newValue == true) {
						numbername.add(str);
					}
					if (oldValue == true && newValue == false) {
						numbername.remove(str);
					}
//					System.out.println(oldValue);
//					System.out.println(newValue);
				}
			});
		}

	}

	/** 关闭Stage操作 */
	public void close() {
		this.arithmetic = "PSO";
		this.numbername.clear();
	}
}
