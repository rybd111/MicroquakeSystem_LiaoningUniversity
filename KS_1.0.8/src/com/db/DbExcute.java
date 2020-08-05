package com.db;

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

import com.h2.constant.Parameters;

import bean.QuackResults;

//锟斤拷锟斤拷锟斤拷锟捷匡拷锟斤拷锟斤拷锟接癸拷锟斤拷
/**
 * 2017/10/21
 * 
 * @author lemo
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
			JdbcUtil.close(connection, (com.mysql.jdbc.Statement) statement,
					resultSet);
		}
	}
	
	public ResultSet Query(String sql) {
		try {
			connection = JdbcUtil.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

		} catch (SQLException e) {e.printStackTrace();}
		finally {
			JdbcUtil.releaseResources(resultSet, statement, connection);
		}
		return resultSet;
	}


	public void addElement(String sql) {
		update(sql);
	}

	public void addElement(QuackResults aQuackResults) {
		
		String sqlStr = "insert into "+ Parameters.DatabaseName5 +" values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			
			System.out.println(aStatement.execute()+"-shujuku");
			connection.close();
		} catch (SQLException e) {e.printStackTrace();}
		finally {
			JdbcUtil.close(connection,(com.mysql.jdbc.Statement) statement,resultSet);
		}
	}
	
	public void addElement3(QuackResults aQuackResults) {

//		String sqlStr = "insert into mine_quack_results values(null,?,?,?,?,?)";
		String sqlStr3 = "insert into "+ Parameters.DatabaseName3 +" values(null,?,?,?,?,?,?,?,?,?,?)";
		connection = JdbcUtil.getConnection();
		PreparedStatement aStatement3 = null;
		try {
			aStatement3 = connection.prepareStatement(sqlStr3);
			
				aStatement3.setDouble(1, aQuackResults.getxData());
				aStatement3.setDouble(2, aQuackResults.getyData());
				aStatement3.setDouble(3, aQuackResults.getzData());
				aStatement3.setString(4, aQuackResults.getQuackTime());
				aStatement3.setDouble(5, aQuackResults.getQuackGrade());
				aStatement3.setDouble(6, aQuackResults.getParrival());
				aStatement3.setString(7, aQuackResults.getPanfu());
				aStatement3.setDouble(8, aQuackResults.getDuringGrade());
				aStatement3.setDouble(9, aQuackResults.getNengliang());
				aStatement3.setString(10, aQuackResults.getFilename_S());
				aStatement3.setDouble(11, aQuackResults.getTensor());
				aStatement3.setString(12, aQuackResults.getKind());
				System.out.println(aStatement3.execute()+"-shujuku");
				connection.close();
		} catch (SQLException e) {e.printStackTrace();}
		finally {
			JdbcUtil.close(connection,(com.mysql.jdbc.Statement) statement,resultSet);
		}
	}

	
	public ArrayList<String> getData(String paras[]) {
		// String sql="select * from mine_quack_results where 1=?";
		
		String sql = null;
//		sql[0] = "select * from "+Parameters.DatabaseName3+" where quackTime>=? and quackTime<? and quackGrade>? and nengliang>?";
//		sql[1] = "select * from "+Parameters.DatabaseName4+" where quackTime>=? and quackTime<? and quackGrade>? and nengliang>?";
		sql = "select * from "+Parameters.DatabaseName5+" where quackTime>=? and quackTime<? and quackGrade>? and nengliang>?";
		
		
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
				e.printStackTrace();
			} finally {
				JdbcUtil.close(connection, (com.mysql.jdbc.Statement) statement,
						resultSet);
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
	// 删锟斤拷锟斤拷锟斤拷
	public void removeElement(String sql) {
		update(sql);

	}
	// 锟斤拷锟斤拷一锟斤拷锟斤拷
	public void createTable(String sql) {
		update(sql);
	}
	// 删锟斤拷一锟斤拷锟斤拷
	public void dropTable(String sql) {
		update(sql);
	}
	public void deleteRepate(String sql) {
		// TODO Auto-generated method stub
		update(sql);
	}

}
