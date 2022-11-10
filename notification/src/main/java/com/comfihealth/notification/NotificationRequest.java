package com.comfihealth.notification;

import java.time.LocalDateTime;

public record NotificationRequest(String userId, String destination, LocalDateTime createdAt) {
}
