package com.drill.project.trading.jsppp.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class JDBCUtils {

	public Connection conn;
	public PreparedStatement stmt;
	public ResultSet rs;

	public JDBCUtils() {
		getConnection();
	}

	public void getConnection() {
		try {
			Class.forName(DBInfo.JDBC_DRIVER);
			conn = DriverManager.getConnection(DBInfo.DB_URL, DBInfo.USER, DBInfo.PASSWORD);
			log.info("create connection with {}", DBInfo.DB_URL);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			log.error("fail to connect db due to {}", e.getMessage());
		}

	}

	public ResultSet executeQuery(String sql, Object... obj) {
		try {
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				stmt.setObject(i + 1, obj[i]);
			}
			rs = stmt.executeQuery();

		} catch (SQLException e) {
			log.error("fail to fetch rows from DB due to {}", e.getMessage());
		}
		return rs;
	}

	public int executeUpdate(String sql, Object... obj) {
		int num = 0;
		try {
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				stmt.setObject(i + 1, obj[i]);
			}
			num = stmt.executeUpdate();
		} catch (SQLException e) {
			log.error("fail to update rows in DB due to {}", e.getMessage());
		}
		return num;
	}
	//return generated row id
	public int executeUpdateWithGeneratedIntId(String sql, Object... obj) {
		try {
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < obj.length; i++) {
				stmt.setObject(i + 1, obj[i]);
			}
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				log.info("successfully insert a row in DB");
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error("failed to create data in DB due to {}", e.getMessage());
		}

		return -1;
	}

	public int executeUpdateWithSpecifiedStrId(String sql, Object... obj) {
		try {
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < obj.length; i++) {
				stmt.setObject(i + 1, obj[i]);
			}
			return stmt.executeUpdate();
		} catch (SQLException e) {
			log.error("failed to create data in DB due to {}", e.getMessage());
		}

		return 0;
	}

	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("failed to close result set due to {} ", e.getMessage());
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error("failed to close statement due to {} ", e.getMessage());
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("failed to close connection due to {} ", e.getMessage());
			}
		}
		log.info("close connection with {}", DBInfo.DB_URL);
	}

}