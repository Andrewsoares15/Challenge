package com.alura.challenge.domain.DTOs;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserCreateRequest {

    @NotBlank
    private String nome;
    @NotBlank
    private String email;


}
