package com.alura.challenge.controller;

import com.alura.challenge.domain.DTOs.AutenticaRequest;
import com.alura.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Repository
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity autenticar(@RequestBody @Valid AutenticaRequest request){
        UsernamePasswordAuthenticationToken dados = service.autentica(request);
        try {
            Authentication authenticate = manager.authenticate(dados);
            return ResponseEntity.ok().build();
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
