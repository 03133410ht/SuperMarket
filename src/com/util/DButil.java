package com.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 数据库工具类
 * 
 * @author Goddard
 *
 */
public class DButil {
	private static Logger log = Logger.getLogger(DButil.class);

	private DButil() {

	}

	private static String url;
	private static String user;
	private static String password;
	private static String driver;

	/**
	 * 静态读取文件
	 */
	static {
		Properties pro = new Properties();
		try {
			pro.load(DButil.class.getClassLoader().getResourceAsStream("conn.properties"));
			url = pro.getProperty("url");
			user = pro.getProperty("username");
			password = pro.getProperty("password");
			driver = pro.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} 
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		return conn;
	}

	/**
	 * 获取Statement
	 * 
	 * @param conn
	 *            Connection连接
	 * @return Statement
	 */
	public static Statement getStatement(Connection conn) {
		Statement stmt = null;
		if (conn != null) {
			try {
				stmt = conn.createStatement();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
		return stmt;
	}

	/**
	 * 获取 PreparedStatement
	 * 
	 * @param conn
	 *            Connection连接
	 * @param sql
	 *            sql语句
	 * @return PreParedStatement
	 */
	public static PreparedStatement getPrePareStatement(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		if (conn != null) {
			try {
				pstmt = conn.prepareStatement(sql);
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
		return pstmt;
	}

	/**
	 * 动态绑定参数
	 * 
	 * @param pstmt
	 *            PreparedStatement
	 * @param args
	 *            参数
	 */
	public static void bindParam(PreparedStatement pstmt, Object... args) {
		try {
			for (int i = 0; i < args.length; i++) {
				pstmt.setObject(i + 1, args[i]);
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 释放资源
	 * 
	 * @param conn
	 *            Connection 连接
	 * @param rs
	 *            ResultSet 结果集
	 * @param stmt
	 *            Statement
	 */

	public static void closeAll(Connection conn, ResultSet rs, Statement stmt) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}

}
