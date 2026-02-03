package com.drill.dao;

import com.drill.dto.UserCreationDto;
import com.drill.entity.User;

import java.util.List;

public interface UserDao {
    Integer createUser(UserCreationDto user);
    boolean updateUser(Integer id, User updatedUser);
}
