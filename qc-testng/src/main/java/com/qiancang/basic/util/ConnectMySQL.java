package com.qiancang.basic.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConnectMySQL {
	public static String driver = "com.mysql.jdbc.Driver";
	private static String host;
	private static String user;
	private static String pwd;
	private static Connection conn = null;
	private static Statement stmt = null;

	//连接数据库的基本属性
	public static void connect(String host, String user, String pwd) {
		ConnectMySQL.close();
		ConnectMySQL.host = host;
		ConnectMySQL.user = user;
		ConnectMySQL.pwd = pwd;
	}
	// 查询语句
	public static synchronized List<HashMap<String, String>> query(String sql) {
		return ConnectMySQL.result(sql);
	}

	//关闭数据库
	public static synchronized void close() {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//连接数据库mysql
	private static void connectMySQL() {
		try {
			Class.forName(driver).newInstance();
			conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://" + host + "?useUnicode=true&characterEncoding=UTF8", user, pwd);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//创建statement连接对象
	private static void statement() {
		if (conn == null) {
			ConnectMySQL.connectMySQL();
		}
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//创建statement连接对象的值ResultSet
	private static ResultSet resultSet(String sql) {
		ResultSet rs = null;
		if (stmt == null) {
			ConnectMySQL.statement();
		}
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	//sql语句的塞入
	private static List<HashMap<String, String>> result(String sql) {
		ResultSet rs = ConnectMySQL.resultSet(sql);
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		try {
			ResultSetMetaData md = rs.getMetaData();
			int cc = md.getColumnCount();
			while (rs.next()) {
				HashMap<String, String> columnMap = new HashMap<String, String>();
				for (int i = 1; i <= cc; i++) {
					columnMap.put(md.getColumnName(i), rs.getString(i));
				}
				result.add(columnMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) throws SQLException {
		/**
		 erp-out-172.27.128.6(测试库)
		 129.28.68.45
		 3307
		 test_erp
		 密码:qiancang4321
		 **/
		//查询新增采购订单表的新增的一条订单号为指定订单号的记录
		ConnectMySQL.connect("129.28.68.45:3307/erp", "test_erp", "wangcai");
		List<HashMap<String, String>> rs = ConnectMySQL.query("SELECT * from bill_buy where `code` ='2019049509462720140'");
		System.out.println(rs.size());
		System.out.println(rs.get(0).get("code"));
		System.out.println(rs.get(0).get("supplier_name"));
		ConnectMySQL.close();

	}
}
