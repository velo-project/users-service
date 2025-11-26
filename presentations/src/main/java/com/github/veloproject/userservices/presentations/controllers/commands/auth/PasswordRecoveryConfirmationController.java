package com.github.veloproject.userservices.presentations.controllers.commands.auth;

import com.github.veloproject.userservices.application.commands.auth.forgot_my_password_2fa.PasswordRecoveryConfirmationCommand;
import com.github.veloproject.userservices.application.commands.auth.forgot_my_password_2fa.PasswordRecoveryConfirmationCommandResult;
import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class PasswordRecoveryConfirmationController {
    private final LoggingMediatorImp mediator;

    public PasswordRecoveryConfirmationController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/v1/password-recovery/confirmation")
    public ResponseEntity<PasswordRecoveryConfirmationCommandResult> passwordRecoveryConfirmation(
            @RequestBody PasswordRecoveryConfirmationCommand command
            ) {
        var response = mediator.send(command);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
