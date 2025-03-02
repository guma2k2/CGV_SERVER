package com.movie.backend.dto;

public record PaymentRequestVM(
        int amount,
        String bankCode,
        Long bookingId
) {
}
