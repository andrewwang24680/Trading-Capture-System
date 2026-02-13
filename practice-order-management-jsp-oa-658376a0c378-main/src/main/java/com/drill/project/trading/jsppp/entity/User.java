package com.drill.project.trading.jsppp.entity;

import lombok.*;

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
}
