package com.github.veloproject.application.commands.auth.register_new_user;

import com.github.veloproject.application.mediators.contracts.Request;
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
