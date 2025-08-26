package com.github.veloproject.userservices.application.mediators.contracts;

public interface Mediator {
    <TResponse extends Response> TResponse send(Request<TResponse> request);
}
