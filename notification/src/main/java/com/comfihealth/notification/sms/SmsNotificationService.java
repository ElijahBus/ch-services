package com.comfihealth.notification.sms;

import com.comfihealth.notification.sms.rabbitmq.ConfirmationTokenNotification;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsNotificationService {

    @Value("${comfihealth.twilio.account-sid}")
    private String twilioAccountSid;

    @Value("${comfihealth.twilio.auth-token}")
    private String twilioAuthToken;

    @Value("${comfihealth.twilio.sender-phone-number}")
    private String twilioAccountPhoneNumber;

    public boolean sendSms(ConfirmationTokenNotification notification) {

        try {
            Twilio.init(twilioAccountSid, twilioAuthToken);

            Message.creator(new PhoneNumber(notification.channel()),
                            new PhoneNumber(twilioAccountPhoneNumber),
                            notification.message()
                                    + " : is your ComfiHealth verification code."
                                    + " This code will expire in 15 min"
                    )
                    .create();

            return true;
        } catch (Exception e) {
            //TODO:  Report the exception here
            return false;
        }
    }
}
