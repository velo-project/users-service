package com.github.veloproject.userservices.application.commands.auth.refresh_token.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.commands.auth.refresh_token.RefreshTokenCommand;
import com.github.veloproject.userservices.application.commands.auth.refresh_token.RefreshTokenCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.domain.entities.RoleEntity;
import com.github.veloproject.userservices.domain.entities.UserEntity;
import com.github.veloproject.userservices.domain.exceptions.InvalidParameterException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class RefreshTokenCommandHandler extends NoAuthRequestHandler<RefreshTokenCommand, RefreshTokenCommandResult> {
    private final JwtEncoder tokenEncoder;
    private final IUserRepository repository;
    private final RSAPublicKey publicKey;

    public RefreshTokenCommandHandler(JwtEncoder tokenEncoder,
                                      IUserRepository repository,
                                      @Value("${jwt.public.key}") RSAPublicKey publicKey) {
        this.tokenEncoder = tokenEncoder;
        this.repository = repository;
        this.publicKey = publicKey;
    }

    @Override
    public RefreshTokenCommandResult handle(RefreshTokenCommand request) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(request.refreshToken());

            if (!validateTokenSignature(signedJWT)) {
                return new RefreshTokenCommandResult(
                        400,
                        "Refresh token has invalid signature.",
                        null,
                        0L
                );
            }

            Date expirationTime = signedJWT
                    .getJWTClaimsSet()
                    .getExpirationTime();
            if (expirationTime == null) {
                return new RefreshTokenCommandResult(
                        400,
                        "Refresh token does not contain expiration time.",
                        null,
                        0L
                );
            }

            Instant expiresAt = expirationTime.toInstant();
            Instant now = Instant.now();

            if (expiresAt.isBefore(now)) {
                Instant oneHourAgo = now.minusSeconds(3600);
                if (expiresAt.isBefore(oneHourAgo)) {
                    return new RefreshTokenCommandResult(
                            401,
                            "Refresh token is expired more than the limit time.",
                            null,
                            0L
                    );
                }
            }

            String userId = signedJWT
                    .getJWTClaimsSet()
                    .getSubject();
            if (userId == null || userId.isEmpty()) {
                return new RefreshTokenCommandResult(
                        400,
                        "Refresh token does not contain a valid subject.",
                        null,
                        0L
                );
            }

            var user = repository.findById(Integer.valueOf(userId))
                    .orElseThrow(() -> new RuntimeException("User not found."));

            /* 24 HORAS -- AJUSTE CONFORME NECESSÁRIO */
            var expiresIn = 60L * 24L;
            var newAccessToken = generateJwt(user, expiresIn);

            return new RefreshTokenCommandResult(
                    200,
                    "Token renovated.",
                    newAccessToken,
                    expiresIn
            );
        } catch (ParseException e) {
            return new RefreshTokenCommandResult(
                    400,
                    "Refresh token is invalid or malformed.",
                    null,
                    0L
            );
        } catch (NumberFormatException e) {
            return new RefreshTokenCommandResult(
                    400,
                    "Invalid user ID format in token.",
                    null,
                    0L
            );
        } catch (Exception e) {
            return new RefreshTokenCommandResult(
                    500,
                    "Error while processing token.",
                    null,
                    0L
            );
        }
    }

    /**
     * Valida a assinatura do token sem verificar expiração
     */
    private boolean validateTokenSignature(SignedJWT signedJWT) {
        try {
            JWSVerifier verifier = new RSASSAVerifier(publicKey);
            return signedJWT
                    .verify(verifier);
        } catch (Exception e) {
            return false;
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
                .claim("nickname", user.getNickname())
                .claim("name", user.getName())
                .expiresAt(
                        OffsetDateTime.now()
                                .plusMinutes(expiresIn)
                                .toInstant())
                .build();

        return tokenEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}