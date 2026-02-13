package com.drill.currencyexchangeapp;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}