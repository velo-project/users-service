package com.github.veloproject.infrastructure.repositories;

import com.github.veloproject.domain.entities.RoleEntity;
import com.github.veloproject.domain.entities.UserEntity;
import com.github.veloproject.domain.valueObjects.PasswordValueObject;
import com.github.veloproject.infrastructure.tables.RoleTable;
import com.github.veloproject.infrastructure.tables.UserTable;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserTable toPersistence(UserEntity entity) {
        if (entity == null) return null;

        UserTable table = new UserTable();
        table.setId(entity.getId());
        table.setName(entity.getName());
        table.setEmail(entity.getEmail());
        table.setPassword(entity.getPassword() != null ? entity.getPassword().getPassword() : null);
        table.setNickname(entity.getNickname());
        table.setBannerPhotoUrl(entity.getBannerPhotoUrl());
        table.setProfilePhotoUrl(entity.getProfilePhotoUrl());
        table.setDescription(entity.getDescription());
        table.setIsBlocked(entity.getIsBlocked());
        table.setIsDeleted(entity.getIsDeleted());
        table.setRegisteredAt(entity.getRegisteredAt());

        if (entity.getRoles() != null) {
            table.setRoles(
                    entity.getRoles()
                            .stream()
                            .map(RoleMapper::toPersistence)
                            .collect(Collectors.toSet())
            );
        }

        return table;
    }

    public static UserEntity toDomain(UserTable table) {
        if (table == null) return null;

        return UserEntity.builder()
                .id(table.getId())
                .name(table.getName())
                .email(table.getEmail())
                .password(table.getPassword() != null
                        ? new PasswordValueObject(table.getPassword(), false)
                        : null)
                .nickname(table.getNickname())
                .bannerPhotoUrl(table.getBannerPhotoUrl())
                .profilePhotoUrl(table.getProfilePhotoUrl())
                .description(table.getDescription())
                .isBlocked(table.getIsBlocked())
                .isDeleted(table.getIsDeleted())
                .registeredAt(table.getRegisteredAt())
                .roles(table.getRoles() != null
                        ? table.getRoles()
                        .stream()
                        .map(RoleMapper::toDomain)
                        .collect(Collectors.toSet())
                        : Set.of())
                .build();
    }
}

