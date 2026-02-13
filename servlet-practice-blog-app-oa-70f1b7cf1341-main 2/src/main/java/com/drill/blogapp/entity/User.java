package com.drill.blogapp.entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
}
