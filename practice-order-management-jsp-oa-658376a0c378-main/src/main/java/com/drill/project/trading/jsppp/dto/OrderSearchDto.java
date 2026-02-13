package com.drill.project.trading.jsppp.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class OrderSearchDto {
    private Integer totalFound;
    private List<OrderListDto> orders;
}
