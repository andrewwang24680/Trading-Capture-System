package com.drill.entity;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderId;

    private String clOrderId;

    private Integer orderStatus;

    private String orderQuantity;

    private Integer side;

    private Integer orderType;

    private String price;

    private Integer priceType;

    private String currency;

    private String instrumentName;

    private Integer settleType;

    private String settleDate;

    private String tradeDate;

    private ZonedDateTime creationTime;

    private String interestedParty;

    private Integer userId;
}