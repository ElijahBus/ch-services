package com.comfihealth.notification.sms.rabbitmq;

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

    @Value("${rabbitmq.exchanges.sms}")
    private String smsExchange;

    @Value("${rabbitmq.queues.sms}")
    private String smsQueue;

    @Value("${rabbitmq.routing-keys.sms-notification}")
    private String smsNotificationRoutingKey;

    @Bean
    public TopicExchange smsTopicExchange() {
        return new TopicExchange(this.smsExchange);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(this.smsQueue);
    }

    @Bean
    public Binding internalNotificationBinding() {
        return BindingBuilder
                .bind(smsQueue())
                .to(smsTopicExchange())
                .with(this.smsNotificationRoutingKey);
    }
}
