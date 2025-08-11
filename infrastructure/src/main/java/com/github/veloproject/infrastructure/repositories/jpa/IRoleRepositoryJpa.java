package com.github.veloproject.infrastructure.repositories.jpa;

import com.github.veloproject.infrastructure.tables.RoleTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepositoryJpa extends JpaRepository<RoleTable, Integer> {
    RoleTable findByName(String name);
}
