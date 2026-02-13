package com.drill.project.trading.jsppp.constant;

public interface OrderSchema {
    String TABLE_NAME = "`Order`";
    String ORDER_ID = "order_id";
    String CL_ORDER_ID = "cl_order_id";
    String ORDER_STATUS = "order_status";
    String ORDER_QTY = "order_quantity";
    String SIDE = "side";
    String ORDER_TYPE = "order_type";
    String PRICE = "price";
    String PRICE_TYPE = "price_type";
    String CURRENCY = "currency";
    String INSTRUMENT_NAME = "instrument_name";
    String SETTLE_TYPE = "settle_type";
    String SETTLE_DATE = "settle_date";
    String TRADE_DATE = "trade_date";
    String CREATION_TIME = "creation_time";
    String INTERESTED_PARTY = "interested_party";
    String USER_ID = "user_id";
}