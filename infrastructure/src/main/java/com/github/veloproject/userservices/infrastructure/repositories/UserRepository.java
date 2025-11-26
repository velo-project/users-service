package com.github.veloproject.userservices.infrastructure.repositories;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.domain.entities.UserEntity;
import com.github.veloproject.userservices.infrastructure.mappers.UserMapper;
import com.github.veloproject.userservices.infrastructure.repositories.jpa.IUserRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {
    private final IUserRepositoryJpa jpa;

    public UserRepository(IUserRepositoryJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return jpa.existsByEmailAndIsDeletedFalse(email);
    }

    @Override
    public Boolean existsByNickname(String nickname) {
        return jpa.existsByNicknameAndIsDeletedFalse(nickname);
    }

    @Override
    public Optional<UserEntity> findByNickname(String nickname) {
        var userTableRow = jpa.findByNicknameAndIsDeletedFalse(nickname);

        return userTableRow.map(UserMapper::toDomain);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        var userTableRow = jpa.findByEmailAndIsDeletedFalse(email);

        return userTableRow.map(UserMapper::toDomain);
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        var table = jpa.findByIdAndIsDeletedIsFalse(id);

        return table.map(UserMapper::toDomain);
    }

    @Override
    public Integer save(UserEntity entity) {
        var table = UserMapper.toPersistence(entity);

        table = jpa.save(table);

        return table.getId();
    }

    @Override
    public UserEntity getReferenceById(Integer id) {
        return UserMapper.toDomain(jpa.getReferenceByIdAndIsDeletedFalse(id));
    }

    @Override
    public List<UserEntity> findAllByIdIn(Collection<Integer> ids) {
        return jpa.findAllByIdInAndIsDeletedFalse(ids)
                .stream()
                .map(UserMapper::toDomain)
                .toList();
    }
}
