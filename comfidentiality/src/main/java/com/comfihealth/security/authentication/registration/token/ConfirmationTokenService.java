package com.comfihealth.security.authentication.registration.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    /**
     * Verify if a given token is valid based of the destination it was sent to
     * and the token expiration time.
     *
     * @param destination The destination the token was sent to
     * @param incomingToken The incoming token in the request. (can be the correct one or not)
     * @return A boolean value certifying whether the token is valid
     */
    public boolean isValidToken(String destination, String incomingToken) {
        var confirmationToken = confirmationTokenRepository
                .findUserConfirmationToken(destination, incomingToken)
                .orElseThrow();

        var tokenExpirationTime = confirmationToken.getExpiresAt();
        // Check if the token has not expired yet
        return !LocalDateTime.now().isAfter(tokenExpirationTime);
    }

    /**
     * Confirm the token sent to a specific destination.
     * This will normally occur after the token has been verified as valid.
     *
     * @param destination The destination the token was sent to.
     * @param incomingToken The incoming token to confirm
     */
    public void confirmToken(String destination, String incomingToken) {
        confirmationTokenRepository.confirmUserToken(destination, incomingToken);
    }
}
