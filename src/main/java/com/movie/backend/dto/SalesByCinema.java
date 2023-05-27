package com.movie.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class SalesByCinema {
    private Long cinemaId ;
    private String cinemaName ;
    private LocalDate date ;
    private Long totalAmount;

    public SalesByCinema(LocalDate date) {
        this.date = date;
    }
}
