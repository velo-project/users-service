package com.github.veloproject.userservices.commands.login_user.handler;

import com.github.veloproject.userservices.commands.login_user.LoginUserCommand;
import com.github.veloproject.userservices.commands.login_user.LoginUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.RequestHandler;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.IncorrectInformationsProvided;
import com.github.veloproject.userservices.shared.utils.CryptographyUtils;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LoginUserCommandHandler implements RequestHandler<LoginUserCommand, LoginUserCommandResult> {
    private final JwtEncoder jwtEncoder;
    private final UserRepository repository;

    public LoginUserCommandHandler(UserRepository repository, JwtEncoder jwtEncoder) {
        this.repository = repository;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public LoginUserCommandResult handle(LoginUserCommand request) {
        var user = validateUser(request.getEmail(), request.getPassword());

        var now = Instant.now();
        var expiresIn = 300L;
        var claims = JwtClaimsSet.builder()
                .issuer("velo-user-services")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginUserCommandResult(
                200,
                "Successfully registered.",
                jwtValue,
                expiresIn
        );
    }

    private UserEntity validateUser(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) throw new IncorrectInformationsProvided();

        return repository.getByEmail(email)
                .filter(u -> CryptographyUtils.compare(password, u.getPassword()))
                .orElseThrow(IncorrectInformationsProvided::new);
    }
}
