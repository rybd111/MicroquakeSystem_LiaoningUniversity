package visual;

import org.eclipse.swt.widgets.Display;

public class test_dialog_along {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Preferences window = new Preferences();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
