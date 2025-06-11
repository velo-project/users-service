package com.github.veloproject.userservices.commands.user.edit_user_profile.handler;

import com.github.veloproject.userservices.commands.user.edit_user_profile.EditUserProfileCommand;
import com.github.veloproject.userservices.commands.user.edit_user_profile.EditUserProfileCommandResult;
import com.github.veloproject.userservices.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.enums.UserProfileUpdatableField;
import com.github.veloproject.userservices.shared.exceptions.AlreadyExistsException;
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
            case DESCRIPTION -> user.setDescription(fieldValue);
            case NICKNAME -> {
                validateNickname(fieldValue);
                user.setNickname(fieldValue);
            }
            default -> throw new InvalidParameterException("Field '" + field + "' is not supported to update.");
        }

        repository.save(user);
    }

    private void validateNickname(String fieldValue) {
        String regex = "^[a-zA-Z0-9._]{2,20}$";
        if (repository.existsByNickname(fieldValue)) throw new AlreadyExistsException("Nickname already registered.");
        else if (!fieldValue.matches(regex)) throw new InvalidParameterException("Nickname must be valid.");
    }
}
