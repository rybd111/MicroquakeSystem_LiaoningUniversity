package visual.view;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.db.DbExcute;
import com.h2.constant.Parameters;

import DataExchange.QuackResults;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import visual.controller.MyCAD;
import visual.controller.MyTableView;
import visual.model.TableData;
import visual.util.Tools_DataCommunication;
import visual.util.Tools_DataCommunication.tableViewType;

/**
 * 查询面板控制
 * 
 * @author Sunny
 *
 */
public class QueryPanelController {
	private Thread myThread = null;

	public Thread getMyThread() {
		return myThread;
	}

	private ObservableList data;

	private MyTableView mytable = null;
	@FXML
	private ComboBox<String> select_db;// 数据库选择

	@FXML
	private TextField query_grade;// 震级
	@FXML
	private TextField query_energy;// 能量

	@FXML
	private ComboBox<String> destroyType;// 破坏类型
	@FXML
	private ComboBox<String> eventType;// 事件类型

	@FXML
	private DatePicker startDate;// 起始日期
	@FXML
	private DatePicker endDate;// 终止日期
	@FXML
	private Button mExportButton;// 导出按钮

	@FXML
	private void onClickExport() {
		System.out.println("点击了导出按钮");
//    	Date startTime = new Date();
//    	MemoryUsage startMemory = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();

		// 创建工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建工作表
		XSSFSheet sheet = workbook.createSheet("sheet1");
		// Excel表第一行标题
		XSSFRow sheetRowFirst = sheet.createRow(0);
		sheetRowFirst.createCell(0).setCellValue("QuackTime");
		sheetRowFirst.createCell(1).setCellValue("Nengliang");
		sheetRowFirst.createCell(2).setCellValue("QuackGrade");
		sheetRowFirst.createCell(3).setCellValue("xData");
		sheetRowFirst.createCell(4).setCellValue("yData");
		sheetRowFirst.createCell(5).setCellValue("zData");
		sheetRowFirst.createCell(6).setCellValue("Parrival");
//		sheetRowFirst.createCell().setCellValue("DuringGrade");
		sheetRowFirst.createCell(7).setCellValue("Panfu");
//		sheetRowFirst.createCell(9).setCellValue("Filename_S");
		sheetRowFirst.createCell(8).setCellValue("Tensor");
		sheetRowFirst.createCell(9).setCellValue("bvalue");
		sheetRowFirst.createCell(10).setCellValue("Kind");
		// 设置数据
		for (int row = 1; row < data.size()+1; row++) {
			XSSFRow sheetRow = sheet.createRow(row);
			QuackResults results = ((TableData) data.get(row-1)).getQuackResults();
			sheetRow.createCell(0).setCellValue(results.getQuackTime());
			sheetRow.createCell(1).setCellValue(results.getNengliang());
			sheetRow.createCell(2).setCellValue(results.getQuackGrade());
			sheetRow.createCell(3).setCellValue(results.getxData());
			sheetRow.createCell(4).setCellValue(results.getyData());
			sheetRow.createCell(5).setCellValue(results.getzData());
			sheetRow.createCell(6).setCellValue(results.getParrival());
//			sheetRow.createCell(6).setCellValue(results.getDuringGrade());
			sheetRow.createCell(7).setCellValue(results.getPanfu());
//			sheetRow.createCell(9).setCellValue(results.getFilename_S());
			sheetRow.createCell(8).setCellValue(results.getTensor());
			sheetRow.createCell(9).setCellValue(results.getbvalue());
			sheetRow.createCell(10).setCellValue(results.getKind());
		}
		// Excel表文件命名是以生成时间命名
		Date date = new Date();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String str=sdf.format(date).toString();
		// 写入文件
		try {
			workbook.write(new FileOutputStream(
					new File(Parameters.prePath + "/Excel/" + str + ".xlsx")));
			workbook.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
//        Date endTime = new Date();
//        MemoryUsage endMemory = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		System.out.println("生成Excel表成功!");
		// 弹出对话框
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("提示");
		alert.setHeaderText("导出Excel成功");
		alert.setContentText("导出Excel成功，请前往" + Parameters.prePath + "/Excel/" + "查看文件" + str);
		alert.showAndWait();
	}

	@FXML
	private void onClickQuery() {// 查询按钮
		System.out.println("点击了查询按钮");
		// 选择的数据库表
		String db_tablename = list_db.get(select_db.getSelectionModel().getSelectedIndex());
		// 震级
		String db_grade = query_grade.getText();
		// 能量
		String db_energy = query_energy.getText();
		// 破坏类型
		String db_destory = list_destory.get(destroyType.getSelectionModel().getSelectedIndex());
		// 事件类型
		String db_event = list_event.get(eventType.getSelectionModel().getSelectedIndex());
		// 起始时间
		String db_startTime = startDate.getValue() + " 00:00:00";
		// 终止时间
		String db_endTime = endDate.getValue() + " 00:00:00";
		/** 开始组装SQL语句 */
		String sql = "";
		if (db_grade != null && !db_grade.equals("") && !db_grade.equals(" ")) {
			if (db_energy != null && !db_energy.equals("") && !db_energy.equals(" "))
				sql = "select * from " + db_tablename + " where quackTime between '" + db_startTime + "' and '"
						+ db_endTime + "' and quackGrade >= " + db_grade + " and nengliang >= " + db_energy;// 震级和能量都不为空
			else
				sql = "select * from " + db_tablename + " where quackTime between '" + db_startTime + "' and '"
						+ db_endTime + "' and quackGrade >= " + db_grade;// 震级不为空，能量为空
		} else {
			if (db_energy != null && !db_energy.equals("") && !db_energy.equals(" "))
				sql = "select * from " + db_tablename + " where quackTime between '" + db_startTime + "' and '"
						+ db_endTime + "' and nengliang >= " + db_energy;// 震级为空，能量不为空
			else
				sql = "select * from " + db_tablename + " where quackTime between '" + db_startTime + "' and '"
						+ db_endTime + "'";// 震级和能量都为空
		}

		System.out.println("对数据库操作的SQL语句为：" + sql);
//		Tools_DataCommunication.getCommunication().dataList_QueryPanel.clear();
//		DbExcute aDbExcute = new DbExcute();
//		aDbExcute.Query_panel(sql);
		mytable.setTableViewData(sql, tableViewType.Query);

		System.out.println("===============================================");
		/** 在CAD图纸上绘制按照约束条件查询到的所有的定位点 */
		data = Tools_DataCommunication.getCommunication().dataList_QueryPanel;
		if (data.size() <= 0) {
			// 禁止导出按钮
			mExportButton.setDisable(true);
			return;
		}
		// 激活导出按钮
		mExportButton.setDisable(false);

		MyCAD cad = Tools_DataCommunication.getCommunication().getmCAD();
		cad.getController().deleteALL();
//		TableData tabledata = null;
//		for (int i = 0; i < data.size(); i++) {
//			tabledata = (TableData) data.get(i);
//			Tools_DataCommunication.getCommunication().getmCAD().getController().ShowcircleALL(
//					tabledata.getQuackResults().getxData(), tabledata.getQuackResults().getyData(),
//					Tools_DataCommunication.getCommunication().circleRadius);
//		}
		if (myThread != null)
			myThread.interrupt();
		Tools_DataCommunication.getCommunication().getmCAD().getController().deleteALL();
		this.myThread = new Thread(new Runnable() {
			@Override
			public void run() {
				TableData tabledata = null;
				for (int i = 0; i < data.size(); i++) {
					try {
						Thread.sleep(2000);
						tabledata = (TableData) data.get(i);
						Tools_DataCommunication.getCommunication().getmCAD().getController().ShowcircleALL(
								tabledata.getQuackResults().getxData(), tabledata.getQuackResults().getyData(),
								Tools_DataCommunication.getCommunication().circleRadius);
					} catch (InterruptedException e) {
						System.out.println("======Info:绘制查询CAD定位点线程被正常中断。------QueryPanelController========");
//						e.printStackTrace();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});
		this.myThread.start();
	}

	private ArrayList<String> list_db = new ArrayList<String>();
	private ArrayList<String> list_destory = new ArrayList<>();
	private ArrayList<String> list_event = new ArrayList<>();

	@FXML
	void initialize() {
		mytable = Tools_DataCommunication.getCommunication().getmTableView();
		/** 数据库选择 */
		db_addcomboBoxdata();
		initComboBox(select_db, list_db);
		/** 破坏类型 */
		list_destory.add("剪切");
		list_destory.add("张拉");
		list_destory.add("混合");
		initComboBox(destroyType, list_destory);
		/** 事件类型 */
		list_event.add("煤层错动");
		list_event.add("顶板垮塌");
		list_event.add("冒顶");
		initComboBox(eventType, list_event);
		/** 起始时间 */
		startDate.setValue(LocalDate.of(2018, 10, 8));
		/** 终止时间 */
		endDate.setValue(LocalDate.now());
	}

	/***
	 * 将合法的数据库表名显示在ComboBox上
	 */
	private void db_addcomboBoxdata() {

//		if (Parameters.DatabaseName3 != null && Parameters.DatabaseName3 != "" && Parameters.DatabaseName3 != " ")
//			list_db.add(Parameters.DatabaseName3);
//
//		if (Parameters.DatabaseName3_updated != null && Parameters.DatabaseName3_updated != ""
//				&& Parameters.DatabaseName3_updated != " ")
//			list_db.add(Parameters.DatabaseName3_updated);
//
//		if (Parameters.DatabaseName4 != null && Parameters.DatabaseName4 != "" && Parameters.DatabaseName4 != " ")
//			list_db.add(Parameters.DatabaseName4);

		if (Parameters.DatabaseName5 != null && Parameters.DatabaseName5 != "" && Parameters.DatabaseName5 != " ")
			list_db.add(Parameters.DatabaseName5);
//
//		if (Parameters.DatabaseName5_updated != null && Parameters.DatabaseName5_updated != ""
//				&& Parameters.DatabaseName5_updated != " ")
//			list_db.add(Parameters.DatabaseName5_updated);
	}

	private void initComboBox(ComboBox<String> c, ArrayList<String> data) {

		c.getItems().addAll(data);
		c.getSelectionModel().select(0);
		c.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (myThread != null)
					myThread.interrupt();
				Tools_DataCommunication.getCommunication().dataList_QueryPanel.clear();
				Tools_DataCommunication.getCommunication().getmCAD().getController().deleteALL();
				// TODO：对更改选中Item的监听
				if (newValue.equals(Parameters.DatabaseName5))
					mytable.setTableViewData("select * from " + Parameters.DatabaseName5, tableViewType.Query);
			}
		});
	}
}
