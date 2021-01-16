package visual;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

/**
 * 
 * @author Hanlin Zhang
 */
public class historyQuery {

	protected Shell shlHistory;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			historyQuery window = new historyQuery();
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
		createContents();
		shlHistory.setLocation(Display.getCurrent().getClientArea().width / 2 - shlHistory.getShell().getSize().x/2, Display.getCurrent()
                .getClientArea().height / 2 - shlHistory.getSize().y/2);
		shlHistory.setLayout(null);
		
		table = new Table(shlHistory, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(0, 482, 1049, 273);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		
		Composite composite = new Composite(shlHistory, SWT.NONE);
		composite.setBounds(522, 0, 527, 476);
		int num=5;
		chartFrame []f = new chartFrame[5];
		
		for(int i=0;i<num;i++) {
			f[i] = new chartFrame(shlHistory);
			f[i].chartConSWT(0,shlHistory.getSize().y/num*i,num);
//			f[i].start();
		}
		
		shlHistory.open();
		shlHistory.layout();
		while (!shlHistory.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlHistory = new Shell();
		shlHistory.setText("历史灾害查询与修正");
		shlHistory.setSize(1067, 800);

	}
}
