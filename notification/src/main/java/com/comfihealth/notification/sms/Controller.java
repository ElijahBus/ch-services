package com.comfihealth.notification.sms;

import com.comfihealth.notification.sms.rabbitmq.ConfirmationTokenNotification;
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
    public String send(@RequestBody ConfirmationTokenNotification request) {

        var response = smsNotificationService.sendSms(request);

        return "Notification successfully sent";
    }
}
