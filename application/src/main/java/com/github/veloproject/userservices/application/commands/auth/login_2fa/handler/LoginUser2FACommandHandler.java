package com.github.veloproject.userservices.application.commands.auth.login_2fa.handler;

import com.github.veloproject.userservices.application.abstractions.cache.IMemoryCache;
import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.commands.auth.login_2fa.LoginUser2FACommand;
import com.github.veloproject.userservices.application.commands.auth.login_2fa.LoginUser2FACommandResult;
import com.github.veloproject.userservices.application.dtos.TFACode;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.domain.entities.RoleEntity;
import com.github.veloproject.userservices.domain.entities.UserEntity;
import com.github.veloproject.userservices.domain.exceptions.IncorrectInformationsProvidedException;
import com.google.gson.Gson;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@Service
public class LoginUser2FACommandHandler extends NoAuthRequestHandler<LoginUser2FACommand, LoginUser2FACommandResult> {
    private final IUserRepository repository;
    private final IMemoryCache cache;
    private final JwtEncoder tokenEncoder;
    private final Gson gson;

    public LoginUser2FACommandHandler(IUserRepository repository, IMemoryCache cache, JwtEncoder tokenEncoder) {
        this.repository = repository;
        this.cache = cache;
        this.tokenEncoder = tokenEncoder;
        gson = new Gson();
    }

    @Override
    public LoginUser2FACommandResult handle(LoginUser2FACommand request) {
        var code = cache.get(request.getKey());
        var isDeleted = cache.delete(request.getKey());

        if (!isDeleted)
            throw new RuntimeException();

        var codeObject = gson.fromJson(code, TFACode.class);
        var user = repository.findByEmail(codeObject.getEmail())
                .orElseThrow(IncorrectInformationsProvidedException::new);
        /* 0,5 HORA -- AJUSTE CONFORME NECESSÁRIO */
        var expiresIn = 30L;

        var token = generateJwt(user, expiresIn);

        return new LoginUser2FACommandResult(
                200,
                "User sucessfully authenticated",
                token,
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
                .claim("email", user.getEmail())
                .expiresAt(
                        OffsetDateTime.now()
                        .plusMinutes(expiresIn)
                        .toInstant())
                .build();

        return tokenEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
}
