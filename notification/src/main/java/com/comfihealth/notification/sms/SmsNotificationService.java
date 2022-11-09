package com.comfihealth.notification.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationService {

    @Value("${comfihealth.twilio.account-sid}")
    private String twilioAccountSid;

    @Value("${comfihealth.twilio.auth-token}")
    private String twilioAuthToken;

    @Value("${comfihealth.twilio.sender-phone-number}")
    private String twilioAccountPhoneNumber;

    public boolean sendSms(String receiver, String message) {

        //TODO: Save the sms notification sent for tracking purpose

        try {
            Twilio.init(twilioAccountSid, twilioAuthToken);

            Message.creator(new PhoneNumber(receiver),
                            new PhoneNumber(twilioAccountPhoneNumber),
                            message
                    )
                    .create();

            return true;
        } catch (Exception e) {
            //TODO:  Report the exception here
            return false;
        }
    }
}
