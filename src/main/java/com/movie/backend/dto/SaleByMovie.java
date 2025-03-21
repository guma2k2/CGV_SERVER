package com.movie.backend.dto;

public record SaleByMovie(
        Long id,
        String title,
        String poster,
        int ticketCount,
        long totalRevenue
) {
}
