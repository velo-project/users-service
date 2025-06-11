package com.github.veloproject.userservices.commands.edit_user_profile.handler;

import com.github.veloproject.userservices.commands.user.edit_user_profile.EditUserProfileCommand;
import com.github.veloproject.userservices.commands.user.edit_user_profile.handler.EditUserProfileCommandHandler;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.enums.UserProfileUpdatableField;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditUserProfileCommandHandlerTest {
    private UserRepository repository;
    private EditUserProfileCommandHandler handler;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        handler = new EditUserProfileCommandHandler(repository);
    }

    @Test
    void shouldThrowWhenFieldIsNull() {
        var command = new EditUserProfileCommand();
        command.setFieldValue("value");

        var token = mock(JwtAuthenticationToken.class);

        assertThrows(InvalidParameterException.class, () -> handler.handle(command, token));
    }

    @Test
    void shouldThrowWhenFieldValueIsNull() {
        var command = new EditUserProfileCommand();
        command.setField(UserProfileUpdatableField.DESCRIPTION);

        var token = mock(JwtAuthenticationToken.class);

        assertThrows(InvalidParameterException.class, () -> handler.handle(command, token));
    }

    @Test
    void shouldThrowWhenTokenIsNull() {
        var command = new EditUserProfileCommand();
        command.setField(UserProfileUpdatableField.DESCRIPTION);
        command.setFieldValue("value");

        assertThrows(Exception.class, () -> handler.handle(command, null));
    }
}