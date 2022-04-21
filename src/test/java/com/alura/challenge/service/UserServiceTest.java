package com.alura.challenge.service;

import com.alura.challenge.domain.DTOs.UserCreateRequest;
import com.alura.challenge.domain.DTOs.UserResponse;
import com.alura.challenge.domain.entity.User;
import com.alura.challenge.exception.UserException;
import com.alura.challenge.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.mapping.Any;
import org.hibernate.mapping.Array;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService service;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setParams(){
        service = new UserService(userRepository, emailService);
    }
    @Test
    void createUser() throws Exception {

        UserCreateRequest userCreateRequest = new UserCreateRequest("andrew", "andrewfialho@unigranrio.br");
        service.createUser(userCreateRequest);

        verify(userRepository).save(Mockito.any());
        verify(emailService).sendEmail(Mockito.any());

    }
    @Test
    void DeveriaRetornarExceptionAoTentarCriarUserComEmailJaUtilizado() throws Exception {

        UserCreateRequest userCreateRequest = new UserCreateRequest("andrew", "andrewfialho@unigranrio.br");

        when(userRepository.findByEmail(userCreateRequest.getEmail())).thenReturn(new User());

        var userException = assertThrows(UserException.class, () -> service.createUser(userCreateRequest));

        assertEquals("Email já cadastrado", userException.getMessage());
    }
    @Test
    void listUser() {

        List<UserResponse> users = new ArrayList<>();
        UserResponse user = new UserResponse("asdasdads", "asdasd");

        lenient().when(restTemplate.getForEntity("http://localhost:8080/users", UserResponse.class))
                .thenReturn(new ResponseEntity(users, HttpStatus.OK));

        List<UserResponse> userResponses = service.listUser();
        Assertions.assertEquals(users, userResponses);
    }

    @Test
    void updateUser() {
        UserCreateRequest userCreateRequest = new UserCreateRequest("andrewUpdate", "andrewfialho@unigranrio.br");

        User user = new User(2l, "andrew", "andrewfialho@unigranrio.br", "test");

        when(userRepository.findByIdIgnoreAdmin(2l)).thenReturn(Optional.of(user));

        service.updateUser(2l, userCreateRequest);

        verify(userRepository).findByIdIgnoreAdmin(2l);
        assertTrue(user.getName().equals("andrewUpdate"));
    }

    @Test
    @Disabled
    void deleteUser() {
    }
}