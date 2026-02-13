package com.drill.blogapp.servlets;

import com.drill.blogapp.dao.UserDao;
import com.drill.blogapp.dao.impl.UserDaoImpl;
import com.drill.blogapp.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})

public class LoginServlet extends HttpServlet {

    private UserDao userDao;

    public LoginServlet(){
        userDao = new UserDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDao.login(username, password);

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.html?error=true");
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        String targetUrl = (String) session.getAttribute("targetUrl");
        if (targetUrl != null) {
            session.removeAttribute("targetUrl");
            response.sendRedirect(targetUrl);
        } else {
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}
 
