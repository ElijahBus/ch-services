package com.comfihealth.notification.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class Controller {

    private final SmsNotificationService smsNotificationService;

    @GetMapping("/status")
    public String status() {
        return "Working...";
    }

    @PostMapping("/send-sms")
    public String send(@RequestBody SmsNotificationRequest request) {

        var response = smsNotificationService.sendSms(request.receiver(), request.message());

        return "Notification successfully sent";
    }
}
