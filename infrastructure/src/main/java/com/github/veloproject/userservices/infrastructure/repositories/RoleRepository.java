package com.github.veloproject.userservices.infrastructure.repositories;

import com.github.veloproject.userservices.application.abstractions.repositories.IRoleRepository;
import com.github.veloproject.userservices.domain.entities.RoleEntity;
import com.github.veloproject.userservices.infrastructure.mappers.RoleMapper;
import com.github.veloproject.userservices.infrastructure.repositories.jpa.IRoleRepositoryJpa;
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
