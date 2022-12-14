package com.comfihealth.security.authentication.registration.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.*;

/**
 * Represents the user DTO, used to validate data
 * for user registration.
 *
 * @since 0.1.0
 * @author elijah
 */
@AllArgsConstructor
@Getter
public class UserRegistrationRequest {

    @NotEmpty
    @NotNull
    private String username;

    @NotEmpty
    @NotNull
    @Email
    private String email;

    @Null
    @Length(min = 8)
    private String password;

    @NotEmpty
    @NotNull
    private String firstName;

    @NotEmpty
    @NotNull
    private String lastName;

    @NotEmpty
    @NotNull
    private String sex;

    @Null
    private String phoneNumber;

    @NotEmpty
    @NotNull
    @Length(min = 3, max = 50)
    private String country;

    @NotEmpty
    @NotNull
    @Length(min = 3, max = 50)
    private String city;

    @NotEmpty
    @NotNull
    @Length(min = 3, max = 50)
    private String address;
}
