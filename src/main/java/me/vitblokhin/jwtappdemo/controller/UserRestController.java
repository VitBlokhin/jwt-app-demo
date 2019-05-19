package me.vitblokhin.jwtappdemo.controller;

import me.vitblokhin.jwtappdemo.dto.ObjectFilter;
import me.vitblokhin.jwtappdemo.dto.UserDto;
import me.vitblokhin.jwtappdemo.model.User;
import me.vitblokhin.jwtappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserRestController {
    private final String URL = "/api/v1/users";

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(URL)
    public ResponseEntity<List<UserDto>> getList(@Valid ObjectFilter filter) {
        List<UserDto> users = userService.getPage(filter);

        return ResponseEntity.ok(users);
    }

    @GetMapping(URL + "/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.findById(id));
    }
} // class UserRestController
