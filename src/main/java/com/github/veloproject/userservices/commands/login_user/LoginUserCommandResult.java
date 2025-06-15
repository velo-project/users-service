package com.github.veloproject.userservices.commands.login_user;

import com.github.veloproject.userservices.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserCommandResult extends Response {
    private String key;

    public LoginUserCommandResult(Integer status, String message, String key) {
        super(status, message);
        this.key = key;
    }
}