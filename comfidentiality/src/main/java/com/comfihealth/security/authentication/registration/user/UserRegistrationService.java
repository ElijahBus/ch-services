package com.comfihealth.security.authentication.registration.user;

import com.comfihealth.security.authentication.registration.token.ConfirmationTokenRequest;
import com.comfihealth.security.authentication.user.User;
import com.comfihealth.security.authentication.user.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserAuthenticationService userAuthenticationService;

    public String register(UserRegistrationRequest request) {

        var userToken = getRegistrationToken(request);

        // Implement phone number of email validation

        return userToken;
    }

    private String getRegistrationToken(UserRegistrationRequest request) {

        return userAuthenticationService.signUp(new User(
                request.getPhoneNumber(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                request.getSex(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getCountry(),
                request.getCity(),
                request.getAddress()
        ));
    }

    public void confirmToken(ConfirmationTokenRequest request) {
        userAuthenticationService.validateUserRegistration(request);
    }
}
