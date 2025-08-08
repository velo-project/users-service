package com.github.veloproject.application.mediators.contracts.handlers;


import com.github.veloproject.application.mediators.contracts.Request;
import com.github.veloproject.application.mediators.contracts.Response;
import com.github.veloproject.application.mediators.enums.HandlerAuthType;

public abstract class NoAuthRequestHandler<TRequest extends Request<TResponse>, TResponse extends Response> extends RequestHandler<TRequest, TResponse> {
    public abstract TResponse handle(TRequest request);

    protected NoAuthRequestHandler() {
        super(HandlerAuthType.NO_AUTHENTICATION);
    }
}
