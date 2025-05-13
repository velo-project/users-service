package com.github.veloproject.userservices.mediators.contracts.handlers;

import com.github.veloproject.userservices.mediators.contracts.Request;
import com.github.veloproject.userservices.mediators.contracts.Response;
import com.github.veloproject.userservices.mediators.enums.HandlerAuthType;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public abstract class AuthRequestHandler
        <TRequest extends Request<TResponse>, TResponse extends Response>
        extends RequestHandler<TRequest, TResponse> {

    public abstract TResponse handle(TRequest request,
                                        JwtAuthenticationToken token);

    protected AuthRequestHandler() {
        super((HandlerAuthType.AUTHENTICATION));
    }
}
