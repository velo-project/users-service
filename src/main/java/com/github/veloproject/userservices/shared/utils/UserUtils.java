package com.github.veloproject.userservices.shared.utils;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserUtils {
    public static String generateDeletedUserNickname() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "deleted_" + uuid.substring(0, 12);
    }
}
