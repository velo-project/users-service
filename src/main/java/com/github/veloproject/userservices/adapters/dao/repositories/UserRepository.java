package com.github.veloproject.userservices.adapters.dao.repositories;

import com.github.veloproject.userservices.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
