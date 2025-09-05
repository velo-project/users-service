package com.github.veloproject.userservices.application.mediators.contracts.handlers;


import com.github.veloproject.userservices.application.mediators.contracts.Request;
import com.github.veloproject.userservices.application.mediators.contracts.Response;
import com.github.veloproject.userservices.application.mediators.enums.HandlerAuthType;

public abstract class NoAuthRequestHandler<TRequest extends Request<TResponse>, TResponse extends Response> extends RequestHandler<TRequest, TResponse> {
    public abstract TResponse handle(TRequest request);

    protected NoAuthRequestHandler() {
        super(HandlerAuthType.NO_AUTHENTICATION);
    }
}
