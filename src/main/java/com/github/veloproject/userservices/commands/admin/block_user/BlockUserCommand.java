package com.github.veloproject.userservices.commands.admin.block_user;

import com.github.veloproject.userservices.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockUserCommand implements Request<BlockUserCommandResult> {
    private String nickname;

    public BlockUserCommand() {}
    public BlockUserCommand(String nickname) {
        this.nickname = nickname;
    }
}
