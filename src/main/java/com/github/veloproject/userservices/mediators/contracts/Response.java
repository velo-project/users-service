package com.github.veloproject.userservices.mediators.contracts;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Response {
    private Integer status;
    private String message;
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime timestamp;

    public Response(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
