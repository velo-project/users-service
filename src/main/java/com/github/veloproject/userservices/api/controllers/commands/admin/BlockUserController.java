package com.github.veloproject.userservices.api.controllers.commands.admin;

import com.github.veloproject.userservices.commands.admin.block_user.BlockUserCommand;
import com.github.veloproject.userservices.commands.admin.block_user.BlockUserCommandResult;
import com.github.veloproject.userservices.commands.admin.delete_user.DeleteUserCommand;
import com.github.veloproject.userservices.mediators.implementations.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user_services/admin")
public class BlockUserController {
    private final LoggingMediatorImp mediator;

    public BlockUserController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PatchMapping("/block")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BlockUserCommandResult> blockUser(@RequestParam String nickname,
                                                            JwtAuthenticationToken token) {
        var command = new BlockUserCommand(nickname);
        var response = mediator.send(command, token);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
