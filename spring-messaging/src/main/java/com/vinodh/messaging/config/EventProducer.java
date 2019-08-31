package com.vinodh.messaging.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author thimmv
 * @createdAt 31-08-2019 14:50
 */
@Component
@Slf4j
public class EventProducer {

    private RabbitTemplate rabbitTemplate;
    private Exchange exchange;

    public EventProducer(RabbitTemplate rabbitTemplate, Exchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void publishMessage(String routingKey, String message) {
        this.rabbitTemplate.convertAndSend(exchange.getName(), routingKey, message);
    }
}
