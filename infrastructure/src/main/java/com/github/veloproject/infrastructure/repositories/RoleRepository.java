package com.github.veloproject.infrastructure.repositories;

import com.github.veloproject.application.abstractions.repositories.IRoleRepository;
import com.github.veloproject.domain.entities.RoleEntity;
import com.github.veloproject.infrastructure.mappers.RoleMapper;
import com.github.veloproject.infrastructure.repositories.jpa.IRoleRepositoryJpa;
import com.github.veloproject.infrastructure.tables.RoleTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public class RoleRepository implements IRoleRepository {
    private final IRoleRepositoryJpa jpa;

    public RoleRepository(IRoleRepositoryJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public RoleEntity findByName(String name) {
        var role = jpa.findByName(name);
        return RoleMapper.toDomain(role);
    }
}
