package com.github.veloproject.infrastructure.repositories.jpa;

import com.github.veloproject.domain.entities.UserEntity;
import com.github.veloproject.infrastructure.tables.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepositoryJpa extends JpaRepository<UserTable, Integer> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<UserTable> getByNickname(String nickname);
    Optional<UserTable> getByEmail(String email);
}
