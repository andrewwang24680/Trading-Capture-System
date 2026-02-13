package com.drill.project.trading.jsppp.dto;

import com.drill.project.trading.jsppp.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FindOrderWithCountDto {
    private Integer count;
    private List<Order> orders;
}
