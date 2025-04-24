package com.github.veloproject.userservices.adapters.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.veloproject.userservices.adapters.dao.tables.UserTable;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTable, Long> {
    Optional<UserTable> findByEmail(String email);
}
