package com.comfihealth.rabbitmq;

import com.comfihealth.profiles.AppUserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final AppUserProfileService appUserProfileService;

    @RabbitListener(queues = "clinical.service.queue")
    public void consumeAppUserRegisteredNotification(NewAppUserRegisteredNotification payload) {

                // Create new app user profile
                appUserProfileService.store(payload.username(), null);

                log.info("Consumed new notification with payload - " +  payload);
    }
}
