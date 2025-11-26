package com.github.veloproject.userservices.presentations.controllers.commands.admin;

import com.github.veloproject.userservices.application.commands.admin.unblock_user.UnblockUserCommand;
import com.github.veloproject.userservices.application.commands.admin.unblock_user.UnblockUserCommandResult;
import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/admin")
public class UnblockUserController {
    private final LoggingMediatorImp mediator;

    public UnblockUserController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PatchMapping("/v1/unblock")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UnblockUserCommandResult> unblockUser(@RequestParam String nickname,
                                                                JwtAuthenticationToken token) {
        var command = new UnblockUserCommand(nickname);
        var response = mediator.send(command, token);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
