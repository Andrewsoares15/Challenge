package com.alura.challenge.controller;

import com.alura.challenge.domain.DTOs.AutenticaRequest;
import com.alura.challenge.domain.DTOs.TokenResponse;
import com.alura.challenge.service.TokenService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AutenticacaoController {

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/auth")
    public ResponseEntity autenticar(@RequestBody @Valid AutenticaRequest request){
        UsernamePasswordAuthenticationToken dados = service.autentica(request);
        try {
            Authentication authenticate = manager.authenticate(dados);
            String token = tokenService.gerarToken(authenticate);
            return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
