package com.github.veloproject.userservices.commands.edit_user_profile.handler;

import com.github.veloproject.userservices.commands.edit_user_profile.EditUserProfileCommand;
import com.github.veloproject.userservices.commands.edit_user_profile.EditUserProfileCommandResult;
import com.github.veloproject.userservices.mediators.contracts.RequestHandler;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class EditUserProfileCommandHandler implements RequestHandler<EditUserProfileCommand, EditUserProfileCommandResult> {
    private final UserRepository repository;

    public EditUserProfileCommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public EditUserProfileCommandResult handle(EditUserProfileCommand request,
                                               JwtAuthenticationToken token) {
        if (token == null) throw new InvalidBearerTokenException("Bearer token is null.");
        var user = repository.getReferenceById(Integer.valueOf(token.getName()));

        switch (request.getField()) {
            case BANNER_PHOTO -> user.setBannerPhotoUrl(request.getFieldValue());
            case DESCRIPTION -> user.setDescription(request.getFieldValue());
            case PROFILE_PHOTO -> user.setProfilePhotoUrl(request.getFieldValue());
            case PREFERRED_NAME -> user.setPrefferedName(request.getFieldValue());
            default -> throw new InvalidParameterException("field");
        }
        repository.save(user);

        return new EditUserProfileCommandResult(
                200,
                "Field '" + request.getField() + "' successfully modified."
        );
    }

    @Override
    @Deprecated
    public EditUserProfileCommandResult handle(EditUserProfileCommand request) {
        throw new UnsupportedOperationException("Error while handling request: Not supported.");
    }
}
