package com.github.veloproject.userservices.shared.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UserUtils {
    public static String generateDeletedUserNickname() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "deleted_" + uuid.substring(0, 12);
    }
}
