package com.github.veloproject.userservices.application.queries.get_token_expiration;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTokenExpirationQueryResult extends Response {
    private Long expiresIn;

    public GetTokenExpirationQueryResult(Integer statusCode,
                                         String message,
                                         Long expiresIn) {
        super(statusCode, message);
        this.expiresIn = expiresIn;
    }
}
