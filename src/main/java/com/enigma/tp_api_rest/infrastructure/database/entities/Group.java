package com.enigma.tp_api_rest.infrastructure.database.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "groups")
public class Group extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String reference;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "groups")
    private Set<User> users = new HashSet<>();
}
