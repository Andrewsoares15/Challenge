package com.alura.challenge.domain.DTOs;

import com.alura.challenge.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    private String name;
    private String email;


    public UserResponse(User save) {
        if (save != null) {
            this.name = save.getName();
            this.email = save.getEmail();
        }
    }
}
