package com.drill.dao.impl;

import com.drill.constant.OrderSchema;
import com.drill.constant.OrderStatus;
import com.drill.dao.AdminOrderDao;
import com.drill.dto.FindOrderWithCountDto;
import com.drill.entity.Order;
import com.drill.entity.OrderJoinUser;
import com.drill.utils.JDBCUtils;
import com.drill.utils.SQLGenerator;
import com.drill.entity.User;
import com.drill.constant.UserSchema;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AdminOrderDaoImpl implements AdminOrderDao {
    @Override
    public String createOrder(Order order) {
        String sql = SQLGenerator.buildInsertSql(OrderSchema.TABLE_NAME,
                OrderSchema.ORDER_ID, OrderSchema.CL_ORDER_ID,
                OrderSchema.ORDER_STATUS, OrderSchema.ORDER_QTY,
                OrderSchema.SIDE, OrderSchema.ORDER_TYPE,
                OrderSchema.PRICE, OrderSchema.PRICE_TYPE,
                OrderSchema.CURRENCY, OrderSchema.INSTRUMENT_NAME,
                OrderSchema.SETTLE_TYPE, OrderSchema.SETTLE_DATE,
                OrderSchema.TRADE_DATE, OrderSchema.CREATION_TIME,
                OrderSchema.INTERESTED_PARTY, OrderSchema.USER_ID
        );
        JDBCUtils jdbcUtils = new JDBCUtils();
        try {
            log.info("create order {}", order.getOrderId());
            int effectedRows = jdbcUtils.executeUpdateWithSpecifiedStrId(sql,
                    order.getOrderId(), order.getClOrderId(),
                    order.getOrderStatus(), order.getOrderQuantity(),
                    order.getSide(), order.getOrderType(),
                    order.getPrice(), order.getPriceType(),
                    order.getCurrency(), order.getInstrumentName(),
                    order.getSettleType(), order.getSettleDate(),
                    order.getTradeDate(), order.getCreationTime(),
                    order.getInterestedParty(), order.getUserId());
            return effectedRows == 0 ? "" : order.getOrderId();
        } finally {
            jdbcUtils.close();
        }
    }

    @Override
    public Optional<Order> getOrderById(String id) {
        JDBCUtils jdbcUtils = new JDBCUtils();
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", OrderSchema.TABLE_NAME, OrderSchema.ORDER_ID);
        try (ResultSet rs = jdbcUtils.executeQuery(sql, id)) {
            if (rs.next()) {
                return Optional.of(convertResultSet(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to fetch order by id due to: {}", e.getMessage());
        } finally {
            jdbcUtils.close();
        }
        return Optional.empty();
    }

    @Override
    public Optional<OrderJoinUser> getOrderByIdJoinUser(String id) {
        JDBCUtils jdbcUtils = new JDBCUtils();
        String sql = String.format(
                "SELECT * FROM `Order` o JOIN `User` u ON o.user_id = u.user_id WHERE %s = ?",
                OrderSchema.ORDER_ID
        );
        try (ResultSet rs = jdbcUtils.executeQuery(sql, id)) {
            if (rs.next()) {
                return Optional.of(convertOrderJOINUserResultSet(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to fetch order by id due to: {}", e.getMessage());
        } finally {
            jdbcUtils.close();
        }
        return Optional.empty();
    }


    private OrderJoinUser convertOrderJOINUserResultSet(ResultSet rs) throws SQLException{
        return OrderJoinUser.builder()
                .orderId(rs.getString(OrderSchema.ORDER_ID))
                .clOrderId(rs.getString(OrderSchema.CL_ORDER_ID))
                .orderStatus(rs.getInt(OrderSchema.ORDER_STATUS))
                .orderQuantity(rs.getString(OrderSchema.ORDER_QTY))
                .side(rs.getInt(OrderSchema.SIDE))
                .orderType(rs.getInt(OrderSchema.ORDER_TYPE))
                .price(rs.getString(OrderSchema.PRICE))
                .priceType(rs.getInt(OrderSchema.PRICE_TYPE))
                .currency(rs.getString(OrderSchema.CURRENCY))
                .instrumentName(rs.getString(OrderSchema.INSTRUMENT_NAME))
                .settleType(rs.getInt(OrderSchema.SETTLE_TYPE))
                .settleDate(rs.getString(OrderSchema.SETTLE_DATE))
                .tradeDate(rs.getString(OrderSchema.TRADE_DATE))
                .creationTime(convertFromTimestamp(rs.getTimestamp(OrderSchema.CREATION_TIME)))
                .interestedParty(rs.getString(OrderSchema.INTERESTED_PARTY))
                .user(User.builder()
                        .userId(rs.getInt(UserSchema.ID))
                        .firstName(rs.getString(UserSchema.FIRST_NAME))
                        .lastName(rs.getString(UserSchema.LAST_NAME))
                        .email(rs.getString(UserSchema.EMAIL)).build())
                .build();
    }

    private Order convertResultSet(ResultSet rs) throws SQLException {
        return Order.builder()
                .orderId(rs.getString(OrderSchema.ORDER_ID))
                .clOrderId(rs.getString(OrderSchema.CL_ORDER_ID))
                .orderStatus(rs.getInt(OrderSchema.ORDER_STATUS))
                .orderQuantity(rs.getString(OrderSchema.ORDER_QTY))
                .side(rs.getInt(OrderSchema.SIDE))
                .orderType(rs.getInt(OrderSchema.ORDER_TYPE))
                .price(rs.getString(OrderSchema.PRICE))
                .priceType(rs.getInt(OrderSchema.PRICE_TYPE))
                .currency(rs.getString(OrderSchema.CURRENCY))
                .instrumentName(rs.getString(OrderSchema.INSTRUMENT_NAME))
                .settleType(rs.getInt(OrderSchema.SETTLE_TYPE))
                .settleDate(rs.getString(OrderSchema.SETTLE_DATE))
                .tradeDate(rs.getString(OrderSchema.TRADE_DATE))
                .creationTime(convertFromTimestamp(rs.getTimestamp(OrderSchema.CREATION_TIME)))
                .interestedParty(rs.getString(OrderSchema.INTERESTED_PARTY))
                .userId(rs.getInt(OrderSchema.USER_ID))
                .build();
    }

    private ZonedDateTime convertFromTimestamp(Timestamp timestamp) {
        return timestamp == null ? null :
                timestamp.toLocalDateTime().atZone(ZoneOffset.UTC);
    }

    @Override
    public boolean updateOrder(String id, Order updatedOrder) {
        JDBCUtils jdbcUtils = new JDBCUtils();
        String sql = SQLGenerator.buildUpdateSql(OrderSchema.TABLE_NAME, OrderSchema.ORDER_ID,
                OrderSchema.CL_ORDER_ID,
                OrderSchema.ORDER_STATUS, OrderSchema.ORDER_QTY,
                OrderSchema.SIDE, OrderSchema.ORDER_TYPE,
                OrderSchema.PRICE, OrderSchema.PRICE_TYPE,
                OrderSchema.CURRENCY, OrderSchema.INSTRUMENT_NAME,
                OrderSchema.SETTLE_TYPE, OrderSchema.SETTLE_DATE,
                OrderSchema.TRADE_DATE,
                OrderSchema.INTERESTED_PARTY, OrderSchema.USER_ID);

        try {
            int rowsUpdated = jdbcUtils.executeUpdate(sql,
                    updatedOrder.getClOrderId(),
                    updatedOrder.getOrderStatus(), updatedOrder.getOrderQuantity(),
                    updatedOrder.getSide(), updatedOrder.getOrderType(),
                    updatedOrder.getPrice(), updatedOrder.getPriceType(),
                    updatedOrder.getCurrency(), updatedOrder.getInstrumentName(),
                    updatedOrder.getSettleType(), updatedOrder.getSettleDate(),
                    updatedOrder.getTradeDate(),
                    updatedOrder.getInterestedParty(), updatedOrder.getUserId(), id);
            if (rowsUpdated < 0) {
                log.error("fail to update order since not able to locate order with id {}", id);
                return false;
            }
            log.info("successfully update order {}", id);
            return true;
        } finally {
            jdbcUtils.close();
        }
    }

    @Override
    public boolean deleteOrder(String id) {
        JDBCUtils jdbcUtils = new JDBCUtils();
        String sql = SQLGenerator.buildDeleteSql(OrderSchema.TABLE_NAME, OrderSchema.ORDER_ID);

        try {
            int rowsUpdated = jdbcUtils.executeUpdate(sql, id);
            if (rowsUpdated < 0) {
                log.error("fail to delete order since not able to locate order with id {}", id);
                return false;
            }
            log.info("successfully delete order {}", id);
            return true;
        } finally {
            jdbcUtils.close();
        }
    }

    @Override
    public FindOrderWithCountDto findOrdersForAdmin(int pageNumber, int pageSize, String orderIdLike, OrderStatus status) {
        JDBCUtils jdbcUtils = new JDBCUtils();
        StringBuilder countSql = new StringBuilder(String.format(
                "SELECT count(*) FROM %s WHERE 1=1", OrderSchema.TABLE_NAME
        ));
        StringBuilder sql = new StringBuilder(String.format(
                "SELECT * FROM %s WHERE 1=1", OrderSchema.TABLE_NAME
        ));
        List<Order> orders = new ArrayList<>();
        List<Object> sqlParams = new ArrayList<>();
        if (orderIdLike != null && orderIdLike.trim().length() != 0) {
            String clause = String.format(" AND %s LIKE ?", OrderSchema.ORDER_ID);
            countSql.append(clause);
            sql.append(clause);
            sqlParams.add("%" + orderIdLike + "%");
        }
        if (status != null) {
            String clause = String.format(" AND %s = ?", OrderSchema.ORDER_STATUS);
            countSql.append(clause);
            sql.append(clause);
            sqlParams.add(status.getDbValue());
        }

        int count = 0;
        try(ResultSet rs = jdbcUtils.executeQuery(countSql.toString(), sqlParams.toArray(new Object[0]))) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("fail to fetch order by id due to {}", e.getMessage());
        }

        if (count == 0) {
            return FindOrderWithCountDto.builder()
                    .orders(orders)
                    .count(count)
                    .build();
        }

        int offset = (pageNumber-1) * pageSize;
        sql.append(String.format(" ORDER BY %s DESC LIMIT ? OFFSET ?", OrderSchema.CREATION_TIME));
        sqlParams.add(pageSize);
        sqlParams.add(offset);

        try (ResultSet rs = jdbcUtils.executeQuery(sql.toString(), sqlParams.toArray(new Object[0]))) {
            while (rs.next()) {
                orders.add(convertResultSet(rs));
            }
        } catch (SQLException e) {
            log.error("fail to fetch order by id due to {}", e.getMessage());
        } finally {
            jdbcUtils.close();
        }

        return FindOrderWithCountDto.builder()
                .orders(orders)
                .count(count)
                .build();
    }
}

