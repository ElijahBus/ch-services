package com.comfihealth.notification.sms.rabbitmq;

import com.comfihealth.notification.Notification;
import com.comfihealth.notification.NotificationService;
import com.comfihealth.notification.sms.SmsNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    private final SmsNotificationService smsNotificationService;

    @RabbitListener(queues = "${rabbitmq.queues.sms}")
    public void consume(ConfirmationTokenNotification payload) {

        smsNotificationService.sendSms(payload);

        notificationService.save(new Notification(
                payload.userId(),
                payload.channel(),
                payload.message(),
                LocalDateTime.now()
        ));

        log.info("Consumed new message with payload: " + payload);
    }

}
