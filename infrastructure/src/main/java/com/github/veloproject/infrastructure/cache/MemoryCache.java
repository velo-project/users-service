package com.github.veloproject.infrastructure.cache;

import com.github.veloproject.application.abstractions.cache.IMemoryCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
public class MemoryCache implements IMemoryCache {
    private final StringRedisTemplate redisTemplate;

    public MemoryCache(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String code, String value, Duration duration) {
        redisTemplate.opsForValue()
                .set(code, value, duration);
    }
}
