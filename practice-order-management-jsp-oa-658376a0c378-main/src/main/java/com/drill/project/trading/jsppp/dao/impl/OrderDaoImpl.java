package com.drill.project.trading.jsppp.dao.impl;

import com.drill.project.trading.jsppp.constant.OrderSchema;
import com.drill.project.trading.jsppp.dao.OrderDao;
import com.drill.project.trading.jsppp.dto.SearchCriteria;
import com.drill.project.trading.jsppp.entity.Order;
import com.drill.project.trading.jsppp.utils.JDBCUtils;
import com.drill.project.trading.jsppp.utils.SQLGenerator;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderDaoImpl implements OrderDao {

    @Override
    public String createOrder(JDBCUtils jdbcUtils, Order order) {
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
    }

    @Override
    public Order getOrderById(JDBCUtils jdbcUtils, String id) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", OrderSchema.TABLE_NAME, OrderSchema.ORDER_ID);
        try (ResultSet rs = jdbcUtils.executeQuery(sql, id)) {
            if (rs.next()) {
                return convertResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("fail to fetch order by id due to {}", e.getMessage());
        }
        return null;
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
    public String updateOrder(JDBCUtils jdbcUtils, String id, Order updatedOrder) {
        String sql = SQLGenerator.buildUpdateSql(OrderSchema.TABLE_NAME, OrderSchema.ORDER_ID,
                OrderSchema.CL_ORDER_ID,
                OrderSchema.ORDER_STATUS, OrderSchema.ORDER_QTY,
                OrderSchema.SIDE, OrderSchema.ORDER_TYPE,
                OrderSchema.PRICE, OrderSchema.PRICE_TYPE,
                OrderSchema.CURRENCY, OrderSchema.INSTRUMENT_NAME,
                OrderSchema.SETTLE_TYPE, OrderSchema.SETTLE_DATE,
                OrderSchema.TRADE_DATE,
                OrderSchema.INTERESTED_PARTY, OrderSchema.USER_ID);

        int rowsUpdated = jdbcUtils.executeUpdate(sql,
                updatedOrder.getClOrderId(),
                updatedOrder.getOrderStatus(), updatedOrder.getOrderQuantity(),
                updatedOrder.getSide(), updatedOrder.getOrderType(),
                updatedOrder.getPrice(), updatedOrder.getPriceType(),
                updatedOrder.getCurrency(), updatedOrder.getInstrumentName(),
                updatedOrder.getSettleType(), updatedOrder.getSettleDate(),
                updatedOrder.getTradeDate(),
                updatedOrder.getInterestedParty(), updatedOrder.getUserId(), id);
        if (rowsUpdated == 0) {
            log.error("fail to update order since not able to locate order with id {}", id);
            return "";
        }
        log.info("successfully update order {}", id);
        return id;
    }

    @Override
    public String deleteOrder(JDBCUtils jdbcUtils, String id) {
        String sql = SQLGenerator.buildDeleteSql(OrderSchema.TABLE_NAME, OrderSchema.ORDER_ID);

        int rowsUpdated = jdbcUtils.executeUpdate(sql, id);
        if (rowsUpdated == 0) {
            log.error("fail to delete order since not able to locate order with id {}", id);
            return "";
        }
        log.info("successfully delete order {}", id);
        return id;

    }

    @Override
    public List<Order> findOrders(JDBCUtils jdbcUtils, SearchCriteria searchCriteria, Integer userId) {
        StringBuilder sql = new StringBuilder(String.format(
                "SELECT * FROM %s WHERE 1=1", OrderSchema.TABLE_NAME
        ));

        List<Order> orders = new ArrayList<>();
        List<Object> sqlParams = new ArrayList<>();

        if (userId != null) {
            String clause = String.format(" AND %s = ?", OrderSchema.USER_ID);
            sql.append(clause);
            sqlParams.add(userId);
        }

        if (searchCriteria.getOrderIdLike() != null && !searchCriteria.getOrderIdLike().trim().isEmpty()) {
            String clause = String.format(" AND %s LIKE ?", OrderSchema.ORDER_ID);
            sql.append(clause);
            sqlParams.add("%" + searchCriteria.getOrderIdLike() + "%");
        }

        if (searchCriteria.getStatus() != null) {
            String clause = String.format(" AND %s = ?", OrderSchema.ORDER_STATUS);
            sql.append(clause);
            sqlParams.add(searchCriteria.getStatus().getDbValue());
        }

        if (searchCriteria.getSide() != null) {
            String clause = String.format(" AND %s = ?", OrderSchema.SIDE);
            sql.append(clause);
            sqlParams.add(searchCriteria.getSide().getDbValue());
        }

        if (searchCriteria.getInstrumentName() != null && !searchCriteria.getInstrumentName().trim().isEmpty()) {
            String clause = String.format(" AND %s = ?", OrderSchema.INSTRUMENT_NAME);
            sql.append(clause);
            sqlParams.add(searchCriteria.getInstrumentName());
        }

        if (searchCriteria.getSettleDateFrom() != null && !searchCriteria.getSettleDateFrom().trim().isEmpty()) {
            String clause = String.format(" AND %s >= ?", OrderSchema.SETTLE_DATE);
            sql.append(clause);
            sqlParams.add(searchCriteria.getSettleDateFrom());
        }

        if (searchCriteria.getSettleDateTo() != null && !searchCriteria.getSettleDateTo().trim().isEmpty()) {
            String clause = String.format(" AND %s <= ?", OrderSchema.SETTLE_DATE);
            sql.append(clause);
            sqlParams.add(searchCriteria.getSettleDateTo());
        }

        String orderByClause = buildOrderByClause(searchCriteria);
        sql.append(orderByClause);

        int offset = (searchCriteria.getPageNumber() - 1) * searchCriteria.getPageSize();
        sql.append(" LIMIT ? OFFSET ?");
        sqlParams.add(searchCriteria.getPageSize());
        sqlParams.add(offset);

        try (ResultSet rs = jdbcUtils.executeQuery(sql.toString(), sqlParams.toArray())) {
            while (rs.next()) {
                orders.add(convertResultSet(rs));
            }
        } catch (SQLException e) {
            log.error("fail to fetch order by id due to {}", e.getMessage());
        }

        return orders;
    }

    private String buildOrderByClause(SearchCriteria searchCriteria) {

        String sortBy = searchCriteria.getSortBy();
        String sortOrder = searchCriteria.getSortOrder();


        if (sortBy == null || sortBy.trim().isEmpty()) {
            sortBy = "creation_time";
        }
        if (sortOrder == null || sortOrder.trim().isEmpty()) {
            sortOrder = "desc";
        }


        String dbColumnName;
        switch (sortBy.toLowerCase()) {
            case "orderid":
                dbColumnName = OrderSchema.ORDER_ID;
                break;
            case "orderstatus":
            case "status":
                dbColumnName = OrderSchema.ORDER_STATUS;
                break;
            case "price":
                dbColumnName = OrderSchema.PRICE;
                break;
            case "settledate":
                dbColumnName = OrderSchema.SETTLE_DATE;
                break;
            case "creationtime":
            case "systemcreationtime":
                dbColumnName = OrderSchema.CREATION_TIME;
                break;
            default:

                log.warn("Invalid sortBy field: {}, using default", sortBy);
                dbColumnName = OrderSchema.CREATION_TIME;
        }


        String dbSortOrder = "desc".equalsIgnoreCase(sortOrder) ? "DESC" : "ASC";

        return String.format(" ORDER BY %s %s", dbColumnName, dbSortOrder);
    }

    @Override
    public int countOrders(JDBCUtils jdbcUtils, SearchCriteria searchCriteria, Integer userId) {
        StringBuilder countSql = new StringBuilder(String.format(
                "SELECT count(*) FROM %s WHERE 1=1", OrderSchema.TABLE_NAME
        ));

        List<Object> sqlParams = new ArrayList<>();

        if (userId != null) {
            String clause = String.format(" AND %s = ?", OrderSchema.USER_ID);
            countSql.append(clause);
            sqlParams.add(userId);
        }

        if (searchCriteria.getOrderIdLike() != null && !searchCriteria.getOrderIdLike().trim().isEmpty()) {
            String clause = String.format(" AND %s LIKE ?", OrderSchema.ORDER_ID);
            countSql.append(clause);
            sqlParams.add("%" + searchCriteria.getOrderIdLike() + "%");
        }

        if (searchCriteria.getStatus() != null) {
            String clause = String.format(" AND %s = ?", OrderSchema.ORDER_STATUS);
            countSql.append(clause);
            sqlParams.add(searchCriteria.getStatus().getDbValue());
        }

        if (searchCriteria.getSide() != null) {
            String clause = String.format(" AND %s = ?", OrderSchema.SIDE);
            countSql.append(clause);
            sqlParams.add(searchCriteria.getSide().getDbValue());
        }

        if (searchCriteria.getInstrumentName() != null && !searchCriteria.getInstrumentName().trim().isEmpty()) {
            String clause = String.format(" AND %s = ?", OrderSchema.INSTRUMENT_NAME);
            countSql.append(clause);
            sqlParams.add(searchCriteria.getInstrumentName());
        }

        if (searchCriteria.getSettleDateFrom() != null && !searchCriteria.getSettleDateFrom().trim().isEmpty()) {
            String clause = String.format(" AND %s >= ?", OrderSchema.SETTLE_DATE);
            countSql.append(clause);
            sqlParams.add(searchCriteria.getSettleDateFrom());
        }

        if (searchCriteria.getSettleDateTo() != null && !searchCriteria.getSettleDateTo().trim().isEmpty()) {
            String clause = String.format(" AND %s <= ?", OrderSchema.SETTLE_DATE);
            countSql.append(clause);
            sqlParams.add(searchCriteria.getSettleDateTo());
        }


        int count = 0;
        try (ResultSet rs = jdbcUtils.executeQuery(countSql.toString(), sqlParams.toArray())) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("Failed to fetch order count due to: {}", e.getMessage());
        }

        return count;
    }
}
