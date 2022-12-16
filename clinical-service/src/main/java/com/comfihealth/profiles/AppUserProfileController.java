package com.comfihealth.profiles;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/app-user-profile")
@RequiredArgsConstructor
public class AppUserProfileController {

    private final AppUserProfileService appUserProfileService;

    @GetMapping("/find")
    public AppUserProfile getAppUserProfileDetails(@RequestParam String username) {
        return appUserProfileService.getAppUserProfileDetails(username);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserProfile createAppUserProfile(@RequestBody AppUserProfileRequest request) {
        return appUserProfileService.store(request.appUserUsername(), request.kinProfiles());
    }
}
