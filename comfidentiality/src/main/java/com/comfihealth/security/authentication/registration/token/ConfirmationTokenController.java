package com.comfihealth.security.authentication.registration.token;

import com.comfihealth.security.authentication.registration.user.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class ConfirmationTokenController {

    private final ConfirmationTokenService confirmationTokenService;

    private final UserRegistrationService userRegistrationService;


    @GetMapping("/status")
    public String status() {
        return "Working...";
    }
}
