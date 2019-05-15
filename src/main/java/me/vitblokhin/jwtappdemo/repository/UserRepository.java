package me.vitblokhin.jwtappdemo.repository;

import me.vitblokhin.jwtappdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
} // interface UserRepository
