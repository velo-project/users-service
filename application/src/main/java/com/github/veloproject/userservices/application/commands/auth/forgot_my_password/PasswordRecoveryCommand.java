package com.github.veloproject.userservices.application.commands.auth.forgot_my_password;

import com.github.veloproject.userservices.application.mediators.contracts.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordRecoveryCommand(
    @NotBlank
    @Size(max = 60)
    String email
) implements Request<PasswordRecoveryCommandResult> {
}
