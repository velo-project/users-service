package com.github.veloproject.userservices.commands.admin.delete_user;

import com.github.veloproject.userservices.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserCommand implements Request<DeleteUserCommandResult> {
    private String nickname;

    public DeleteUserCommand(String nickname) {
        this.nickname = nickname;
    }
}
