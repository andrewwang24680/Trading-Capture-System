package com.drill.dao;

import com.drill.constant.OrderStatus;
import com.drill.dto.FindOrderWithCountDto;

public interface AdminOrderDao extends OrderDao {
    FindOrderWithCountDto findOrdersForAdmin(int pageNumber, int pageSize, String orderIdLike, OrderStatus status);
}
