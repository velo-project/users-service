package com.github.veloproject.userservices.application.commands.user.edit_user_profile.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.commands.user.edit_user_profile.EditUserProfileCommand;
import com.github.veloproject.userservices.application.commands.user.edit_user_profile.EditUserProfileCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.domain.entities.UserEntity;
import com.github.veloproject.userservices.domain.enums.UserProfileUpdatableField;
import com.github.veloproject.userservices.domain.exceptions.AlreadyExistsException;
import com.github.veloproject.userservices.domain.exceptions.InvalidParameterException;
import com.github.veloproject.userservices.domain.exceptions.NotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditUserProfileCommandHandler extends AuthRequestHandler<EditUserProfileCommand, EditUserProfileCommandResult> {
    private final IUserRepository repository;

    public EditUserProfileCommandHandler(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public EditUserProfileCommandResult handle(EditUserProfileCommand request,
                                               JwtAuthenticationToken token) {
        var user = repository.findById(Integer.valueOf(token.getName()))
                .orElseThrow(() -> new NotFoundException("User"));
        updateField(request.getField(), request.getFieldValue(), user);

        return new EditUserProfileCommandResult(
                200,
                "Field '" + request.getField() + "' successfully updated."
        );
    }

    private void updateField(UserProfileUpdatableField field, String fieldValue, UserEntity user) {
        switch (field) {
            case DESCRIPTION -> {
                if (fieldValue.length() > 255) throw new InvalidParameterException("The description must be a maximum of 255 characters.");
                user.setDescription(fieldValue);
            }
            case NICKNAME -> {
                validateNickname(fieldValue);
                user.setNickname(fieldValue);
            }
            case NAME -> {
                validateName(fieldValue);
                user.setName(fieldValue);
            }
            default -> throw new InvalidParameterException("Field '" + field + "' is not supported to update on this method.");
        }

        repository.save(user);
    }

    private void validateName(String name) {
        if (name.isEmpty()) {
            throw new InvalidParameterException("Name must be valid.");
        }
    }

    private void validateNickname(String fieldValue) {
        String regex = "^[a-zA-Z0-9._]{2,20}$";
        if (repository.existsByNickname(fieldValue)) throw new AlreadyExistsException("Nickname already registered.");
        else if (!fieldValue.matches(regex)) throw new InvalidParameterException("Nickname must be valid.");
    }
}
