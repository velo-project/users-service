package com.github.veloproject.application.mediators.contracts.handlers;

import com.github.veloproject.application.mediators.contracts.Request;
import com.github.veloproject.application.mediators.contracts.Response;
import com.github.veloproject.application.mediators.enums.HandlerAuthType;
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
