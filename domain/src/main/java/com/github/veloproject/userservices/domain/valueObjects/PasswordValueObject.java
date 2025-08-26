package com.github.veloproject.userservices.domain.valueObjects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class PasswordValueObject {
    @Getter(AccessLevel.PRIVATE)
    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private final String password;

    public PasswordValueObject(String rawPassword) {
        this(rawPassword, true);
    }

    public PasswordValueObject(String rawPassword, Boolean isToEncrypt) {
        if (!isToEncrypt) {
            this.password = rawPassword;
            return;
        }

        this.password = encode(rawPassword);
    }

    private String encode(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty())
            throw new IllegalArgumentException("Password is null");

        rawPassword = rawPassword.trim();

        return encoder.encode(rawPassword);
    }

    public Boolean compare(String rawPassword) {
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Password is null");

        String encodedPassword = encode(rawPassword);

        return encoder.matches(password, encodedPassword);
    }
}
