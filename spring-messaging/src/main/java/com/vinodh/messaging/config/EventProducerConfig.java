package com.vinodh.messaging.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author thimmv
 * @createdAt 31-08-2019 14:55
 */
@Configuration
public class EventProducerConfig {

    @Value("${exchange}")
    private String exchangeRoute;

    @Bean
    public Exchange exchange() {
        return new TopicExchange(exchangeRoute);
    }

}
