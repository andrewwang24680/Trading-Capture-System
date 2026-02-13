package com.drill.project.trading.jsppp.service.impl;

import com.drill.project.trading.jsppp.constant.*;
import com.drill.project.trading.jsppp.dao.OrderDao;
import com.drill.project.trading.jsppp.dao.UserDao;
import com.drill.project.trading.jsppp.dto.*;
import com.drill.project.trading.jsppp.entity.Order;
import com.drill.project.trading.jsppp.entity.Role;
import com.drill.project.trading.jsppp.entity.User;
import com.drill.project.trading.jsppp.service.OrderService;
import com.drill.project.trading.jsppp.utils.JDBCUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private UserDao userDao;

    public OrderServiceImpl(OrderDao orderDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
    }

    private OrderListDto convertOrderToListDto(Order order) {
        return OrderListDto.builder()
                .orderId(order.getOrderId())
                .status(OrderStatus.getFromDbValue(order.getOrderStatus()).getValue())
                .quantity(order.getOrderQuantity())
                .side(Side.getFromDbValue(order.getSide()).getValue())
                .price(order.getPrice())
                .instrumentName(order.getInstrumentName())
                .settleDate(order.getSettleDate())
                .systemCreationTime(order.getCreationTime())
                .build();
    }

    private OrderDetailDto convertOrderToDetailDto(Order order) {
        return OrderDetailDto.builder()
                .orderId(order.getOrderId())
                .clOrderId(order.getClOrderId())
                .orderStatus(OrderStatus.getFromDbValue(order.getOrderStatus()).getValue())
                .orderQuantity(order.getOrderQuantity())
                .side(Side.getFromDbValue(order.getSide()).getValue())
                .orderType(OrderType.getFromDbValue(order.getOrderType()).getValue())
                .price(order.getPrice())
                .priceType(PriceType.getFromDbValue(order.getPriceType()).getValue())
                .currency(order.getCurrency())
                .instrumentName(order.getInstrumentName())
                .settleType(SettleType.getFromDbValue(order.getSettleType()).getValue())
                .settleDate(order.getSettleDate())
                .tradeDate(order.getTradeDate())
                .creationTime(order.getCreationTime())
                .interestedParty(order.getInterestedParty())
                .userId(order.getUserId())
                .build();
    }

    private String generateOrderId() {
        return "ORD" + System.currentTimeMillis();
    }

    @Override
    public String createOrder(OrderCreateDto createDto) {
        //DONE
        JDBCUtils jdbcUtils = new JDBCUtils();
        try {
            String orderId = createDto.getOrderId();

            if (orderId == null || orderId.trim().isEmpty()) {
                orderId = generateOrderId();
            } else {
                Order existingOrder = orderDao.getOrderById(jdbcUtils, orderId);
                if (existingOrder != null) {
                    log.error("Duplicate OrderID: {}", orderId);
                    return null;
                }
            }

            OrderType orderType = OrderType.getFromValue(createDto.getOrderType());
            if (orderType == OrderType.MARKET) {

                List<Role> roles = userDao.findRolesByUserId(jdbcUtils, createDto.getUserId());
                boolean isAdmin = roles.stream()
                        .anyMatch(role -> "admin".equalsIgnoreCase(role.getRoleName()));

                if (!isAdmin) {
                    log.error("User {} is not authorized to create Market order - only admin can create Market orders",
                            createDto.getUserId());
                    return null;
                }
            }

            Order order = Order.builder()
                    .orderId(orderId)
                    .clOrderId(createDto.getClOrderId())
                    .orderStatus(OrderStatus.NEW.getDbValue())
                    .orderQuantity(createDto.getOrderQuantity())
                    .side(Side.getFromValue(createDto.getSide()).getDbValue())
                    .orderType(OrderType.getFromValue(createDto.getOrderType()).getDbValue())
                    .price(createDto.getPrice())
                    .priceType(PriceType.getFromValue(createDto.getPriceType()).getDbValue())
                    .currency(createDto.getCurrency())
                    .instrumentName(createDto.getInstrumentName())
                    .settleType(SettleType.getFromValue(createDto.getSettleType()).getDbValue())
                    .settleDate(createDto.getSettleDate())
                    .tradeDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDate().toString())
                    .creationTime(ZonedDateTime.now(ZoneOffset.UTC))
                    .interestedParty(createDto.getInterestedParty())
                    .userId(createDto.getUserId())
                    .build();

            String result = orderDao.createOrder(jdbcUtils, order);

            if (result == null || result.trim().isEmpty()) {
                log.error("Fail to create order {}", orderId);
                return null;
            }

            log.info("Successfully create order {}", orderId);
            return result;

        }finally {
            jdbcUtils.close();
        }
    }

    @Override
    public OrderDetailDto getOrderById(String id, Integer userId) {
        //DONE
        JDBCUtils jdbcUtils = new JDBCUtils();

        try {
            Order order = orderDao.getOrderById(jdbcUtils, id);
            if (order == null) {
                return null;
            }

            if (!order.getUserId().equals(userId)) {
                List<Role> roles = userDao.findRolesByUserId(jdbcUtils, userId);
                boolean isAdmin = roles.stream().anyMatch(role -> "admin".equals(role.getRoleName()));

                if (!isAdmin) {
                    log.warn("User {} is not authorized to access order {} without permission", userId, id);
                    return null;
                }
            }
            return convertOrderToDetailDto(order);

        } finally {
            jdbcUtils.close();
        }
    }

    @Override
    public boolean updateOrder(String id, OrderCreateDto updatedDto, Integer userId) {
        //DONE
        JDBCUtils jdbcUtils = new JDBCUtils();
        try {
            Order existingOrder = orderDao.getOrderById(jdbcUtils, id);
            if (existingOrder == null) {
                log.error("Order {} does not exist", id);
                return false;
            }

            List<Role> roles = userDao.findRolesByUserId(jdbcUtils, userId);
            boolean isAdmin = roles.stream().anyMatch(role -> "admin".equalsIgnoreCase(role.getRoleName()));

            if(!isAdmin) {
                log.warn("User {} is not authorized to update order {} without permission", userId, id);
                return false;
            }

            if(existingOrder.getOrderStatus() != OrderStatus.NEW.getDbValue()) {
                log.error("Cannot update order {} with status {}",
                        id, OrderStatus.getFromDbValue(existingOrder.getOrderStatus()).getValue());
                return false;
            }

            Order updatedOrder = Order.builder()
                    .orderId(id)
                    .clOrderId(updatedDto.getClOrderId())
                    .orderStatus(existingOrder.getOrderStatus())
                    .orderQuantity(updatedDto.getOrderQuantity())
                    .side(Side.getFromValue(updatedDto.getSide()).getDbValue())
                    .orderType(existingOrder.getOrderType())
                    .price(updatedDto.getPrice())
                    .priceType(PriceType.getFromValue(updatedDto.getPriceType()).getDbValue())
                    .currency(updatedDto.getCurrency())
                    .instrumentName(updatedDto.getInstrumentName())
                    .settleType(SettleType.getFromValue(updatedDto.getSettleType()).getDbValue())
                    .settleDate(updatedDto.getSettleDate())
                    .tradeDate(existingOrder.getTradeDate())
                    .interestedParty(updatedDto.getInterestedParty())
                    .userId(existingOrder.getUserId())
                    .build();

            String result = orderDao.updateOrder(jdbcUtils, id, updatedOrder);
            if(result == null || result.trim().isEmpty()) {
                log.error("Fail to update order {}", id);
                return false;
            }

            log.info("Successfully update order {}", id);
            return true;

        } finally {
            jdbcUtils.close();
        }
    }

    @Override
    public boolean deleteOrder(String id, Integer userId) {
        //DONE
        JDBCUtils jdbcUtils = new JDBCUtils();

        try {
            Order existingOrder = orderDao.getOrderById(jdbcUtils, id);
            if (existingOrder == null) {
                log.error("Order {} does not exist", id);
                return false;
            }

            if(existingOrder.getOrderStatus() != OrderStatus.NEW.getDbValue()){
                log.error("Cannot delete order {} with status {}",
                        id, OrderStatus.getFromDbValue(existingOrder.getOrderStatus()).getValue());
                return false;
            }

            if (!existingOrder.getUserId().equals(userId)) {
                List<Role> roles = userDao.findRolesByUserId(jdbcUtils, userId);
                boolean isAdmin = roles.stream().anyMatch(role -> "admin".equalsIgnoreCase(role.getRoleName()));

                if (!isAdmin) {
                    log.warn("User {} is not authorized to delete order {} without permission", userId, id);
                    return false;
                }

                log.info("User {} is authorized to delete order {}", userId, id);
            }

            String result = orderDao.deleteOrder(jdbcUtils, id);
            if(result == null || result.trim().isEmpty()) {
                log.error("Fail to delete order {}", id);
                return false;
            }

            log.info("Successfully delete order {}", id);
            return true;

        }finally {
            jdbcUtils.close();
        }
    }

    @Override
    public OrderSearchDto findOrders(SearchCriteria searchCriteria, Integer userId) {
        //DONE
        JDBCUtils jdbcUtils = new JDBCUtils();
        try {
            List<Role> roles = userDao.findRolesByUserId(jdbcUtils, userId);
            boolean isAdmin = roles.stream().anyMatch(role -> "admin".equalsIgnoreCase(role.getRoleName()));
            Integer filteredUserId = isAdmin ? null : userId;

            List<Order> orders = orderDao.findOrders(jdbcUtils, searchCriteria, filteredUserId);
            int totalCount = orderDao.countOrders(jdbcUtils, searchCriteria, filteredUserId);

            //Stream API --> java 8
            return OrderSearchDto.builder()
                    .orders(orders.stream()
                            .map(this::convertOrderToListDto)
                            .collect(Collectors.toList()))
                    .totalFound(totalCount)
                    .build();
        } finally {
            jdbcUtils.close();
        }
    }
}
