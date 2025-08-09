package com.github.veloproject.application.commands.auth.login;

import com.github.veloproject.application.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserCommand implements Request<LoginUserCommandResult> {
    private String email;
    private String password;
}
