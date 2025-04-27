package com.github.veloproject.userservices.mediators;

import com.github.veloproject.userservices.mediators.contracts.Mediator;
import com.github.veloproject.userservices.mediators.contracts.Request;
import com.github.veloproject.userservices.mediators.contracts.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j(topic = "LoggingMediator")
@Component
public class LoggingMediatorImp implements Mediator {
    private final ApplicationContext appContext;

    @Autowired
    public LoggingMediatorImp(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public <TResponse> TResponse send(Request<TResponse> request) {
        var className = request.getClass().getSimpleName();
        log.info("Request received: {}", className);

        var handlerBeanName = Character.toLowerCase(className.charAt(0)) + className.substring(1) + "Handler";

        if (!appContext.containsBean(handlerBeanName)) {
            log.error("No handler bean found: {}", handlerBeanName);
            throw new NoSuchBeanDefinitionException("Handler not found: " + handlerBeanName);
        }
        try {
            @SuppressWarnings("unchecked")
            var handler = (RequestHandler<Request<TResponse>, TResponse>) appContext.getBean(handlerBeanName);

            log.info("Handling request: {}", handlerBeanName);
            return handler.handle(request);
        } catch (Exception e) {
            log.error("Bean type invalid: {}", handlerBeanName);
            throw new IllegalArgumentException("Bean type invalid: " + handlerBeanName + " |", e);
        }
    }
}
