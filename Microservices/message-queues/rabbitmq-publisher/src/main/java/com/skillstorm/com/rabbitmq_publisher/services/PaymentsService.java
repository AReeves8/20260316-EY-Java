package com.skillstorm.com.rabbitmq_publisher.services;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skillstorm.com.rabbitmq_publisher.dtos.PaymentDto;

@Service
public class PaymentsService {

    @Value("${routing-key}")
    private String routingKey;

    /**
     * RABBIT TEMPLATE
     *      - used to write messages to an exchange
     *      - Template Design Pattern
     */
    private final RabbitTemplate rabbitTemplate;
    private final Exchange exchange;

    public PaymentsService(RabbitTemplate rabbitTemplate, Exchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }


    public void processPayment(String accountNumber, double amount) {

        // need to tell the account service that a specific account has paid some amount
        // payments don't care if the message is received or not, because they already handled the payment on their end

        // convert and send will put the payload through the message converter before sending it off
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, new PaymentDto(accountNumber, amount));
    }
    
}
