package com.comfihealth.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppUserProfileRepository extends JpaRepository<AppUserProfile, Long> {

    @Query("select p from app_user_profiles p where p.appUserUsername = ?1")
    AppUserProfile getAppUserProfileDetails(String appUserUsername);

    @Query("select p from app_user_profiles p where p.appUserUsername = ?1")
    Optional<AppUserProfile> findByUsername(String appUserUsername);

}
