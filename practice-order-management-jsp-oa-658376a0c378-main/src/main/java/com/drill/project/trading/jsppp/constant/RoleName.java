package com.drill.project.trading.jsppp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleName {
    ADMIN("admin"),
    INVESTOR("investor");
    private final String value;
}
