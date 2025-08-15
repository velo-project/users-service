package com.github.veloproject.presentations.controllers.commands;

import com.github.veloproject.application.commands.auth.login.LoginUserCommand;
import com.github.veloproject.application.commands.auth.login.LoginUserCommandResult;
import com.github.veloproject.application.mediators.implementations.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class LoginUserController {
    private final LoggingMediatorImp mediator;

    public LoginUserController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/v1/login")
    public ResponseEntity<LoginUserCommandResult> loginUser(@RequestBody LoginUserCommand command) {
        var response = mediator.send(command);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
