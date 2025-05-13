package com.github.veloproject.userservices.api.controllers.advicers;

import com.github.veloproject.userservices.mediators.contracts.Response;
import com.github.veloproject.userservices.shared.exceptions.InternalErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InternalErrorExceptionHandler {
    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<Response> handleException() {
        int responseStatus = 500;
        return ResponseEntity
                .status(responseStatus)
                .body(new Response(responseStatus, "An Internal Error Ocurred."));
    }
}
