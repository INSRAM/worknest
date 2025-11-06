package com.insram.worknest.repository.role;

import com.insram.worknest.model.entities.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
 Optional<Role> findByName(String name);
}
