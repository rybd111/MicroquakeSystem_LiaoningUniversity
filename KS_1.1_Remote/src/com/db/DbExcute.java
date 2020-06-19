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
		
		String sqlStr = "insert into "+ Parameters.DatabaseName5 +" values(null,?,?,?,?,?,?,?,?,?,?)";
		connection = JdbcUtil.getConnection();
		PreparedStatement aStatement = null;
		try {
			aStatement = connection.prepareStatement(sqlStr);
			/*double[] a=new double[4];
			
				a[0]=0.64940742498539*aQuackResults.getxData()+35985600.9610101;
				a[1]=-1.53986503030543*aQuackResults.getxData()+44192917.6882761;
				a[2]=-1.53983673409655*aQuackResults.getxData()+44185403.6645409;
				a[3]=0.649460010261126*aQuackResults.getxData()+35995275.1940533;
				for(int i=0;i<a.length;i++){
					System.out.println("a["+i+"]:"+a[i]);
				}*/
				
			/*if((aQuackResults.getxData()<Parameters.PINGDDINGMAP_MAX_X)&&aQuackResults.getxData()>Parameters.PINGDDINGMAP_MIN_X&&
				aQuackResults.getyData()>(0.64940742498539*aQuackResults.getxData()+35985600.9610101)&&
				aQuackResults.getyData()<(-1.53986503030543*aQuackResults.getxData()+44192917.6882761)&&
				aQuackResults.getyData()>(-1.53983673409655*aQuackResults.getxData()+44185403.6645409)&&
				aQuackResults.getyData()<(0.649460010261126*aQuackResults.getxData()+35995275.1940533)){*///锟睫讹拷锟斤拷平锟斤拷山锟斤拷图锟斤拷围锟节的碉拷娲拷锟斤拷锟斤拷菘锟斤拷锟�
//			if(aQuackResults.getxData()>533990.667 && aQuackResults.getyData()>4417004.76
//					&&aQuackResults.getxData()<546000 && aQuackResults.getyData()<4429001.8){
			//if(aQuackResults.getxData()>0 && aQuackResults.getyData()>0){
				aStatement.setDouble(1, aQuackResults.getxData());
				aStatement.setDouble(2, aQuackResults.getyData());
				aStatement.setDouble(3, aQuackResults.getzData());
				aStatement.setTimestamp(4, aQuackResults.getQuackTime());
				aStatement.setDouble(5, aQuackResults.getQuackGrade());
				aStatement.setDouble(6, aQuackResults.getParrival()); 
				aStatement.setString(7, aQuackResults.getPanfu());
				aStatement.setDouble(8, aQuackResults.getDuringGrade());
				aStatement.setDouble(9, aQuackResults.getNengliang());
				aStatement.setString(10, aQuackResults.getFilename_S());
//				aStatement.execute();
				System.out.println(aStatement.execute()+"-shujuku");
				connection.close();
//			}
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
			/*double[] a=new double[4];
			
				a[0]=0.64940742498539*aQuackResults.getxData()+35985600.9610101;
				a[1]=-1.53986503030543*aQuackResults.getxData()+44192917.6882761;
				a[2]=-1.53983673409655*aQuackResults.getxData()+44185403.6645409;
				a[3]=0.649460010261126*aQuackResults.getxData()+35995275.1940533;
				for(int i=0;i<a.length;i++){
					System.out.println("a["+i+"]:"+a[i]);
				}*/
				
			/*if((aQuackResults.getxData()<Parameters.PINGDDINGMAP_MAX_X)&&aQuackResults.getxData()>Parameters.PINGDDINGMAP_MIN_X&&
				aQuackResults.getyData()>(0.64940742498539*aQuackResults.getxData()+35985600.9610101)&&
				aQuackResults.getyData()<(-1.53986503030543*aQuackResults.getxData()+44192917.6882761)&&
				aQuackResults.getyData()>(-1.53983673409655*aQuackResults.getxData()+44185403.6645409)&&
				aQuackResults.getyData()<(0.649460010261126*aQuackResults.getxData()+35995275.1940533)){*///锟睫讹拷锟斤拷平锟斤拷山锟斤拷图锟斤拷围锟节的碉拷娲拷锟斤拷锟斤拷菘锟斤拷锟�
//			if(aQuackResults.getxData()>533990.667 && aQuackResults.getyData()>4417004.76
//					&&aQuackResults.getxData()<546000 && aQuackResults.getyData()<4429001.8){
			//if(aQuackResults.getxData()>0 && aQuackResults.getyData()>0){
				aStatement3.setDouble(1, aQuackResults.getxData());
				aStatement3.setDouble(2, aQuackResults.getyData());
				aStatement3.setDouble(3, aQuackResults.getzData());
				aStatement3.setTimestamp(4, aQuackResults.getQuackTime());
				aStatement3.setDouble(5, aQuackResults.getQuackGrade());
				aStatement3.setDouble(6, aQuackResults.getParrival());
				aStatement3.setString(7, aQuackResults.getPanfu());
				aStatement3.setDouble(8, aQuackResults.getDuringGrade());
				aStatement3.setDouble(9, aQuackResults.getNengliang());
				aStatement3.setString(10, aQuackResults.getFilename_S());
				System.out.println(aStatement3.execute()+"-shujuku");
				connection.close();
//			}
		} catch (SQLException e) {e.printStackTrace();}
		finally {
			JdbcUtil.close(connection,(com.mysql.jdbc.Statement) statement,resultSet);
		}
	}

	
	public ArrayList<String> getData(String paras[]) {
		// String sql="select * from mine_quack_results where 1=?";
		
		String []sql = new String[3];
		sql[0] = "select * from "+Parameters.DatabaseName3+" where quackTime>=? and quackTime<? and quackGrade>? and nengliang>?";
		sql[1] = "select * from "+Parameters.DatabaseName4+" where quackTime>=? and quackTime<? and quackGrade>? and nengliang>?";
		sql[2] = "select * from "+Parameters.DatabaseName5+" where quackTime>=? and quackTime<? and quackGrade>? and nengliang>?";
		
		
//		String sql_union = "select * from ("
//				+ "select * from "+Parameters.DatabaseName3+" union all "
//				+ "select * from "+Parameters.DatabaseName5+" union all "
//				+ "select * from "+Parameters.DatabaseName4+") as t where quackTime>=? and quackTime<? and quackGrade>?";
		
		
		ArrayList<String> newAl = new ArrayList<String>();
		for(int i=0;i<sql.length;i++) {		
			// String paras[]={"2017-03-25 00:00:00","2017-06-02 00:00:00"};
			ArrayList al = new ArrayList();
			try {
				connection = JdbcUtil.getConnection();
				PreparedStatement aStatement = null;
//				aStatement = connection.prepareStatement("sql"+String.valueOf(i+3));
				aStatement = connection.prepareStatement(sql[i]);
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
				BigDecimal bd = new BigDecimal(obj[1].toString());
				newAl.add(bd.toPlainString());
				newAl.add(obj[2].toString());
				newAl.add(obj[5].toString());
				newAl.add(obj[8].toString());
				newAl.add(String.valueOf(i+3));
				// System.out.println(obj[0].toString()+" "+obj[1].toString());
	
				// System.out.println(al.get(i).toString());
			}
		}
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
