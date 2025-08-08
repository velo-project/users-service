package com.github.veloproject.application.mediators.contracts;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Response {
    private Integer statusCode;
    private String message;
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime timestamp;

    public Response(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
