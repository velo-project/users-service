package com.github.veloproject.userservices.commands.register_new_user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterNewUserCommand {
    private String name;
    private String email;
    private String password;
}
