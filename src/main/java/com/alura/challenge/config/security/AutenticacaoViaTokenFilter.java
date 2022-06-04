package com.alura.challenge.config.security;

import com.alura.challenge.domain.entity.User;
import com.alura.challenge.exception.UserNotFoundException;
import com.alura.challenge.repository.UserRepository;
import com.alura.challenge.service.TokenService;
import com.alura.challenge.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
    private TokenService tokenService;
    private UserRepository repository;

    public AutenticacaoViaTokenFilter(TokenService tokenService, UserRepository repository){
        this.tokenService = tokenService;
        this.repository = repository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = recuperaToken(request);
        Boolean valido = tokenService.isTokenValid(token);
        if(valido){ //kkk
            autenticarCliente(token);
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) {
        Long idUsuario = tokenService.getIdUsuario(token);
        User usuario = repository.findById(idUsuario).orElseThrow(() -> new UserNotFoundException("Usuario não existe"));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());// passando como param o usuario, a senha e os perfis de acesso
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // forçando a autenticação
    }

    private String recuperaToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if(authorization == null || authorization.isEmpty() || !authorization.startsWith("Bearer ")){
            return null;
        }
        return authorization.substring(7, authorization.length());
    }
}
