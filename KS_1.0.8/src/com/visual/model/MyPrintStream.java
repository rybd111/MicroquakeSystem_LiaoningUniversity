package com.visual.model;

import java.io.OutputStream;
import java.io.PrintStream;

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
		text.appendText(message);
	}
}