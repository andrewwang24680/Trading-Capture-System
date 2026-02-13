package com.drill.project.trading.jsppp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum AvailableCurrency {
    USD("USD"),
    JPY("JPY"),
    EUR("EUR"),
    AUD("AUD");

    private final String value;

    public static List<String> getAll(){
        return Arrays.stream(AvailableCurrency.values())
                .map(AvailableCurrency::getValue)
                .collect(Collectors.toList());
    }
}
