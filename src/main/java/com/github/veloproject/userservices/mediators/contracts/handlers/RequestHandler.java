package com.github.veloproject.userservices.mediators.contracts.handlers;

import com.github.veloproject.userservices.mediators.contracts.Request;
import com.github.veloproject.userservices.mediators.contracts.Response;
import com.github.veloproject.userservices.mediators.enums.HandlerAuthType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RequestHandler<TRequest extends Request<TResponse>, TResponse extends Response> {
    private HandlerAuthType handlerAuthType;

    protected RequestHandler(HandlerAuthType handlerAuthType) {
        this.handlerAuthType = handlerAuthType;
    }
}
