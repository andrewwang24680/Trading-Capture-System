package com.drill.dao.impl;

import com.drill.constant.RoleSchema;
import com.drill.constant.UserSchema;
import com.drill.dao.InvestorDao;
import com.drill.dto.UserCreationDto;
import com.drill.entity.Role;
import com.drill.entity.User;
import com.drill.utils.JDBCUtils;
import com.drill.utils.SQLGenerator;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class InvestorDaoImpl implements InvestorDao {

    @Override
    public Integer createUser(UserCreationDto user) {
        Integer investorRoleId = getInvestorRoleId();

        String userInsertSql = SQLGenerator.buildInsertSql(UserSchema.TABLE_NAME,
                UserSchema.FIRST_NAME, UserSchema.LAST_NAME,
                UserSchema.EMAIL, UserSchema.PASSWORD);

        String userRoleInsertSql = "INSERT INTO UserRole (user_id, role_id) VALUES (?, ?)";

        JDBCUtils jdbcUtils = new JDBCUtils();
        log.info("Creating user with email: {}", user.getEmail());

        Integer userId = jdbcUtils.executeUpdateWithGeneratedIntId(
                userInsertSql,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );

        jdbcUtils.executeUpdate(userRoleInsertSql, userId, investorRoleId);

        return userId;
    }

    private Integer getInvestorRoleId() {
        String sql = "SELECT role_id FROM Role WHERE role_name = 'Investor'";
        JDBCUtils jdbcUtils = new JDBCUtils();
        try (ResultSet rs = jdbcUtils.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("role_id");
            }
        } catch (SQLException e) {
            log.error("Failed to fetch Investor role ID: {}", e.getMessage(), e);
        } finally {
            jdbcUtils.close();
        }
        throw new RuntimeException("Investor role not found in Role table.");
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
        String sql = String.format("SELECT * FROM %s WHERE %s = ? and %s = ?", UserSchema.TABLE_NAME, UserSchema.EMAIL, UserSchema.PASSWORD);
        try (ResultSet rs = jdbcUtils.executeQuery(sql, email, password)) {
            if (rs.next()) {
                return convertResultSet(rs);
            }
            log.info("no data hit on given credential");
        } catch (SQLException e) {
            log.error("fail to find user by email and pwd due to {}", e.getMessage());
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
}
