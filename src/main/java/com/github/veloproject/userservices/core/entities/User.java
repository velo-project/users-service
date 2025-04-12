package com.github.veloproject.userservices.core.entities;

import lombok.*;


@Getter
@Setter
@Builder
public class User {
    private String fullName;
    private String preferredName;
    private String email;
    private HashPassword hashPassword;
}
