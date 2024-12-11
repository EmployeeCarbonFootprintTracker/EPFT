package org.kevin.garrett.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles") // Explicitly maps to the 'roles' table
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Ensures 'name' cannot be null and is unique
    private String name;

    // Default constructor
    public Role() {
    }

    // Constructor with name
    public Role(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
