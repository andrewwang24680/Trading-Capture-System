package com.drill.project.trading.jsppp.dto;

import lombok.*;

import java.time.ZonedDateTime;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private String orderId;
    private String clOrderId;
    private String orderStatus;
    private String orderQuantity;
    private String side;
    private String orderType;
    private String price;
    private String priceType;
    private String currency;
    private String instrumentName;
    private String settleType;
    private String settleDate;
    private String tradeDate;
    private ZonedDateTime creationTime;
    private String interestedParty;
    private Integer userId; //DONE: thinking whether we should need to return UserDto or simply userId
}