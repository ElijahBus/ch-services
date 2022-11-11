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

    public boolean isValidToken(ConfirmationTokenRequest request) {
        var token = confirmationTokenRepository
                .findUserConfirmationToken(request.username(), request.token())
                .orElseThrow();

        var tokenExpirationTime = token.getExpiresAt();
        // Check if the token has not expired yet
        return !LocalDateTime.now().isAfter(tokenExpirationTime);
    }

    public void confirmToken(ConfirmationTokenRequest request) {
        confirmationTokenRepository.confirmUserToken(request.username(), request.token());
    }
}
