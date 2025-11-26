package com.github.veloproject.userservices.application.commands.user.delete_account;

import com.github.veloproject.userservices.application.mediators.contracts.Response;

public class DeleteAccountCommandResult extends Response {
    public DeleteAccountCommandResult(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
