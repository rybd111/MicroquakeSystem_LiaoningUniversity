package visual;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;

import com.h2.constant.ConfigToParameters;
import com.h2.constant.Parameters;

import visual.model.ParametersToConfig;
import visual.util.Tools_DataCommunication;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class Preferences extends ApplicationWindow {
	private Text t1_12;
	private Text t1_1;
	private Text t1_13;
	private Text t1_14;
	private Text t1_2;
	private Text t1_3;
	private Text t1_4;
	private Text t1_6;
	private Text t1_5;
	private Text t1_17;
	private Text t1_18;
	private Text t1_7;
	private Text t1_11;
	private Text t1_15;
	private Text t1_16;
	private Text t2_2;
	private Text t1_8;
	private Text t1_9;
	private Text t1_10;
	private Text t4_4;
	private Text t4_5;
	private Text t4_3;
	private Text t4_6;
	private Text t4_7;
	private Text t2_31;
	private Text t2_32;
	private Text t3_2;
	private Text t4_1;
	private Text t4_2;
	private Text t3_31;
	private Text t3_32;
	private Text t1_19;

	/**
	 * Create the application window.
	 * 
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public Preferences() throws NumberFormatException, IOException {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		container.setLayout(null);

		Button b1 = new Button(container, SWT.NONE);
		b1.setBounds(5, 501, 283, 30);
		b1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				System.out.println("点击了确认修改按钮");
				sureAlter();
				try {
					ParametersToConfig p = new ParametersToConfig();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// 结束程序
				Tools_DataCommunication.getCommunication().exitThreadAll();
			}

		});
		b1.setText("确认修改");

		container.setTabList(new Control[] {

		});

		Button b2 = new Button(container, SWT.NONE);
		b2.setBounds(294, 501, 303, 30);
		b2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

//				restoreValue(t1_1, t1_2, t1_3, t1_4, t1_5, t1_6, t1_7, t1_8, t1_9, t1_10, t1_11, t1_12, t1_13, t1_14,
//						t1_15, t1_16, t1_17, t1_18, t1_19, t3_2, t3_31, t3_32, t2_2, t2_31, t2_32, t4_1, t4_2, t4_3,
//						t4_4, t4_5, t4_6, t4_7);
				String initPath = System.getProperty("user.dir") + "/resource/InitConfig.ini";
//				System.out.println(initPath);
				try {
					ConfigToParameters c = new ConfigToParameters(initPath);
					ParametersToConfig p = new ParametersToConfig();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// 结束程序
				Tools_DataCommunication.getCommunication().exitThreadAll();
			}
		});
		b2.setText("恢复原值");

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		tabFolder.setBounds(5, 10, 592, 485);

		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("公用参数");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		tabItem.setControl(composite);

		Label l1_1 = new Label(composite, SWT.NONE);
		l1_1.setBounds(10, 13, 152, 20);
		l1_1.setText("距离高电平的时间（s）");

		t1_1 = new Text(composite, SWT.BORDER);
		t1_1.setBounds(168, 10, 104, 26);
		t1_1.setText(String.valueOf(Parameters.distanceToSquareWave));

		Label l1_2 = new Label(composite, SWT.NONE);
		l1_2.setBounds(10, 42, 152, 20);
		l1_2.setText("短长时窗比值");

		t1_2 = new Text(composite, SWT.BORDER);
		t1_2.setBounds(168, 39, 104, 26);
		t1_2.setText(String.valueOf(Parameters.ShortCompareLong));

		t1_3 = new Text(composite, SWT.BORDER);
		t1_3.setBounds(168, 68, 104, 26);
		t1_3.setText(String.valueOf(Parameters.afterRange));

		Label l1_3 = new Label(composite, SWT.NONE);
		l1_3.setBounds(10, 71, 152, 20);
		l1_3.setText("粗略探视点数（个）");

		Label l1_4 = new Label(composite, SWT.NONE);
		l1_4.setBounds(10, 100, 152, 20);
		l1_4.setText("粗略探视阈值");

		t1_4 = new Text(composite, SWT.BORDER);
		t1_4.setBounds(168, 97, 104, 26);
//		t1_4.setText(String.valueOf(Parameters.afterRange_Threshold456));

		Label l1_5 = new Label(composite, SWT.NONE);
		l1_5.setBounds(10, 129, 152, 20);
		l1_5.setText("精细探视点数");

		t1_5 = new Text(composite, SWT.BORDER);
		t1_5.setBounds(168, 126, 104, 26);
		t1_5.setText(String.valueOf(Parameters.refineRange));

		Label l1_6 = new Label(composite, SWT.NONE);
		l1_6.setBounds(10, 159, 152, 20);
		l1_6.setText("精细探视阈值");

		t1_6 = new Text(composite, SWT.BORDER);
		t1_6.setBounds(168, 156, 104, 26);
		t1_6.setText(String.valueOf(Parameters.refineRange_ThresholdMin));

		Label l1_7 = new Label(composite, SWT.NONE);
		l1_7.setBounds(10, 188, 120, 20);
		l1_7.setText("传感器数量（个）");

		t1_7 = new Text(composite, SWT.BORDER);
		t1_7.setBounds(168, 185, 104, 26);
		t1_7.setText(String.valueOf(Parameters.SensorNum));

		Label l1_8 = new Label(composite, SWT.NONE);
		l1_8.setBounds(10, 217, 120, 20);
		l1_8.setText("单增益系数");

		t1_8 = new Text(composite, SWT.BORDER);
		t1_8.setBounds(168, 214, 104, 26);
		t1_8.setText(String.valueOf(Parameters.plusSingle_coefficient));

		Label l1_9 = new Label(composite, SWT.NONE);
		l1_9.setBounds(10, 246, 123, 20);
		l1_9.setText("双增益系数45通道");

		t1_9 = new Text(composite, SWT.BORDER);
		t1_9.setBounds(168, 243, 104, 26);
		t1_9.setText(String.valueOf(Parameters.plusDouble_coefficient_45));

		Label l1_10 = new Label(composite, SWT.NONE);
		l1_10.setBounds(10, 275, 123, 20);
		l1_10.setText("双增益系数12通道");

		t1_10 = new Text(composite, SWT.BORDER);
		t1_10.setBounds(168, 272, 104, 26);
		t1_10.setText(String.valueOf(Parameters.plusDouble_coefficient_12));

		Label l1_11 = new Label(composite, SWT.NONE);
		l1_11.setBounds(10, 304, 96, 20);
		l1_11.setText("背景噪声方差");

		t1_11 = new Text(composite, SWT.BORDER);
		t1_11.setBounds(10, 330, 566, 104);
		t1_11.setText(double_arrayToSingle(Parameters.backGround));

		Label l1_18 = new Label(composite, SWT.NONE);
		l1_18.setBounds(297, 188, 120, 20);
		l1_18.setText("P波速（KM/h）");

		t1_18 = new Text(composite, SWT.BORDER);
		t1_18.setBounds(424, 185, 152, 26);
		t1_18.setText(String.valueOf(Parameters.C));

		t1_17 = new Text(composite, SWT.BORDER);
		t1_17.setBounds(472, 156, 104, 26);
		t1_17.setText(String.valueOf(Parameters.INTERVAL));

		Label l1_17 = new Label(composite, SWT.NONE);
		l1_17.setBounds(296, 159, 170, 20);
		l1_17.setText("滑动窗口跳数");

		Label l1_16 = new Label(composite, SWT.NONE);
		l1_16.setBounds(297, 129, 170, 20);
		l1_16.setText("激发后截取波形时间（s）");

		t1_16 = new Text(composite, SWT.BORDER);
		t1_16.setBounds(472, 126, 104, 26);
		t1_16.setText(String.valueOf(Parameters.endTime));

		t1_15 = new Text(composite, SWT.BORDER);
		t1_15.setBounds(472, 97, 104, 26);
		t1_15.setText(String.valueOf(Parameters.startTime));

		Label l1_15 = new Label(composite, SWT.NONE);
		l1_15.setBounds(297, 100, 170, 20);
		l1_15.setText("激发前截取波形时间（s）");

		Label l1_14 = new Label(composite, SWT.NONE);
		l1_14.setBounds(297, 71, 170, 20);
		l1_14.setText("短时窗采样时长（毫秒）");

		t1_14 = new Text(composite, SWT.BORDER);
		t1_14.setBounds(472, 68, 104, 26);
		t1_14.setText(String.valueOf(Parameters.SHORTTIMEWINDOW));

		t1_13 = new Text(composite, SWT.BORDER);
		t1_13.setBounds(472, 39, 104, 26);
		t1_13.setText(String.valueOf(Parameters.LONGTIMEWINDOW));

		Label l1_13 = new Label(composite, SWT.NONE);
		l1_13.setBounds(297, 42, 170, 20);
		l1_13.setText("长时窗采样时长（毫秒）");

		Label l1_12 = new Label(composite, SWT.NONE);
		l1_12.setBounds(297, 13, 54, 20);
		l1_12.setText("频率");

		t1_12 = new Text(composite, SWT.BORDER);
		t1_12.setBounds(472, 10, 104, 26);
		t1_12.setText(String.valueOf(Parameters.FREQUENCY));

		Label l1_19 = new Label(composite, SWT.NONE);
		l1_19.setText("S波速（KM/h）");
		l1_19.setBounds(297, 217, 120, 20);

		t1_19 = new Text(composite, SWT.BORDER);
		t1_19.setText(String.valueOf(Parameters.S));
		t1_19.setBounds(424, 214, 152, 26);

		TabItem tabItem2 = new TabItem(tabFolder, SWT.NONE);
		tabItem2.setText("在线参数");

		Composite composite1 = new Composite(tabFolder, SWT.NONE);
		composite1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		tabItem2.setControl(composite1);

		Label l2_1 = new Label(composite1, SWT.NONE);
		l2_1.setText("盘符须按照原格式进行修改，盘符号必须大于等于盘符个数");
		l2_1.setBounds(10, 10, 497, 20);

		Label l2_2 = new Label(composite1, SWT.NONE);
		l2_2.setText("盘符名");
		l2_2.setBounds(10, 36, 54, 20);

		Label l2_3 = new Label(composite1, SWT.NONE);
		l2_3.setText("在线坐标");
		l2_3.setBounds(10, 68, 60, 20);


		TabItem tabItem3 = new TabItem(tabFolder, SWT.NONE);
		tabItem3.setText("离线参数");

		Composite composite3 = new Composite(tabFolder, SWT.NONE);
		tabItem3.setControl(composite3);
		composite3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		Label l3_1 = new Label(composite3, SWT.NONE);
		l3_1.setText("盘符须按照原格式进行修改，盘符号必须大于等于盘符个数");
		l3_1.setBounds(10, 10, 497, 20);

		Label l3_2 = new Label(composite3, SWT.NONE);
		l3_2.setText("盘符名");
		l3_2.setBounds(10, 65, 54, 20);

		t3_2 = new Text(composite3, SWT.BORDER);
//		t3_2.setText(string_arrayToSingle_non(Parameters.diskName_offline));
		t3_2.setBounds(70, 62, 506, 26);

		Button r3_1 = new Button(composite3, SWT.CHECK);
		r3_1.setBounds(10, 36, 93, 20);
		r3_1.setText("离线模式");

		Combo c3_1 = new Combo(composite3, SWT.NONE);
		c3_1.setBounds(10, 123, 73, 28);

		String[] items1 = new String[4];
		items1[0] = "平顶山";
		items1[1] = "双鸭山";
		items1[2] = "红阳三矿";
		items1[3] = "大同塔山";
		c3_1.setItems(items1);
		c3_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (c3_1.getText().equals("平顶山") == true)
					t3_32.setText(double_twoDimarrayToLine(Parameters.SENSORINFO_pingdingshan));
				if (c3_1.getText().equals("大同塔山") == true)
					t3_32.setText(double_twoDimarrayToLine(Parameters.SENSORINFO_datong));
				if (c3_1.getText().equals("红阳三矿") == true)
					t3_32.setText(double_twoDimarrayToLine(Parameters.SENSORINFO_hongyang));
				if (c3_1.getText().equals("双鸭山") == true)
					t3_32.setText(double_twoDimarrayToLine(Parameters.SENSORINFO_shuangyashan));
			}
		});
		// set the content displayed in combo.
//		c3_1.setText(Parameters.region);

		Label l3_3 = new Label(composite3, SWT.NONE);
		l3_3.setText("离线坐标");
		l3_3.setBounds(10, 97, 60, 20);

		t3_31 = new Text(composite3, SWT.BORDER | SWT.MULTI);
//		t3_31.setText(string_arrayToSingle(Parameters.diskName_offline));
		t3_31.setBounds(89, 94, 33, 196);

		t3_32 = new Text(composite3, SWT.BORDER | SWT.MULTI);
		t3_32.setText(double_twoDimarrayToLine(Parameters.SENSORINFO_hongyang));
		t3_32.setBounds(128, 94, 448, 196);

		TabItem tabItem_1 = new TabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("开关与路径");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		tabItem_1.setControl(composite_1);

		Button r4_1 = new Button(composite_1, SWT.CHECK);
		r4_1.setText("存储到数据库");
		r4_1.setBounds(10, 10, 121, 20);

		Button r4_2 = new Button(composite_1, SWT.CHECK);
		r4_2.setText("存储到CSV文件");
		r4_2.setBounds(10, 36, 144, 20);

		Button r4_3 = new Button(composite_1, SWT.CHECK);
		r4_3.setText("精细判断");
		r4_3.setBounds(10, 62, 144, 20);

		Button r4_4 = new Button(composite_1, SWT.CHECK);
		r4_4.setText("是否6个通道");
		r4_4.setBounds(10, 88, 144, 20);

		Button r4_5 = new Button(composite_1, SWT.CHECK);
		r4_5.setText("读次新文件");
		r4_5.setBounds(10, 114, 107, 20);

		Button r4_6 = new Button(composite_1, SWT.CHECK);
		r4_6.setText("调试模式");
		r4_6.setBounds(10, 140, 107, 20);

		Button r4_8 = new Button(composite_1, SWT.CHECK);
		r4_8.setText("两两传感器时间间隔");
		r4_8.setBounds(123, 140, 159, 20);

		Button r4_7 = new Button(composite_1, SWT.CHECK);
		r4_7.setText("减去固定震级");
		r4_7.setBounds(123, 114, 114, 20);

		t4_1 = new Text(composite_1, SWT.BORDER);
		t4_1.setText(String.valueOf(Parameters.MinusAFixedOnMagtitude));
		t4_1.setBounds(243, 111, 37, 26);

		t4_2 = new Text(composite_1, SWT.BORDER);
		t4_2.setText(String.valueOf(Parameters.IntervalToOtherSensors));
		t4_2.setBounds(286, 137, 37, 26);

		Label l4_6 = new Label(composite_1, SWT.NONE);
		l4_6.setText("S");
		l4_6.setBounds(329, 140, 11, 20);

		Label l4_1 = new Label(composite_1, SWT.NONE);
		l4_1.setBounds(10, 179, 97, 20);
		l4_1.setText("到时存储路径");
		t4_3 = new Text(composite_1, SWT.BORDER);
		t4_3.setBounds(112, 176, 454, 26);
		t4_3.setText(Parameters.AbsolutePath_allMotiTime_record);

		t4_4 = new Text(composite_1, SWT.BORDER);
		t4_4.setBounds(112, 205, 454, 26);
		t4_4.setText(Parameters.AbsolutePath_CSV3);

		Label l4_2 = new Label(composite_1, SWT.NONE);
		l4_2.setBounds(10, 208, 97, 20);
		l4_2.setText("CSV存储路径3");

		Label l4_3 = new Label(composite_1, SWT.NONE);
		l4_3.setBounds(10, 237, 97, 20);
		l4_3.setText("CSV存储路径5");

		t4_5 = new Text(composite_1, SWT.BORDER);
		t4_5.setBounds(112, 234, 454, 26);
		t4_5.setText(Parameters.AbsolutePath_CSV5);

		t4_6 = new Text(composite_1, SWT.BORDER);
		t4_6.setBounds(112, 263, 454, 26);
//		t4_6.setText(Parameters.DatabaseName3);

		Label l4_4 = new Label(composite_1, SWT.NONE);
		l4_4.setBounds(10, 266, 97, 20);
		l4_4.setText("3台数据库名");

		Label l4_5 = new Label(composite_1, SWT.NONE);
		l4_5.setBounds(10, 295, 97, 20);
		l4_5.setText("5台数据库名");

		t4_7 = new Text(composite_1, SWT.BORDER);
		t4_7.setBounds(112, 292, 454, 26);
		t4_7.setText(Parameters.DatabaseName5);

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Preferences window = new Preferences();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("系统参数设置");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
//		System.out.println(newShell.getSize().x+" "+ newShell.getSize().y);
//		System.out.println((screenWidth - newShell.getSize().x)/2+" "+ (screenHeight - newShell.getSize().y)/2);
		newShell.setLocation((screenWidth - newShell.getSize().x), (screenHeight - newShell.getSize().y));
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(621, 610);
	}

	public static String double_twoDimarrayToLine(double[][] s) {
		String temp = "";

		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[0].length; j++) {
				temp += s[i][j] + "  ";
			}
			temp += " \n";
		}
		return temp;
	}

	/**
	 * 
	 * @author Sunny
	 * @param s
	 * @return
	 */
	public static double[][] string_ToDoubleArray(String s) {
		String[] temp1 = s.split("\n");
		double[][] doubtemp = { { 0.0, 0.0, 0.0 }, // T
				{ 0.0, 0.0, 0.0 }, // U
				{ 0.0, 0.0, 0.0 }, // W
				{ 0.0, 0.0, 0.0 }, // X
				{ 0.0, 0.0, 0.0 }, // Z
				{ 0.0, 0.0, 0.0 }, // Y
				{ 0.0, 0.0, 0.0 }, // V
				{ 0.0, 0.0, 0.0 }, // S
				{ 0.0, 0.0, 0.0 }// R
		};
		for (int i = 0; i < temp1.length; i++) {
			String[] temp2 = temp1[i].split("  ");
			for (int j = 0; j < temp2.length; j++) {
				System.out.print(temp2[i]);
			}
			System.out.println("\n" + temp2.length);
		}
		return null;
	}

	public static String double_twoDimarrayToLine_non(double[][] s) {
		String temp = "";

		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[0].length; j++) {
				temp += s[i][j] + "  ";
			}
		}
		return temp;
	}

	public static String double_arrayToSingle(double[] s) {
		String temp = "";

		for (int i = 0; i < s.length; i++) {
			temp += s[i] + " ";
			temp += " \n";
		}

		return temp;
	}

	public static String double_arrayToSingle_non(double[] s) {
		String temp = "";

		for (int i = 0; i < s.length; i++) {
			temp += s[i] + " ";
		}

		return temp;
	}

	public static String int_arrayToSingle(int[] s) {
		String temp = "";

		for (int i = 0; i < s.length; i++) {
			temp += s[i] + " ";
			temp += " \n";
		}

		return temp;
	}

	public static String int_arrayToSingle_non(int[] s) {
		String temp = "";

		for (int i = 0; i < s.length; i++) {
			temp += s[i] + " ";
		}

		return temp;
	}

	public static String string_arrayToSingle(String[] s) {
		String temp = "";
		for (int i = 0; i < s.length; i++) {
			temp += s[i] + " ";
			temp += " \n";
		}
		return temp;
	}

	/**
	 * 
	 * @author Sunny
	 * @param s
	 * @return
	 */
	public static String[] string_ToStringArray(String s) {
		String[] temp = s.split("\n");
		for (int i = 0; i < temp.length; i++)
			temp[i] = temp[i].substring(0, 4);
		return temp;
	}

	public static String string_arrayToSingle_non(String[] s) {
		String temp = "";

		for (int i = 0; i < s.length; i++) {
			temp += s[i] + " ";
		}

		return temp;
	}

	/**
	 * restore all parameters in Parameters.java at restore button.
	 * 
	 * @param t1_1
	 * @param t1_2
	 * @param t1_3
	 * @param t1_4
	 * @param t1_5
	 * @param t1_6
	 * @param t1_7
	 * @param t1_8
	 * @param t1_9
	 * @param t1_10
	 * @param t1_11
	 * @param t1_12
	 * @param t1_13
	 * @param t1_14
	 * @param t1_15
	 * @param t1_16
	 * @param t1_17
	 * @param t1_18
	 * @param t1_19
	 * @param t2_2
	 * @param t2_31
	 * @param t2_32
	 * @param t3_2
	 * @param t3_31
	 * @param t3_32
	 * @param t4_1
	 * @param t4_2
	 * @param t4_3
	 * @param t4_4
	 * @param t4_5
	 * @param t4_6
	 * @param t4_7  19+3+3+7=32
	 */
	public static void restoreValue(Text t1_1, Text t1_2, Text t1_3, Text t1_4, Text t1_5, Text t1_6, Text t1_7,
			Text t1_8, Text t1_9, Text t1_10, Text t1_11, Text t1_12, Text t1_13, Text t1_14, Text t1_15, Text t1_16,
			Text t1_17, Text t1_18, Text t1_19, Text t2_2, Text t2_31, Text t2_32, Text t3_2, Text t3_31, Text t3_32,
			Text t4_1, Text t4_2, Text t4_3, Text t4_4, Text t4_5, Text t4_6, Text t4_7) {
		t1_1.setText(String.valueOf(Parameters.distanceToSquareWave));
		t1_2.setText(String.valueOf(Parameters.ShortCompareLong));
		t1_3.setText(String.valueOf(Parameters.afterRange));
//		t1_4.setText(String.valueOf(Parameters.afterRange_Threshold456));
		t1_5.setText(String.valueOf(Parameters.refineRange));
		t1_6.setText(String.valueOf(Parameters.refineRange_ThresholdMin));
		t1_7.setText(String.valueOf(Parameters.SensorNum));
		t1_8.setText(String.valueOf(Parameters.plusSingle_coefficient));
		t1_9.setText(String.valueOf(Parameters.plusDouble_coefficient_45));
		t1_10.setText(String.valueOf(Parameters.plusDouble_coefficient_12));
		t1_11.setText(double_arrayToSingle(Parameters.backGround));
		t1_12.setText(String.valueOf(Parameters.FREQUENCY));
		t1_13.setText(String.valueOf(Parameters.LONGTIMEWINDOW));
		t1_14.setText(String.valueOf(Parameters.SHORTTIMEWINDOW));
		t1_15.setText(String.valueOf(Parameters.startTime));
		t1_16.setText(String.valueOf(Parameters.endTime));
		t1_17.setText(String.valueOf(Parameters.INTERVAL));
		t1_18.setText(String.valueOf(Parameters.C));
		t1_19.setText(String.valueOf(Parameters.S));


		t3_32.setText(double_twoDimarrayToLine(Parameters.SENSORINFO_hongyang));

		t4_1.setText(String.valueOf(Parameters.MinusAFixedOnMagtitude));
		t4_2.setText(String.valueOf(Parameters.IntervalToOtherSensors));
		t4_3.setText(Parameters.AbsolutePath_allMotiTime_record);
		t4_4.setText(Parameters.AbsolutePath_CSV3);
		t4_5.setText(Parameters.AbsolutePath_CSV5);
//		t4_6.setText(Parameters.DatabaseName3);
		t4_7.setText(Parameters.DatabaseName5);

	}

	/***
	 * 所有的TODO:都是被final修饰的
	 * 
	 * @author Sunny
	 */
	private void sureAlter() {
		Parameters.distanceToSquareWave = Double.parseDouble(t1_1.getText());
		Parameters.ShortCompareLong = Double.parseDouble(t1_2.getText());
		Parameters.afterRange = Integer.parseInt(t1_3.getText());
//		Parameters.afterRange_Threshold456 = Double.parseDouble(t1_4.getText());
		Parameters.refineRange = Integer.parseInt(t1_5.getText());
		Parameters.refineRange_ThresholdMin = Double.parseDouble(t1_6.getText());
		Parameters.SensorNum = Integer.parseInt(t1_7.getText());
//TODO:	Parameters.backGround=Double.parseDouble(t1_11.getText());
		Parameters.FREQUENCY = Integer.parseInt(t1_12.getText());
//TODO:	Parameters.LONGTIMEWINDOW = Integer.parseInt(t1_13.getText());
//TODO:	Parameters.SHORTTIMEWINDOW = Integer.parseInt(t1_14.getText());
		Parameters.startTime = Integer.parseInt(t1_15.getText());
		Parameters.endTime = Integer.parseInt(t1_16.getText());
		Parameters.INTERVAL = Integer.parseInt(t1_17.getText());
		Parameters.C = Integer.parseInt(t1_18.getText());
//TODO:	Parameters.S = Double.parseDouble(t1_19.getText());

/** Parameters.diskName = string_ToStringArray(t2_31.getText()); */
//TODO:	Parameters.SENSORINFO = Double.parseDouble(t2_32.getText());

//TODO:	Parameters.diskName_offline = Double.parseDouble(t3_2.getText());
//TODO:	Parameters.SENSORINFO_offline_hongyang = Double.parseDouble(t3_32.getText());

		Parameters.MinusAFixedOnMagtitude = Boolean.parseBoolean(t4_1.getText());
		Parameters.IntervalToOtherSensors = Integer.parseInt(t4_2.getText());
		Parameters.AbsolutePath_allMotiTime_record = t4_3.getText();
		Parameters.AbsolutePath_CSV3 = t4_4.getText();
		Parameters.AbsolutePath_CSV5 = t4_5.getText();
//		Parameters.DatabaseName3 = t4_6.getText();
		Parameters.DatabaseName5 = t4_7.getText();
	}
}
