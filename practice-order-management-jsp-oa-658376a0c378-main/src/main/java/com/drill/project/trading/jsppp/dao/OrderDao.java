package com.drill.project.trading.jsppp.dao;

import com.drill.project.trading.jsppp.dto.SearchCriteria;
import com.drill.project.trading.jsppp.entity.Order;
import com.drill.project.trading.jsppp.utils.JDBCUtils;

import java.util.List;

public interface OrderDao {
    String createOrder(JDBCUtils jdbcUtils, Order order);
    Order getOrderById(JDBCUtils jdbcUtils, String id);

    String updateOrder(JDBCUtils jdbcUtils, String id, Order updatedOrder);
    String deleteOrder(JDBCUtils jdbcUtils, String id);

    List<Order> findOrders(JDBCUtils jdbcUtils, SearchCriteria searchCriteria, Integer userId);
    int countOrders(JDBCUtils jdbcUtils, SearchCriteria searchCriteria, Integer userId);
}
