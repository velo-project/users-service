package com.github.veloproject.userservices.application.queries.get_token_expiration;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTokenExpirationQueryResult extends Response {
    private final Long expiresIn;
    private final Boolean expired;

    public GetTokenExpirationQueryResult(Integer statusCode,
                                         String message,
                                         Long expiresIn,
                                         Boolean expired) {
        super(statusCode, message);
        this.expiresIn = expiresIn;
        this.expired = expired;
    }
}
