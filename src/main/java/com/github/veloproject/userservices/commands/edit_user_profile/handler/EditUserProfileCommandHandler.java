package com.github.veloproject.userservices.commands.edit_user_profile.handler;

import com.github.veloproject.userservices.commands.edit_user_profile.EditUserProfileCommand;
import com.github.veloproject.userservices.commands.edit_user_profile.EditUserProfileCommandResult;
import com.github.veloproject.userservices.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.enums.UserProfileUpdatableField;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditUserProfileCommandHandler extends AuthRequestHandler<EditUserProfileCommand, EditUserProfileCommandResult> {
    private final UserRepository repository;

    public EditUserProfileCommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public EditUserProfileCommandResult handle(EditUserProfileCommand request,
                                               JwtAuthenticationToken token) {
        if (request.getField() == null) throw new InvalidParameterException("Field must be specified.");
        else if (request.getFieldValue() == null) throw new InvalidParameterException("fieldValue must be specified.");
        else if (token == null) throw new InvalidBearerTokenException("Bearer Token must be specified.");

        var user = repository.getReferenceById(Integer.valueOf(token.getName()));
        updateField(request.getField(), request.getFieldValue(), user);

        return new EditUserProfileCommandResult(
                200,
                "Field '" + request.getField() + "' successfully updated."
        );
    }

    private void updateField(UserProfileUpdatableField field, String fieldValue, UserEntity user) {
        switch (field) {
            case BANNER_PHOTO -> user.setBannerPhotoUrl(fieldValue);
            case DESCRIPTION -> user.setDescription(fieldValue);
            case PROFILE_PHOTO -> user.setProfilePhotoUrl(fieldValue);
            case PREFERRED_NAME -> {
                validatePreferredName(fieldValue);
                user.setPrefferedName(fieldValue);
            }
            default -> throw new InvalidParameterException("Field '" + field + "' is not supported to update.");
        }

        repository.save(user);
    }

    private void validatePreferredName(String fieldValue) {
        if (fieldValue.length() > 20 || fieldValue.length() < 2) throw new InvalidParameterException("Preferred name field value must have between 2 and 20 characters.");
    }
}
