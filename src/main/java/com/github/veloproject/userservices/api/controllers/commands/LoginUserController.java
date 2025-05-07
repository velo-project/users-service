package com.github.veloproject.userservices.api.controllers.commands;

import com.github.veloproject.userservices.commands.login_user.LoginUserCommand;
import com.github.veloproject.userservices.commands.login_user.LoginUserCommandResult;
import com.github.veloproject.userservices.mediators.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user_services")
public class LoginUserController {
    private final LoggingMediatorImp mediator;

    public LoginUserController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserCommandResult> registerNewUser(@RequestBody LoginUserCommand command) {
        var response = mediator.send(command);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }
}
