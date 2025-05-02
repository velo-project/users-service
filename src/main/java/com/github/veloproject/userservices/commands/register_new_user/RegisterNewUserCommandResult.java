package com.github.veloproject.userservices.commands.register_new_user;

import com.github.veloproject.userservices.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterNewUserCommandResult extends Response {
    private Integer createdUserId;

    public RegisterNewUserCommandResult(Integer status, String message, Integer createdUserId) {
        super(status, message);
        this.createdUserId = createdUserId;
    }
}
