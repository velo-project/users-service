package com.github.veloproject.userservices.infrastructure.repositories.jpa;

import com.github.veloproject.userservices.infrastructure.tables.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepositoryJpa extends JpaRepository<UserTable, Integer> {
    List<UserTable> findAllByIdInAndIsDeletedFalse(Collection<Integer> ids);
    boolean existsByEmailAndIsDeletedFalse(String email);
    boolean existsByNicknameAndIsDeletedFalse(String nickname);
    Optional<UserTable> findByNicknameAndIsDeletedFalse(String nickname);
    Optional<UserTable> findByEmailAndIsDeletedFalse(String email);
    Optional<UserTable> findByIdAndIsDeletedIsFalse(Integer id);
    UserTable getReferenceByIdAndIsDeletedFalse(Integer id);
}
