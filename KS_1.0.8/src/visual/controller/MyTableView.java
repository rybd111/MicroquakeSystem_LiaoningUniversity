package visual.controller;

import com.db.DbExcute;
import com.h2.constant.Parameters;
import visual.model.TableData;
import visual.util.Tools_DataCommunication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableView;

public class MyTableView {
	private TableView<TableData> tableView = null;

	public MyTableView(TableView<TableData> tv) {
		this.tableView = tv;
	}

	public void setTableViewData() {
		DbExcute aDbExcute = new DbExcute();
		aDbExcute.Query3("select * from " + Parameters.DatabaseName5);
//		Tools_DataCommunication.getCommunication().dataList.clear();
//		/**如果在这里进行数据库读入内存操作的话，会报错：java.sql.SQLException: Operation not allowed after ResultSet closed*/
//		try {
//			while(resultSet.next()) {
//				Tools_DataCommunication.getCommunication().dataList.add(new TableData(resultSet.getString("id"),
//						resultSet.getString("quackTime"), resultSet.getString("panfu"),
//						resultSet.getString("xData") + "," + resultSet.getString("yData") + ","
//								+ resultSet.getString("zData"),
//						resultSet.getString("nengliang"), resultSet.getString("quackGrade"),
//						new QuackResults(resultSet.getDouble("xData"), resultSet.getDouble("yData"),
//								resultSet.getDouble("zData"), resultSet.getTimestamp("quackTime"),
//								resultSet.getDouble("quackGrade"), resultSet.getDouble("Parrival"),
//								resultSet.getString("panfu"), resultSet.getDouble("duringGrade"),
//								resultSet.getDouble("nengliang"), resultSet.getString("wenjianming"))));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		/** 添加选择记录监听事件 */
		if (null == tableView)
			return;
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableData>() {

			@Override
			public void changed(ObservableValue<? extends TableData> observable, TableData oldValue,
					TableData newValue) {

				System.out.println("开始绘制第" + newValue.getEventIndex() + "事件波形图");
				/** 显示波形图 */
				Tools_DataCommunication.getCommunication().getmChart().setLineChart(
						Tools_DataCommunication.getCommunication().readTime,
						newValue.getQuackResults().getFilename_S());
				/** 显示CAD定位 */
				Tools_DataCommunication.getCommunication().getmCAD().exeJS(newValue.getQuackResults().getxData(),
						newValue.getQuackResults().getyData(), Tools_DataCommunication.getCommunication().circleRadius);
			}
		});
	}

	/**
	 * 设置TableView默认选中
	 * 
	 * @param data
	 */
	private void setSelcetItem(TableData data) {
		// 触发选择监听事件
		tableView.getSelectionModel().select(data);
	}

	/***
	 * 自动显示更新数据
	 * 
	 * @param data
	 */
	public void autoShowData(TableData data) {
		System.out.println("有新数据产生，并已自动显示，请注意查收！");
		// 显示在TableView上，并默认选中
		setSelcetItem(data);

	}
}
