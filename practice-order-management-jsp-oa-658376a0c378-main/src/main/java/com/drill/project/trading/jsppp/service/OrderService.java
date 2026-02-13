package com.drill.project.trading.jsppp.service;

import com.drill.project.trading.jsppp.dto.*;

import java.util.List;

public interface OrderService {
    String createOrder(OrderCreateDto createDto);
    OrderDetailDto getOrderById(String id, Integer userId);
    boolean updateOrder(String id, OrderCreateDto updatedDto, Integer userId);
    boolean deleteOrder(String id, Integer userId);
    OrderSearchDto findOrders(SearchCriteria searchCriteria, Integer userId);
}
