package com.comfihealth.security.authentication.registration.token;

import com.comfihealth.contracts.UserConsentNotification;

/**
 * Represents the notification to be sent to the user
 * in order to verify or validate user actions.
 *
 * @author elijah
 * @since 0.1.0
 */
public record ConfirmationTokenNotification(
        String userId,
        String channel,
        String message) implements UserConsentNotification {
}
