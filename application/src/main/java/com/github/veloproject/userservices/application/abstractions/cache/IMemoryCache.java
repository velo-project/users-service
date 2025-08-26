package com.github.veloproject.userservices.application.abstractions.cache;

import java.time.Duration;

public interface IMemoryCache {
    void save(String code, String value, Duration duration);
    String get(String code);
    boolean delete(String code);
}
