package com.drill.project.trading.jsppp.controller;

import com.drill.project.trading.jsppp.dao.UserDao;
import com.drill.project.trading.jsppp.dao.impl.UserDaoImpl;
import com.drill.project.trading.jsppp.dto.UserDto;
import com.drill.project.trading.jsppp.service.UserService;
import com.drill.project.trading.jsppp.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        UserDao userDao = new UserDaoImpl();
        this.userService = new UserServiceImpl(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("errorMessage") != null) {
            String errorMessage = (String) session.getAttribute("errorMessage");
            request.setAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }
        request.getRequestDispatcher("/WEB-INF/login.jsp").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDto userDto = userService.loginUser(email, password);
        //DONE: what to do after validate user's credential?
        if (userDto == null) {
            request.getSession().setAttribute("errorMessage", "Wrong credential!");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getSession().setAttribute("user", userDto);
        response.sendRedirect(request.getContextPath());
    }
}
