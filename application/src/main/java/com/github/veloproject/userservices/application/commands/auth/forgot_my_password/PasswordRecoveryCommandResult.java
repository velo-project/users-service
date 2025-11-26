package com.github.veloproject.userservices.application.commands.auth.forgot_my_password;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRecoveryCommandResult extends Response {
    private final String key;

    public PasswordRecoveryCommandResult(Integer statusCode, String message, String key) {
        super(statusCode, message);
        this.key = key;
    }
}
