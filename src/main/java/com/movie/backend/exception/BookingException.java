package com.movie.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
public class BookingException extends RuntimeException {
    public BookingException(String message) {
        super(message);
    }
}
