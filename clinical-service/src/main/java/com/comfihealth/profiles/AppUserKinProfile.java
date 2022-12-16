package com.comfihealth.profiles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "app_user_kin_profiles")
@Getter
@Setter
@NoArgsConstructor
public class AppUserKinProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "app_user_kin_profile_sequence",
            sequenceName = "app_user_kin_profile_sequence",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "app_user_profile_id")
    private AppUserProfile appUserProfile;

    @ManyToOne
    @JoinColumn(name = "kin_profile_id")
    private KinProfile kinProfile;

    @Column(nullable = true)
    private Boolean isDirectKin;

    public AppUserKinProfile(AppUserProfile appUserProfile, KinProfile kinProfile, Boolean isDirectKin) {
        this.appUserProfile = appUserProfile;
        this.kinProfile = kinProfile;
        this.isDirectKin = isDirectKin;
    }
}
