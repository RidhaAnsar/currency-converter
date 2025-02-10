package com.example.currencyconverter.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Currencytest {
    private final CurrencyService currencyService = new CurrencyService();

    @Test
    void testConvertCurrency_ValidConversion() {
        double convertedAmount = currencyService.convertCurrency("USD", "EUR", 100);
        assertTrue(convertedAmount > 0);
    }

    @Test
    void testConvertCurrency_InvalidCurrencyCode() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyService.convertCurrency("USD", "XYZ", 100);
        });

        assertEquals("Invalid currency code: XYZ", exception.getMessage());
    }
}
