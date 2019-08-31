package com.vinodh.messaging.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author thimmv
 * @createdAt 31-08-2019 14:57
 */
@Slf4j
@Component
public class EventConsumer {

    @RabbitListener(queues = {"${subscriber.queue}"})
    public void receive(String message) {
        log.info("Received message '{}'", message);
    }
}
