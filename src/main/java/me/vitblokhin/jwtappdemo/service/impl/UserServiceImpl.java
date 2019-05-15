package me.vitblokhin.jwtappdemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.vitblokhin.jwtappdemo.enums.Status;
import me.vitblokhin.jwtappdemo.model.Role;
import me.vitblokhin.jwtappdemo.model.User;
import me.vitblokhin.jwtappdemo.repository.RoleRepository;
import me.vitblokhin.jwtappdemo.repository.UserRepository;
import me.vitblokhin.jwtappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = userRepository.findAll();

        log.info("getAll users: {} users found", userList.size());
        return userList;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findById(Long id) {
        return this.get(id);
    }

    @Override
    public User create(User user) {
        Role role = roleRepository.findByName("ROLE_USER");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("create user: {}", registeredUser);
        return registeredUser;
    }

    // TODO:
    @Override
    public User update(User user) {
        User oldUser = this.get(user.getId());
        if(oldUser == null) {
            log.error("update user error: user not found");
            throw new RuntimeException("User not found");
        }

        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        User updatedUser = userRepository.save(user);

        log.info("update user: {}", updatedUser);
        return updatedUser;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User get(Long id) {
        return userRepository.findById(id).orElse(null);
    }
} // class UserServiceImpl
