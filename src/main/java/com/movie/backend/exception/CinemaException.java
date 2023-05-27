package com.movie.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CinemaException extends RuntimeException {
    public CinemaException(String message) {
        super(message);
    }
}
