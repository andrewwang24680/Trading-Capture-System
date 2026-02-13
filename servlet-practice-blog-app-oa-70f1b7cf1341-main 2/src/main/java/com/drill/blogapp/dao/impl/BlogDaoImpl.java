package com.drill.blogapp.dao.impl;

import com.drill.blogapp.dao.BlogDao;
import com.drill.blogapp.entity.Blog;
import com.drill.blogapp.entity.User;
import com.drill.blogapp.util.DatabaseConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BlogDaoImpl implements BlogDao {

    @Override
    public Blog createBlog(String title, String content, User author) {

        String sql = "INSERT INTO blogs (title, content, author_id) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setInt(3, author.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int blogId = rs.getInt(1);

                    Blog newBlog = Blog.builder()
                            .id(blogId)
                            .title(title)
                            .content(content)
                            .author(author)
                            .build();

                    log.info("Blog '{}' created successfully with ID {}", title, blogId);
                    return newBlog;
                }
            }

            log.error("Failed to create blog '{}': no rows affected", title);
            return null;

        } catch (SQLException e) {
            log.error("Database error while creating blog '{}'", title, e);
            return null;
        } finally {
            closeResources(rs, pstmt, conn);
        }
    }

    @Override
    public List<Blog> getBlogs() {
        String sql = "SELECT b.id, b.title, b.content, b.created_at, " +
                "u.id AS user_id, u.username, u.email " +
                "FROM blogs b " +
                "INNER JOIN users u ON b.author_id = u.id " +
                "ORDER BY b.created_at DESC";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Blog> blogList = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                User author = User.builder()
                        .id(rs.getInt("user_id"))
                        .username(rs.getString("username"))
                        .email(rs.getString("email"))
                        .build();

                Blog blog = Blog.builder()
                        .id(rs.getInt("id"))
                        .title(rs.getString("title"))
                        .content(rs.getString("content"))
                        .author(author)
                        .build();

                blogList.add(blog);
            }

            log.info("Retrieved {} blogs from database", blogList.size());
            return blogList;

        } catch (SQLException e) {
            log.error("Database error while retrieving blogs", e);
            return new ArrayList<>();
        } finally {
            closeResources(rs, pstmt, conn);
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