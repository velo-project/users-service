package com.github.veloproject.userservices.api.controllers.commands;

import com.github.veloproject.userservices.commands.edit_user_profile.EditUserProfileCommand;
import com.github.veloproject.userservices.commands.edit_user_profile.EditUserProfileCommandResult;
import com.github.veloproject.userservices.mediators.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/edit_profile")
    public ResponseEntity<EditUserProfileCommandResult> editUserProfile(@RequestBody EditUserProfileCommand command,
                                                                        JwtAuthenticationToken token) {
        var response = mediator.send(command, token);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }
}
