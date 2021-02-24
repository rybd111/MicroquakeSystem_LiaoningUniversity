/**
 * 
 */
package visual;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import mutiThread.MainThread;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.*;
import javax.swing.*;
/**
 * @author Hanlin Zhang
 */
public class Start {

	private JFrame frmComplexEventcoalMine;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start window = new Start();
					window.frmComplexEventcoalMine.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Start() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmComplexEventcoalMine = new JFrame();
		frmComplexEventcoalMine.setTitle("Complex Event(Coal Mine)");
		frmComplexEventcoalMine.setBounds(100, 100, 841, 605);
		frmComplexEventcoalMine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmComplexEventcoalMine.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MainThread.exitVariable_visual = false;
				MainThread main = new MainThread();
				main.start();
			}
		});
		btnNewButton.setBounds(61, 49, 113, 27);
		frmComplexEventcoalMine.getContentPane().add(btnNewButton);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainThread.exitVariable_visual = true;
			}
		});
		btnStop.setBounds(225, 49, 113, 27);
		frmComplexEventcoalMine.getContentPane().add(btnStop);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(26, 93, 744, 454);
		frmComplexEventcoalMine.getContentPane().add(textPane);
		
		MyPrintStream mps = new MyPrintStream(System.out, textPane); 
		System.setOut(mps); 
		System.setErr(mps);
	}
	
}



class MyPrintStream extends PrintStream { 
private JTextPane text;
private StringBuffer sb = new StringBuffer();
  
   public MyPrintStream(OutputStream out, JTextPane text) { 
       super(out); 
        this.text = text; 
   }
 
  /**
     * 在这里重截,所有的打印方法都要调用的方法
     */ 
    public void write(byte[] buf, int off, int len) { 
         final String message = new String(buf, off, len);  
         SwingUtilities.invokeLater(new Runnable(){
         public void run(){
          sb.append(message+"\n");
          text.setText(sb.toString());
         }
      });
   }
}