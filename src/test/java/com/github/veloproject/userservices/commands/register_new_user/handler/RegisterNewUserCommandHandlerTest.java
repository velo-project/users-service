package com.github.veloproject.userservices.commands.register_new_user.handler;

import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommand;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterNewUserCommandHandlerTest {
    @Mock
    UserRepository userRepository;

    RegisterNewUserCommandHandler handler;

    @BeforeEach
    void beforeEach() {
        handler = new RegisterNewUserCommandHandler(userRepository);
    }

    @AfterEach
    void afterEach() {
        reset(userRepository);
    }

    @Test
    void shouldCreateUserSuccessfuly() {
        // Arrange
        var command = new RegisterNewUserCommand();
        command.setEmail("johndoe@example.com");
        command.setPassword("12345678");
        command.setName("John Doe");

        var user = new UserEntity(
                command.getName(),
                command.getEmail(),
                ""
        );
        user.setId(1);

        when(userRepository.save(any())).thenReturn(user);

        // Act
        var result = handler.handle(command);

        // Assert
        assertEquals("Successfully registered.", result.getMessage());
        assertEquals(200, result.getStatus());
        assertEquals(1, result.getCreatedUserId());
        assertNotNull(result.getCreatedUserId());
    }

    @ParameterizedTest
    @ValueSource(strings = { "johndoe.example.com", "johndo", " johndoe@example.c om", "themisterandgreatjohndoe@mypersonalandbeautifulemailprovider.com" })
    void shouldFailBecauseInvalidEmail(String email) {
        // Arrange
        var command = new RegisterNewUserCommand();
        command.setEmail(email);
        command.setPassword("12345678");
        command.setName("John Doe");

        var user = new UserEntity(
                command.getName(),
                command.getEmail(),
                ""
        );
        user.setId(1);

        when(userRepository.save(any())).thenReturn(user);

        // Act
       var result = assertThrows(InvalidParameterException.class, () -> handler.handle(command));

        // Assert
        assertEquals("Email address must be valid.", result.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "jo", "john" , "the great and master joooooooooooooooooooooooooooooooooooooooooooooooohn doeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" })
    void shouldFailBecauseInvalidName(String name) {
        // Arrange
        var command = new RegisterNewUserCommand();
        command.setEmail("johndoe@example.com");
        command.setPassword("12345678");
        command.setName(name);

        var user = new UserEntity(
                command.getName(),
                command.getEmail(),
                ""
        );
        user.setId(1);

        when(userRepository.save(any())).thenReturn(user);

        // Act
        var result = assertThrows(InvalidParameterException.class, () -> handler.handle(command));

        // Assert
        assertEquals("Name must be valid.", result.getMessage());
    }


    @ParameterizedTest
    @ValueSource(strings = { "1234567", "123456789123456789123", "1234 56789" })
    void shouldFailBecauseInvalidPassword(String password) {
        // Arrange
        var command = new RegisterNewUserCommand();
        command.setEmail("johndoe@example.com");
        command.setPassword(password);
        command.setName("John Doe");

        var user = new UserEntity(
                command.getName(),
                command.getEmail(),
                ""
        );
        user.setId(1);

        when(userRepository.save(any())).thenReturn(user);

        // Act
        var result = assertThrows(InvalidParameterException.class, () -> handler.handle(command));

        // Assert
        assertEquals("Password must have between 8 and 20 characters and no invalid characters.", result.getMessage());
    }
}