package com.github.veloproject.userservices.application.commands.auth.refresh_token;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenCommandResult extends Response {
    private final String refreshToken;
    private final Long expiresIn;

    public RefreshTokenCommandResult(Integer statusCode,
                                     String message,
                                     String refreshToken,
                                     Long expiresIn) {
        super(statusCode, message);
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }
}
