package com.github.veloproject.userservices.core.entities;

import lombok.*;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PUBLIC)
public class HashPassword {
    private String value;

    public void validate()
    {
    }
}
