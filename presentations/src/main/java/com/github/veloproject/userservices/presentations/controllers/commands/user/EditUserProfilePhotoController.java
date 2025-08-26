package com.github.veloproject.userservices.presentations.controllers.commands.user;

import com.github.veloproject.userservices.application.commands.user.edit_user_profile_photo.EditUserProfilePhotoCommand;
import com.github.veloproject.userservices.application.commands.user.edit_user_profile_photo.EditUserProfilePhotoCommandResult;
import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user_services")
public class EditUserProfilePhotoController {
    private final LoggingMediatorImp mediator;

    public EditUserProfilePhotoController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PatchMapping("/edit_photo")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<EditUserProfilePhotoCommandResult> editUserProfilePhoto(@RequestParam("image") MultipartFile image,
                                                                                  JwtAuthenticationToken token) {
        var command = new EditUserProfilePhotoCommand(image);
        var response = mediator.send(command, token);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
