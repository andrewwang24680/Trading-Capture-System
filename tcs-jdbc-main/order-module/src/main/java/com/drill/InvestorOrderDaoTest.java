package com.drill;

import com.drill.dao.InvestorOrderDao;
import com.drill.dao.impl.InvestorOrderDaoImpl;
import com.drill.dto.FindOrderWithCountDto;
import com.drill.entity.Order;
import com.drill.entity.OrderJoinUser;

import java.time.ZonedDateTime;
import java.util.Optional;

public class InvestorOrderDaoTest {
    public static void main(String[] args) {
        InvestorOrderDao investorOrderDao = new InvestorOrderDaoImpl();

        Order newOrder = Order.builder()
                .orderId("ORDER004")
                .clOrderId("CL004")
                .orderStatus(1)
                .orderQuantity("300")
                .side(0)
                .orderType(1)
                .price("250.50")
                .priceType(1)
                .currency("EUR")
                .instrumentName("TSLA")
                .settleType(2)
                .settleDate("2025-01-15")
                .tradeDate("2025-01-10")
                .creationTime(ZonedDateTime.now())
                .interestedParty("Investor Party")
                .userId(2)
                .build();
        String createdOrderId = investorOrderDao.createOrder(newOrder);
        System.out.println("Created Order ID: " + createdOrderId);

        Optional<Order> orderOptional = investorOrderDao.getOrderById(createdOrderId);
        orderOptional.ifPresent(order -> System.out.println("Order Retrieved: " + order));

        investorOrderDao.getOrderByIdJoinUser(createdOrderId).ifPresent(orderJoinUser ->
                System.out.println("Order with User Retrieved: " + orderJoinUser));

        Order updatedOrder = Order.builder()
                .orderId(createdOrderId)
                .clOrderId("CLIENT_INVESTOR_ORDER_001_UPDATED")
                .orderStatus(2)
                .orderQuantity("350")
                .side(1)
                .orderType(0)
                .price("275.75")
                .priceType(2)
                .currency("EUR")
                .instrumentName("TSLA_UPDATED")
                .settleType(1)
                .settleDate("2025-02-01")
                .tradeDate("2025-01-15")
                .creationTime(ZonedDateTime.now())
                .interestedParty("Updated Investor Party")
                .userId(2)
                .build();
        boolean isUpdated = investorOrderDao.updateOrder(createdOrderId, updatedOrder);
        System.out.println("Order Updated: " + isUpdated);

        String existingOrderId = "ORDER002";
        Optional<OrderJoinUser> orderJoinUserOptional = investorOrderDao.getOrderByIdJoinUser(existingOrderId);
        orderJoinUserOptional.ifPresent(orderJoinUser -> {
            System.out.println("Order ID: " + orderJoinUser.getOrderId());
            System.out.println("User ID: " + orderJoinUser.getUser().getUserId());
        });

        if (!orderJoinUserOptional.isPresent()) {
            System.out.println("No order found with ID: " + existingOrderId);
        }


        FindOrderWithCountDto ordersForInvestor = investorOrderDao.findOrdersForInvestor(1, 5, null, 2, null);
        System.out.println("Orders Found: " + ordersForInvestor.getOrders());
        System.out.println("Total Count: " + ordersForInvestor.getCount());

        boolean isDeleted = investorOrderDao.deleteOrder(createdOrderId);
        System.out.println("Order Deleted: " + isDeleted);
    }
}