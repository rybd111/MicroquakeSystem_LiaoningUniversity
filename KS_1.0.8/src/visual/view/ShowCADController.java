package visual.view;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;

import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import visual.util.Tools_DataCommunication;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ShowCADController implements Initializable {

	@FXML
	private BrowserView browserView;
	private Browser browser = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		browser = browserView.getBrowser();
		Tools_DataCommunication.getCommunication().jxBrowser = browser;
		browser.loadURL("http://localhost:3000/good2.html");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/** 一直检测mybool是否为true */
				while (!browser.executeJavaScriptAndReturnValue("mybool").getBooleanValue()) {
				}
				// 再进行一次判断mybool的是否为true
				if (browser.executeJavaScriptAndReturnValue("mybool").getBooleanValue()) {
					Tools_DataCommunication.getCommunication().isFinishLoadCAD = true;
					System.out.println("CAD加载完成！");
				}
			}
		}).start();
	}

	/** 二维CAD图纸 */
	public void exeJs(double x, double y, double r) {
		browser.executeJavaScript("MyCircle(" + x + "," + y + "," + r + ")");
		
		double d = Math.sqrt(x * x + y * y);
		
		double z=(r/y)*d;
		
		browser.executeJavaScript("MyView(" + (x - z) + "," + (y - z) + "," + (x + z) + "," + (y + z) + ")");
	}

	/** 三维CAD图纸 */
	public void exeJs(double x, double y, double z, double r) {

	}
}
