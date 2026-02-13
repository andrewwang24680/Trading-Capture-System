package com.drill.project.trading.jsppp.dto;

import com.drill.project.trading.jsppp.constant.OrderStatus;
import com.drill.project.trading.jsppp.constant.Side;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class SearchCriteria {
    private int pageNumber;
    private int pageSize;
    private String orderIdLike;
    private OrderStatus status;

    private Side side;
    private String instrumentName;
    private String settleDateFrom;
    private String settleDateTo;

    private String sortBy;
    private String sortOrder;

    public SearchCriteria() {
        this.pageNumber = 1;
        this.pageSize = 10;
    }

}