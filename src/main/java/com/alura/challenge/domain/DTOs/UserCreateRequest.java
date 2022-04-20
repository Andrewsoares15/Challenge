package com.alura.challenge.domain.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @NotBlank(message = "Email é obrigatório")
    private String email;


}
