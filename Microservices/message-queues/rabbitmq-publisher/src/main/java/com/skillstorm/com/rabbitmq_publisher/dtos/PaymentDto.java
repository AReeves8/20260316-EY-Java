package com.skillstorm.com.rabbitmq_publisher.dtos;

public record PaymentDto(
    String accountNumber,
    double amount
) {

}
