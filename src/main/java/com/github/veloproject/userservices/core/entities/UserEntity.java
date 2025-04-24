package com.github.veloproject.userservices.core.entities;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserEntity {
    private Long userId;
    private String fullName;
    private String preferredName;
    private String email;
    private String password;
}
