package com.drill.project.trading.jsppp.controller;

import com.drill.project.trading.jsppp.dao.OrderDao;
import com.drill.project.trading.jsppp.dao.UserDao;
import com.drill.project.trading.jsppp.dao.impl.OrderDaoImpl;
import com.drill.project.trading.jsppp.dao.impl.UserDaoImpl;
import com.drill.project.trading.jsppp.dto.UserDto;
import com.drill.project.trading.jsppp.service.OrderService;
import com.drill.project.trading.jsppp.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrderDeleteServlet", value = "/order/delete")
@Slf4j
public class OrderDeleteServlet extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        //DONE
        OrderDao orderDao = new OrderDaoImpl();
        UserDao userDao = new UserDaoImpl();
        this.orderService = new OrderServiceImpl(orderDao, userDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //DONE
        String orderId = request.getParameter("orderId");
        UserDto userDto = (UserDto) request.getSession().getAttribute("user");
        boolean isDeleted = orderService.deleteOrder(orderId, userDto.getUserId());
        if (isDeleted) {
            response.sendRedirect(request.getContextPath() + "/order/list");
        } else {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
