package com.github.veloproject.userservices.application.commands.auth.forgot_my_password_2fa;

import com.github.veloproject.userservices.application.mediators.contracts.Request;

public record PasswordRecoveryConfirmationCommand(
    String key,
    String code,
    String newPassword
) implements Request<PasswordRecoveryConfirmationCommandResult> {
}
