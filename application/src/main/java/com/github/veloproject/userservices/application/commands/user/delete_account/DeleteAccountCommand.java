package com.github.veloproject.userservices.application.commands.user.delete_account;

import com.github.veloproject.userservices.application.mediators.contracts.Request;

public record DeleteAccountCommand() implements Request<DeleteAccountCommandResult> {
}
