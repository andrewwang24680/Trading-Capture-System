package com.drill.blogapp.entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Blog {
    private Integer id;
    private String title;
    private String content;
    private User author;
}
