/**
 * 
 */
package visual;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
/**
 * @author Hanlin Zhang
 */
public class rightCorner extends JFrame{
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 */
	public static void main(String[] args) {
		rightCorner r = new rightCorner();
	}
	
	public rightCorner() {
		init();
	}
	
	public void init() {
//		try {
//			MainFrame window = new MainFrame();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		this.setLocationRelativeTo(null);
//		this.setTray();
		this.setVisible(true);
	}
	
	//添加托盘显示：1.先判断当前平台是否支持托盘显示
	public void setTray() {
		
		if(SystemTray.isSupported()){//判断当前平台是否支持托盘功能
			//创建托盘实例
			SystemTray tray = SystemTray.getSystemTray();
			//创建托盘图标：1.显示图标Image 2.停留提示text 3.弹出菜单popupMenu 4.创建托盘图标实例
			//1.创建Image图像
			Image image = Toolkit.getDefaultToolkit().getImage("I:/研究生阶段/矿山/更新程序/KS_1.0.8/WebRoot/image/lndx - 副本.png");
			//2.停留提示text
			String text = "Coal Mine";
			//3.弹出菜单popupMenu
			PopupMenu popMenu = new PopupMenu();
			MenuItem itmOpen = new MenuItem("show");
			itmOpen.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					Show();
				}
			});
			MenuItem itmHide = new MenuItem("hide");
			itmHide.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					UnVisible();
				}
			});
			MenuItem itmExit = new MenuItem("exit");
			itmExit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					Exit();
				}
			});
			popMenu.add(itmOpen);
			popMenu.add(itmHide);
			popMenu.add(itmExit);
			
			//创建托盘图标
			TrayIcon trayIcon = new TrayIcon(image,text,popMenu);
			//将托盘图标加到托盘上
			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//内部类中不方便直接调用外部类的实例（this不能指向）
	public void UnVisible() {
		this.setVisible(false);
	}
	public void Show() {
		this.setVisible(true);
	}
	public void Exit() {
		System.exit(0);
	}
	
	
	

}
