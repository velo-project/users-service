package com.github.veloproject.userservices.presentations.advicers;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import com.github.veloproject.userservices.domain.exceptions.InvalidParameterException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidParameterExceptionHandler {

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Response> handleException(InvalidParameterException e) {
        int responseStatus = 400;
        return ResponseEntity
                .status(responseStatus)
                .body(new Response(responseStatus, e.getMessage()));
    }
}
