package com.comfihealth.notification.sms;


/**
 * Defines the SMS Notification request body
 *
 * @param receiver
 * @param message
 *
 * @author elijah
 * @since 0.1.0
 */
public record SmsNotificationRequest(String receiver, String message) {
}
