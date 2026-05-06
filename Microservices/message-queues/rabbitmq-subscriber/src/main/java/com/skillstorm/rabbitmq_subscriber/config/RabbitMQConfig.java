package com.skillstorm.rabbitmq_subscriber.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${exchanges.fanout}")
    private String exchangeName;

    @Value("${queues.fanout}")
    private String queueName;

    @Bean
    Exchange fanout() {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    Queue queue() {
        return new Queue(queueName);
    }

    // binding the queue to the exchange
    @Bean
    Binding binding(FanoutExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
