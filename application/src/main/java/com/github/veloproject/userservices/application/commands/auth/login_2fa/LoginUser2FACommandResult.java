package com.github.veloproject.userservices.application.commands.auth.login_2fa;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser2FACommandResult extends Response {
    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
    private Long refreshTokenExpiresIn;

    public LoginUser2FACommandResult(Integer statusCode, String message, String accessToken, Long expiresIn, String refreshToken, Long refreshTokenExpiresIn) {
        super(statusCode, message);
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }
}
