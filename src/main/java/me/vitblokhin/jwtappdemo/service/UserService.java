package me.vitblokhin.jwtappdemo.service;

import me.vitblokhin.jwtappdemo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    User create(User user);

    User update(User user);

    void delete(Long id);
} // interface UserService
