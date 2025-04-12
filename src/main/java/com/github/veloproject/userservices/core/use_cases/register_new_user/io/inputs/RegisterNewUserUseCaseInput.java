package com.github.veloproject.userservices.core.use_cases.register_new_user.io.inputs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class RegisterNewUserUseCaseInput {
    @NotNull
    private final String fullName;

    @NotNull
    private final String prefferedName;

    @Email
    @NotNull
    private final String email;

    @NotNull
    private final String password;
}
