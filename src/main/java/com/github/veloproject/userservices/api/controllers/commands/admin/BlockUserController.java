package com.github.veloproject.userservices.api.controllers.commands.admin;

import com.github.veloproject.userservices.commands.admin.block_user.BlockUserCommand;
import com.github.veloproject.userservices.commands.admin.block_user.BlockUserCommandResult;
import com.github.veloproject.userservices.mediators.implementations.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user_services")
public class BlockUserController {
    private final LoggingMediatorImp mediator;

    public BlockUserController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PatchMapping("/block")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BlockUserCommandResult> blockUser(@RequestBody BlockUserCommand command,
                                                            JwtAuthenticationToken token) {
        var response = mediator.send(command, token);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
