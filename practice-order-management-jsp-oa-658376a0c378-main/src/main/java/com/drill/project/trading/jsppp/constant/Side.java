package com.drill.project.trading.jsppp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Side {

    BUY("Buy", 0),
    SELL("Sell", 1);

    private final String value;
    private final Integer dbValue;

    public static Side getFromValue(String value) {
        if (value == null || value.length() == 0) {
            return BUY;
        }

        for (Side side: Side.values()) {
            if (side.getValue().compareToIgnoreCase(value) == 0) {
                return side;
            }
        }

        return BUY;
    }

    public static Side getFromDbValue(Integer dbValue) {
        if (dbValue == null) {
            return BUY;
        }

        for (Side side: Side.values()) {
            if (Objects.equals(side.getDbValue(), dbValue)) {
                return side;
            }
        }
        return BUY;
    }

    public static List<String> getAll() {
        return Arrays.stream(Side.values())
                .map(Side::getValue)
                .collect(Collectors.toList());
    }
}
