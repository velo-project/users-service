package com.github.veloproject.userservices.infrastructure.cache;

import com.github.veloproject.userservices.application.abstractions.cache.IMemoryCache;
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

    @Override
    public String get(String code) {
        return redisTemplate.opsForValue().get(code);

    }

    @Override
    public boolean delete(String code) {
        var value = get(code);

        if (value != null && !value.isEmpty()) {
            redisTemplate.delete(code);
            return true;
        }

        return false;
    }
}
