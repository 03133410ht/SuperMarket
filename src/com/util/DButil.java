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
 * ���ݿ⹤����
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
	 * ��̬��ȡ�ļ�
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
	 * ��ȡ���ݿ�����
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
	 * ��ȡStatement
	 * 
	 * @param conn
	 *            Connection����
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
	 * ��ȡ PreparedStatement
	 * 
	 * @param conn
	 *            Connection����
	 * @param sql
	 *            sql���
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
	 * ��̬�󶨲���
	 * 
	 * @param pstmt
	 *            PreparedStatement
	 * @param args
	 *            ����
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
	 * �ͷ���Դ
	 * 
	 * @param conn
	 *            Connection ����
	 * @param rs
	 *            ResultSet �����
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
