package com.drill.project.trading.jsppp.service;

import com.drill.project.trading.jsppp.dto.UserDto;
import com.drill.project.trading.jsppp.entity.User;

import java.util.List;

public interface UserService {
    Integer createUser(String firstName, String lastName, String email, String password);
    UserDto getUserById(Integer id);
    boolean updateUser(Integer id, String firstName, String lastName, String email, String password);
    UserDto loginUser(String email, String password);
    List<UserDto> getUsers();
}
