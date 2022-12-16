package com.comfihealth.profiles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "app_user_profiles")
@Getter
@Setter
@NoArgsConstructor
public class AppUserProfile {

    @Id
    @GeneratedValue(
            generator = "app_user_profile_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "app_user_profile_sequence",
            sequenceName = "app_user_profile_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String appUserUsername; // Define the main application user

    @ManyToMany
    @JoinTable(
            name = "app_user_kin_profiles",
            joinColumns = @JoinColumn(name = "app_user_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "kin_profile_id")
    )
    Set<KinProfile> kinProfiles;

    public AppUserProfile(String appUserUsername, Set<KinProfile> kinProfiles) {
        this.appUserUsername = appUserUsername;
    }
}
