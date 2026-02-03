package com.drill.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserCreationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Integer> roleIds;
}

