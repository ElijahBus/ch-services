package com.comfihealth.rabbitmq;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RabbitNotificationConfig {

    @Value("${rabbitmq.exchanges.clinical-service}")
    private String clinicalServiceExchange;

    @Value("${rabbitmq.queues.clinical-service}")
    private String clinicalServiceQueue;

    @Value("${rabbitmq.routing-keys.clinical-service-notification}")
    private String clinicalServiceRoutingKey;

    @Bean
    public TopicExchange clinicalServiceTopicExchange() {
        return new TopicExchange(this.clinicalServiceExchange);
    }

    @Bean
    public Queue clinicalServiceQueue() {
        return new Queue(this.clinicalServiceQueue);
    }

    @Bean
    public Binding internalNotificationBinding() {
        return BindingBuilder
                .bind(clinicalServiceQueue())
                .to(clinicalServiceTopicExchange())
                .with(this.clinicalServiceRoutingKey);
    }
}
