package com.ruixun.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JDBCUitls {
	private Connection conn;
	private Statement st;
	private PreparedStatement pps;
	private ResultSet rs;
	public String url;
	private String user;
	private String password;

	public JDBCUitls(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			// 注意链接时，要换成自己的数据库名，数据库用户名及密码
			Connection con = DriverManager.getConnection(this.url, this.user,
					this.password);
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 统计总数
	 * 
	 * @param sql
	 */
	// TODO 待修改 局限性太大 不能通用
	public int queryCount(String sql) {
		int number = 0;
		try {
			if (conn == null || conn.isClosed()) {
				conn = getConnection();
			}
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			while (rs.next()) {
				number = rs.getInt("COUNT");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return number;
	}

	/**
	 * 查询
	 * 
	 * @param sql
	 *            sql语句
	 * @return 返回列表结果集
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryToList(String sql) throws SQLException {
		
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		if (conn == null || conn.isClosed()) {
			conn = getConnection();
		}
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columncount = rsmd.getColumnCount();
		while (rs.next()) {
			HashMap<String, Object> onerow = new HashMap<String, Object>();
			for (int i = 0; i < columncount; i++) {
				String columnName = rsmd.getColumnName(i + 1);
				onerow.put(columnName, rs.getObject(i + 1));
			}
			list.add(onerow);
		}
		close();
		return list;
	}

	/**
	 * 关闭连接
	 */
	private void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pps != null) {
			try {
				pps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			if (conn != null && !conn.isClosed()) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
