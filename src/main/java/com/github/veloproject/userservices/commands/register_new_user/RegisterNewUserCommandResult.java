package com.github.veloproject.userservices.commands.register_new_user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterNewUserCommandResult {
    private Integer createdUserId;
}
