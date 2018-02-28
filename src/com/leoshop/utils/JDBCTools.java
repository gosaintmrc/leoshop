package com.leoshop.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 操作JDBC 的工具类，其中封装了一些工具方法 version 1
 */
public class JDBCTools {

	// 提交事务
	public static void commit(Connection con) {
		if (con != null) {
			try {
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 开始事物
	public static void beginTx(Connection con) {
		if (con != null) {
			try {
				con.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 回滚事务
	public static void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	

	private static DataSource dataSource = null;

	// 数据库连接池应只被初始化一次
	static {
		dataSource = new ComboPooledDataSource("leoshop_c3p0");
	}

	/**
	 * 1.获取连接的方法 通过读取配置文件从数据库服务器获取一个连接
	 */
	public static Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

	/**
	 * 关闭数据库资源，关闭Statement和Connection、RestltSet
	 */
	public static void release(Statement statement, Connection con, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				// 数据库连接池的Connection对象进行close时，并不是真的进行关闭，而是把该数据库连接归还到数据库连接池中。
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
