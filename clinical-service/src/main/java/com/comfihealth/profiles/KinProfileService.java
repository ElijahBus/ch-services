package com.comfihealth.profiles;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class KinProfileService {

    private final KinProfileRepository kinProfileRepository;

    private final AppUserProfileRepository appUserProfileRepository;

    private final AppUserKinProfileRepository appUserKinProfileRepository;

    /**
     * Create kin profile
     * <p>
     * We consider we are creating the kin profile with its first app user profile,
     * other app user profiles can be assigned later depending on the relation with
     * the kin profile.
     *
     * @param request The request shape of the kin profile creation
     * @return The saved kin profile entity
     */
    public KinProfile createKinProfile(KinProfileRequest request) {

        var appUserProfile = appUserProfileRepository.findByUsername(request.appUserUsername())
                .orElseThrow(); // Throw custom exception with 400 status code

        var savedKinProfile = kinProfileRepository.saveAndFlush(new KinProfile(
                request.firstName(),
                request.middleName(),
                request.lastName(),
                request.birthdate(),
                request.birthStatus(),
                request.estimatedBirthdate(),
                request.sex()
        ));

        appUserKinProfileRepository.save(new AppUserKinProfile(
                appUserProfile,
                savedKinProfile,
                true)
        );

        return savedKinProfile;
    }
}
