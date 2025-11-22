package com.github.veloproject.userservices.presentations.controllers.commands.auth;

import com.github.veloproject.userservices.application.commands.auth.refresh_token.RefreshTokenCommand;
import com.github.veloproject.userservices.application.commands.auth.refresh_token.RefreshTokenCommandResult;
import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class RefreshTokenController {
    private final LoggingMediatorImp mediator;

    public RefreshTokenController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/v1/refresh")
    public ResponseEntity<RefreshTokenCommandResult> refreshToken(
            @RequestBody RefreshTokenCommand command
    ) {
        var response = mediator.send(command);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
