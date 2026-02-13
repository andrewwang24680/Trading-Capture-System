package com.drill.blogapp.dao.impl;

import com.drill.blogapp.dao.UserDao;
import com.drill.blogapp.entity.User;
import com.drill.blogapp.util.DatabaseConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class UserDaoImpl implements UserDao {

    @Override
    public User login(String username, String password) {
        String sql = "SELECT id, username, password, email, created_at FROM users WHERE username = ? AND password = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = User.builder()
                        .id(rs.getInt("id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .email(rs.getString("email"))
                        .build();
                log.info("user {} logged in successfully", username);
                return user;
            } else {
                log.warn("Login failed for user {}: invalid credentials", username);
                return null;
            }
        } catch (SQLException e) {
            log.warn("Database error during login for user {}", username, e);
            return null;
        } finally {
            closeResources(rs, pstmt, conn);
        }
    }

    @Override
    public Integer register(String username, String password, String email) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    log.info("User {} registered successfully with ID {}", username, userId);
                    return userId;
                }
            }

            log.error("Failed to register user {}: no rows affected", username);
            return -1;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {  // MySQL 错误码 1062 = Duplicate entry
                log.error("Failed to register user {}: username already exists", username);
            } else {
                log.error("Database error during registration for user {}", username, e);
            }
            return -1;
        } finally {
            closeResources (rs, pstmt, conn);
        }
    }
    private void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("Error closing ResultSet", e);
            }
        }

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.error("Error closing PreparedStatement", e);
            }
        }

        if (conn != null) {
            DatabaseConnection.closeConnection(conn);
        }
    }
}
