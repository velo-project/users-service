package com.github.veloproject.userservices.presentations.controllers.commands.admin;

import com.github.veloproject.userservices.application.commands.admin.delete_user.DeleteUserCommand;
import com.github.veloproject.userservices.application.commands.admin.delete_user.DeleteUserCommandResult;
import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/admin/")
public class DeleteUserController {
    private final LoggingMediatorImp mediator;

    public DeleteUserController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @DeleteMapping("/v1/delete")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<DeleteUserCommandResult> deleteUser(
            @RequestParam @Valid
            @NotBlank
            @Size(max = 20)
            String nickname,
            JwtAuthenticationToken token
    ) {
        var command = new DeleteUserCommand(nickname);
        var response = mediator.send(command, token);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
