package com.github.veloproject.userservices.commands.edit_user_profile.handler;

import com.github.veloproject.userservices.commands.user.edit_user_profile.EditUserProfileCommand;
import com.github.veloproject.userservices.commands.user.edit_user_profile.EditUserProfileCommandResult;
import com.github.veloproject.userservices.commands.user.edit_user_profile.handler.EditUserProfileCommandHandler;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.enums.UserProfileUpdatableField;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
    void shouldUpdatePreferredNameSuccessfully() {
        // Arrange
        var command = new EditUserProfileCommand();
        command.setField(UserProfileUpdatableField.NICKNAME);
        command.setFieldValue("NewName");

        var token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn("1");

        var user = new UserEntity();
        when(repository.getReferenceById(1)).thenReturn(user);

        // Act
        EditUserProfileCommandResult result = handler.handle(command, token);

        // Assert
        assertEquals(200, result.getStatusCode());
        assertEquals("Field 'PREFERRED_NAME' successfully updated.", result.getMessage());

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(repository).save(captor.capture());

        assertEquals("NewName", captor.getValue().getNickname());
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

    @Test
    void shouldThrowWhenPreferredNameTooShort() {
        var command = new EditUserProfileCommand();
        command.setField(UserProfileUpdatableField.NICKNAME);
        command.setFieldValue("A");

        var token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn("1");

        var user = new UserEntity();
        when(repository.getReferenceById(1)).thenReturn(user);

        assertThrows(InvalidParameterException.class, () -> handler.handle(command, token));
    }
}