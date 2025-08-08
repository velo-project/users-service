package com.github.veloproject.application.mediators.contracts;

public interface Mediator {
    <TResponse extends Response> TResponse send(Request<TResponse> request);
}
