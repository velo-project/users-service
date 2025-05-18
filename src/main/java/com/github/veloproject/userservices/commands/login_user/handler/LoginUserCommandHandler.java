package com.github.veloproject.userservices.commands.login_user.handler;

import com.github.veloproject.userservices.commands.login_user.LoginUserCommand;
import com.github.veloproject.userservices.commands.login_user.LoginUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.persistence.entities.RoleEntity;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.IncorrectInformationsProvided;
import com.github.veloproject.userservices.shared.utils.CryptographyUtils;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class LoginUserCommandHandler extends NoAuthRequestHandler<LoginUserCommand, LoginUserCommandResult> {
    private final JwtEncoder jwtEncoder;
    private final UserRepository repository;

    public LoginUserCommandHandler(UserRepository repository, JwtEncoder jwtEncoder) {
        this.repository = repository;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public LoginUserCommandResult handle(LoginUserCommand request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()
                || request.getPassword() == null || request.getPassword().isEmpty())
            throw new IncorrectInformationsProvided();

        var user = repository.getByEmail(request.getEmail())
                .filter(u -> CryptographyUtils.compare(request.getPassword(), u.getPassword()))
                .orElseThrow(IncorrectInformationsProvided::new);

        Long expiresIn = 500L;
        var jwtValue = generateJwt(user, expiresIn);

        return new LoginUserCommandResult(
                200,
                "Logged in.",
                jwtValue,
                expiresIn
        );
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

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
