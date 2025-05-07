package com.github.veloproject.userservices.commands.login_user;

import com.github.veloproject.userservices.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserCommandResult extends Response {
    private String token;
    private Long expiresIn;

    public LoginUserCommandResult(Integer status, String message, String token, Long expiresIn) {
        super(status, message);
        this.token = token;
        this.expiresIn = expiresIn;
    }
}