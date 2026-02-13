package com.drill.project.trading.jsppp.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserDto {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
}
