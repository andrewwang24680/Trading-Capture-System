package com.drill.dao;

import com.drill.entity.User;

import java.util.List;

public interface AdminDao extends UserDao{
    User login(String email, String password);
    User getUserById(Integer id);
    boolean isAdmin(Integer id);
    List<User> getAllUsers();
}
