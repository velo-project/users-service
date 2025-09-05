package com.github.veloproject.userservices.application.abstractions.repositories;

import com.github.veloproject.userservices.domain.entities.RoleEntity;

public interface IRoleRepository {
    RoleEntity findByName(String name);
}
