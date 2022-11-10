package com.comfihealth.contracts;

public interface UserConsentNotification {

    /**
     * The id of the user the notification is intended to
     *
     * @return user id
     */
    String userId();

    /**
     * The communication channel via which to send the
     * notification. can be SMS, or EMAIL
     *
     * @return channel
     */
    String channel();

    /**
     * The intended notification message to be sent
     *
     * @return message
     */
    String message();
}
