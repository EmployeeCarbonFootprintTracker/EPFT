package org.kevin.garrett.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;



/**
 * Represents a role in the application, like "USER" or "ADMIN".
 * Each role can be linked to multiple users, and users can have multiple roles.
 */
@Entity
@Table(name = "roles")
public class Role {


    // Unique ID for each role, generated automatically by the database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The name of the role (like "USER" or "ADMIN"), must be unique
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Many-to-Many link with User class.
     * Shows which users have this role.
     *
     * "mappedBy" points to the "roles" field in the User class,
     * indicating that User handles this link.
     *
     * "EAGER" loading means the roles are loaded with the user data.
     */




    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    // Default constructor needed by JPA
    public Role() {}

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
