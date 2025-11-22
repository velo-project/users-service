package com.github.veloproject.userservices.application.commands.auth.refresh_token.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.commands.auth.refresh_token.RefreshTokenCommand;
import com.github.veloproject.userservices.application.commands.auth.refresh_token.RefreshTokenCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.domain.entities.RoleEntity;
import com.github.veloproject.userservices.domain.entities.UserEntity;
import com.github.veloproject.userservices.domain.exceptions.InvalidParameterException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@Service
public class RefreshTokenCommandHandler extends NoAuthRequestHandler<RefreshTokenCommand, RefreshTokenCommandResult> {
    private final JwtDecoder jwtDecoder;
    private final JwtEncoder tokenEncoder;
    private final IUserRepository repository;

    public RefreshTokenCommandHandler(JwtDecoder jwtDecoder, JwtEncoder tokenEncoder, IUserRepository repository) {
        this.jwtDecoder = jwtDecoder;
        this.tokenEncoder = tokenEncoder;
        this.repository = repository;
    }

    @Override
    public RefreshTokenCommandResult handle(RefreshTokenCommand request) {
        if (request.refreshToken() == null || request.refreshToken().isEmpty()) {
            throw new InvalidParameterException("Refresh Token is null or empty.");
        }

        try {
            var jwt = jwtDecoder.decode(request.refreshToken());

            var userId = jwt.getSubject();
            var user = repository.findById(Integer.valueOf(userId))
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            /* 24 HORAS -- AJUSTE CONFORME NECESSÁRIO */
            var expiresIn = 1440L;
            var newAccessToken = generateJwt(user, expiresIn);

            return new RefreshTokenCommandResult(
                    200,
                    "Token renovado com sucesso",
                    newAccessToken,
                    expiresIn
            );
        } catch (Exception e) {
            return new RefreshTokenCommandResult(
                    401,
                    "Refresh token inválido ou expirado",
                    null,
                    0L
            );
        }
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
                .expiresAt(OffsetDateTime.now().plusMinutes(expiresIn).toInstant())
                .build();

        return tokenEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}