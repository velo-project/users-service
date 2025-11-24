package com.github.veloproject.userservices.application.commands.auth.forgot_my_password;

import com.github.veloproject.userservices.application.mediators.contracts.Response;

public class ForgotMyPasswordCommandResult extends Response {
    public ForgotMyPasswordCommandResult(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
