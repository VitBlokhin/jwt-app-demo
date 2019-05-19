package me.vitblokhin.jwtappdemo.service;

import me.vitblokhin.jwtappdemo.dto.ObjectFilter;
import me.vitblokhin.jwtappdemo.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getPage(ObjectFilter filter);

    //UserDto findByUsername(String username);

    UserDto findById(Long id);

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void delete(Long id);
} // interface UserService
