package com.comfihealth.security.authentication.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * Utility responsible for generating unique tokens
 *
 * @since 0.1.0
 * @author elijah
 */
@Component
public class TokenGenerator {

    /**
     * Generates a code to be sent to the user phone number
     * for verification.
     *
     * @return String the verification code
     */
    public String generateSignUpToken() {
        return generateNumbersToken(6);
    }

    public String generateNumbersToken(int length) {
        String token = "";

        for (int i = 0; i < length; i++) {
            var randomNumber = new Random().nextInt(0, 9);
            token = token.concat(Integer.toString(randomNumber));
        }

        return token;
    }
}
