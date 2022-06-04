package com.alura.challenge.domain.DTOs;

import lombok.Data;

@Data
public class AutenticaRequest {

    private String email;
    private String senha;

}
