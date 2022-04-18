package com.alura.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserCreateException extends RuntimeException {

    public UserCreateException(String message) {
        super (message);
    }
}
