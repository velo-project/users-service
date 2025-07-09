package com.github.veloproject.userservices.api.controllers.advicers;

import com.github.veloproject.userservices.mediators.contracts.Response;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotFoundExceptionHandler {
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Response> handleException(InvalidParameterException e) {
        int responseStatus = 404;
        return ResponseEntity
                .status(responseStatus)
                .body(new Response(responseStatus, e.getMessage()));
    }
}
