package com.github.veloproject.infrastructure.repositories;

import com.github.veloproject.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.domain.entities.UserEntity;
import com.github.veloproject.infrastructure.repositories.jpa.IUserRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {
    private final IUserRepositoryJpa jpa;

    public UserRepository(IUserRepositoryJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return jpa.existsByEmailAndIsBlockedFalse(email);
    }

    @Override
    public Boolean existsByNickname(String nickname) {
        return jpa.existsByNicknameAndIsBlockedFalse(nickname);
    }

    @Override
    public Optional<UserEntity> findByNickname(String nickname) {
        var userTableRow = jpa.findByNicknameAndIsBlockedFalse(nickname);

        return userTableRow.map(UserMapper::toDomain);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        var userTableRow = jpa.findByEmailAndIsBlockedFalse(email);

        return userTableRow.map(UserMapper::toDomain);
    }
}
