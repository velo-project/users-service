package com.github.veloproject.userservices.application.commands.auth.refresh_token;

import com.github.veloproject.userservices.application.mediators.contracts.Request;

public record RefreshTokenCommand(
        String refreshToken
) implements Request<RefreshTokenCommandResult> {
}
