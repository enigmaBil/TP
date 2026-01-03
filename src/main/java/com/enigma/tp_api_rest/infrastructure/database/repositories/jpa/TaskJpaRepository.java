package com.enigma.tp_api_rest.infrastructure.database.repositories.jpa;

import com.enigma.tp_api_rest.infrastructure.database.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<Task, UUID> {
    boolean existsTaskByReference(String reference);
}
