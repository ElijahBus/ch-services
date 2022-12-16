package com.comfihealth.profiles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "kin_profiles")
@Getter
@Setter
@NoArgsConstructor
public class KinProfile {

    @Id
    @GeneratedValue(
            generator = "kin_profile_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "kin_profile_sequence",
            sequenceName = "kin_profile_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    private String birthStatus; // make it an enum with number => value like definition

    private LocalDate estimatedBirthdate;

    @Column(nullable = false)
    private char sex;

    //TODO: Add timestamps fields with JPA magics

    public KinProfile(String firstName,
                      String middleName,
                      String lastName,
                      LocalDate birthdate,
                      String birthStatus,
                      LocalDate estimatedBirthdate,
                      char sex
    ) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.birthStatus = birthStatus;
        this.estimatedBirthdate = estimatedBirthdate;
        this.sex = sex;
    }
}
