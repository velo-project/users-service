package com.github.veloproject.userservices.presentations.controllers.commands.auth;

import com.github.veloproject.userservices.application.commands.auth.register_new_user.RegisterNewUserCommand;
import com.github.veloproject.userservices.application.commands.auth.register_new_user.RegisterNewUserCommandResult;
import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user_services")
public class RegisterNewUserController {
    private final LoggingMediatorImp mediator;

    public RegisterNewUserController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterNewUserCommandResult> registerNewUser(@RequestBody RegisterNewUserCommand command) {
        var response = mediator.send(command);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
