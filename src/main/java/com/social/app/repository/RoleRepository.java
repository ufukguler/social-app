package com.social.app.repository;

import com.social.app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String role);

    boolean existsByName(String name);
}
