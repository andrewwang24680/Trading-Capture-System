package com.drill.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<Role> roles;
}