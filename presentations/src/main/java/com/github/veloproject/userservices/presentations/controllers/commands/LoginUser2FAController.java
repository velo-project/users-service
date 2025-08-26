package com.github.veloproject.userservices.presentations.controllers.commands;

import com.github.veloproject.userservices.application.commands.auth.login_2fa.LoginUser2FACommand;
import com.github.veloproject.userservices.application.commands.auth.login_2fa.LoginUser2FACommandResult;
import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class LoginUser2FAController {
    private final LoggingMediatorImp mediator;

    public LoginUser2FAController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/v1/login/2fa")
    public ResponseEntity<LoginUser2FACommandResult> loginUser2FA(@RequestBody LoginUser2FACommand command) {
        var response = mediator.send(command);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
