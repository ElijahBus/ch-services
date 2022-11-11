package com.comfihealth.security.authentication.user;

import com.comfihealth.amqp.RabbitMQMessageProducer;
import com.comfihealth.security.authentication.registration.token.ConfirmationToken;
import com.comfihealth.security.authentication.registration.token.ConfirmationTokenNotification;
import com.comfihealth.security.authentication.registration.token.ConfirmationTokenRequest;
import com.comfihealth.security.authentication.registration.token.ConfirmationTokenService;
import com.comfihealth.security.authentication.utils.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {

    public static final int TOKEN_LIFETIME_MINUTES = 15;

    @Value("${rabbitmq.exchanges.sms}")
    private String smsNotificationExchange;

    @Value("${rabbitmq.routing-keys.sms-notification}")
    private String smsNotificationRoutingKey;

    private final UserRepository userRepository;

    private final ConfirmationTokenService confirmationTokenService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenGenerator tokenGenerator;

    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public String signUp(User user) {

        var userExists = userRepository.findUserByUsername(user.getUsername()).isPresent();
        if (userExists) {
            //TODO: check whether the user exists but has not yet confirmed the email or phone
            throw new IllegalStateException("The user already exists");
        }

        var encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        // Phone number code in this case
        var signUpToken = tokenGenerator.generateSignUpToken();
        saveConfirmationToken(user, signUpToken);
        sendConfirmationToken(new ConfirmationTokenNotification(
                user.getId().toString(),
                user.getPhoneNumber(),
                signUpToken
        ));

        return signUpToken;
    }

    private void saveConfirmationToken(User user, String token) {
        var confirmationToken = new ConfirmationToken(
                token,
                user.getUsername(),
                user,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(TOKEN_LIFETIME_MINUTES)
        );

        confirmationTokenService.saveToken(confirmationToken);
    }

    private void sendConfirmationToken(ConfirmationTokenNotification notification) {
        rabbitMQMessageProducer.publish(
                notification,
                smsNotificationExchange,
                smsNotificationRoutingKey
        );
    }

    public void validateUserRegistration(ConfirmationTokenRequest request) {
        var isValidToken = confirmationTokenService.isValidToken(request);

        if (!isValidToken) {
            throw new IllegalStateException("The provided token is invalid");
        }

        userRepository.enableUser(request.username());
        userRepository.setUserPhoneNumberVerified(request.username(), LocalDateTime.now());

        confirmationTokenService.confirmToken(request);
    }
}
