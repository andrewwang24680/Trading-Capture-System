package com.drill.project.trading.jsppp.dao;

import com.drill.project.trading.jsppp.entity.Role;
import com.drill.project.trading.jsppp.entity.User;
import com.drill.project.trading.jsppp.utils.JDBCUtils;

import java.util.List;

public interface UserDao {
    Integer createUser(JDBCUtils jdbcUtils, User user);
    User getUserById(JDBCUtils jdbcUtils, Integer id);
    User findUserByEmailAndPassword(JDBCUtils jdbcUtils, String email, String password);
    List<Role> findRolesByUserId(JDBCUtils jdbcUtils, Integer userId);
    Integer updateUser(JDBCUtils jdbcUtils, Integer id, User updatedUser);
    List<User> getAllUsers(JDBCUtils jdbcUtils);
}
