package com.github.veloproject.userservices.application.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TFACode {
    private String email;
    private String code;
}
