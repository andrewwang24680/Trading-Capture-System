package com.drill.project.trading.jsppp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum SettleType {

    REGULAR("Regular", 0),
    CASH("Cash", 1),
    NEXT_DAY("Next Day (T+)", 2),
    T_PLUS_2("T+2", 3),
    T_PLUS_3("T+3", 4),
    T_PLUS_4("T+4", 5);

    private final String value;
    private final Integer dbValue;

    public static SettleType getFromValue(String value) {
        if (value == null || value.length() == 0) {
            return REGULAR;
        }

        for (SettleType type: SettleType.values()) {
            if (type.getValue().compareToIgnoreCase(value) == 0) {
                return type;
            }
        }

        return REGULAR;
    }

    public static SettleType getFromDbValue(Integer dbValue) {
        if (dbValue == null) {
            return REGULAR;
        }

        for (SettleType type: SettleType.values()) {
            if (Objects.equals(type.getDbValue(), dbValue)) {
                return type;
            }
        }

        return REGULAR;
    }
}
