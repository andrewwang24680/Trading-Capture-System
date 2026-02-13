package com.drill.project.trading.jsppp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum AvailableInstrument {
    HSBA("HSBC Holdings PLC Class A Common Stock"),
    SHEL("Shell PLC Common Stock"),
    GOOGL("Alphabet Inc. Class A Common Stock"),
    AAPL("Apple Inc. Common Stock"),
    DIS("Walt Disney Company (The) Common Stock"),
    META("Meta Platforms, Inc Class A Common Stock"),
    HSBA_ADR("HSBC Holdings PLC ADRs"),
    MSFT("Microsoft Corporation Common Stock"),
    NVDA("NVIDIA Corporation Common Stock"),
    SHEL_ADR("Shell PLC American Depositary Shares (each representing two (2) Ordinary Shares)"),
    V("Visa Inc. Common Stock"),
    WMT("Walmart Inc. Common Stock");

    private final String value;

    public static List<String> getAll(){
        return Arrays.stream(AvailableInstrument.values())
                .map(AvailableInstrument::getValue)
                .collect(Collectors.toList());
    }
}
