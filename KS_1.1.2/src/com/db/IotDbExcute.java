package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
//import org.apache.iotdb.jdbc.IoTDBSQLException;

public class IotDbExcute {
	private Connection connection = null;
	private Statement statement = null;

	private void Init() {
		// 连接数据库
		connection = getConnection();
		// 判断是否连接成功
		this.isconnection();

		try {
			Statement statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		// 顺序不能颠倒
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/***
	 * 定义IOT数据库的存储组
	 * 
	 * @param groupname 存储组名
	 */
	public void setStorageGroup(String groupname) {
		this.Init();
		String sql = "SET STORAGE GROUP TO " + groupname;
		System.out.println(sql);
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
	}

	/**
	 * 查看IOT数据库中已存在的存储组
	 */
	public void showStorageGroup() {
		this.Init();
		System.out.println("1111111111");
		try {
			statement.execute("SHOW STORAGE GROUP");
			outputResult(statement.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		this.close();
	}

	/***
	 * 创建新的时间序列
	 * 
	 * @param timeseriesName 时间序列名称
	 * @param datatype       数据类型
	 * @param encoding       编码类型
	 */
	public void createTimeseries(String timeseriesName, String datatype, String encoding) {
		this.Init();
		String sql = "CREATE TIMESERIES " + timeseriesName + " WITH DATATYPE = " + datatype + ", ENCODING = " + encoding
				+ ";";
		System.out.println(sql);
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
	}

	/***
	 * 查看数据库中所有的时间序列
	 */
	public void showTimeseriesALL() {
		this.Init();
		try {
			statement.execute("SHOW TIMESERIES root.demo");
			outputResult(statement.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
	}

	/***
	 * 查找指定存储组的时间序列
	 * 
	 * @param groupname 指定存储组名
	 */
	public void showTimeseries(String groupname) {
		this.Init();
		String sql = "SHOW TIMESERIES " + groupname;
		System.out.println(sql);
		try {
			statement.execute(sql);
			outputResult(statement.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
	}

	/**
	 * 待定
	 */
	public void showDevices() {
		this.Init();
		try {
			statement.execute("SHOW DEVICES");
			outputResult(statement.getResultSet());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
	}

	/***
	 * 向指定存储组中的指定时间序列插入数据
	 * 
	 * @param groupName      指定存储组名
	 * @param timeseriesName 指定时间序列
	 * @param data1          时间
	 * @param data2          数据
	 */
	public void insert(String groupName, String timeseriesName, String data1, String data2) {
		this.Init();
		String sql = "insert into " + groupName + "(timestamp," + timeseriesName + ") values(" + data1 + "," + data2
				+ ");";
		System.out.println(sql);
		try {
			statement.addBatch(sql);
			statement.executeBatch();
			statement.clearBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
	}

	/**
	 * 查找指定存储组内所有时间序列的所有数据
	 * 
	 * @param groupName 指定存储组名
	 */
	public void selectALL(String groupName) {
		select(groupName, "*");
	}

	/***
	 * 查找指定存储组内指定时间序列的所有数据
	 * 
	 * @param groupName
	 * @param timeseriesName
	 */
	public void select(String groupName, String timeseriesName) {
		this.Init();
		String sql = "select " + timeseriesName + " from " + groupName;
		System.out.println(sql);
		ResultSet resultSet;
		try {
			resultSet = statement.executeQuery(sql);
			outputResult(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
	}

	public void delectTimeseries(String timeseriesName) {
		this.Init();
		String sql = "delete timeseries " + timeseriesName;
		System.out.println(sql);
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
	}

//=======================================================Init============================================================
	/***
	 * 判断是否连接到IOT数据库
	 */
	private void isconnection() {
		if (connection == null) {
			System.out.println("IOT数据库连接失败！");
			return;
		}
	}

	/***
	 * 连接到IOT数据库
	 * 
	 * @return
	 */
	public Connection getConnection() {
		// JDBC driver name and database URL
		String driver = "org.apache.iotdb.jdbc.IoTDBDriver";
		String url = "jdbc:iotdb://127.0.0.1:6667/";

		// Database credentials
		String username = "root";
		String password = "root";

		Connection connection = null;
		try {
			Class.forName("org.apache.iotdb.jdbc.IoTDBDriver");
			connection = DriverManager.getConnection(url, username, password);
//      Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/***
	 * 自定义输出IOT数据库内容
	 * 
	 * @param resultSet
	 * @throws SQLException
	 */
	private void outputResult(ResultSet resultSet) throws SQLException {
		if (resultSet != null) {
			System.out.println("--------开始输出------------------");
			final ResultSetMetaData metaData = resultSet.getMetaData();
			final int columnCount = metaData.getColumnCount();
			for (int i = 0; i < columnCount; i++) {
				System.out.print(metaData.getColumnLabel(i + 1) + " ");
			}
			System.out.println();
			while (resultSet.next()) {
				for (int i = 1;; i++) {
					System.out.print(resultSet.getString(i));
					if (i < columnCount) {
						System.out.print(", ");
					} else {
						System.out.println();
						break;
					}
				}
			}
			System.out.println("----------输出完毕----------------\n");
		}
	}
}
