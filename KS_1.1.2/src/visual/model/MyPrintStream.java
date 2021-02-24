package visual.model;

import java.io.OutputStream;
import java.io.PrintStream;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class MyPrintStream extends PrintStream {

	private TextArea text;

	public MyPrintStream(OutputStream out, TextArea text) {
		super(out);
		this.text = text;
	}

	// 重写父类write方法,这个方法是所有打印方法里面都要调用的方法
	public void write(byte[] buf, int off, int len) {
		final String message = new String(buf, off, len);
		//Platform.runLater 这个方法会将传入的函数放入一个队列，用于更新ui，最开始我没有用这个方法结果导致控制台输出时界面时常卡死。
		Platform.runLater(()->text.appendText(message));
	}
}