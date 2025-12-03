package com.github.veloproject.userservices.application.queries.get_token_expiration.handler;

import com.github.veloproject.userservices.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.application.queries.get_token_expiration.GetTokenExpirationQuery;
import com.github.veloproject.userservices.application.queries.get_token_expiration.GetTokenExpirationQueryResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class GetTokenExpirationQueryHandler extends NoAuthRequestHandler<GetTokenExpirationQuery, GetTokenExpirationQueryResult> {
    private final JwtDecoder jwtDecoder;

    public GetTokenExpirationQueryHandler(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public GetTokenExpirationQueryResult handle(GetTokenExpirationQuery request) {
        try {
            Jwt jwt = jwtDecoder.decode(request.token());
            Instant expiresAt = jwt.getExpiresAt();
            return new GetTokenExpirationQueryResult(200,
                    "Token válido.",
                    Duration.between(Instant.now(), expiresAt).toMinutes(),
                    false);
        } catch (Exception e) {
            return new GetTokenExpirationQueryResult(
                    401,
                    "Token inválido ou expirado.",
                    0L,
                    true
            );
        }
    }
}
