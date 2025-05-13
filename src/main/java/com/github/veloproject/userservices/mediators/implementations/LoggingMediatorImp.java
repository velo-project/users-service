package com.github.veloproject.userservices.mediators.implementations;

import com.github.veloproject.userservices.mediators.contracts.Mediator;
import com.github.veloproject.userservices.mediators.contracts.Request;
import com.github.veloproject.userservices.mediators.contracts.Response;
import com.github.veloproject.userservices.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.mediators.contracts.handlers.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
public class LoggingMediatorImp implements Mediator {
    private final ApplicationContext appContext;

    public LoggingMediatorImp(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public <TResponse extends Response> TResponse send(Request<TResponse> request) {
        return sendRequestToHandler(request, null);
    }

    public <TResponse extends Response> TResponse send(Request<TResponse> request,
                                                       JwtAuthenticationToken token) {
        return sendRequestToHandler(request, token);
    }

    private <TRequest extends Request<TResponse>, TResponse extends Response> TResponse sendRequestToHandler(
            TRequest request,
            JwtAuthenticationToken token
    ) {
        String className = request.getClass().getSimpleName();
        log.info("{}: Request received.", className);

        String handlerBeanName = Character.toLowerCase(className.charAt(0))
                + className.substring(1)
                + "Handler";

        if (!appContext.containsBean(handlerBeanName)) {
            log.error("{}: No handler bean found.", className);
            throw new NoSuchBeanDefinitionException(handlerBeanName);
        }

        try {
            Object handler = appContext.getBean(handlerBeanName);
            log.info("{}: Using handler '{}'.", className, handlerBeanName);

            if (!(handler instanceof RequestHandler<?, ?> baseHandler)) {
                log.error("{}: Invalid handler type.", className);
                throw new IllegalStateException("Handler does not implement RequestHandler.");
            }

            return switch (baseHandler.getHandlerAuthType()) {
                case AUTHENTICATION -> {
                    if (token == null) {
                        log.error("{}: Authenticated request but token is null.", className);
                        throw new InvalidBearerTokenException("Missing Bearer token.");
                    }
                    @SuppressWarnings("unchecked")
                    var typed = (AuthRequestHandler<TRequest, TResponse>) handler;
                    yield handleWithLogs(className, () -> typed.handle(request, token));
                }

                case NO_AUTHENTICATION -> {
                    @SuppressWarnings("unchecked")
                    var typed = (NoAuthRequestHandler<TRequest, TResponse>) handler;
                    yield handleWithLogs(className, () -> typed.handle(request));
                }
            };

        } catch (Exception e) {
            log.error("{}: Exception occurred - {}", className, e.getMessage(), e);
            throw e;
        }
    }

    private <T> T handleWithLogs(String className,
                                 Supplier<T> handlerAction) {
        log.info("{}: Handling request.", className);
        T result = handlerAction.get();
        log.info("{}: Handled successfully.", className);
        return result;
    }
}
