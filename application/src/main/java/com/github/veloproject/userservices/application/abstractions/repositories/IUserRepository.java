package com.github.veloproject.userservices.application.abstractions.repositories;

import com.github.veloproject.userservices.domain.entities.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);

    Optional<UserEntity> findByNickname(String nickname);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(Integer id);

    Integer save(UserEntity entity);

    UserEntity getReferenceById(Integer id);

    List<UserEntity> findAllByIdIn(Collection<Integer> ids);
}
