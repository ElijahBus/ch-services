package com.comfihealth.security.authentication.registration.user;

import com.comfihealth.security.authentication.registration.token.ConfirmationTokenRequest;
import com.comfihealth.security.authentication.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;


    @GetMapping("status")
    public String status() {
        return "Working...";
    }

    @PostMapping("/user-details")
    public String register(@Valid @RequestBody UserRegistrationRequest request) {
        // validate the data with the registration request
        // convert the registration request into the user model
        // user the user service to store the user
        //TODO: add middleware to check for username verification
        var response = userRegistrationService.register(new User(
                request.getUsername(),
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

        return response;
    }

    @PostMapping("/update-user-password")
    public String updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        userRegistrationService
                .updateUserPassword(request.getUsername(), request.getPassword());

        return "Password updated";
    }

    @PostMapping("/request-token")
    public String requestRegistrationToken(@RequestParam String username) {
        var token = userRegistrationService.requestRegistrationToken(username);

        return token;
    }

    @PostMapping("/confirm-token")
    public String confirmRegistrationToken(@RequestBody ConfirmationTokenRequest request) {
        userRegistrationService.confirmToken(request.username(), request.token());

        return "Token confirmed";
    }
}
