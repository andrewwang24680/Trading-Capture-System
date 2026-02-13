package com.drill.currencyexchangeapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@SpringBootTest
class ExchangeRateServiceTest {

    @Autowired
    private ExchangeRateService service;

    @Autowired
    private ExchangeRateRepository repository;

    @Test
    void shouldConvertAmountCorrectlyWhenRateExists() {
        // Given: insert a test rate into DB
        ExchangeRate testRate = new ExchangeRate();
        testRate.setFromCurrency("USD");
        testRate.setToCurrency("EUR");
        testRate.setRate(new BigDecimal("0.85"));
        testRate.setLastUpdated(LocalDateTime.now());
        repository.save(testRate);

        // When
        BigDecimal result = service.convert("USD", "EUR", new BigDecimal("200"));

        // Then
        assertThat(result)
                .isEqualTo(new BigDecimal("170.00"));
    }

    @Test
    void shouldThrowNotFoundWhenRateDoesNotExist() {
        // When & Then
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            service.getLatestRate("XYZ", "ABC");
        });
    }
}