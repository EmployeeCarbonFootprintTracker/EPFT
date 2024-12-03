package org.kevin.garrett.service;

import org.kevin.garrett.model.User;
import org.kevin.garrett.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles business logic related to User operations.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection for the UserRepository dependency
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save a new user or update an existing one
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Fetch all users from the database
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find a user by their username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
