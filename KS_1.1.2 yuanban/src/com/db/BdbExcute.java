package com.db;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

/***
 * BerkeleyDB数据库操作类
 * 
 * @author Sunny-胡永亮 2020-11-21
 */
public class BdbExcute {
	/** 环境对象 */
	private Environment exampleEnv;
	/** 环境设置对象 */
	private EnvironmentConfig envConfig;
	/** 数据库对象 */
	private Database bdb;

	/** 判断是否运行 */
	private boolean isrunning = false;
	/** 数据库设置对象 */
	private DatabaseConfig dbConfig;
	private String dbname;
	private String path;

	public static void main(String[] args) {
		BdbExcute excute = new BdbExcute("D:\\dbEnv", "ks");
//		Vector<Integer> array = new Vector<>();
//		for (int i = 0; i < 50; i++) {
//			array.add(i);
//		}
//		excute.add("1", array.toString());

		excute.selectAll();
		excute.selectDBALL();
	}

	/***
	 * 初始化数据库环境和数据库设置
	 * 
	 * @param path   数据库环境路径
	 * @param dbname 数据库名称
	 */
	public BdbExcute(String path, String dbname) {
		this.path = path;
		this.dbname = dbname;
	}

	/** 数据库是否运行 */
	public boolean isrunning() {
		return isrunning;
	}

	/***
	 * 查看该环境下的数据库
	 */
	public void selectDBALL() {
		// 打开数据库
		this.openDB();
		List myDbNames = exampleEnv.getDatabaseNames();
		if (myDbNames.size() <= 0) {
			System.out.println("该环境不存在数据库");
			return;
		}
		for (int i = 0; i < myDbNames.size(); i++) {
			System.out.println("Database Name: " + (String) myDbNames.get(i));
		}
		// 关闭数据库
		this.closeDB();
	}

	/***
	 * 如果数据库中key值不存在，则添加数据。如果数据库中存在key值，则修改数据
	 * 
	 * @param key-键值对
	 * @param data-键值对
	 */
	public void add(String key, String data) {
		// 打开数据库
		this.openDB();
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();
		// 存储数据
		try {
			keyEntry.setData(key.getBytes("utf-8"));
			dataEntry.setData(data.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		OperationStatus status = bdb.put(null, keyEntry, dataEntry);// 持久化数据

		if (status != OperationStatus.SUCCESS) {
			throw new RuntimeException("数据插入状态：" + status);
		}
		// 关闭数据库
		this.closeDB();
	}

	/***
	 * 根据键值对删除数据
	 * 
	 * @param key-键值对
	 */
	public void delete(String key) {
		// 打开数据库
		this.openDB();
		DatabaseEntry keyEntry = null;
		try {
			keyEntry = new DatabaseEntry(key.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		bdb.delete(null, keyEntry);
		// 关闭数据库
		this.closeDB();
	}

	/***
	 * 根据键值对向数据库中取出数据
	 * 
	 * @param aKey-键值对
	 */
	public void selectByKey(String aKey) {
		// 打开数据库
		this.openDB();
		DatabaseEntry theKey = null;
		DatabaseEntry theData = new DatabaseEntry();
		try {
			theKey = new DatabaseEntry(aKey.getBytes("utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bdb.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) { // 根据key值，进行数据查询
			// Recreate the data String.
			byte[] retData = theData.getData();
			String foundData = new String(retData);
			System.out.println("key值：'" + aKey + "' value值： '" + foundData + "'.");
		} else
			System.out.println("没有成功取出来");
		// 关闭数据库
		this.closeDB();
	}

	/**
	 * 查询数据库中存储的所有数据
	 */
	public void selectAll() {
		// 打开数据库
		this.openDB();
		Cursor cursor = null;
		cursor = bdb.openCursor(null, null);
		DatabaseEntry theKey = null;
		DatabaseEntry theData = null;
		theKey = new DatabaseEntry();
		theData = new DatabaseEntry();

		while (cursor.getNext(theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
			System.out.println(new String(theData.getData()));
		}
		cursor.close();
		// 关闭数据库
		this.closeDB();
	}

	/***
	 * 打开数据库
	 */
	private void openDB() {
		if (isrunning) {
			return;
		}
		/******************** 文件处理 ***********************/
		File envDir = new File(path);// 操作文件
		if (!envDir.exists())// 判断文件路径是否存在，不存在则创建
			envDir.mkdir();// 创建
		/******************** 环境配置 ***********************/
		envConfig = new EnvironmentConfig();
		envConfig.setTransactional(false); // 不进行事务处理
		envConfig.setAllowCreate(true); // 如果不存在则创建一个
		exampleEnv = new Environment(envDir, envConfig);// 通过路径，设置属性进行创建
		/******************* 创建适配器对象 ******************/
		dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(false); // 不进行事务处理
		dbConfig.setAllowCreate(true);// 如果不存在则创建一个
//		dbConfig.setSortedDuplicates(true);// 数据分类
		dbConfig.setSortedDuplicates(true);
		bdb = exampleEnv.openDatabase(null, dbname, dbConfig); // 使用适配器打开数据库
		isrunning = true;
	}

	/***
	 * 关闭数据库
	 */
	private void closeDB() {
		if (isrunning) {
			isrunning = false;
			try {
				if (bdb != null)
					bdb.close();
				if (exampleEnv != null) {
					exampleEnv.cleanLog(); //  在关闭环境前清理下日志
					exampleEnv.close();
				}

			} catch (DatabaseException dbe) {

			}
		}
	}
}
