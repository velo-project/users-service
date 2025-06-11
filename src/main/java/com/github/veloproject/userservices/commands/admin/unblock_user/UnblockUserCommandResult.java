package com.github.veloproject.userservices.commands.admin.unblock_user;

import com.github.veloproject.userservices.mediators.contracts.Response;

public class UnblockUserCommandResult extends Response {
    public UnblockUserCommandResult(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
