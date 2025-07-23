package com.github.veloproject.userservices.api.controllers.advicers;

import com.github.veloproject.userservices.mediators.contracts.Response;
import com.github.veloproject.userservices.shared.exceptions.IncorrectInformationsProvidedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IncorrectInformationsProvidedExceptionHandler {
    @ExceptionHandler(IncorrectInformationsProvidedException.class)
    public ResponseEntity<Response> handleException() {
        int responseStatus = 400;
        return ResponseEntity
                .status(responseStatus)
                .body(new Response(responseStatus, "Incorrect informations provided."));
    }
}
