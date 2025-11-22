package com.github.veloproject.userservices.infrastructure.repositories.jpa;

import com.github.veloproject.userservices.infrastructure.tables.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepositoryJpa extends JpaRepository<UserTable, Integer> {
    boolean existsByEmailAndIsBlockedFalse(String email);
    boolean existsByNicknameAndIsBlockedFalse(String nickname);
    Optional<UserTable> findByNicknameAndIsBlockedFalse(String nickname);
    Optional<UserTable> findByEmailAndIsBlockedFalse(String email);
    List<UserTable> findAllByIdIn(Collection<Integer> ids);
}
