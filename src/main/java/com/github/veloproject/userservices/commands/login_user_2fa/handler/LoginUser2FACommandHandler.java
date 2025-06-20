package com.github.veloproject.userservices.commands.login_user_2fa.handler;

import com.github.veloproject.userservices.commands.login_user_2fa.LoginUser2FACommand;
import com.github.veloproject.userservices.commands.login_user_2fa.LoginUser2FACommandResult;
import com.github.veloproject.userservices.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.persistence.entities.RoleEntity;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.IncorrectInformationsProvided;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class LoginUser2FACommandHandler
        extends NoAuthRequestHandler<LoginUser2FACommand, LoginUser2FACommandResult> {
    private final UserRepository repository;
    private final StringRedisTemplate redisTemplate;
    private final JwtEncoder jwtEncoder;

    public LoginUser2FACommandHandler(UserRepository repository, StringRedisTemplate redisTemplate, JwtEncoder jwtEncoder) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public LoginUser2FACommandResult handle(LoginUser2FACommand request) {
        if (request.getKey() == null || request.getKey().isEmpty()
                || request.getCode() == null || request.getCode().isEmpty()) {
            throw new InvalidParameterException("Key and code are required.");
        }
        if (!request.getKey().startsWith("2fa:")) {
            throw new InvalidParameterException("Invalid key format.");
        }
        if (!validate2FACode(request.getKey(), request.getCode())) {
            throw new IncorrectInformationsProvided();
        }

        String email = request.getKey().substring(4);
        var user = repository.getByEmail(email)
                .filter(u -> !u.getIsDeleted())
                .orElseThrow(IncorrectInformationsProvided::new);
        var expiresIn = 500L;
        String token = generateJwt(user, expiresIn);

        return new LoginUser2FACommandResult(
                200,
                "User successfully authenticated.",
                token,
                expiresIn
        );
    }

    public boolean validate2FACode(String key, String code) {
        String storedCode = redisTemplate.opsForValue().get(key);
        if (storedCode != null && storedCode.equals(code)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }

    private String generateJwt(UserEntity user, Long expiresIn) {
        var now = Instant.now();
        var scopes = user.getRoles()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("velo-user-services")
                .subject(user.getId().toString())
                .issuedAt(now)
                .claim("scope", scopes)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
}
