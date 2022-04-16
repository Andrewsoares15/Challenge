package com.alura.challenge.domain.DTOs;

import com.alura.challenge.domain.entity.User;
import lombok.Data;

@Data
public class UserResponse {

    private String name;
    private String email;


    public UserResponse(User save) {
        this.name = save.getName();
        this.email = save.getEmail();
    }
}
