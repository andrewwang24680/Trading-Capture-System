package com.drill;

import com.drill.dao.AdminOrderDao;
import com.drill.dao.impl.AdminOrderDaoImpl;
import com.drill.dto.FindOrderWithCountDto;
import com.drill.entity.Order;
import com.drill.constant.OrderStatus;

import java.time.ZonedDateTime;
import java.util.Optional;

public class AdminOrderDaoTest {
    public static void main(String[] args) {
        AdminOrderDao adminOrderDao = new AdminOrderDaoImpl();
        
        Order newOrder = Order.builder()
                .orderId("ADMIN_ORDER_001")
                .clOrderId("CLIENT_ORDER_001")
                .orderStatus(1)
                .orderQuantity("500")
                .side(1)
                .orderType(0)
                .price("300.75")
                .priceType(1)
                .currency("USD")
                .instrumentName("MSFT")
                .settleType(1)
                .settleDate("2024-12-31")
                .tradeDate("2024-12-01")
                .creationTime(ZonedDateTime.now())
                .interestedParty("Admin Party")
                .userId(1)
                .build();
        String createdOrderId = adminOrderDao.createOrder(newOrder);
        System.out.println("Created Order ID: " + createdOrderId);

        Optional<Order> orderOptional = adminOrderDao.getOrderById(createdOrderId);
        orderOptional.ifPresent(order -> System.out.println("Order Retrieved: " + order));

        Order updatedOrder = Order.builder()
                .orderId(createdOrderId)
                .clOrderId("CLIENT_ORDER_001_UPDATED")
                .orderStatus(2)
                .orderQuantity("600")
                .side(0)
                .orderType(1)
                .price("320.00")
                .priceType(2)
                .currency("USD")
                .instrumentName("MSFT_UPDATED")
                .settleType(2)
                .settleDate("2025-01-15")
                .tradeDate("2025-01-01")
                .creationTime(ZonedDateTime.now())
                .interestedParty("Updated Party")
                .userId(1)
                .build();
        boolean isUpdated = adminOrderDao.updateOrder(createdOrderId, updatedOrder);
        System.out.println("Order Updated: " + isUpdated);

        FindOrderWithCountDto ordersForAdmin = adminOrderDao.findOrdersForAdmin(1, 5, null, OrderStatus.PARTIALLY_FILLED);
        System.out.println("Orders Found: " + ordersForAdmin.getOrders());
        System.out.println("Total Count: " + ordersForAdmin.getCount());

        boolean isDeleted = adminOrderDao.deleteOrder(createdOrderId);
        System.out.println("Order Deleted: " + isDeleted);
    }
}
