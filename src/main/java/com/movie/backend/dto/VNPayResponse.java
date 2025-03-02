package com.movie.backend.dto;

public record VNPayResponse(
         String code,
         String message,
         String paymentUrl
) {
}
