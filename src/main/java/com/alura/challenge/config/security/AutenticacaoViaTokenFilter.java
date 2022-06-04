package com.alura.challenge.config.security;

import com.alura.challenge.repository.UserRepository;
import com.alura.challenge.service.UserService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {


    private UserService userService;
    private UserRepository repository;

    public AutenticacaoViaTokenFilter(UserService userService, UserRepository repository){
        this.userService = userService;
        this.repository = repository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        recuperaCredencias(request);
        filterChain.doFilter(request, response);

    }

    private void recuperaCredencias(HttpServletRequest request) throws IOException {
        request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
