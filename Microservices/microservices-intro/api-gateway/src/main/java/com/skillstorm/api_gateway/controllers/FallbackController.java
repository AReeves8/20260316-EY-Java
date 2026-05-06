package com.skillstorm.api_gateway.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/cache")
public class FallbackController {


    @GetMapping("/accounts")
    public String cachedAccounts() {
        return "CircuitBreaker popped. Accounts from cache.";
    }

    @GetMapping("/payments")
    public String cachedPayments() {
        return "CircuitBreaker popped. Payments from cache.";
    }
    
}
