package com.github.veloproject.userservices.commands.login_user.handler;

import com.github.veloproject.userservices.commands.login_user.LoginUserCommand;
import com.github.veloproject.userservices.commands.login_user.LoginUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.IncorrectInformationsProvided;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import com.github.veloproject.userservices.shared.utils.CryptographyUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
public class LoginUserCommandHandler extends NoAuthRequestHandler<LoginUserCommand, LoginUserCommandResult> {
    private final UserRepository repository;
    private final StringRedisTemplate redisTemplate;

    public LoginUserCommandHandler(UserRepository repository,
                                   StringRedisTemplate redisTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public LoginUserCommandResult handle(LoginUserCommand request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()
                || request.getPassword() == null || request.getPassword().isEmpty())
            throw new InvalidParameterException("Email and password are required.");

        var user = repository.getByEmail(request.getEmail())
                .filter(u -> !u.getIsDeleted())
                .filter(u -> CryptographyUtils.compare(request.getPassword(), u.getPassword()))
                .orElseThrow(IncorrectInformationsProvided::new);

        String key = generate2FACodeAndReturnsKey(user.getEmail());

        return new LoginUserCommandResult(
                200,
                "Waiting for confirmation.",
                key
        );
    }

    private String generate2FACodeAndReturnsKey(String userEmail) {
        String code = String
                .format("%06d", new SecureRandom().nextInt(999999));
        String key = "2fa:" + userEmail;
        redisTemplate
                .opsForValue()
                .set(key, code, Duration.ofMinutes(5));

        return key;
    }
}
