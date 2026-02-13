package com.drill.project.trading.jsppp.controller;

import com.drill.project.trading.jsppp.constant.*;
import com.drill.project.trading.jsppp.dao.OrderDao;
import com.drill.project.trading.jsppp.dao.UserDao;
import com.drill.project.trading.jsppp.dao.impl.OrderDaoImpl;
import com.drill.project.trading.jsppp.dao.impl.UserDaoImpl;
import com.drill.project.trading.jsppp.dto.OrderCreateDto;
import com.drill.project.trading.jsppp.dto.OrderDetailDto;
import com.drill.project.trading.jsppp.dto.UserDto;
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
import java.util.List;

@WebServlet(name = "OrderUpdateServlet", value = "/order/update")
public class OrderUpdateServlet extends HttpServlet {

    private UserService userService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        //DONE
        OrderDao orderDao = new OrderDaoImpl();
        UserDao userDao = new UserDaoImpl();
        this.userService = new UserServiceImpl(userDao);
        this.orderService = new OrderServiceImpl(orderDao, userDao);
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

        //request.setAttribute("statusOptions", OrderStatus.getAll());
        //request.setAttribute("sideOptions", Side.getAll());
        //request.setAttribute("orderTypeOptions", OrderType.getAll());
        //request.setAttribute("priceTypeOptions", PriceType.getAll());
        //request.setAttribute("settleTypeOptions", SettleType.getAll());
        request.setAttribute("currencyOptions", AvailableCurrency.getAll());
        request.setAttribute("instrumentOptions", AvailableInstrument.getAll());

        List<UserDto> users = userService.getUsers();
        request.setAttribute("users", users);

        request.setAttribute("order", order);

        request.getRequestDispatcher("/WEB-INF/order-update.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //DONE
        String orderId = request.getParameter("orderId");
        OrderCreateDto order = OrderCreateDto.builder()
                .clOrderId(request.getParameter("clOrderId"))
                .orderStatus(request.getParameter("orderStatus"))
                .orderQuantity(request.getParameter("quantity"))
                .side(request.getParameter("side"))
                .orderType(request.getParameter("orderType"))
                .price(request.getParameter("price"))
                .priceType(request.getParameter("priceType"))
                .currency(request.getParameter("currency"))
                .instrumentName(request.getParameter("instrument"))
                .settleType(request.getParameter("settleType"))
                .settleDate(request.getParameter("settleDate"))
                .interestedParty(request.getParameter("interestedParty"))
                .userId(Integer.valueOf(request.getParameter("userId")))
                .build();
        UserDto userDto = (UserDto) request.getSession().getAttribute("user");
        boolean isUpdated = orderService.updateOrder(orderId, order, userDto.getUserId());
        if (isUpdated) {
            response.sendRedirect(request.getContextPath() + "/order/list");
        } else {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
