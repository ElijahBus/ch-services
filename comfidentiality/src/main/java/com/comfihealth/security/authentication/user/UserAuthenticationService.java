package com.comfihealth.security.authentication.user;

import com.comfihealth.security.authentication.registration.token.ConfirmationToken;
import com.comfihealth.security.authentication.registration.token.ConfirmationTokenService;
import com.comfihealth.security.authentication.utils.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {
    private final UserRepository userRepository;

    private final ConfirmationTokenService confirmationTokenService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenGenerator tokenGenerator;

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

        //TODO: Send the confirmation code to the user's phone number

        return signUpToken;
    }

    private void saveConfirmationToken(User user, String token) {
        var confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now(),
                user
        );

        confirmationTokenService.saveToken(confirmationToken);
    }

}
