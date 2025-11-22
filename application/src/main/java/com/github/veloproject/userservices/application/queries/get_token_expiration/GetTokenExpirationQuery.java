package com.github.veloproject.userservices.application.queries.get_token_expiration;

import com.github.veloproject.userservices.application.mediators.contracts.Request;

public record GetTokenExpirationQuery(
        String token
) implements Request<GetTokenExpirationQueryResult> {
}
