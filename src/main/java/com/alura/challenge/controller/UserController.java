package com.alura.challenge.controller;

import com.alura.challenge.domain.DTOs.UserCreateRequest;
import com.alura.challenge.domain.DTOs.UserResponse;
import com.alura.challenge.domain.entity.User;
import com.alura.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        var user = userService.createUser(userCreateRequest);
        URI location = URI.create("/users/" + user.getEmail());
        return ResponseEntity.created(location).build();
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> listUser(){
        var userResponses = userService.listUser();
        return ResponseEntity.ok(userResponses);
    }
}
