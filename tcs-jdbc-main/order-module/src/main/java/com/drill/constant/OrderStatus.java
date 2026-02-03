package com.drill.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    NEW("New", 0),
    PARTIALLY_FILLED("Partially Filled", 1),
    FILLED("Filled", 2);

    private final String value;
    private final Integer dbValue;

    public static OrderStatus getFromValue(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }

        for (OrderStatus status: OrderStatus.values()) {
            if (status.getValue().compareToIgnoreCase(value) == 0) {
                return status;
            }
        }

        return null;
    }

    public static OrderStatus getFromDbValue(Integer dbValue) {
        if (dbValue == null) {
            return NEW;
        }

        for (OrderStatus status: OrderStatus.values()) {
            if (Objects.equals(status.getDbValue(), dbValue)) {
                return status;
            }
        }

        return NEW;
    }

    public static List<String> getAll() {
        return Arrays.stream(OrderStatus.values()).map(OrderStatus::getValue).collect(Collectors.toList());
    }
}

