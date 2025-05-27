package com.github.veloproject.userservices.shared.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CryptographyUtilsTest {

    @Test
    void shouldEncryptPassword() {
        // Arrange
        var password = "12345678";

        // Act
        var result = CryptographyUtils.encrypt(password);

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("$2a$12"));
    }

    @Test
    void shouldMatchPasswords() {
        // Arrange
        var password = "12345678";
        var hashedPassword = CryptographyUtils.encrypt(password);

        // Act
        var result = CryptographyUtils.compare(password, hashedPassword);

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldFailBecausePasswordInvalidValueToEncrypt() {
        // Arrange
        var password = "";

        // Act
        var result = assertThrows(IllegalArgumentException.class, () -> CryptographyUtils.encrypt(password));

        // Assert
        assertNotNull(result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "12345678, ''",
            "'' , 12345678"
    }, nullValues = "NULL")
    void shouldFailtBecausePasswordInvalidValueToCompare(String password, String toCompare) {
        // Act
        var result = assertThrows(IllegalArgumentException.class, () -> CryptographyUtils.compare(password, toCompare));

        // Assert
        assertNotNull(result);
    }
}