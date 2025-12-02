package com.github.veloproject.userservices.presentations.controllers.commands.user;

import com.github.veloproject.userservices.application.commands.user.edit_user_profile_photo.EditUserProfilePhotoCommand;
import com.github.veloproject.userservices.application.commands.user.edit_user_profile_photo.EditUserProfilePhotoCommandResult;
import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class EditUserProfilePhotoController {
    private final LoggingMediatorImp mediator;

    public EditUserProfilePhotoController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @PutMapping(value = "/v1/edit_photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<EditUserProfilePhotoCommandResult> editUserProfilePhoto(
            @RequestParam("image") @Valid @NotNull MultipartFile image,
            JwtAuthenticationToken token) {
        var command = new EditUserProfilePhotoCommand(image);
        var response = mediator.send(command, token);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
