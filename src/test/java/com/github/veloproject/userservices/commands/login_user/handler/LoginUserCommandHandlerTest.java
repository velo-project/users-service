package com.github.veloproject.userservices.commands.login_user.handler;

import com.github.veloproject.userservices.commands.login_user.LoginUserCommand;
import com.github.veloproject.userservices.persistence.entities.RoleEntity;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.IncorrectInformationsProvided;
import com.github.veloproject.userservices.shared.utils.CryptographyUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginUserCommandHandlerTest {
    @Mock
    JwtEncoder jwtEncoder;

    @Mock
    UserRepository userRepository;

    LoginUserCommandHandler handler;

    @BeforeEach
    void beforeEach() {
        handler = new LoginUserCommandHandler(userRepository, jwtEncoder);
    }

    @AfterEach
    void afterEach() {
        Mockito.reset(userRepository);
        Mockito.reset(jwtEncoder);
    }

    @Test
    void shouldLoginSuccessfully() {
        // Arrange
        var command = new LoginUserCommand();
        command.setEmail("johndoe@email.com");
        command.setPassword("12345678");

        var user = new UserEntity(
                "John Doe",
                "john.doe",
                command.getEmail(),
                CryptographyUtils.encrypt(command.getPassword())
        );
        user.setId(1);

        var role = new RoleEntity();
        role.setId(1);
        role.setName("User");
        var roles = Set.of(role);

        user.setRoles(roles);

        var jwt = Jwt.withTokenValue("mock-token")
                .header("alg", "HS256")
                .claim("sub", "1")
                .build();

        Mockito.when(jwtEncoder.encode(Mockito.any())).thenReturn(jwt);
        Mockito.when(userRepository.getByEmail(command.getEmail())).thenReturn(Optional.of(user));

        // Act
        var result = handler.handle(command);

        // Assert
        assertEquals(200, result.getStatusCode());
        assertEquals("Logged in.", result.getMessage());
        assertEquals(500L, result.getExpiresIn());
    }

    @Test
    void shouldFailBecauseWrongInformationsProvided() {
        // Arrange
        var command = new LoginUserCommand();
        command.setEmail("johndoe@email.com");
        command.setPassword("12345678");

        var jwt = Jwt.withTokenValue("mock-token")
                .header("alg", "HS256")
                .claim("sub", "1")
                .build();

        Mockito.when(jwtEncoder.encode(Mockito.any())).thenReturn(jwt);
        Mockito.when(userRepository.getByEmail(command.getEmail())).thenReturn(Optional.empty());

        // Act
        var result = assertThrows(IncorrectInformationsProvided.class, () ->handler.handle(command));

        // Assert
        assertEquals("Error while handling request: Incorrect informations provided.", result.getMessage());
    }
}