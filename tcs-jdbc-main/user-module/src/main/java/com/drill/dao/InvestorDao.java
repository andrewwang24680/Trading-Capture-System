package com.drill.dao;

import com.drill.entity.User;

public interface InvestorDao extends UserDao{
    User login(String email, String password);
}
