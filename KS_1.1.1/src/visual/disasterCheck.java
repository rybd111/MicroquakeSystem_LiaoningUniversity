package visual;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import com.h2.constant.Parameters;

import mutiThread.MainThread;

public class disasterCheck {

	private JFrame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					disasterCheck window = new disasterCheck();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public disasterCheck() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("实时数据监测");
		frame.setBounds(0, 0, 740, 1000);
		//设置默认退出操作
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//数据
		chartFrame []f = new chartFrame[Parameters.SensorNum];
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGap(0, 693, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGap(0, 597, Short.MAX_VALUE)
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
		//==========画数据=============
		int num=Parameters.SensorNum;
		for(int i=0;i<num;i++) {
			f[i] = new chartFrame(frame,i);
			frame.getContentPane().add(f[i].chartCon(0,(frame.getSize().height-20)/num*i,num,
					MainThread.fileStr[i].substring(MainThread.fileStr[i].length()-2, MainThread.fileStr[i].length()-1),
					"swing"));
			f[i].start();
//		System.out.println("1111111111111:"+MainThread.fileStr[i].substring(MainThread.fileStr[i].length()-2, MainThread.fileStr[i].length()-1));
		}
		//监听窗口关闭
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowevent) {
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//close this window and release the hard ware source.
				for(int i=0;i<num;i++) {
					f[i].exitVariable=true;
				}
			}
		});
	}
	
}
