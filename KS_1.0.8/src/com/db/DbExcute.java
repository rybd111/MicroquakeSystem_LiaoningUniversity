package com.db;

import java.io.FileNotFoundException;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.tools.Tool;

import com.h2.constant.Parameters;
import visual.model.TableData;
import visual.util.Tools_DataCommunication;

import bean.QuackResults;
import javafx.application.Platform;
import javazoom.jl.decoder.JavaLayerException;

/**
 * 2017/10/21
 * @revision 2020-9-23
 * @author Yilong Zhang, Hanlin Zhang, Yongliang Hu, Gang Zhang, et al.
 * 
 */
public class DbExcute {

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	public void update(String sql) {
		try {
			connection = JdbcUtil.getConnection();
			statement = connection.createStatement();

			boolean num = statement.execute(sql);
			System.out.println(num);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(connection, (com.mysql.jdbc.Statement) statement, resultSet);
		}
	}

	public ResultSet Query(String sql) {
		try {
			connection = JdbcUtil.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

		} catch (SQLException e) {
			System.out.println("Error:----ks数据库中不存在该表");
//			e.printStackTrace();
		} finally {
			JdbcUtil.releaseResources(resultSet, statement, connection);
		}
		return resultSet;
	}

	/***
	 * 在客户端程序启动时读取数据库
	 * 
	 * @param sql
	 * @return
	 * @author Sunny
	 */
	@SuppressWarnings("unchecked")
	public ResultSet Query_3_5(String sql) {
		try {
			connection = JdbcUtil.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			/***
			 * @param eventIndex 事件序号
			 * @param eventTime  触发时间
			 * @param eventLoca  触发台站
			 * @param eventPos   定位坐标
			 * @param energy     能量
			 * @param grade      震级
			 */
			while (resultSet.next()) {
				Tools_DataCommunication.getCommunication().dataList.add(new TableData(resultSet.getString("id"),
						resultSet.getString("quackTime"),  resultSet.getString("kind"), resultSet.getString("panfu"),
						resultSet.getString("xData") + "," + resultSet.getString("yData") + ","	+ resultSet.getString("zData"),
						resultSet.getString("nengliang"), resultSet.getString("quackGrade"),
						resultSet.getString("parrival"), resultSet.getString("tensor"),resultSet.getString("b_Value"),
						new QuackResults(resultSet.getDouble("xData"), resultSet.getDouble("yData"),
								resultSet.getDouble("zData"), resultSet.getString("quackTime"),
								resultSet.getDouble("quackGrade"), resultSet.getDouble("Parrival"),
								resultSet.getString("panfu"), resultSet.getDouble("duringGrade"),
								resultSet.getDouble("nengliang"), resultSet.getString("wenjianming"), resultSet.getDouble("tensor"), resultSet.getString("kind"), resultSet.getDouble("b_Value"))));
			}
		} catch (SQLException e) {
			System.out.println("Error:----ks数据库中不存在该表");
//			e.printStackTrace();
		} finally {
			JdbcUtil.releaseResources(resultSet, statement, connection);
		}
		return resultSet;
	}

	/***
	 * 用于监测数据库中的mine_quack_3_results表是否添加了新数据
	 * 
	 * @param sql
	 * @return
	 * @author Sunny
	 */
	public ResultSet QueryIsadd(String sql) {
		boolean isadd = false;
		TableData data = null;
		try {
			connection = JdbcUtil.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			/***
			 * 
			 * @param eventIndex 事件序号
			 * @param eventTime  触发时间
			 * @param eventLoca  触发台站
			 * @param eventPos   定位坐标
			 * @param energy     能量
			 * @param grade      震级
			 * 
			 */
			while (resultSet.next()) {
				int id = resultSet.getShort("id");
				// 从数据库表中依次读取数据，并把数据与内存中现有数据依次进行比较
				for (Object obj : Tools_DataCommunication.getCommunication().dataList) {
					data = (TableData) obj;
					if (id == Integer.parseInt(data.getEventIndex())) {
						isadd = false;
						break;
					} else
						isadd = true;
				}
				if (isadd) {// 如果表新添加了数据，就把数据显示在TableView表中
					// 添加新数据
					TableData newdata = new TableData(resultSet.getString("id"), resultSet.getString("quackTime"),
							resultSet.getString("kind"), resultSet.getString("panfu"),
							resultSet.getString("xData") + "," + resultSet.getString("yData") + ","
									+ resultSet.getString("zData"),
							resultSet.getString("nengliang"), resultSet.getString("quackGrade"),
							resultSet.getString("parrival"), resultSet.getString("tensor"),resultSet.getString("b_value"),
							new QuackResults(resultSet.getDouble("xData"), resultSet.getDouble("yData"),
									resultSet.getDouble("zData"), resultSet.getString("quackTime"),
									resultSet.getDouble("quackGrade"), resultSet.getDouble("Parrival"),
									resultSet.getString("panfu"), resultSet.getDouble("duringGrade"),
									resultSet.getDouble("nengliang"), resultSet.getString("wenjianming"), resultSet.getDouble("tensor"), 
									resultSet.getString("kind"), resultSet.getDouble("b_value")));

					/** 自动在UI界面上显示新数据操作 */
					// 播放语音
					try {
						Tools_DataCommunication.getCommunication().getAudioPlayer().play();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						System.out.println("没有找到提示音频文件。");
//						e.printStackTrace();
					} catch (JavaLayerException e) {
						// TODO Auto-generated catch block
						System.out.println("Java播放异常，请及时联系开发人员！");
//						e.printStackTrace();
					}
					/** 将数据更新到UI界面上 */
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// 更新tableview的数据链
							Tools_DataCommunication.getCommunication().dataList.add(newdata);
							Tools_DataCommunication.getCommunication().getmTableView().autoShowData(newdata);
							// 更新最新事件的时间:
							Tools_DataCommunication.getCommunication().getmCAD().updataNewLabel(newdata.getEventTime());
						}
					});				
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.releaseResources(resultSet, statement, connection);
		}
		return resultSet;
	}

	public void addElement(String sql) {
		update(sql);
	}

	public void addElement(QuackResults aQuackResults) {

		String sqlStr = "insert into " + Parameters.DatabaseName5 + " values(null,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		connection = JdbcUtil.getConnection();
		PreparedStatement aStatement = null;
		try {
			aStatement = connection.prepareStatement(sqlStr);
			aStatement.setString(1, aQuackResults.getKind());
			aStatement.setDouble(2, aQuackResults.getxData());
			aStatement.setDouble(3, aQuackResults.getyData());
			aStatement.setDouble(4, aQuackResults.getzData());
			aStatement.setString(5, aQuackResults.getQuackTime());
			aStatement.setDouble(6, aQuackResults.getQuackGrade());
			aStatement.setDouble(7, aQuackResults.getParrival());
			aStatement.setString(8, aQuackResults.getPanfu());
			aStatement.setDouble(9, aQuackResults.getDuringGrade());
			aStatement.setDouble(10, aQuackResults.getNengliang());
			aStatement.setString(11, aQuackResults.getFilename_S());
			aStatement.setDouble(12, aQuackResults.getTensor());
			aStatement.setDouble(13, aQuackResults.getbvalue());
			
			System.out.println(aStatement.execute() + "-shujuku");
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error:----ks数据库中不存在该表");
//			e.printStackTrace();
		} finally {
			JdbcUtil.close(connection, (com.mysql.cj.api.jdbc.Statement) statement, resultSet);
		}
		if (Tools_DataCommunication.getCommunication().isClient)
			if (Parameters.DatabaseName5.equals(Tools_DataCommunication.getCommunication().getmTableView()
					.getmComboBox().getSelectionModel().getSelectedItem()))
				QueryIsadd("select * from " + Parameters.DatabaseName5);
	}

	public ArrayList<String> getData(String paras[]) {
		// String sql="select * from mine_quack_results where 1=?";

		String sql = null;
//		sql[0] = "select * from "+Parameters.DatabaseName3+" where quackTime>=? and quackTime<? and quackGrade>? and nengliang>?";
//		sql[1] = "select * from "+Parameters.DatabaseName4+" where quackTime>=? and quackTime<? and quackGrade>? and nengliang>?";
		sql = "select * from " + Parameters.DatabaseName5
				+ " where quackTime>=? and quackTime<? and quackGrade>? and nengliang>?";

//		String sql_union = "select * from ("
//				+ "select * from "+Parameters.DatabaseName3+" union all "
//				+ "select * from "+Parameters.DatabaseName5+" union all "
//				+ "select * from "+Parameters.DatabaseName4+") as t where quackTime>=? and quackTime<? and quackGrade>?";

		ArrayList<String> newAl = new ArrayList<String>();
//		for(int i=0;i<sql.length;i++) {
		// String paras[]={"2017-03-25 00:00:00","2017-06-02 00:00:00"};
		ArrayList al = new ArrayList();
		try {
			connection = JdbcUtil.getConnection();
			PreparedStatement aStatement = null;
//				aStatement = connection.prepareStatement("sql"+String.valueOf(i+3));
			aStatement = connection.prepareStatement(sql);
			if (paras != null) {
				for (int j = 0; j < paras.length; j++) {
					aStatement.setString(j + 1, paras[j]);
				}
			}
			resultSet = aStatement.executeQuery();

			ResultSetMetaData rsmd = resultSet.getMetaData();
			int column = rsmd.getColumnCount();

			while (resultSet.next()) {
				Object[] ob = new Object[column];
				for (int j = 1; j <= column; j++) {
					ob[j - 1] = resultSet.getObject(j);
				}
				al.add(ob);
			}
		} catch (SQLException e) {
			System.out.println("Error:----ks数据库中不存在该表");
//			e.printStackTrace();
		} finally {
			JdbcUtil.close(connection, (com.mysql.cj.api.jdbc.Statement) statement, resultSet);
		}

		for (int j = 0; j < al.size(); j++) {
			Object obj[] = (Object[]) al.get(j);
//				BigDecimal bd = new BigDecimal(obj[1].toString());
//				newAl.add(bd.toPlainString());
			newAl.add(obj[1].toString());
			newAl.add(obj[2].toString());
			newAl.add(obj[3].toString());
			newAl.add(obj[6].toString());
			newAl.add(obj[10].toString());

			// System.out.println(obj[0].toString()+" "+obj[1].toString());

			// System.out.println(al.get(i).toString());
		}
//		}
		return newAl;
	}

	public void removeElement(String sql) {
		update(sql);

	}

	public void createTable(String sql) {
		update(sql);
	}

	public void dropTable(String sql) {
		update(sql);
	}

	public void deleteRepate(String sql) {
		update(sql);
	}

	/**
	 * 用于查询面板上对数据库进行查询操作的函数
	 * 
	 * @author Sunny
	 * @param sql
	 */
	@SuppressWarnings("unchecked")
	public void Query_panel(String sql) {
		if (sql == null || sql == "" || sql == " ")
			return;
		Tools_DataCommunication.getCommunication().dataList_QueryPanel.clear();
		try {
			connection = JdbcUtil.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			/***
			 * @param eventIndex 事件序号
			 * @param eventTime  触发时间
			 * @param eventLoca  触发台站
			 * @param eventPos   定位坐标
			 * @param energy     能量
			 * @param grade      震级
			 */
			while (resultSet.next()) {
				Tools_DataCommunication.getCommunication().dataList_QueryPanel.add(new TableData(
						resultSet.getString("id"), resultSet.getString("quackTime"), resultSet.getString("kind"), resultSet.getString("panfu"),
						resultSet.getString("xData") + "," + resultSet.getString("yData") + ","	+ resultSet.getString("zData"),
						resultSet.getString("nengliang"), resultSet.getString("quackGrade"),
						resultSet.getString("parrival"), resultSet.getString("tensor"),resultSet.getString("b_value"),
						new QuackResults(resultSet.getDouble("xData"), resultSet.getDouble("yData"),
								resultSet.getDouble("zData"), resultSet.getString("quackTime"),
								resultSet.getDouble("quackGrade"), resultSet.getDouble("Parrival"),
								resultSet.getString("panfu"), resultSet.getDouble("duringGrade"),
								resultSet.getDouble("nengliang"), resultSet.getString("wenjianming"), resultSet.getDouble("tensor"), 
								resultSet.getString("kind"), resultSet.getDouble("b_value"))));
			}
		} catch (SQLException e) {
			System.out.println("Error:----ks数据库中不存在该表");
//			e.printStackTrace();
		} finally {
			JdbcUtil.releaseResources(resultSet, statement, connection);
		}
	}

}
