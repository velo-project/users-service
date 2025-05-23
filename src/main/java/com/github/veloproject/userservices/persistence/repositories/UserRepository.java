package com.github.veloproject.userservices.persistence.repositories;

import com.github.veloproject.userservices.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    boolean existsByEmail(String email);
    Optional<UserEntity> getByEmail(String email);
}
