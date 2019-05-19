package me.vitblokhin.jwtappdemo.controller;

import me.vitblokhin.jwtappdemo.dto.UserDto;
import me.vitblokhin.jwtappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AdminRestController {

    private final String URL = "/api/v1/admin";

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(URL + "/adduser")
    public ResponseEntity<UserDto> post(@Valid @RequestBody UserDto newUser) {
        UserDto user = userService.create(newUser);

        return ResponseEntity.ok(user);
    }
} // class AdminRestController
