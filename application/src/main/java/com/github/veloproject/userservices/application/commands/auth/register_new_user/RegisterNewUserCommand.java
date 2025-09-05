package com.github.veloproject.userservices.application.commands.auth.register_new_user;

import com.github.veloproject.userservices.application.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterNewUserCommand implements Request<RegisterNewUserCommandResult> {
    private String name;
    private String nickname;
    private String email;
    private String password;
}
