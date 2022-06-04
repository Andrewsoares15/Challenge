package com.alura.challenge.service;

import com.alura.challenge.domain.entity.User;
import com.alura.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    private User userLogado;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var byEmail = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Dados inválidos"));
        userLogado = byEmail;
        return byEmail;
    }
    public User getUserLogado(){
        return this.userLogado;
    }
}
