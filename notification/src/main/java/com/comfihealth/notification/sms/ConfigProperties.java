package com.comfihealth.notification.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("comfihealth.twilio")
public record ConfigProperties(String accountSid, String authToken, String senderPhoneNumber) {
}
