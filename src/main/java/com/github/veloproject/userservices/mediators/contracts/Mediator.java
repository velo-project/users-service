package com.github.veloproject.userservices.mediators.contracts;

public interface Mediator {
    <TResponse> TResponse send(Request<TResponse> request);
}
