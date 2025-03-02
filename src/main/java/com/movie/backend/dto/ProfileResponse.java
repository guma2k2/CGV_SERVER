package com.movie.backend.dto;

import com.movie.backend.entity.User;

public record ProfileResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
    public static ProfileResponse fromUser(User user) {
        return new ProfileResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone_number());
    }
}
