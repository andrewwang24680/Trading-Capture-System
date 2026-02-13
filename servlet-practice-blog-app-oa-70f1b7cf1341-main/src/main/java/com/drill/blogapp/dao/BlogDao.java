package com.drill.blogapp.dao;

import com.drill.blogapp.entity.Blog;
import com.drill.blogapp.entity.User;

import java.util.List;

public interface BlogDao {
    Blog createBlog(String title, String content, User author);
    List<Blog> getBlogs();
}
