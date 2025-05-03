package com.github.veloproject.userservices.shared.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@UtilityClass
public class CryptographyUtils {
    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public static String encrypt(String value) throws IllegalArgumentException {
        if (value == null || value.isEmpty()) throw new IllegalArgumentException();
        value = value.trim();

        return encoder
                .encode(value);
    }

    public static Boolean compare(String value, String compare) throws IllegalArgumentException {
        if (value == null || value.isEmpty()) throw new IllegalArgumentException();
        if (compare == null || compare.isEmpty()) throw new IllegalArgumentException();

        return encoder
                .matches(value, compare);
    }
}
