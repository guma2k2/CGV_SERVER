package com.movie.backend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EventValidException.class})
    public ResponseEntity<Object> handleEventException(
            EventValidException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Event của bạn đã bị trùng lịch" ,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler({JwtException.class})
    public ResponseEntity<Object> handleJwtException(
            JwtException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Jwt của đã hết hạn hoặc không khả dụng" ,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler({SeatException.class})
    public ResponseEntity<Object> handleSeatNameException(
            SeatException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Not valid seat !!" ,
                details);
        return ResponseEntityBuilder.build(err);
    }
    @ExceptionHandler({MovieException.class})
    public ResponseEntity<Object> handleTitleMovie(
            MovieException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Not valid title movie !!" ,
                details);
        return ResponseEntityBuilder.build(err);
    }
    @ExceptionHandler({CinemaException.class})
    public ResponseEntity<Object> handleTitleMovie(
            CinemaException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Not valid name cinema !!" ,
                details);
        return ResponseEntityBuilder.build(err);
    }
    @ExceptionHandler({RoomException.class})
    public ResponseEntity<Object> handleTitleMovie(
            RoomException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "The name of room was not valid" ,
                details);
        return ResponseEntityBuilder.build(err);
    }
    @ExceptionHandler({UserException.class})
    public ResponseEntity<Object> handleUserException(
            UserException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "The user was not valid" ,
                details);
        return ResponseEntityBuilder.build(err);
    }


    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleException(
            BadRequestException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                details);
        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleException(
            NotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                details);
        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex , WebRequest request) {
        List<String> detail = new ArrayList<>();
        detail.add(ex.getLocalizedMessage());
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Error occurred" ,
                detail);
        return ResponseEntityBuilder.build(apiError);

    }
}
