package visual;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import mutiThread.MainThread;

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
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.wb.swt.SWTResourceManager;

import com.h2.constant.ConfigToParameters;
import com.h2.constant.Parameters;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class MainFrame {

	protected Shell shell;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			ConfigToParameters c = new ConfigToParameters();
			MainFrame window = new MainFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents(display);
		//set to the center of the screen.
//		this.setTray(display);
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x/2, Display.getCurrent().getClientArea().height / 2 - shell.getSize().y/2);
		
		shell.open();
		shell.layout();
//		if(MainThread.exitVariable_visual == true) {
//			MainThread.exitVariable_visual = false;
//			MainThread main = new MainThread();
//			main.start();
//		}
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			
		}
		
		display.dispose();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Display display) {
		shell = new Shell(display, SWT.TITLE);
		String j = System.getProperty("user.dir");//get the procedure absolute path.
		shell.setImage(SWTResourceManager.getImage(j+"/WebRoot/image/Coal Mining.png"));
		shell.setSize(1691, 666);
		shell.setText("复杂事件监测系统（煤矿版）");
		shell.setLayout(null);
		
		//screen out the back end content.
		text = new Text(shell, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		text.setBounds(10, 62, 1649, 495);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		text.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		
		//System.out.flow reset to the front text widget. Reset the direct to the text in SWT.
		MyPrintStream mps = new MyPrintStream(System.out, text);
//		System.setOut(mps);
//		System.setErr(mps);
		
		//stop button.
		Button button_2 = new Button(shell, SWT.NONE);
		button_2.setBounds(586, 10, 91, 34);
		button_2.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent e) {
				MainThread.exitVariable_visual = true;
			}
		});
		button_2.setText("停止");
		
		//start button.
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.setBounds(451, 10, 91, 34);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(MainThread.exitVariable_visual == true) {
					MainThread main = new MainThread();
					main.start();
//					System.out.println("database's name"+Parameters.DatabaseName5);
				}
			}
		});
		button_1.setText("启动");
		
		//Preferences button.
		Button button_3 = new Button(shell, SWT.NONE);
		button_3.setBounds(724, 10, 91, 34);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Preferences window = new Preferences();
					window.open();
//					Display.getCurrent().dispose();
				} catch (Exception event) {
					event.printStackTrace();
				}
			}
		});
		button_3.setText("设置");
		
		Button clear_button = new Button(shell, SWT.NONE);
		clear_button.setBounds(10, 10, 91, 34);
		clear_button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
			}
		});
		clear_button.setText("清空文本");
		
		Button button_4 = new Button(shell, SWT.NONE);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
//					disasterMonitor disaster = new disasterMonitor();
//					disaster.open();
					disasterCheck disaster = new disasterCheck();
				} catch (Exception event) {
					event.printStackTrace();
				}
			}
		});
		button_4.setText("实时动力灾害监测");
		button_4.setBounds(852, 10, 156, 34);
		
		Button button_5 = new Button(shell, SWT.NONE);
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					historyQuery history = new historyQuery();
					history.open();
				}catch(Exception event){
					event.printStackTrace();
				}
			}
		});
		button_5.setText("历史灾害查询与显示");
		button_5.setBounds(1049, 10, 156, 34);
		
		Button button_6 = new Button(shell, SWT.NONE);
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		button_6.setText("退出");
		button_6.setBounds(10, 578, 91, 34);
	}
	
	//添加托盘显示：1.先判断当前平台是否支持托盘显示
	public void setTray(Display display) {
		
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
		if(!shell.isDisposed())
			System.out.println(" jingchang ");
			shell.setVisible(false);
	}
	public void Show() {
		if(!shell.isDisposed())
			shell.setVisible(true);
	}
	public void Exit() {
		System.exit(0);
	}
	
	//reset function.
	class MyPrintStream extends PrintStream {
		
		private Text text;
		public MyPrintStream(OutputStream out, Text text) {
			super(out);
			this.text = text;
		}
		// 重写父类write方法,这个方法是所有打印方法里面都要调用的方法
		public void write(byte[] buf, int off, int len) {
			final String message = new String(buf, off, len);
			
			// SWT非界面线程访问组件的方式
			Display.getDefault().syncExec(new Thread() {
				public void run() {
					// 把信息添加到组件中
					if (text != null && !text.isDisposed()) {
						text.append(message);
					}
				}
			});
		}
	}
	
}
