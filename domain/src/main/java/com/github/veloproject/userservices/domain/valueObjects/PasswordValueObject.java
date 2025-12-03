package com.github.veloproject.userservices.domain.valueObjects;

import com.github.veloproject.userservices.domain.cryptography.SecureEncoder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class PasswordValueObject {
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
            throw new IllegalArgumentException("Senha nula.");

        rawPassword = rawPassword.trim();

        return SecureEncoder.encrypt(rawPassword);
    }

    public Boolean compare(String rawPassword) {
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Senha nula.");

        return SecureEncoder.compare(rawPassword, this.password);
    }
}
