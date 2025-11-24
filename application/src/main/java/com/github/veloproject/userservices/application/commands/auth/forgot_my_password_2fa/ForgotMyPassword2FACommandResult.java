package com.github.veloproject.userservices.application.commands.auth.forgot_my_password_2fa;

import com.github.veloproject.userservices.application.mediators.contracts.Response;

public class ForgotMyPassword2FACommandResult extends Response {
    public ForgotMyPassword2FACommandResult(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
