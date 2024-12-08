package org.kevin.garrett.config;

import org.kevin.garrett.model.Role;
import org.kevin.garrett.model.User;
import org.kevin.garrett.repository.RoleRepository;
import org.kevin.garrett.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder; // Import PasswordEncoder
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing hardcoded users and roles...");

       // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); needed for Bcrypt
        // NoOp for plain-text passwords



        // Create roles if they don't already exist
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
        Role roleManager = roleRepository.findByName("ROLE_MANAGER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_MANAGER")));
        Role roleEmployee = roleRepository.findByName("ROLE_EMPLOYEE")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_EMPLOYEE")));

        // Create users with passwords already using {noop} prefix
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User("admin", "{noop}admin123", "admin@example.com");
            admin.getRoles().add(roleAdmin);
            userRepository.save(admin);
            System.out.println("Admin user created: admin");
        }

        if (userRepository.findByUsername("manager").isEmpty()) {
            User manager = new User("manager", "{noop}manager123", "manager@example.com");
            manager.getRoles().add(roleManager);
            userRepository.save(manager);
            System.out.println("Manager user created: manager");
        }

        if (userRepository.findByUsername("employee").isEmpty()) {
            User employee = new User("employee", "{noop}employee123", "employee@example.com");
            employee.getRoles().add(roleEmployee);
            userRepository.save(employee);
            System.out.println("Employee user created: employee");
        }

        System.out.println("Hardcoded users and roles have been initialized!");
    }
}
