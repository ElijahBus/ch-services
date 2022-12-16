package com.comfihealth.profiles;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserProfileService {

    private final AppUserProfileRepository appUserProfileRepository;

    public AppUserProfile getAppUserProfileDetails(String appUserUsername) {
        return appUserProfileRepository.getAppUserProfileDetails(appUserUsername);
    }

    public AppUserProfile store(String appUserUsername, Set<KinProfile> kinProfiles) {
        return appUserProfileRepository.save(new AppUserProfile(appUserUsername, kinProfiles));
    }

}
