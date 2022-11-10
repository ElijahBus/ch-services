package com.comfihealth.amqp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publish(Object payload, String exchange, String routingKey) {
        log.info("Publishing message with payload: " + payload);
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
        log.info("Successfully published the message");
    }
}
