package me.vitblokhin.jwtappdemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.vitblokhin.jwtappdemo.dto.ObjectFilter;
import me.vitblokhin.jwtappdemo.dto.UserDto;
import me.vitblokhin.jwtappdemo.enums.Status;
import me.vitblokhin.jwtappdemo.exception.ItemAlreadyExistsException;
import me.vitblokhin.jwtappdemo.exception.ItemNotFoundException;
import me.vitblokhin.jwtappdemo.model.Role;
import me.vitblokhin.jwtappdemo.model.User;
import me.vitblokhin.jwtappdemo.repository.RoleRepository;
import me.vitblokhin.jwtappdemo.repository.UserRepository;
import me.vitblokhin.jwtappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<UserDto> getPage(ObjectFilter filter) {

        List<UserDto> userList = userRepository
                .findAll(PageRequest.of(filter.getPage(), filter.getSize()))
                .getContent().stream().map(UserDto::new).collect(Collectors.toList());

        log.info("UserService.getAll():  {} users found", userList.size());
        return userList;
    }

    @Override
    public UserDto findById(Long id) {
        return new UserDto(this.get(id));
    }

    @Override
    public UserDto create(UserDto userDto) {
        if (this.userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new ItemAlreadyExistsException("User with username: " + userDto.getUsername() + " is already exist");
        }

        Role role = roleRepository.findByName("ROLE_USER");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserDto registeredUser = new UserDto(userRepository.save(user));

        log.info("UserService.create() user: {}", registeredUser);
        return registeredUser;
    }

    @Override
    public UserDto update(UserDto user) {
        User oldUser = this.get(user.getId());

        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());

        oldUser.setUpdatedAt(LocalDateTime.now());

        UserDto updatedUser = new UserDto(userRepository.save(oldUser));

        log.info("UserService.update() user: {}", updatedUser);
        return updatedUser;
    }

    @Override
    public void delete(Long id) {
        this.get(id);
        userRepository.deleteById(id);
    }

    private User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User not found"));
    }
} // class UserServiceImpl
