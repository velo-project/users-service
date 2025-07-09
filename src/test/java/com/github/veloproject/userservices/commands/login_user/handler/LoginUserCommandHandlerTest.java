package com.github.veloproject.userservices.commands.login_user.handler;

import com.github.veloproject.userservices.commands.login_user.LoginUserCommand;
import com.github.veloproject.userservices.persistence.entities.RoleEntity;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.emails.EmailService;
import com.github.veloproject.userservices.shared.exceptions.IncorrectInformationsProvidedException;
import com.github.veloproject.userservices.shared.utils.CryptographyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginUserCommandHandlerTest {
    @Mock
    UserRepository userRepository;

    @Mock
    StringRedisTemplate redisTemplate;

    @Mock
    EmailService emailService;

    @Mock
    ValueOperations<String, String> valueOperations;

    LoginUserCommandHandler handler;

    @BeforeEach
    void beforeEach() {
        handler = new LoginUserCommandHandler(userRepository, redisTemplate, emailService);
        Mockito.lenient().when(redisTemplate.opsForValue()).thenReturn(valueOperations);
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
        user.setIsDeleted(false);
        user.setIsBlocked(false);
        user.setId(1);

        var role = new RoleEntity();
        role.setId(1);
        role.setName("User");
        var roles = Set.of(role);

        user.setRoles(roles);

        Mockito.when(userRepository.getByEmail(command.getEmail())).thenReturn(Optional.of(user));

        Mockito.doNothing().when(valueOperations).set(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(Duration.class)
        );

        Mockito.doNothing().when(emailService).sendSimpleMail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

        var result = handler.handle(command);

        // Assert
        assertEquals(200, result.getStatusCode());
        assertEquals("Waiting for confirmation.", result.getMessage());

        Mockito.verify(emailService).sendSimpleMail(Mockito.eq(user.getEmail()), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(valueOperations).set(Mockito.anyString(), Mockito.anyString(), Mockito.any(Duration.class));
    }

    @Test
    void shouldFailBecauseWrongInformationsProvided() {
        var command = new LoginUserCommand();
        command.setEmail("johndoe@email.com");
        command.setPassword("12345678");

        Mockito.when(userRepository.getByEmail(command.getEmail())).thenReturn(Optional.empty());

        var exception = assertThrows(IncorrectInformationsProvidedException.class, () -> handler.handle(command));
        assertEquals("Error while handling request: Incorrect informations provided.", exception.getMessage());
    }
}