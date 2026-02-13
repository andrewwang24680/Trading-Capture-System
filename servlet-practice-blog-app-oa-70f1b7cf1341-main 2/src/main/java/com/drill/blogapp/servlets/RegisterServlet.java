package com.drill.blogapp.servlets;

import com.drill.blogapp.dao.UserDao;
import com.drill.blogapp.dao.impl.UserDaoImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})

public class RegisterServlet extends HttpServlet {

    private UserDao userDao;

    public RegisterServlet(){
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
        String email = request.getParameter("email");

        Integer result = userDao.register(username, password, email);

        if (result != null && result > 0) {
            response.sendRedirect(request.getContextPath() + "/login.html");
        } else {
            response.sendRedirect(request.getContextPath() + "/register.html?error=true");
        }

    }
}
 
