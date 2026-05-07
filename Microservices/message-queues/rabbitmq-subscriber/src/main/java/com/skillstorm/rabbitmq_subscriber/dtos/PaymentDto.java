package com.skillstorm.rabbitmq_subscriber.dtos;

public record PaymentDto(
    String accountNumber,
    double amount
) {

}
