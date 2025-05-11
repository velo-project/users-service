package com.github.veloproject.userservices.mediators.contracts;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface RequestHandler<TRequest extends Request<TResponse>, TResponse extends Response> {
    TResponse handle(TRequest request);

    default TResponse handle(TRequest request, JwtAuthenticationToken token) {
        return handle(request);
    }
}
