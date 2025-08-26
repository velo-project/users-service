package com.github.veloproject.userservices.presentations.controllers.commands.user;

import com.github.veloproject.userservices.application.commands.user.edit_user_profile.EditUserProfileCommand;
import com.github.veloproject.userservices.application.commands.user.edit_user_profile.EditUserProfileCommandResult;
import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user_services")
public class EditUserProfileController {
    private final LoggingMediatorImp mediator;

    public EditUserProfileController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PatchMapping("/edit_profile")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<EditUserProfileCommandResult> editUserProfile(@RequestBody EditUserProfileCommand command,
                                                                        JwtAuthenticationToken token) {
        var response = mediator.send(command, token);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
