package visual.controller;

import java.io.File;
import java.util.ArrayList;
import com.db.DbExcute;
import com.h2.constant.Parameters;
import visual.model.TableData;
import visual.util.Tools_DataCommunication;
import visual.util.Tools_DataCommunication.tableViewType;
import visual.view.RepositionPanelController_pingdingshan;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MyTableView {
	private TableView<TableData> tableView = null;

	private ArrayList<String> list_db = new ArrayList<String>();
	private ComboBox<String> mComboBox;

	public ComboBox<String> getmComboBox() {
		return mComboBox;
	}

	private Label mEventLabel;

	public MyTableView(TableView<TableData> tv, ComboBox<String> mComboBox, Label mEventLabel) {
		this.tableView = tv;
		this.mComboBox = mComboBox;
		this.mEventLabel = mEventLabel;
		db_addcomboBoxdata();
		initComboBox(this.mComboBox, list_db);
		/** 添加选择记录监听事件 */
		if (null == tableView)
			return;
		/** 点击事件是可以重复注册的，为了避免重复注册导致多次触发点击事件。所以在初始化时进行注册以保证只注册一次点击事件 */
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableData>() {
			@Override
			public void changed(ObservableValue<? extends TableData> observable, TableData oldValue,
					TableData newValue) {
				/**
				 * 在进行数据库转换时，ComboBox会展开到TableView的范围内，而此时TableView已添加监听点击事件，在这里就算是点击了ComboBox组件
				 * 也会触发TableView的监听事件，但是监听的newValue却为NULL，所有这里判断newValue是否为空
				 */
				if (null == newValue)
					return;
				System.out.println("开始绘制第" + newValue.getEventIndex() + "事件波形图");
				System.out.println("Filename_S" + newValue.getQuackResults().getFilename_S());
				/** 显示CAD定位 */
				Tools_DataCommunication.getCommunication().getmCAD().exeJS(newValue.getQuackResults().getxData(),
						newValue.getQuackResults().getyData(), Tools_DataCommunication.getCommunication().circleRadius,
						newValue.getQuackResults().getKind());
				if (newValue.getQuackResults().getFilename_S() != null) {
					File f = new File(newValue.getQuackResults().getFilename_S());
					if (!f.exists()) {
						Tools_DataCommunication.getCommunication().getReport().isReport = false;
						Tools_DataCommunication.getCommunication().getmChart().clearSeries();
						Tools_DataCommunication.getCommunication().getmChart().clearP();
						Tools_DataCommunication.getCommunication().getmChart().clearName();
						return;
					}
					/** 显示波形图 */
					Tools_DataCommunication.getCommunication().csvPath = newValue.getQuackResults().getFilename_S();
					Tools_DataCommunication.getCommunication().getmChart().setLineChart(
							Tools_DataCommunication.getCommunication().readTime,
							Tools_DataCommunication.getCommunication().csvPath);

					/** 允许生成报表 */
					Tools_DataCommunication.getCommunication().getReport().isReport = true;
					Tools_DataCommunication.getCommunication().getReport().setData(newValue.getQuackResults());
					/** 激活重定位面板的定位台站 */
					Tools_DataCommunication.getCommunication().reLocateData = newValue;
					switch (Parameters.diskNameNum) {
					case 0:// 红阳三矿
						if (Tools_DataCommunication.getCommunication().controller_hongyang != null)
							Tools_DataCommunication.getCommunication().controller_hongyang.setAble();
						break;
					case 1:// 大同
						if (Tools_DataCommunication.getCommunication().controller_datong != null)
							Tools_DataCommunication.getCommunication().controller_datong.setAble();
						break;
					case 2:// 平顶山
						if (Tools_DataCommunication.getCommunication().controller_pingdingshan != null)
							Tools_DataCommunication.getCommunication().controller_pingdingshan.setAble();
						break;
					case 3:// 马道头
						if (Tools_DataCommunication.getCommunication().controller_madaotou != null)
							Tools_DataCommunication.getCommunication().controller_madaotou.setAble();
						break;
					default:
						System.out.println("匹配出现问题--------MyTableView");
						break;
					}
				}
				else {
					Tools_DataCommunication.getCommunication().getReport().isReport = false;
					Tools_DataCommunication.getCommunication().getmChart().clearSeries();
					Tools_DataCommunication.getCommunication().getmChart().clearP();
					Tools_DataCommunication.getCommunication().getmChart().clearName();
//					return;
				}
			}
		});
		InitTableViewData();
	}

	/** 将TableColumn与TableData每一个属性连接起来 */
	private void InitTableViewData() {
		this.tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("eventIndex"));
		this.tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("eventTime"));
		this.tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("kind"));
		this.tableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("eventLoca"));
		this.tableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("eventPos"));
		this.tableView.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("energy"));
		this.tableView.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("grade"));
		this.tableView.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("parrival"));
		this.tableView.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("tensor"));
		this.tableView.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("bvalue"));
	}

	/***
	 * 从数据库读取数据，并填充到tableview上
	 * 
	 * @param sql
	 */
	public void setTableViewData(String sql, tableViewType type) {
		// 清空原来所有数据，重新读取
		if (Tools_DataCommunication.getCommunication().list != null)
			Tools_DataCommunication.getCommunication().list.clear();
		DbExcute aDbExcute = new DbExcute();
		switch (type) {
		case Normal:// 正常事件读取操作
			this.tableView.setItems(Tools_DataCommunication.getCommunication().dataList);
			Tools_DataCommunication.getCommunication().dataList.clear();
			aDbExcute.Query_3_5(sql);
			/** 更新label */
			if (Tools_DataCommunication.getCommunication().dataList.size() == 0)
				return;
			TableData data = (TableData) Tools_DataCommunication.getCommunication().dataList
					.get(Tools_DataCommunication.getCommunication().dataList.size() - 1);
			Tools_DataCommunication.getCommunication().getmCAD().updataNewLabel(data.getEventTime());
			break;
		case Query:// 查询事件读取操作
			this.tableView.setItems(Tools_DataCommunication.getCommunication().dataList_QueryPanel);
			Tools_DataCommunication.getCommunication().dataList_QueryPanel.clear();
			aDbExcute.Query_panel(sql);
			break;
		default:
			break;
		}

		// /**如果在这里进行数据库读入内存操作的话，会报错：java.sql.SQLException: Operation not allowed after
		// ResultSet closed*/
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

	/***
	 * 将合法的数据库表名显示在ComboBox上
	 */
	private void db_addcomboBoxdata() {

		// if (Parameters.DatabaseName3 != null && Parameters.DatabaseName3 != "" &&
		// Parameters.DatabaseName3 != " ")
		// list_db.add(Parameters.DatabaseName3);

		// if (Parameters.DatabaseName3_updated != null &&
		// Parameters.DatabaseName3_updated != ""
		// && Parameters.DatabaseName3_updated != " ")
		// list_db.add(Parameters.DatabaseName3_updated);

		// if (Parameters.DatabaseName4 != null && Parameters.DatabaseName4 != "" &&
		// Parameters.DatabaseName4 != " ")
		// list_db.add(Parameters.DatabaseName4);

		if (Parameters.DatabaseName5 != null && Parameters.DatabaseName5 != "" && Parameters.DatabaseName5 != " ")
			list_db.add(Parameters.DatabaseName5);

		// if (Parameters.DatabaseName5_updated != null &&
		// Parameters.DatabaseName5_updated != ""
		// && Parameters.DatabaseName5_updated != " ")
		// list_db.add(Parameters.DatabaseName5_updated);
	}

	private void initComboBox(ComboBox<String> c, ArrayList<String> data) {
		c.getItems().addAll(data);
		c.getSelectionModel().select(0);
		c.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals(Parameters.DatabaseName5))
					setTableViewData("select * from " + Parameters.DatabaseName5, tableViewType.Normal);
			}
		});
	}
}
