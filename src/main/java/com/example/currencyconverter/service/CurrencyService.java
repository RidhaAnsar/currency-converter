package com.example.currencyconverter.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Service
public class CurrencyService {
    private final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public Map<String, Double> getExchangeRates(String baseCurrency) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = API_URL + baseCurrency;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("rates")) {
                throw new RuntimeException("Failed to fetch exchange rates.");
            }

            return (Map<String, Double>) response.get("rates");
        } catch (RestClientException e) {
            throw new RuntimeException("External API unavailable. Please try again later.");
        }
    }

    public double convertCurrency(String from, String to, double amount) {
        Map<String, Double> rates = getExchangeRates(from);

        if (!rates.containsKey(to)) {
            throw new IllegalArgumentException("Invalid currency code: " + to);
        }
        return Math.round(amount * rates.get(to) * 100.0) / 100.0;
    }
}
