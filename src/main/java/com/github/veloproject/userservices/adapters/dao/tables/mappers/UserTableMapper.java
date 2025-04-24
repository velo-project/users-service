package com.github.veloproject.userservices.adapters.dao.tables.mappers;

import com.github.veloproject.userservices.adapters.dao.tables.UserTable;
import com.github.veloproject.userservices.core.entities.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTableMapper {
    public static UserTable toTable(UserEntity entity) {
        return UserTable.builder()
                .userId(entity.getUserId())
                .fullName(entity.getFullName())
                .preferredName(entity.getPreferredName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }

    public static UserEntity toEntity(UserTable table) {
        return UserEntity.builder()
                .userId(table.getUserId())
                .fullName(table.getFullName())
                .preferredName(table.getPreferredName())
                .email(table.getEmail())
                .password(table.getPassword())
                .build();
    }
}
