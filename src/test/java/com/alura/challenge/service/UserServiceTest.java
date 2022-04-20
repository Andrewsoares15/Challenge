package com.alura.challenge.service;

import com.alura.challenge.domain.DTOs.UserCreateRequest;
import com.alura.challenge.domain.DTOs.UserResponse;
import com.alura.challenge.domain.entity.User;
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
        //dado
//        User user = new User(null, "andrew", "andrewfialho@unigranrio.br", "test");
//        UserResponse userResponse = new UserResponse("andrew", "andrewfialho@unigranrio.br");
        UserCreateRequest userCreateRequest = new UserCreateRequest("andrew", "andrewfialho@unigranrio.br");
        //quando
        service.createUser(userCreateRequest);

        verify(userRepository).save(Mockito.any());

    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    @Disabled
    void updateUser() {
    }

    @Test
    @Disabled
    void deleteUser() {
    }
}