package com.movie.backend.dto;

public record ProfileUpdateRequest(
        String firstName,
        String lastName,
        String phoneNumber,
        String password
) {
}
