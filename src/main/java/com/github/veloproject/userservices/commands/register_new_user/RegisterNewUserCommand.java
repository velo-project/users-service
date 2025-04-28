package com.github.veloproject.userservices.commands.register_new_user;

import com.github.veloproject.userservices.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterNewUserCommand implements Request<RegisterNewUserCommandResult> {
    private String name;
    private String email;
    private String password;
}
