package com.example.currencyconverter.controller;

import com.example.currencyconverter.service.CurrencyService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/rates")
    public Map<String, Double> getRates(@RequestParam(defaultValue = "USD") String base) {
        return currencyService.getExchangeRates(base);
    }

    @PostMapping("/convert")
    public Map<String, Object> convertCurrency(@RequestBody Map<String, Object> request) {
        double amount = Double.parseDouble(request.get("amount").toString());
        double convertedAmount = currencyService.convertCurrency(
                request.get("from").toString(),
                request.get("to").toString(),
                amount
        );

        return Map.of(
                "from", request.get("from"),
                "to", request.get("to"),
                "amount", amount,
                "convertedAmount", convertedAmount
        );
    }

    @ExceptionHandler(Exception.class)
    public Map<String, String> handleException(Exception e) {
        return Map.of("error", e.getMessage());
    }
}
