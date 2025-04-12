package com.github.veloproject.userservices.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter(AccessLevel.PUBLIC)
public abstract class UseCaseOutputBase {
    private final int httpStatusCode;
    private final String message;
}
