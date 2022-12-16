package com.comfihealth.profiles;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kin-profile")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class KinProfileController {

    private final KinProfileService kinProfileService;

    @PostMapping("/create")
    public KinProfile createKinProfile(@RequestBody KinProfileRequest request) {
        return kinProfileService.createKinProfile(request);
    }
}
