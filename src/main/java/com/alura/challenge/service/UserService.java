package com.alura.challenge.service;

import com.alura.challenge.domain.DTOs.UserCreateRequest;
import com.alura.challenge.domain.DTOs.UserResponse;
import com.alura.challenge.domain.entity.Email;
import com.alura.challenge.domain.entity.User;
import com.alura.challenge.exception.UserCreateException;
import com.alura.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private EmailService emailService;

    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        String password = createPassword();

        User byEmail = repository.findByEmail(userCreateRequest.getEmail());
        if(byEmail != null){
            throw new UserCreateException("Email já cadastrado");
        }

        var user = User.builder()
                .name(userCreateRequest.getNome())
                .email(userCreateRequest.getEmail())
                .password(password)
                .build();

        enviarEmail(user);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        var save = repository.save(user);

        return new UserResponse(save);
    }

    private void enviarEmail(User user) {
        var email = Email.builder()
                .ownerRef(user.getName())
                .emailFrom("andrewsoares347@gmail.com")
                .emailTo(user.getEmail())
                .subject("Bem vindo ao Challenge")
                        .text("Olá " + user.getName() + ", seja bem vindo ao Challenge. Sua senha é: " + user.getPassword())
                .build();
        emailService.sendEmail(email);

    }
    private String createPassword() {
        return  UUID.randomUUID().toString().substring(0, 6);
    }
}


