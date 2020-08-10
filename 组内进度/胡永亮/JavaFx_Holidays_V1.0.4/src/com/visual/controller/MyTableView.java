package com.visual.controller;



import com.visual.model.TableData;
import com.visual.util.Tools_DataCommunication;

import javafx.collections.ObservableList;

public class MyTableView {
	public ObservableList dataList=Tools_DataCommunication.getCommunication().dataList;
	public void setTableViewData() {
		// TODO Auto-generated method stub
		dataList.add(new TableData("1", "2020-01-02.10:21:29.679", "T1,T2,T3,T4,T5", "202165.737954.154", "1.50E+103",
				"1.22"));
		dataList.add(new TableData("2", "2020-01-02.10:21:29.579", "T1,T2,T3,T4,T6", "202165.737954.155", "1.70E+104",
				"2.22"));
		dataList.add(new TableData("3", "2020-01-02.10:21:29.479", "T1,T2,T3,T4,T7", "202165.737954.156", "1.30E+105",
				"1.08"));
		dataList.add(new TableData("4", "2020-01-02.10:21:29.379", "T1,T2,T3,T4,T8", "202165.737954.157", "1.78E+103",
				"0.06"));
		dataList.add(new TableData("5", "2020-01-02.10:21:29.279", "T1,T2,T3,T4,T9", "202165.737954.158", "2.42E+102",
				"0.53"));
		dataList.add(new TableData("6", "2020-01-02.10:21:29.179", "T1,T2,T3,T4,T10", "202165.737954.159", "3.07E+104",
				"-0.04"));
		dataList.add(new TableData("7", "2020-01-02.10:21:29.79", "T1,T2,T3,T4,T11", "202165.737954.160", "4.35E+105",
				"-0.394"));
		dataList.add(new TableData("8", "2020-01-02.10:21:29.21", "T1,T2,T3,T4,T12", "202165.737954.161", "4.99E+102",
				"-0..748"));
		dataList.add(new TableData("9", "2020-01-02.10:21:29.121", "T1,T2,T3,T4,T13", "202165.737954.162", "3.71E+104",
				"-1.102"));
		dataList.add(new TableData("10", "2020-01-02.10:21:29.221", "T1,T2,T3,T4,T14", "202165.737954.163",
				"5.64E+105", "-1.456"));
		dataList.add(new TableData("11", "2020-01-02.10:21:29.222", "T1,T2,T3,T4,T15", "202165.737954.164",
				"5.64E+105", "-1.456"));
	}
}
