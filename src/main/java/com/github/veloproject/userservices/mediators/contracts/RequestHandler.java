package com.github.veloproject.userservices.mediators.contracts;

public interface RequestHandler<TRequest extends Request<TResponse>, TResponse> {
    TResponse handle(TRequest request);
}
