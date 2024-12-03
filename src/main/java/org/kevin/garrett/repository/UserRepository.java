package org.kevin.garrett.repository;

import org.kevin.garrett.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Handles all the database work for User. JpaRepository got the basics covered.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username. No extra coding needed—Spring handles it.
     */
    User findByUsername(String username);
}
