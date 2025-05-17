package com.github.veloproject.userservices.mediators.implementations;

import com.github.veloproject.userservices.mediators.contracts.Request;
import com.github.veloproject.userservices.mediators.contracts.Response;
import com.github.veloproject.userservices.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.mediators.contracts.handlers.RequestHandler;
import com.github.veloproject.userservices.mediators.enums.HandlerAuthType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoggingMediatorImpTest {

    @Mock
    ApplicationContext applicationContext;

    @Mock
    JwtAuthenticationToken jwtToken;

    LoggingMediatorImp mediator;

    @BeforeEach
    void setUp() {
        mediator = new LoggingMediatorImp(applicationContext);
    }

    static class TestRequest implements Request<TestResponse> {}
    static class TestResponse extends Response {
        public TestResponse() {
            super(200, "");
        }
    }

    @Test
    void shouldHandleRequestWithoutAuthentication() {
        // Arrange
        var request = new TestRequest();
        var response = new TestResponse();

        var handler = mock(NoAuthRequestHandler.class);
        when(handler.getHandlerAuthType()).thenReturn(HandlerAuthType.NO_AUTHENTICATION);
        when(handler.handle(request)).thenReturn(response);

        when(applicationContext.containsBean("testRequestHandler")).thenReturn(true);
        when(applicationContext.getBean("testRequestHandler")).thenReturn(handler);

        // Act
        var result = mediator.send(request);

        // Assert
        assertSame(response, result);
        verify(handler).handle(request);
    }

    @Test
    void shouldHandleRequestWithAuthentication() {
        // Arrange
        var request = new TestRequest();
        var response = new TestResponse();

        var handler = mock(AuthRequestHandler.class);
        when(handler.getHandlerAuthType()).thenReturn(HandlerAuthType.AUTHENTICATION);
        when(handler.handle(request, jwtToken)).thenReturn(response);

        when(applicationContext.containsBean("testRequestHandler")).thenReturn(true);
        when(applicationContext.getBean("testRequestHandler")).thenReturn(handler);

        // Act
        var result = mediator.send(request, jwtToken);

        // Assert
        assertSame(response, result);
        verify(handler).handle(request, jwtToken);
    }

    @Test
    void shouldThrowIfTokenIsMissingForAuthHandler() {
        // Arrange
        var request = new TestRequest();
        var handler = mock(AuthRequestHandler.class);
        when(handler.getHandlerAuthType()).thenReturn(HandlerAuthType.AUTHENTICATION);

        when(applicationContext.containsBean("testRequestHandler")).thenReturn(true);
        when(applicationContext.getBean("testRequestHandler")).thenReturn(handler);

        // Act & Assert
        assertThrows(InvalidBearerTokenException.class, () -> mediator.send(request));
    }

    @Test
    void shouldThrowIfHandlerBeanDoesNotExist() {
        // Arrange
        var request = new TestRequest();
        when(applicationContext.containsBean("testRequestHandler")).thenReturn(false);

        // Act & Assert
        assertThrows(NoSuchBeanDefinitionException.class, () -> mediator.send(request));
    }

    @Test
    void shouldThrowIfHandlerIsOfWrongType() {
        // Arrange
        var request = new TestRequest();
        var fakeHandler = new Object(); // not a RequestHandler

        when(applicationContext.containsBean("testRequestHandler")).thenReturn(true);
        when(applicationContext.getBean("testRequestHandler")).thenReturn(fakeHandler);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> mediator.send(request));
    }

    @Test
    void shouldLogAndPropagateExceptionsFromHandler() {
        // Arrange
        var request = new TestRequest();
        var handler = mock(NoAuthRequestHandler.class);
        when(handler.getHandlerAuthType()).thenReturn(HandlerAuthType.NO_AUTHENTICATION);
        when(handler.handle(request)).thenThrow(new RuntimeException("Something went wrong"));

        when(applicationContext.containsBean("testRequestHandler")).thenReturn(true);
        when(applicationContext.getBean("testRequestHandler")).thenReturn(handler);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> mediator.send(request));
        assertEquals("Something went wrong", exception.getMessage());
    }
}
