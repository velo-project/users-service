package com.github.veloproject.userservices.application.commands.admin.delete_user;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserCommandResult extends Response {
    public DeleteUserCommandResult(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
