package com.movie.backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime localDateTime;
    private HttpStatus status;
    private String message ;
    private List errors;
}
