package com.github.veloproject.userservices.presentations.advicers;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import com.github.veloproject.userservices.domain.exceptions.AlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AlreadyExistsExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Response> handleException(AlreadyExistsException e) {
        int responseStatus = 409;
        return ResponseEntity
                .status(responseStatus)
                .body(new Response(responseStatus, e.getMessage()));
    }
}
