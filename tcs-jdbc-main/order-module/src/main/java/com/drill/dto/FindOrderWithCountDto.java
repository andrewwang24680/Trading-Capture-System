package com.drill.dto;

import com.drill.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FindOrderWithCountDto {
    private Integer count;
    private List<Order> orders;
}
