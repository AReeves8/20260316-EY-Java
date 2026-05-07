package com.skillstorm.rabbitmq_subscriber.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.skillstorm.rabbitmq_subscriber.dtos.PaymentDto;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    @RabbitListener(queues = "${queues.fanout}")
    public void handlePaymentInfoOnAccount(@Payload PaymentDto paymentDto) {
        // use the payment info to update some account record

        log.info("Payment info recieved for Account {} in the amount of {}.", 
            paymentDto.accountNumber(),
            paymentDto.amount()
        );

    }
}
