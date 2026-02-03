package com.drill.dao;

import com.drill.constant.OrderStatus;
import com.drill.dto.FindOrderWithCountDto;

public interface InvestorOrderDao extends OrderDao {
    FindOrderWithCountDto findOrdersForInvestor(int pageNumber, int pageSize, String orderIdLike, Integer userId, OrderStatus status);
}