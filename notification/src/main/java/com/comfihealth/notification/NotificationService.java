package com.comfihealth.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }
}
