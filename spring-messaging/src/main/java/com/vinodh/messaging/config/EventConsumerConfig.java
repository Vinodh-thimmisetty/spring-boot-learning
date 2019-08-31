package com.vinodh.messaging.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author thimmv
 * @createdAt 31-08-2019 15:00
 */
@Configuration
@Slf4j
public class EventConsumerConfig {

    @Value("${subscriber.queue}")
    private String queueName;

    @Value("${subscriber.routingKey}")
    private String routingKey;

    @Value("${exchange}")
    private String exchangeRoute;

    @Bean
    public Exchange eventExchange() {
        return new TopicExchange(exchangeRoute);
    }

    @Bean
    public Queue queue() {
        return new Queue(routingKey);
    }

    @Bean
    public Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(routingKey).noargs();
    }
}
