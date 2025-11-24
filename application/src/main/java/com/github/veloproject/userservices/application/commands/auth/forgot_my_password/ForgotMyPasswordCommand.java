package com.github.veloproject.userservices.application.commands.auth.forgot_my_password;

import com.github.veloproject.userservices.application.mediators.contracts.Request;

public record ForgotMyPasswordCommand(

) implements Request<ForgotMyPasswordCommandResult> {
}
