package com.yhy.getinformation;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 * This class is used for adding,querying,updating for the database table of  mine_quack_stationcondition
 * @author Haiyou Yu
 * @version 1.0 2020-11-14
 */

public class DatabaseUtil {
	private static String url = null;
	private static String username = null;
	private static String password = null;
	private static String driver = null;
	private static Connection connection = null;
	private static Statement statement;
	private static ResultSet resultSet;
	private static final String TABLENAME = "mine_quack_stationcondition";

	public static Connection getConnection() {
		try {
			Properties properties = new Properties();
			InputStream in = DatabaseUtil.class.getClassLoader().getResourceAsStream("./jdbc.properties");

			properties.load(in);
			properties.setProperty("driver", "com.mysql.cj.jdbc.Driver");
			driver = properties.getProperty("driver");
			properties.setProperty("url", "jdbc:mysql://182.92.239.30:3306/ks?serverTimezone=UTC&useSSL=false");
			url = properties.getProperty("url");
//			username = properties.getProperty("username");
//			password = properties.getProperty("password");

			username = "root";
			password = "root";

			Class.forName(driver);
			if(connection == null)
				connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}

	
	public static void close() {
		try {
			if (resultSet != null)
				resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resultSet = null;
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				statement = null;
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					connection = null;
				}
			}
		}
	}

	
	public static boolean update(String sql) {
		boolean successful = false;
		try {
			statement = DatabaseUtil.getConnection().createStatement();
			successful = statement.execute(sql);
			//System.out.println(successful);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		//true if the first result is a ResultSetobject; 
		//false if it is an update count or there areno results
		return successful;
	}
	
	public static ArrayList<TableProperty> query(String sql) {
		ArrayList<TableProperty> resultSet =new  ArrayList<>();
		ResultSet rs = null;
		TableProperty tp = null;
		try {
			rs = DatabaseUtil.getConnection().
					createStatement().executeQuery(sql);
			
			while (rs.next()) {	
				tp = new TableProperty();
				tp.setDay(rs.getString("day"));
				tp.setPanfu(rs.getString("panfu"));
				tp.setxData(rs.getString("xData"));
				tp.setyData(rs.getString("yData"));
				tp.setzData(rs.getString("zData"));
				tp.setLocation(rs.getString("location"));
				tp.setStatus(rs.getString("status"));
				tp.setUnused(rs.getString("unused"));
				tp.setUsed(rs.getString("used"));
				tp.setTotal(rs.getString("total"));
				tp.setNetspeed(rs.getString("netspeed"));
				resultSet.add(tp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultSet;
	}
	
	
	public static boolean insert(String sql) {
		boolean successful = false;
		try {
			successful = DatabaseUtil.getConnection().
			createStatement().execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return successful;
	}
	
	/**
	 * 
	 * @param values is the whole TableProperty fields
	 * @return true indicates succeed
	 */
	public static boolean insert(String[] values) {
		boolean flag = false;
		String sql = "insert into " + TABLENAME + " values(?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preStatement = null;
		try {
			preStatement = DatabaseUtil.getConnection().prepareStatement(sql);
			preStatement.setString(1,values[0]);
			preStatement.setString(2,values[1]);
			preStatement.setString(3,values[2]);
			preStatement.setString(4,values[3]);
			preStatement.setString(5,values[4]);
			preStatement.setString(6,values[5]);
			preStatement.setString(7,values[6]);
			preStatement.setString(8,values[7]);
			preStatement.setString(9,values[8]);
			preStatement.setString(10,values[9]);
			preStatement.setString(11,values[10]);
			flag = preStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * 
	 * @param values , is the whole TableProperty fields
	 * @param needUpdate  ,if true indicates that we need update the database,
	 * because there may be exist the values that we want to insert in the database.
	 * @return true indicates success when it is insert operation, false indicate 
	 * success when it is update operation.
	 */
	public static boolean insert(String[] values,boolean needUpdate) {
		boolean flag = false;
		
		if(needUpdate) {
			ResultSet rs = null;
			String sql = "select * from "+TABLENAME+" where day='"
						+values[0]+"' and panfu='"+values[1]+"' ";
			//System.out.println(sql);
			try {
				rs = DatabaseUtil.getConnection().
						createStatement().executeQuery(sql);
				if(rs.next()) {
					String updateSql =  "update "+TABLENAME+" set status='"+values[6]
							+"',unused='"+values[7]+"',used='"+values[8]+"',total='"+values[9]
							+"',netspeed='"+values[10]+"' where day='"+values[0]
							+"' and panfu='"+values[1]+"' ";
					flag = DatabaseUtil.getConnection().
							createStatement().execute(updateSql);
				}else {
					flag = insert(values);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			flag = insert(values);
		}
		return flag;
	}
	
	//----------------------------------------------------------------------
	// This is the test code.
	public static void main(String[] args) {
		
//		String []values= {"2020-11-14","K","ddd","140.0","102.5","159.6"
//				,"xiugaile","325G","65G","390G","2.66Mbps"};
//		insert(values,true);
//		
//		ArrayList<TableProperty> al;
//		String sql = "select * from "+TABLENAME;
//		al = query(sql);
//		for(TableProperty tp:al) {
//			System.out.println(tp.toString());
//		}
//		
//		
//		String sql3 = "update "+TABLENAME+" set location='xiugaile' where day='2020-11-14' and panfu='K'";
//		boolean flag = update(sql3);
//		System.out.println(flag);
//		al = query(sql);
//		for(TableProperty tp:al) {
//			System.out.println(tp.toString());
//		}
		
		String sql = "insert into test values('1','123')";
		update(sql);
		close();
	}
	//end test code.
	//-----------------------------------------------------------------------
}
