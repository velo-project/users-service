package com.github.veloproject.userservices.commands.admin.delete_user;

import com.github.veloproject.userservices.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserCommandResult extends Response {
    public DeleteUserCommandResult(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
