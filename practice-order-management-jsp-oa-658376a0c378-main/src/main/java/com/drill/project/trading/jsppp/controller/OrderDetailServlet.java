package com.drill.project.trading.jsppp.controller;

import com.drill.project.trading.jsppp.dao.OrderDao;
import com.drill.project.trading.jsppp.dao.UserDao;
import com.drill.project.trading.jsppp.dao.impl.OrderDaoImpl;
import com.drill.project.trading.jsppp.dao.impl.UserDaoImpl;
import com.drill.project.trading.jsppp.dto.OrderDetailDto;
import com.drill.project.trading.jsppp.dto.UserDto;
import com.drill.project.trading.jsppp.entity.Order;
import com.drill.project.trading.jsppp.service.OrderService;
import com.drill.project.trading.jsppp.service.UserService;
import com.drill.project.trading.jsppp.service.impl.OrderServiceImpl;
import com.drill.project.trading.jsppp.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrderDetailServlet", value = "/order/view")
public class OrderDetailServlet extends HttpServlet {

    private OrderService orderService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        //DONE
        OrderDao orderDao = new OrderDaoImpl();
        UserDao userDao = new UserDaoImpl();
        this.orderService = new OrderServiceImpl(orderDao, userDao);
        this.userService = new UserServiceImpl(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //DONE
        String orderId = request.getParameter("orderId");
        UserDto userDto = (UserDto) request.getSession().getAttribute("user");
        OrderDetailDto order = orderService.getOrderById(orderId, userDto.getUserId());
        if (order == null) {
            response.sendRedirect(request.getContextPath() + "/error");
            return;
        }
        UserDto orderOwner = userService.getUserById(order.getUserId());
        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/order-view.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
