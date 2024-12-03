package org.kevin.garrett.service;

import org.kevin.garrett.model.Role;
import org.kevin.garrett.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles business logic related to Role operations.
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    // Constructor injection for the RoleRepository dependency
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Save a new role or update an existing one
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    // Fetch all roles from the database
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Find a role by its ID
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
}
