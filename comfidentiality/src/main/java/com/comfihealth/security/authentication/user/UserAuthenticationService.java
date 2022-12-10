package com.comfihealth.security.authentication.user;

import com.comfihealth.amqp.RabbitMQMessageProducer;
import com.comfihealth.security.authentication.registration.token.ConfirmationToken;
import com.comfihealth.security.authentication.registration.token.ConfirmationTokenNotification;
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

        var userExists = userRepository.findUserByUsername(user.getUsername());
        if (userExists.isPresent()) {
            if (userExists.get().getPhoneNumberVerifiedAt() == null) {
                throw new IllegalStateException("Redirect user to the phone verification page");
            }

            if (userExists.get().getPassword().isEmpty()) {
                throw new IllegalStateException("Redirect user to the details page, with preloaded info");
            }
        }

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

    public void validateUserRegistration(String username, String token) {
        var isValidToken = confirmationTokenService.isValidToken(username, token);

        if (!isValidToken) {
            throw new IllegalStateException("The provided token is invalid");
        }

        userRepository.enableUser(username);
        userRepository.setUserPhoneNumberVerified(username, LocalDateTime.now());

        confirmationTokenService.confirmToken(username, token);
    }

    public String register(User user) {

        var signedUpUser = userRepository.findUserByUsername(user.getUsername())
                .orElseThrow();

        signedUpUser.setUsername(user.getUsername());
        signedUpUser.setCity(user.getCity());
        signedUpUser.setAddress(user.getAddress());
        signedUpUser.setCountry(user.getCountry());
        signedUpUser.setSex(user.getSex());
        signedUpUser.setFirstName(user.getFirstName());
        signedUpUser.setLastName(user.getLastName());
        signedUpUser.setEmail(user.getEmail());

        userRepository.save(signedUpUser);

        return "User registered";
    }

    public void updateUserPassword(String username, String password) {

        var user = userRepository.findUserByUsername(username).orElseThrow();


        var encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
}
