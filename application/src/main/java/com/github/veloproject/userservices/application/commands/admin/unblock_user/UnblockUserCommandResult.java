package com.github.veloproject.userservices.application.commands.admin.unblock_user;

import com.github.veloproject.userservices.application.mediators.contracts.Response;

public class UnblockUserCommandResult extends Response {
    public UnblockUserCommandResult(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
