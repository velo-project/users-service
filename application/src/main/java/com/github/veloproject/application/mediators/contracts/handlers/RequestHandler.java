package com.github.veloproject.application.mediators.contracts.handlers;

import com.github.veloproject.application.mediators.contracts.Request;
import com.github.veloproject.application.mediators.contracts.Response;
import com.github.veloproject.application.mediators.enums.HandlerAuthType;
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
