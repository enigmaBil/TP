package com.enigma.tp_api_rest.infrastructure.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    @CreatedDate
    @Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIMEZONE")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false, columnDefinition = "TIMESTAMP WITHOUT TIMEZONE")
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(insertable = false)
    private String lastModifiedBy;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIMEZONE")
    private LocalDateTime deletedDate;

    // Utility method to check if the entity is deleted
    public boolean isDeleted() {
        return this.deletedDate != null;
    }
}
