package com.github.veloproject.userservices.application.commands.auth.forgot_my_password_2fa;

import com.github.veloproject.userservices.application.mediators.contracts.Request;

public record ForgotMyPassword2FACommand(
    String key,
    Integer code
) implements Request<ForgotMyPassword2FACommandResult> {
}
