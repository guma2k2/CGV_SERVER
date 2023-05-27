package com.movie.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MovieException extends RuntimeException {
    public MovieException(String message) {
        super(message);
    }
}
