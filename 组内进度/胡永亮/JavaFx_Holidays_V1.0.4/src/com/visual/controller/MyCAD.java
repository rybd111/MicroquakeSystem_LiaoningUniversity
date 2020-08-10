package com.visual.controller;

import java.io.IOException;

import com.visual.view.UIController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class MyCAD {
	public MyCAD(BorderPane root) {
		BorderPane pane;
		try {
			pane = FXMLLoader.load(UIController.class.getResource("ShowCAD.fxml"));
			root.setCenter(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
