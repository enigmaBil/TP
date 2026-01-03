package com.enigma.tp_api_rest.infrastructure.database.repositories.jpa;

import com.enigma.tp_api_rest.infrastructure.database.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryJpaRepository extends JpaRepository<Category, UUID> {
    boolean existsByName(String name);

}
