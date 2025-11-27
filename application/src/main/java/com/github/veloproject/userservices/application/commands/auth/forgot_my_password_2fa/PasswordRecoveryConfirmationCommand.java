package com.github.veloproject.userservices.application.commands.auth.forgot_my_password_2fa;

import com.github.veloproject.userservices.application.mediators.contracts.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordRecoveryConfirmationCommand(
    @NotBlank @Size(max = 550) String key,
    @NotBlank @Size(max = 6) String code,
    @NotBlank @Size(max = 25) String newPassword
) implements Request<PasswordRecoveryConfirmationCommandResult> {
}
