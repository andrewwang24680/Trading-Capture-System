package com.drill.currencyexchangeapp;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeRateService service;

    @GetMapping("/fetch")
    public ResponseEntity<ExchangeRate> fetchRate(@RequestParam String from, @RequestParam String to) {
        if (!from.matches("[A-Z]{3}") || !to.matches("[A-Z]{3}")) {
            throw new BadRequestException("Currency codes must be exactly 3 uppercase letters (e.g., USD, EUR)");
        }
        return ResponseEntity.ok(service.fetchAndSave(from, to));
    }

    @GetMapping("/rate")
    public ResponseEntity<ExchangeRate> getRate(@RequestParam String from, @RequestParam String to) {
        return ResponseEntity.ok(service.getLatestRate(from, to));
    }

    @GetMapping("/convert")
    public ResponseEntity<Map<String, Object>> convert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be greater than zero");
        }

        BigDecimal converted = service.convert(from, to, amount);

        return ResponseEntity.ok(Map.of(
                "from", from.toUpperCase(),
                "to", to.toUpperCase(),
                "originalAmount", amount,
                "convertedAmount", converted,
                "rate", service.getLatestRate(from, to).getRate()
        ));
    }
}