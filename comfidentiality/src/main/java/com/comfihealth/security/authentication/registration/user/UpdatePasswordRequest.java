package com.comfihealth.security.authentication.registration.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class UpdatePasswordRequest {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;
}
