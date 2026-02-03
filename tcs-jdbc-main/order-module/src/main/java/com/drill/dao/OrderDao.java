package com.drill.dao;

import com.drill.constant.OrderStatus;
import com.drill.dto.FindOrderWithCountDto;
import com.drill.entity.Order;
import com.drill.entity.OrderJoinUser;

import java.util.Optional;

public interface OrderDao {
    String createOrder(Order order);
    Optional<Order> getOrderById(String id);
    Optional<OrderJoinUser> getOrderByIdJoinUser(String id);
    boolean updateOrder(String id, Order updatedOrder);
    boolean deleteOrder(String id);
}

