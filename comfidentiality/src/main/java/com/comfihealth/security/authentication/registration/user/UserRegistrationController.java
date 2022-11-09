package com.comfihealth.security.authentication.registration.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping("status")
    public String status() {
        return "Working...";
    }

    @PostMapping("/user")
    public String register(@Valid @RequestBody UserRegistrationRequest request) {
        // validate the data with the registration request
        // convert the registration request into the user model
        // user the user service to store the user
        var response = userRegistrationService.register(request);

        return response;
    }

    @PostMapping("/token/confirm")
    public String confirmRegistrationToken(@RequestParam("token") String token) {
        var response = userRegistrationService.confirmToken(token);

        return "";
    }


}
