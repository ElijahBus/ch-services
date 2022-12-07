package com.comfihealth.security.authentication.registration.user;

import com.comfihealth.security.authentication.user.User;
import com.comfihealth.security.authentication.user.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserAuthenticationService userAuthenticationService;

    public String register(User user) {
        return userAuthenticationService.register(user);
    }

    public void confirmToken(String username, String token) {
        userAuthenticationService.validateUserRegistration(username, token);
    }

    public String requestRegistrationToken(String username) {
        return userAuthenticationService.signUp(new User(
                username,
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                username,
                "N/A",
                "N/A",
                "N/A"
        ));
    }

    public void updateUserPassword(String username, String password) {
        userAuthenticationService.updateUserPassword(username, password);
    }
}
