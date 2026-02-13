package com.drill.project.trading.jsppp.service.impl;

import com.drill.project.trading.jsppp.dao.UserDao;
import com.drill.project.trading.jsppp.dto.UserDto;
import com.drill.project.trading.jsppp.entity.Role;
import com.drill.project.trading.jsppp.entity.User;
import com.drill.project.trading.jsppp.service.UserService;
import com.drill.project.trading.jsppp.utils.JDBCUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Integer createUser(String firstName, String lastName, String email, String password) {
        JDBCUtils jdbcUtils = new JDBCUtils();
        try {
            User newUser = User.builder().firstName(firstName).lastName(lastName)
                    .email(email).password(password).build();
            return userDao.createUser(jdbcUtils, newUser);
        } finally {
            jdbcUtils.close();
        }
    }

    @Override
    public UserDto getUserById(Integer id) {
        JDBCUtils jdbcUtils = new JDBCUtils();
        try {
            User user = userDao.getUserById(jdbcUtils, id);
            if (user == null) {
                return null;
            }
            List<Role> roles = userDao.findRolesByUserId(jdbcUtils, id);
            List<String> roleNames = roles.stream()
                    .map(Role::getRoleName)
                    .collect(Collectors.toList());

            return UserDto.builder()
                    .userId(user.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .roles(roleNames)
                    .build();
        } finally {
            jdbcUtils.close();
        }
    }

    @Override
    public boolean updateUser(Integer id, String firstName, String lastName, String email, String password) {
        JDBCUtils jdbcUtils = new JDBCUtils();
        try {
            User updatedUser = User.builder().firstName(firstName).lastName(lastName)
                    .email(email).password(password).build();
            Integer updatedUserId = userDao.updateUser(jdbcUtils, id, updatedUser);
            if (updatedUserId == null) {
                log.error("update user with id {} failed", id);
                return false;
            } else {
                return true;
            }
        } finally {
            jdbcUtils.close();
        }

    }

    @Override
    public UserDto loginUser(String email, String password) {
        JDBCUtils jdbcUtils = new JDBCUtils();
        try {
            User user = userDao.findUserByEmailAndPassword(jdbcUtils, email, password);
            if (user == null) {
                return null;
            }

            List<Role> roles = userDao.findRolesByUserId(jdbcUtils, user.getUserId());
            List<String> roleNames = roles.stream()
                    .map(Role::getRoleName)
                    .collect(Collectors.toList());

            return UserDto.builder()
                    .userId(user.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .roles(roleNames)
                    .build();
        } finally {
            jdbcUtils.close();
        }
    }

    @Override
    public List<UserDto> getUsers() {
        JDBCUtils jdbcUtils = new JDBCUtils();
        try {
            List<User> users = userDao.getAllUsers(jdbcUtils);
            return users.stream()
                    .map(user -> {
                        List<Role> roles = userDao.findRolesByUserId(jdbcUtils, user.getUserId());
                        List<String> roleNames = roles.stream()
                                .map(Role::getRoleName)
                                .collect(Collectors.toList());

                        return UserDto.builder()
                                .userId(user.getUserId())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .email(user.getEmail())
                                .roles(roleNames)
                                .build();
                    })
                    .collect(Collectors.toList());
        } finally {
            jdbcUtils.close();
        }
    }
}
