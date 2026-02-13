package com.drill.project.trading.jsppp.controller;

import com.drill.project.trading.jsppp.constant.AvailableInstrument;
import com.drill.project.trading.jsppp.constant.OrderStatus;
import com.drill.project.trading.jsppp.constant.Side;
import com.drill.project.trading.jsppp.dao.OrderDao;
import com.drill.project.trading.jsppp.dao.UserDao;
import com.drill.project.trading.jsppp.dao.impl.OrderDaoImpl;
import com.drill.project.trading.jsppp.dao.impl.UserDaoImpl;
import com.drill.project.trading.jsppp.dto.OrderSearchDto;
import com.drill.project.trading.jsppp.dto.SearchCriteria;
import com.drill.project.trading.jsppp.dto.UserDto;
import com.drill.project.trading.jsppp.service.OrderService;
import com.drill.project.trading.jsppp.service.impl.OrderServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "OrderListServlet", value = "/order/list")
public class OrderListServlet extends HttpServlet {

    private OrderService orderService;
    private List<String> validStatus;
    private List<Integer> pageSizes;

    public OrderListServlet() {
        //DONE
        OrderDao orderDao = new OrderDaoImpl();
        UserDao userDao = new UserDaoImpl();
        this.orderService = new OrderServiceImpl(orderDao, userDao);
        pageSizes = Arrays.asList(10, 50, 100);
        validStatus = OrderStatus.getAll();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Done
        //1. process request (fetch input)
        /*
            orderId (search)
            status
            pagination:pageNum, pageSize
         */
        String orderIdContains = "", status = "";
        Integer pageSize = 10, pageNum = 1;

        if (request.getParameter("search") != null) {
            orderIdContains = request.getParameter("search");
        }

        if (request.getParameter("status") != null) {
            status = request.getParameter("status");
        }

        if (request.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }

        if (request.getParameter("pageNum") != null) {
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        }

        String sideParam = null;
        if (request.getParameter("side") != null && !request.getParameter("side").trim().isEmpty()) {
            sideParam = request.getParameter("side");
        }

        String instrumentName = null;
        if (request.getParameter("instrument") != null && !request.getParameter("instrument").trim().isEmpty()) {
            instrumentName = request.getParameter("instrument");
        }

        String settleDateFrom = null;
        if (request.getParameter("settleDateFrom") != null && !request.getParameter("settleDateFrom").trim().isEmpty()) {
            settleDateFrom = request.getParameter("settleDateFrom");
        }

        String settleDateTo = null;
        if (request.getParameter("settleDateTo") != null && !request.getParameter("settleDateTo").trim().isEmpty()) {
            settleDateTo = request.getParameter("settleDateTo");
        }

        String sortBy = request.getParameter("sortBy");
        if (sortBy == null || sortBy.trim().isEmpty()) {
            sortBy = "creation_time";  // 默认按创建时间排序
        }

        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder == null || sortOrder.trim().isEmpty()) {
            sortOrder = "desc";  // 默认降序
        }

        //2. provide data to view
        /*
          totalNum of orders
          totalPages
          order_status_list
          page_size_list
          pageNum
          pageSize
         */
        SearchCriteria searchCriteria = SearchCriteria.builder()
                .pageNumber(pageNum)
                .pageSize(pageSize)
                .orderIdLike(orderIdContains)
                .status(OrderStatus.getFromValue(status))
                .side(Side.getFromValue(sideParam))
                .instrumentName(instrumentName)
                .settleDateFrom(settleDateFrom)
                .settleDateTo(settleDateTo)
                .sortBy(sortBy)
                .sortOrder(sortOrder)
                .build();

        UserDto userDto = (UserDto)request.getSession().getAttribute("user");
        OrderSearchDto result = orderService.findOrders(searchCriteria, userDto.getUserId());

        //request scope (server-side data) OR query param (related to current user input)
        //totalPages?
        //totalCount/pageSize
        int totalPages = (int)Math.ceil(result.getTotalFound()/(pageSize*1.0));
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("orders", result.getOrders());
        request.setAttribute("pageSizes", pageSizes);
        request.setAttribute("statusOptions", validStatus);
        request.setAttribute("sideOptions", Side.getAll());
        request.setAttribute("instrumentOptions", AvailableInstrument.getAll());

        //3. specify which view should be responsible to render the data
        request.getRequestDispatcher("/WEB-INF/order-list.jsp?pageNum=" + pageNum + "&pageSize=" + pageSize).include(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
