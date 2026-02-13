package com.drill.project.trading.jsppp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDto {
    private String orderId;
    private String status;
    private String quantity;
    private String side;
    private String price;
    private String instrumentName;
    private String settleDate;
    private ZonedDateTime systemCreationTime;
}
