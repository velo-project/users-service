package com.github.veloproject.userservices.presentations.advicers;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;

@RestControllerAdvice
public class EmailSendingErrorExceptionHandler {
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Response> handleException(InvalidParameterException e) {
        int responseStatus = 500;
        return ResponseEntity
                .status(responseStatus)
                .body(new Response(responseStatus, "An error has occurred while sending the email."));
    }
}
