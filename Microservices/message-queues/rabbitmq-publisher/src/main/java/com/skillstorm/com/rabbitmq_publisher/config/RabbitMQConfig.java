package com.skillstorm.com.rabbitmq_publisher.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Value("${exchanges.fanout}")
    private String exchangeName;

    @Bean
    Exchange fanout() {
        return new FanoutExchange(exchangeName);
    }

    // create message converter for java <-> json
    // will be used implicitly by AMQP (RabbitMQ)
    @Bean
    MessageConverter jsonMessageConverter() {

        // tell rabbit mq to use Jackson for its object mapping
        return new Jackson2JsonMessageConverter();
    }

}
