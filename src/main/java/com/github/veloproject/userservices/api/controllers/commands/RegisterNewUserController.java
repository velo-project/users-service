package com.github.veloproject.userservices.api.controllers.commands;

import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommand;
import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommandResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/userservices")
public class RegisterNewUserController {
    public RegisterNewUserController() {}

    @PostMapping("/register")
    public RegisterNewUserCommandResult registerNewUser(@RequestBody RegisterNewUserCommand command) {
        return null;
    }
}
