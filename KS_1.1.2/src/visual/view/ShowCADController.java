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
		browser.loadURL("http://182.92.239.30:3001/good2.html");
//		browser.loadURL("http://localhost:3000/good2.html");
//		browser.loadURL("http://www.baidu.com");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
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
					deleteALL();
				}
			}
		}).start();
	}

	/** 二维CAD图纸 。用于在JxBrowser组件上显示点击事件的定位点 */
	public void exeJs(double x, double y, double r,String kind) {
		String strJs=null;
		if(kind.equals("PSO"))//PSO[0,0,255]蓝色
		{
			strJs = "MyCircle(" + x + "," + y + "," + r +",0,0,255"+ ")";
		}
		else if(kind.equals("five")){//five[0,255,0]绿色
			strJs = "MyCircle(" + x + "," + y + "," + r +",0,255,0"+ ")";
		}
		else if(kind.equals("three")) {//three[255,0,0]红色
			strJs = "MyCircle(" + x + "," + y + "," + r +",255,0,0"+ ")";
		}
			
//		System.out.println(strJs);
		if(strJs==null)
		{
			System.out.println("============Error：ShowCADController类中调用JS方法的字符串为空==================");
			return;
		}
			
		browser.executeJavaScript(strJs);

		/** 把定位点放到视区的中心上 */
//		double d = Math.sqrt(x * x + y * y);
//		double z = (r / y) * d;
//		browser.executeJavaScript("MyView(" + (x - z) + "," + (y - z) + "," + (x + z) + "," + (y + z) + ")");
	}

	/** 三维CAD图纸 */
	public void exeJs(double x, double y, double z, double r) {

	}

	/** 删除CAD上所有定位点 */
	public void deleteALL() {
		browser.executeJavaScript("MydeleteAll()");
	}

	/** 二维CAD图纸。用于绘制由通过查询约束查询到的所有事件的定位点 */
	public void ShowcircleALL(double x, double y, double r) {
		browser.executeJavaScript("MyCircleALL(" + x + "," + y + "," + r + ")");
	}
}
