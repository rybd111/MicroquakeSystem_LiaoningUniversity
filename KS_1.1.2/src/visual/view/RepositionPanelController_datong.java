package visual.view;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.h2.constant.Parameters;
import com.h2.locate.ReLocation;
import com.mathworks.toolbox.javabuilder.MWException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import visual.model.TableData;
import visual.util.Tools_DataCommunication;

/***
 * 重定位面板逻辑控制类-大同
 * 
 * @author Sunny-胡永亮
 *
 */
public class RepositionPanelController_datong {

	private Stage mystage = null;
	private TableData data = null;
	private String arithmetic = "PSO_Locate";
	private ArrayList<String> numbername = new ArrayList<String>();

//	private double[][] senserInformation=new double[numbername.size()][4];
	public void setMystage(Stage mystage) {
		this.mystage = mystage;
	}

	@FXML
	private VBox mCheckVBox;

	@FXML
	void onSure(ActionEvent event) throws MWException, ParseException {
		this.data = Tools_DataCommunication.getCommunication().reLocateData;
		if (numbername.size() < 3) {
			// 弹出对话框
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText("请选择参与定位的台站。");
			alert.setContentText("需要三个或三个以上的台站参与定位过程");
			alert.showAndWait();
			return;
		}
		if (this.data == null) {
			System.out.println("RepositionPanelController类出现错误");
			return;
		}
		/** 调用后台函数开始重定位 */
		String fileS[] = data.getQuackResults().getFilename_S().split("/");
		String tempS[] = fileS[fileS.length - 1].split(" ");
		String absolutetime = tempS[tempS.length-2] + " " + tempS[tempS.length-1].split("\\.")[0];
		double[][] senserInformation = new double[numbername.size()][4];
		Map<Character, Double> pHashMap = new HashMap<>();
		char[] filechararray = Tools_DataCommunication.getCommunication().fileSS.toUpperCase().toCharArray();
		// 将台站的P波到时转换为键值对的形式存在
		for (int i = 0; i < filechararray.length; i++)
			pHashMap.put(filechararray[i], Tools_DataCommunication.getCommunication().getmChart().getpArray().get(i)
					.getData().get(0).getXValue().doubleValue());
		System.out.println("用户调整的到时为：" + pHashMap);
		double[] a = new double[3];
		double t = 0.0;
		switch (numbername.get(0)) {
		case "V 3号":
			a = Parameters.SENSORINFO1[Parameters.diskNameNum][0];
			t = pHashMap.get('V') / (Parameters.FREQUENCY + 200);
			break;
		case "W 4号":
			a = Parameters.SENSORINFO1[Parameters.diskNameNum][1];
			t = pHashMap.get('W') / (Parameters.FREQUENCY + 200);
			break;
		case "X 5号":
			a = Parameters.SENSORINFO1[Parameters.diskNameNum][2];
			t = pHashMap.get('X') / (Parameters.FREQUENCY + 200);
			break;
		case "Y 6号":
			a = Parameters.SENSORINFO1[Parameters.diskNameNum][3];
			t = pHashMap.get('Y') / (Parameters.FREQUENCY + 200);
			break;
		case "Z 7号":
			a = Parameters.SENSORINFO1[Parameters.diskNameNum][4];
			t = pHashMap.get('Z') / (Parameters.FREQUENCY + 200);
			break;
		case "U 2号":
			a = Parameters.SENSORINFO1[Parameters.diskNameNum][5];
			t = pHashMap.get('Y') / (Parameters.FREQUENCY + 200);
			break;
		case "T 1号":
			a = Parameters.SENSORINFO1[Parameters.diskNameNum][6];
			t = pHashMap.get('V') / (Parameters.FREQUENCY + 200);
			break;
		default:
			System.out.println("error");
		}
		for (int i = 0; i < numbername.size(); i++) {

			if (numbername.get(i).equals("V 3号")) {
				senserInformation[i][0] = pHashMap.get('V') / (Parameters.FREQUENCY + 200)-t;// 相对时间???
				senserInformation[i][1] = Parameters.SENSORINFO1[Parameters.diskNameNum][0][0] - a[0];// x
				senserInformation[i][2] = Parameters.SENSORINFO1[Parameters.diskNameNum][0][1] - a[1];// y
				senserInformation[i][3] = Parameters.SENSORINFO1[Parameters.diskNameNum][0][2] - a[2];// z
			}
			if (numbername.get(i).equals("W 4号")) {
				senserInformation[i][0] = pHashMap.get('W') / (Parameters.FREQUENCY + 200)-t;
				senserInformation[i][1] = Parameters.SENSORINFO1[Parameters.diskNameNum][1][0] - a[0];
				senserInformation[i][2] = Parameters.SENSORINFO1[Parameters.diskNameNum][1][1] - a[1];
				senserInformation[i][3] = Parameters.SENSORINFO1[Parameters.diskNameNum][1][2] - a[2];
			}
			if (numbername.get(i).equals("X 5号")) {
				senserInformation[i][0] = pHashMap.get('X') / (Parameters.FREQUENCY + 200)-t;
				senserInformation[i][1] = Parameters.SENSORINFO1[Parameters.diskNameNum][2][0] - a[0];
				senserInformation[i][2] = Parameters.SENSORINFO1[Parameters.diskNameNum][2][1] - a[1];
				senserInformation[i][3] = Parameters.SENSORINFO1[Parameters.diskNameNum][2][2] - a[2];
			}
			if (numbername.get(i).equals("Y 6号")) {
				senserInformation[i][0] = pHashMap.get('Y') / (Parameters.FREQUENCY + 200)-t;
				senserInformation[i][1] = Parameters.SENSORINFO1[Parameters.diskNameNum][3][0] - a[0];
				senserInformation[i][2] = Parameters.SENSORINFO1[Parameters.diskNameNum][3][1] - a[1];
				senserInformation[i][3] = Parameters.SENSORINFO1[Parameters.diskNameNum][3][2] - a[2];
			}
			if (numbername.get(i).equals("Z 7号")) {
				senserInformation[i][0] = pHashMap.get('Z') / (Parameters.FREQUENCY + 200)-t;
				senserInformation[i][1] = Parameters.SENSORINFO1[Parameters.diskNameNum][4][0] - a[0];
				senserInformation[i][2] = Parameters.SENSORINFO1[Parameters.diskNameNum][4][1] - a[1];
				senserInformation[i][3] = Parameters.SENSORINFO1[Parameters.diskNameNum][4][2] - a[2];
			}
			if (numbername.get(i).equals("U 2号")) {
				senserInformation[i][0] = pHashMap.get('U') / (Parameters.FREQUENCY + 200)-t;
				senserInformation[i][1] = Parameters.SENSORINFO1[Parameters.diskNameNum][5][0] - a[0];
				senserInformation[i][2] = Parameters.SENSORINFO1[Parameters.diskNameNum][5][1] - a[1];
				senserInformation[i][3] = Parameters.SENSORINFO1[Parameters.diskNameNum][5][2] - a[2];
			}
			if (numbername.get(i).equals("T 1号")) {
				senserInformation[i][0] = pHashMap.get('T') / (Parameters.FREQUENCY + 200)-t;
				senserInformation[i][1] = Parameters.SENSORINFO1[Parameters.diskNameNum][6][0] - a[0];
				senserInformation[i][2] = Parameters.SENSORINFO1[Parameters.diskNameNum][6][1] - a[1];
				senserInformation[i][3] = Parameters.SENSORINFO1[Parameters.diskNameNum][6][2] - a[2];
			}
		}

		System.out.println("您选择的定位算法是：  " + this.arithmetic + ",  您选择参与定位的台站为：  ");
		for (int i = 0; i < numbername.size(); i++)
			System.out.println(numbername.get(i) + ": 到时=" + senserInformation[i][0] + ", x=" + senserInformation[i][1]
					+ ", y=" + senserInformation[i][2] + ", z=" + senserInformation[i][3]);
		// 调用后台重定位函数
		String[] resultString = ReLocation.locate(senserInformation, this.arithmetic, absolutetime);
		if (resultString == null || resultString.length == 0) {
			System.out.println("重定位算法无返回值");
			return;
		}
		Tools_DataCommunication.getCommunication().reLocationResultString = resultString;
		double x = Double.parseDouble(resultString[0]);
		double y = Double.parseDouble(resultString[1]);
		double z = Double.parseDouble(resultString[2]);
		double parrival = Double.parseDouble(resultString[3]);// 新到时???
		String quackTime = resultString[4];// 发震时刻???
		System.out.println("重定位坐标：x=" + x + ", y=" + y + ", z=" + z + ", parrival=" + parrival + ", intequackTime="
				+ quackTime);
		/** 显示CAD定位 */
		Tools_DataCommunication.getCommunication().getmCAD().exeJS(x, y,
				Tools_DataCommunication.getCommunication().circleRadius, "PSO");
		//记录重定位后的数据。
		Tools_DataCommunication.getCommunication().reLocateData.getQuackResults().setxData(x);
		Tools_DataCommunication.getCommunication().reLocateData.getQuackResults().setyData(y);
		Tools_DataCommunication.getCommunication().reLocateData.getQuackResults().setzData(z);
		Tools_DataCommunication.getCommunication().reLocateData.getQuackResults().setQuackTime(quackTime);
		Tools_DataCommunication.getCommunication().reLocateData.getQuackResults().setParrival(parrival);
	}

	@FXML
	void onCancel(ActionEvent event) {
		close();
		this.mystage.close();
	}

	@FXML
	void initialize() {

		CheckBox box = null;
		for (int i = 1; i < mCheckVBox.getChildren().size(); i++) {
			box = (CheckBox) mCheckVBox.getChildren().get(i);
			box.setDisable(true);
			String str = box.getText();
			// 添加监听选择台站事件，用户选择就添加，用户取消就删除
			box.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (oldValue == false && newValue == true) {
						numbername.add(str);// 用户选择，添加
					}
					if (oldValue == true && newValue == false) {
						numbername.remove(str);// 用户取消，删除
					}
				}
			});
		}
		setAble();
	}

	/** 关闭Stage操作 */
	public void close() {
		this.arithmetic = "PSO";
		this.numbername.clear();
	}

	/***
	 * 激活重定位面板中选择台站的按钮
	 */
	public void setAble() {
		if (Tools_DataCommunication.getCommunication().fileSS == null)
			return;
		CheckBox box = null;
		for (int i = 1; i < mCheckVBox.getChildren().size(); i++) {
			box = (CheckBox) mCheckVBox.getChildren().get(i);
			box.setDisable(true);
		}
		char[] name = Tools_DataCommunication.getCommunication().fileSS.toUpperCase().toCharArray();
		for (int i = 0; i < name.length; i++)
			switch (name[i]) {
			case 'V':// V 3号
				box = (CheckBox) mCheckVBox.getChildren().get(1);
				box.setDisable(false);
				break;
			case 'W':// W 4号
				box = (CheckBox) mCheckVBox.getChildren().get(2);
				box.setDisable(false);
				break;
			case 'X':// X 5号
				box = (CheckBox) mCheckVBox.getChildren().get(3);
				box.setDisable(false);
				break;
			case 'Y':// Y 6号
				box = (CheckBox) mCheckVBox.getChildren().get(4);
				box.setDisable(false);
				break;
			case 'Z':// Z 7号
				box = (CheckBox) mCheckVBox.getChildren().get(5);
				box.setDisable(false);
				break;
			case 'U':// U 2号
				box = (CheckBox) mCheckVBox.getChildren().get(6);
				box.setDisable(false);
				break;
			case 'T':// T 1号
				box = (CheckBox) mCheckVBox.getChildren().get(7);
				box.setDisable(false);
				break;
			}
	}
}
