package com.drill.project.trading.jsppp.dao.impl;

import com.drill.project.trading.jsppp.constant.RoleSchema;
import com.drill.project.trading.jsppp.constant.UserRoleSchema;
import com.drill.project.trading.jsppp.constant.UserSchema;
import com.drill.project.trading.jsppp.dao.UserDao;
import com.drill.project.trading.jsppp.entity.Role;
import com.drill.project.trading.jsppp.entity.User;
import com.drill.project.trading.jsppp.utils.JDBCUtils;
import com.drill.project.trading.jsppp.utils.SQLGenerator;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserDaoImpl implements UserDao {

    @Override
    public Integer createUser(JDBCUtils jdbcUtils, User user) {
        String sql = SQLGenerator.buildInsertSql(UserSchema.TABLE_NAME,
                UserSchema.FIRST_NAME, UserSchema.LAST_NAME,
                UserSchema.EMAIL, UserSchema.PASSWORD);

        log.info("create user {}", user.getEmail());
        return jdbcUtils.executeUpdateWithGeneratedIntId(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

    @Override
    public User getUserById(JDBCUtils jdbcUtils, Integer id) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", UserSchema.TABLE_NAME, UserSchema.ID);
        try (ResultSet rs = jdbcUtils.executeQuery(sql, id)) {
            if (rs.next()) {
                return convertResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("fail to fetch user by id due to {}", e.getMessage());
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
    public List<Role> findRolesByUserId(JDBCUtils jdbcUtils, Integer userId) {
        String sql = String.format("SELECT r.* FROM %s ur INNER JOIN %s r ON r.role_id = ur.role_id WHERE %s = ?",
                UserRoleSchema.TABLE_NAME, RoleSchema.TABLE_NAME,
                UserRoleSchema.USER_ID);
        List<Role> roles = new ArrayList<>();
        try (ResultSet rs = jdbcUtils.executeQuery(sql, userId)) {
            while (rs.next()) {
                roles.add(convertRoleResultSet(rs));
            }
        } catch (SQLException e) {
            log.error("fail to fetch roles by userId due to {}", e.getMessage());
        }
        return roles;
    }

    private Role convertRoleResultSet(ResultSet rs) throws SQLException {
        Integer roleId = rs.getInt(RoleSchema.ID);
        String roleName = rs.getString(RoleSchema.ROLE_NAME);

        return Role.builder()
                .id(roleId)
                .roleName(roleName)
                .build();
    }

    @Override
    public User findUserByEmailAndPassword(JDBCUtils jdbcUtils, String email, String password) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ? and %s = ?", UserSchema.TABLE_NAME, UserSchema.EMAIL, UserSchema.PASSWORD);
        try (ResultSet rs = jdbcUtils.executeQuery(sql, email, password)) {
            if (rs.next()) {
                return convertResultSet(rs);
            }
            log.info("no data hit on given credential");
        } catch (SQLException e) {
            log.error("fail to find user by email and pwd due to {}", e.getMessage());
        }

        return null;
    }

    @Override
    public Integer updateUser(JDBCUtils jdbcUtils, Integer id, User updatedUser) {
        String sql = SQLGenerator.buildUpdateSql(UserSchema.TABLE_NAME, UserSchema.ID,
                UserSchema.FIRST_NAME, UserSchema.LAST_NAME,
                UserSchema.EMAIL, UserSchema.PASSWORD);

        int rowsUpdated = jdbcUtils.executeUpdate(sql,
                updatedUser.getFirstName(), updatedUser.getLastName(),
                updatedUser.getEmail(), updatedUser.getPassword(), id);
        if (rowsUpdated == 0) {
            log.error("fail to update user since not able to locate user with id {}", id);
            return null;
        }
        log.info("successfully update user {}", id);
        return id;
    }

    @Override
    public List<User> getAllUsers(JDBCUtils jdbcUtils) {
        String sql = String.format("SELECT * FROM %s", UserSchema.TABLE_NAME);
        List<User> users = new ArrayList<>();
        try (ResultSet rs = jdbcUtils.executeQuery(sql)) {
            while (rs.next()) {
                users.add(convertResultSet(rs));
            }
        } catch (SQLException e) {
            log.error("fail to fetch user by id due to {}", e.getMessage());
        }

        return users;
    }
}
