package com.github.veloproject.userservices.application.commands.auth.refresh_token;

import com.github.veloproject.userservices.application.mediators.contracts.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenCommand(
        @NotBlank
        @Size(max = 550)
        String refreshToken
) implements Request<RefreshTokenCommandResult> {
}
