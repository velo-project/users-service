package com.github.veloproject.infrastructure.repositories.jpa;

import com.github.veloproject.domain.entities.UserEntity;
import com.github.veloproject.infrastructure.tables.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepositoryJpa extends JpaRepository<UserTable, Integer> {
    boolean existsByEmailAndIsBlockedFalse(String email);
    boolean existsByNicknameAndIsBlockedFalse(String nickname);
    Optional<UserTable> findByNicknameAndIsBlockedFalse(String nickname);
    Optional<UserTable> findByEmailAndIsBlockedFalse(String email);
}
