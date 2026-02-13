package com.drill.project.trading.jsppp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum OrderType {

    MARKET("Market", 0),
    LIMIT("Limit", 1);

    private final String value;
    private final Integer dbValue;

    public static OrderType getFromValue(String value) {
        if (value == null || value.length() == 0) {
            return MARKET;
        }

        for (OrderType type: OrderType.values()) {
            if (type.getValue().compareToIgnoreCase(value) == 0) {
                return type;
            }
        }

        return MARKET;
    }

    public static OrderType getFromDbValue(Integer dbValue) {
        if (dbValue == null) {
            return MARKET;
        }

        for (OrderType type: OrderType.values()) {
            if (Objects.equals(type.getDbValue(), dbValue)) {
                return type;
            }
        }
        return MARKET;
    }

    public static List<String> getAll() {
        return Arrays.stream(OrderType.values())
                .map(OrderType::getValue)
                .collect(Collectors.toList());
    }
}
