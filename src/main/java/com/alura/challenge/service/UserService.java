package com.alura.challenge.service;

import com.alura.challenge.domain.DTOs.AutenticaRequest;
import com.alura.challenge.domain.DTOs.UserCreateRequest;
import com.alura.challenge.domain.DTOs.UserResponse;
import com.alura.challenge.domain.entity.Email;
import com.alura.challenge.domain.entity.User;
import com.alura.challenge.exception.UserException;
import com.alura.challenge.exception.UserNotFoundException;
import com.alura.challenge.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AutenticacaoService autenticacaoService;
    public void createUser(UserCreateRequest userCreateRequest) {
        String password = createPassword();

        Optional<User> byEmail = repository.findByEmail(userCreateRequest.getEmail());
        if(!byEmail.isEmpty()){
            throw new UserException("Email já cadastrado!");
        }
        var user = User.builder()
                .name(userCreateRequest.getNome())
                .email(userCreateRequest.getEmail())
                .password(password)
                .build();

        enviarEmail(user);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        var save = repository.save(user);
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

    public List<UserResponse> listUser() {
        List<User> userList = repository.findAllIgnoreAdmin();
        return userList.stream().map(user -> new UserResponse(user)).collect(Collectors.toList());
    }

    public UserResponse updateUser(Long id, UserCreateRequest updateRequest) {

        var user = repository.findByIdIgnoreAdmin(id).orElseThrow(() -> new UserNotFoundException("User notfound!"));

        user.setEmail(updateRequest.getEmail());
        user.setName(updateRequest.getNome());

        User userSave = repository.save(user);
        return new UserResponse(userSave);

    }

    public void deleteUser(Long id) {
        User userLogado = autenticacaoService.getUserLogado();

        var userDelete = repository.findByIdIgnoreAdmin(id).orElseThrow(() -> new UserNotFoundException("User notfound!"));

        if(userLogado.getId() == userDelete.getId()) throw new UserException("Você não pode se deletar!");

        repository.delete(userDelete);
    }

    public UsernamePasswordAuthenticationToken autentica(AutenticaRequest request) {
        return new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha());
    }
}


