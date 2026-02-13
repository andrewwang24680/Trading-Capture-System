package com.drill.blogapp.dao;

import com.drill.blogapp.entity.User;

public interface UserDao {
    User login(String username, String password);
    Integer register(String username, String password, String email);
}
