package com.github.veloproject.application.abstractions.cache;

import java.time.Duration;

public interface IMemoryCache {
    void save(String code, String value, Duration duration);
}
