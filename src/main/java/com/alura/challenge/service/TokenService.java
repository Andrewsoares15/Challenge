package com.alura.challenge.service;

import com.alura.challenge.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${demo.jwt.expiration}")
    private String expiration;

    @Value("${demo.jwt.secret}")
    private String secret; // senha para podermos gerar o token e restringir quem pode gerar tokens

    public String gerarToken(Authentication authenticate) {
        User user = (User) authenticate.getPrincipal(); // recuperando usúario logado
        Date criacaoToken = new Date();
        Date expirationDate = new Date(criacaoToken.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API do forúm Alura") // qual a aplicação que emitiu a geração do token
                .setSubject(user.getId().toString()) // quem é o dono desse token, qual usúario logado
                .setIssuedAt(criacaoToken) // data criação do token
                .setExpiration(expirationDate) // data de expiration do token
                .signWith(SignatureAlgorithm.HS256, secret) // criptografando o token
                .compact(); // compactar tudo e transformar em String
    }

    public Boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);// se o token tiver válido ele retorna o objeto
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public Long getIdUsuario(String token) {
        Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(body.getSubject()); // recuperando o id do usuario
    }
}
