package com.drill.blogapp.servlets;

import com.drill.blogapp.dao.BlogDao;
import com.drill.blogapp.dao.impl.BlogDaoImpl;
import com.drill.blogapp.entity.Blog;
import com.drill.blogapp.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {

    private BlogDao blogDao;

    public HomeServlet() {
        blogDao = new BlogDaoImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Blog> blogList = blogDao.getBlogs();

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>All Blogs</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>All Blog Posts</h1>");
        out.println("<table border='1'>");
        out.println("<tr>");
        out.println("<th>Title</th>");
        out.println("<th>Content</th>");
        out.println("<th>User</th>");
        out.println("</tr>");

        for (Blog blog : blogList) {
            out.println("<tr>");
            out.println("<td>" + blog.getTitle() + "</td>");
            out.println("<td>" + blog.getContent() + "</td>");
            out.println("<td>" + blog.getAuthor().getUsername() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");

        out.println("<h2>Create New Blog</h2>");
        out.println("<form method='POST' action='home'>");
        out.println("Title: <input type='text' name='title'/><br>");
        out.println("Content: <textarea name='content'></textarea><br>");
        out.println("<input type='submit' value='Create Blog'/>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        User currentUser = (User) request.getSession().getAttribute("user");

        blogDao.createBlog(title, content, currentUser);

        response.sendRedirect(request.getContextPath() + "/home");
    }
}
 
