package com.github.veloproject.userservices.presentations.advicers;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentNotValidExceptionResponse> handleValidationException(MethodArgumentNotValidException ex) {
        var errors = new ArrayList<>();
        var statusCode = 400;

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.add(fieldName + ": " + errorMessage);
                });

        return ResponseEntity
                .status(statusCode)
                .body(new MethodArgumentNotValidExceptionResponse(
                        statusCode,
                        "Ocorreu um erro durante a validação dos parâmetros fornecidos.",
                        errors
                ));
    }

    @Getter
    @Setter
    public class MethodArgumentNotValidExceptionResponse extends Response {
        private final ArrayList<Object> errorList;

        public MethodArgumentNotValidExceptionResponse(Integer statusCode,
                                               String message,
                                               ArrayList<Object> errorList) {
            super(statusCode, message);
            this.errorList = errorList;
        }
    }
}