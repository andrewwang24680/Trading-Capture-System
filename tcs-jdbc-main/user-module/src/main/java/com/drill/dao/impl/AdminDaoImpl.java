package com.drill.dao.impl;

import com.drill.constant.RoleSchema;
import com.drill.constant.UserSchema;
import com.drill.dao.AdminDao;
import com.drill.dto.UserCreationDto;
import com.drill.entity.Role;
import com.drill.entity.User;
import com.drill.utils.JDBCUtils;
import com.drill.utils.SQLGenerator;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AdminDaoImpl implements AdminDao {

    @Override
    public Integer createUser(UserCreationDto userDto) {
        if (userDto.getRoleIds() == null || userDto.getRoleIds().isEmpty()) {
            throw new IllegalArgumentException("At least one role must be specified.");
        }

        String insertUserSql = "INSERT INTO User (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
        JDBCUtils jdbcUtils = new JDBCUtils();

        Integer userId = jdbcUtils.executeUpdateWithGeneratedIntId(
                insertUserSql,
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword()
        );

        String insertUserRoleSql = "INSERT INTO UserRole (user_id, role_id) VALUES (?, ?)";
        for (Integer roleId : userDto.getRoleIds()) {
            jdbcUtils.executeUpdate(insertUserRoleSql, userId, roleId);
        }

        return userId;
    }

    @Override
    public User getUserById(Integer id) {
        String sql = "SELECT u.*, r.role_id, r.role_name " +
                "FROM " + UserSchema.TABLE_NAME + " u " +
                "LEFT JOIN UserRole ur ON u." + UserSchema.ID + " = ur.user_id " +
                "LEFT JOIN " + RoleSchema.TABLE_NAME + " r ON ur.role_id = r.role_id " +
                "WHERE u." + UserSchema.ID + " = ?";

        JDBCUtils jdbcUtils = new JDBCUtils();
        ResultSet rs = null;
        try {
            rs = jdbcUtils.executeQuery(sql, id);
            User user = null;
            List<Role> roles = new ArrayList<>();
            while (rs.next()) {
                if (user == null) {
                    // 初始化 User 对象
                    user = convertResultSet(rs);
                }
                // 添加 Role 到角色列表
                Role role = convertRoleResultSet(rs);
                if (role != null) {
                    roles.add(role);
                }
            }
            if (user != null) {
                user.setRoles(roles);
            }
            return user;
        } catch (SQLException e) {
            log.error("Failed to fetch user by id: {}", e.getMessage(), e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.warn("Failed to close ResultSet: {}", e.getMessage(), e);
                }
            }
            jdbcUtils.close();
        }
        return null;
    }

    private Role convertRoleResultSet(ResultSet rs) throws SQLException {
        int roleId = rs.getInt("role_id");
        String roleName = rs.getString("role_name");
        if (roleId > 0 && roleName != null) {
            return Role.builder()
                    .id(roleId)
                    .roleName(roleName)
                    .build();
        }
        return null;
    }

    private User convertResultSet(ResultSet rs) throws SQLException {
        return User.builder()
                .userId(rs.getInt(UserSchema.ID))
                .firstName(rs.getString(UserSchema.FIRST_NAME))
                .lastName(rs.getString(UserSchema.LAST_NAME))
                .password(rs.getString(UserSchema.PASSWORD))
                .email(rs.getString(UserSchema.EMAIL))
                .build();
    }

    @Override
    public User login(String email, String password) {
        JDBCUtils jdbcUtils = new JDBCUtils();
        String sql = "SELECT u.*, r.role_id, r.role_name " +
                "FROM " + UserSchema.TABLE_NAME + " u " +
                "LEFT JOIN UserRole ur ON u." + UserSchema.ID + " = ur.user_id " +
                "LEFT JOIN " + RoleSchema.TABLE_NAME + " r ON ur.role_id = r.role_id " +
                "WHERE u." + UserSchema.EMAIL + " = ? AND u." + UserSchema.PASSWORD + " = ?";

        try (ResultSet rs = jdbcUtils.executeQuery(sql, email, password)) {
            User user = null;
            List<Role> roles = new ArrayList<>();

            while (rs.next()) {
                if (user == null) {
                    user = convertResultSet(rs);
                }
                Role role = convertRoleResultSet(rs);
                if (role != null) {
                    roles.add(role);
                }
            }

            if (user != null) {
                user.setRoles(roles);

                // 验证用户是否是 Admin
                boolean isAdmin = roles.stream()
                        .anyMatch(role -> "admin".equalsIgnoreCase(role.getRoleName()));

                if (!isAdmin) {
                    log.warn("Login attempt by non-admin user: {}", email);
                    return null; // 非管理员用户不能登录
                }
            }

            return user;
        } catch (SQLException e) {
            log.error("Failed to find user by email and password: {}", e.getMessage(), e);
        } finally {
            jdbcUtils.close();
        }

        return null;
    }

    @Override
    public boolean updateUser(Integer id, User updatedUser) {
        JDBCUtils jdbcUtils = new JDBCUtils();
        String sql = SQLGenerator.buildUpdateSql(UserSchema.TABLE_NAME, UserSchema.ID,
                UserSchema.FIRST_NAME, UserSchema.LAST_NAME,
                UserSchema.EMAIL, UserSchema.PASSWORD);

        try {
            int rowsUpdated = jdbcUtils.executeUpdate(sql,
                    updatedUser.getFirstName(), updatedUser.getLastName(),
                    updatedUser.getEmail(), updatedUser.getPassword(), id);
            if (rowsUpdated < 0) {
                log.error("fail to update user since not able to locate user with id {}", id);
                return false;
            }
            log.info("successfully update user {}", id);
            return true;
        } finally {
            jdbcUtils.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        JDBCUtils jdbcUtils = new JDBCUtils();
        String sql = "SELECT u.*, r.role_id, r.role_name " +
                "FROM " + UserSchema.TABLE_NAME + " u " +
                "LEFT JOIN UserRole ur ON u." + UserSchema.ID + " = ur.user_id " +
                "LEFT JOIN " + RoleSchema.TABLE_NAME + " r ON ur.role_id = r.role_id";
        List<User> users = new ArrayList<>();

        try (ResultSet rs = jdbcUtils.executeQuery(sql)) {
            User currentUser = null;
            List<Role> roles = null;

            while (rs.next()) {
                int userId = rs.getInt(UserSchema.ID);
                if (currentUser == null || currentUser.getUserId() != userId) {
                    if (currentUser != null) {
                        currentUser.setRoles(roles);
                        users.add(currentUser);
                    }
                    currentUser = convertResultSet(rs);
                    roles = new ArrayList<>();
                }
                Role role = convertRoleResultSet(rs);
                if (role != null) {
                    roles.add(role);
                }
            }

            if (currentUser != null) {
                currentUser.setRoles(roles);
                users.add(currentUser);
            }
        } catch (SQLException e) {
            log.error("Failed to fetch all users: {}", e.getMessage(), e);
        } finally {
            jdbcUtils.close();
        }

        return users;
    }

    @Override
    public boolean isAdmin(Integer userId) {
        String sql = "SELECT r.role_name " +
                "FROM UserRole ur " +
                "JOIN Role r ON ur.role_id = r.role_id " +
                "WHERE ur.user_id = ?";

        JDBCUtils jdbcUtils = new JDBCUtils();
        ResultSet rs = null;
        try {
            rs = jdbcUtils.executeQuery(sql, userId);
            while (rs.next()) {
                String roleName = rs.getString("role_name");
                if ("admin".equalsIgnoreCase(roleName)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            log.error("Failed to check if user is admin: {}", e.getMessage(), e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.warn("Failed to close ResultSet: {}", e.getMessage(), e);
                }
            }
            jdbcUtils.close();
        }
        return false;
    }
}

