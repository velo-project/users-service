package com.github.veloproject.userservices.commands.admin.unblock_user;

import com.github.veloproject.userservices.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnblockUserCommand implements Request<UnblockUserCommandResult> {
    private String nickname;

    public UnblockUserCommand(String nickname) {
        this.nickname = nickname;
    }
}
