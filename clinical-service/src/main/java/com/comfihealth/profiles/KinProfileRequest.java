package com.comfihealth.profiles;

import java.time.LocalDate;

public record KinProfileRequest(
        String firstName,
        String middleName,
        String lastName,
        LocalDate birthdate,
        String birthStatus,
        LocalDate estimatedBirthdate,
        char sex,
        String appUserUsername
) {
}
