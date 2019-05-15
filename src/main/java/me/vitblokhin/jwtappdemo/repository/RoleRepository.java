package me.vitblokhin.jwtappdemo.repository;

import me.vitblokhin.jwtappdemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
} // interface RoleRepository
