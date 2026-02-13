package com.drill.project.trading.jsppp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum PriceType {

    PERCENTAGE("Percentage", 0),
    PER_UNIT("Per Unit", 1),
    FIXED_AMOUNT("Fixed Amount", 2);

    private final String value;
    private final Integer dbValue;

    public static PriceType getFromValue(String value) {
        if (value == null || value.length() == 0) {
            return PERCENTAGE;
        }

        for (PriceType type: PriceType.values()) {
            if (type.getValue().compareToIgnoreCase(value) == 0) {
                return type;
            }
        }

        return PERCENTAGE;
    }

    public static PriceType getFromDbValue(Integer dbValue) {
        if (dbValue == null) {
            return PERCENTAGE;
        }

        for (PriceType type: PriceType.values()) {
            if (Objects.equals(type.getDbValue(), dbValue)) {
                return type;
            }
        }

        return PERCENTAGE;
    }

    public static List<String> getAll() {
        return Arrays.stream(PriceType.values())
                .map(PriceType::getValue)
                .collect(Collectors.toList());
    }
}
