package com.github.veloproject.userservices.api.controllers.commands;

import com.github.veloproject.userservices.commands.login_user_2fa.LoginUser2FACommand;
import com.github.veloproject.userservices.commands.login_user_2fa.LoginUser2FACommandResult;
import com.github.veloproject.userservices.mediators.implementations.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user_services")
public class LoginUser2FAController {
    private final LoggingMediatorImp mediator;

    public LoginUser2FAController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/login/2fa")
    public ResponseEntity<LoginUser2FACommandResult> loginUser2FA(@RequestBody LoginUser2FACommand command) {
        var response = mediator.send(command);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
