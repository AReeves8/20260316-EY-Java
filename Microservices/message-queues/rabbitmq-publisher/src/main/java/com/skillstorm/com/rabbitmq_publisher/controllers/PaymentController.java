package com.skillstorm.com.rabbitmq_publisher.controllers;

import com.skillstorm.com.rabbitmq_publisher.services.PaymentsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentsService paymentsService;

    public PaymentController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @GetMapping()
    public ResponseEntity<Void> getMethodName(@RequestParam String accountNumber, @RequestParam double amount) {
        paymentsService.processPayment(accountNumber, amount);
        return ResponseEntity.accepted().build();       // 202 - Accepted. the request went through and no data to return at this time
    }
    
}
