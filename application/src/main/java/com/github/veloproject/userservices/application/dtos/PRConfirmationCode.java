package com.github.veloproject.userservices.application.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PRConfirmationCode {
    private final String email;
    private final String code;
}
