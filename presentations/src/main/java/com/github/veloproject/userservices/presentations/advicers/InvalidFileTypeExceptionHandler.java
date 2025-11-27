package com.github.veloproject.userservices.presentations.advicers;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import com.github.veloproject.userservices.domain.exceptions.InvalidFileTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidFileTypeExceptionHandler {
    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<Response> handleException(InvalidFileTypeException e) {
        int responseStatus = 400;
        return ResponseEntity
                .status(responseStatus)
                .body(new Response(responseStatus, e.getMessage()));
    }
}
