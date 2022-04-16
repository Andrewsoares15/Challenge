package com.alura.challenge.controller;

import com.alura.challenge.domain.DTOs.UserCreateRequest;
import com.alura.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody UserCreateRequest userCreateRequest) {
        var user = userService.createUser(userCreateRequest);
        URI location = URI.create("/users/" + user.getEmail());
        return ResponseEntity.created(location).build();
    }
}
