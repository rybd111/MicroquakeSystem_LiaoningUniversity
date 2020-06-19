package visual;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

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
		frame = new JFrame("实时复杂时空事件监测");
		frame.setBounds(100, 100, 711, 642);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		chartFrame []f = new chartFrame[5];
		
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
		int num=5;
		for(int i=0;i<num;i++) {
			f[i] = new chartFrame(frame);
			frame.getContentPane().add(f[i].chartCon(0,frame.getSize().height/num*i,num));
			f[i].start();
		}
		
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
