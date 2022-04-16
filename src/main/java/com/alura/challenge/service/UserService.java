package com.alura.challenge.service;

import com.alura.challenge.domain.DTOs.UserCreateRequest;
import com.alura.challenge.domain.DTOs.UserResponse;
import com.alura.challenge.domain.entity.User;
import com.alura.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        String password = createPassword();

        var user = User.builder()
                .name(userCreateRequest.getNome())
                .email(userCreateRequest.getEmail())
                .password(new BCryptPasswordEncoder().encode(password))
                .build();
        enviarEmail(user);
        var save = repository.save(user);

        return new UserResponse(save);
    }

    private void enviarEmail(User user) {
        System.out.println("Enviando email para: " + user.getEmail());
        System.out.println("Senha: " + user.getPassword());
        System.out.println("Nome: " + user.getName());
        System.out.println();
    }
    private String createPassword() {
        return  UUID.randomUUID().toString();
    }
}
