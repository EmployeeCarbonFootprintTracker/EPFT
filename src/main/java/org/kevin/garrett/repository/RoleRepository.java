package org.kevin.garrett.repository;

import org.kevin.garrett.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // allow mw to crate custom queries if needed
}
