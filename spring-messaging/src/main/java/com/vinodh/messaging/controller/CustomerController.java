package com.vinodh.messaging.controller;

import com.vinodh.messaging.config.EventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thimmv
 * @createdAt 31-08-2019 15:20
 */
@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {

    @Autowired
    private EventProducer eventProducer;

    @GetMapping("/sendMessage/{routingKey}/{message}")
    public String findCustomer(@PathVariable String routingKey, @PathVariable String message) {
        this.eventProducer.publishMessage(routingKey, message);
        log.info("Published message :: '{}' @ routing :: '{}'", message, routingKey);
        return "event sent";
    }
}
