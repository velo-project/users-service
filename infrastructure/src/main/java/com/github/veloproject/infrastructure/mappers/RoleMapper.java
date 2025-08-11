package com.github.veloproject.infrastructure.mappers;

import com.github.veloproject.domain.entities.RoleEntity;
import com.github.veloproject.infrastructure.tables.RoleTable;

public class RoleMapper {

    public static RoleTable toPersistence(RoleEntity entity) {
        if (entity == null) return null;

        RoleTable table = new RoleTable();
        table.setId(entity.getId());
        table.setName(entity.getName());

        return table;
    }

    public static RoleEntity toDomain(RoleTable table) {
        if (table == null) return null;

        return RoleEntity.builder()
                .id(table.getId())
                .name(table.getName())
                .build();
    }
}

