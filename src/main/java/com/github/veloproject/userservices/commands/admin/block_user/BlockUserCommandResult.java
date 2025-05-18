package com.github.veloproject.userservices.commands.admin.block_user;

import com.github.veloproject.userservices.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockUserCommandResult extends Response {
    public BlockUserCommandResult(Integer status, String message) {
        super(status, message);
    }
}
