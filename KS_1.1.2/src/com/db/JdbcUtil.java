package com.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * connect to database.
 * @author 
 *
 */
public class JdbcUtil {
	static String url = null;
	static String username = null;
	static String password = null;
	static String driver = null;

	static {
		try {
			Properties properties = new Properties();
			String j = System.getProperty("user.dir") + "/jdbc.properties";
			InputStreamReader in = null;
			
			try {
				in = new InputStreamReader(new FileInputStream(new File(j)), "UTF-8");
				properties.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}

			url = properties.getProperty("url");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
			driver = properties.getProperty("driver");
			
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
