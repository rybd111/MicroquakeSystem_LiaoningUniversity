package com.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.h2.constant.Parameters;


public class JdbcUtil {
	static String url = null;
	static String username = null;
	static String password = null;
	static String driver = null;

	static {
		try {
			Properties properties = new Properties();
//			String j = "./jdbc.properties";//get the procedure absolute path.
//			System.out.println(j);
//			InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream(j);
//			System.out.println(in);
			
//			properties.load(in);
			properties.put("driver","com.mysql.jdbc.Driver");
			//properties.put("url","url=jdbc:mysql://localhost:3306/ks?useUnicode=true&amp;characterEncoding=utf8&amp;allowMultiQueries=true");
			properties.setProperty("useSSL", "false");
			driver = properties.getProperty("driver");
			
//			url = properties.getProperty("url");
			url = "jdbc:mysql://localhost:3306/ks?useSSL=false";
			//System.out.println(url);
//			username = properties.getProperty("username");
//			password = properties.getProperty("password");
			username = "root";
			password = "root";
			System.out.println(password);
			System.out.println(driver);
			
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("驱动加载失败！");
			throw new RuntimeException(e);
		}
	}

	public static Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void close(Connection conn, Statement st, ResultSet rs) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void close(Connection conn, Statement st) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	 /*
     * 释放资源
     */
    public static void releaseResources(ResultSet resultSet,
            Statement statement, Connection connection) {

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
}
