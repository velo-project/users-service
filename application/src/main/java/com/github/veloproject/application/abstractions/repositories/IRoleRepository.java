package com.github.veloproject.application.abstractions.repositories;

import com.github.veloproject.domain.entities.RoleEntity;

public interface IRoleRepository {
    RoleEntity findByName(String name);
}
