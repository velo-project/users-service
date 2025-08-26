package com.github.veloproject.userservices.infrastructure.repositories;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.domain.entities.UserEntity;
import com.github.veloproject.userservices.infrastructure.mappers.UserMapper;
import com.github.veloproject.userservices.infrastructure.repositories.jpa.IUserRepositoryJpa;
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

    @Override
    public Integer save(UserEntity entity) {
        var table = UserMapper.toPersistence(entity);

        table = jpa.save(table);

        return table.getId();
    }

    @Override
    public UserEntity getReferenceById(Integer id) { return UserMapper.toDomain(jpa.getReferenceById(id)); }
}
