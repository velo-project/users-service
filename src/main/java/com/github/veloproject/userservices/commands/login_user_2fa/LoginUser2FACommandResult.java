package com.github.veloproject.userservices.commands.login_user_2fa;

import com.github.veloproject.userservices.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser2FACommandResult extends Response {
    private String accessToken;
    private Long expiresIn;

    public LoginUser2FACommandResult(Integer statusCode, String message, String accessToken, Long expiresIn) {
        super(statusCode, message);
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}
