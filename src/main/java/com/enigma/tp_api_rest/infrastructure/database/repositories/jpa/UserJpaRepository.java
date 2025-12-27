package com.enigma.tp_api_rest.infrastructure.database.repositories.jpa;

import com.enigma.tp_api_rest.infrastructure.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
