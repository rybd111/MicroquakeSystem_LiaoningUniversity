package visual.view;

import java.net.URL;
import java.util.ResourceBundle;
import visual.model.MyPrintStream;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import mutiThread.MainThread;

public class MyConsoleController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea mConsole;

	@FXML
	void initialize() {
		//绑定流
		MyPrintStream mps = new MyPrintStream(System.out, mConsole);
		System.setOut(mps);
		System.setErr(mps);
	}
	public void startMainThread() {
		if (MainThread.exitVariable_visual == true) {
			MainThread.exitVariable_visual = false;
			MainThread main = new MainThread();
			main.start();
		}
	}
}
