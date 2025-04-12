package com.github.veloproject.userservices.core.entities.factory;

import com.github.veloproject.userservices.core.entities.HashPassword;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashPasswordFactory {
    public static HashPassword createNewInstanceOf(String value)
    {
        return HashPassword.builder()
                .value(value)
                .build();
    }

}
