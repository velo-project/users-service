package com.github.veloproject.userservices.presentations.controllers.commands.user;

import com.github.veloproject.userservices.application.commands.user.delete_account.DeleteAccountCommand;
import com.github.veloproject.userservices.application.commands.user.delete_account.DeleteAccountCommandResult;
import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class DeleteAccountController {
    private final LoggingMediatorImp mediator;

    public DeleteAccountController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @DeleteMapping("/v1/delete")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<DeleteAccountCommandResult> deleteAccount(
            JwtAuthenticationToken token
    ) {
        var command = new DeleteAccountCommand();
        var response = mediator.send(command, token);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
