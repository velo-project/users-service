package com.github.veloproject.userservices.commands.login_user;

import com.github.veloproject.userservices.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserCommand implements Request<LoginUserCommandResult> {
    private String email;
    private String password;
}
