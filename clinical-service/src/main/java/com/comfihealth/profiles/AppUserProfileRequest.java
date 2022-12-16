package com.comfihealth.profiles;

import java.util.Set;

/**
 * Define the request to store an AppUserProfile
 *
 * @param appUserUsername the username of the app user
 */
public record AppUserProfileRequest(
                String appUserUsername,
                Set<KinProfile> kinProfiles
) {
}
