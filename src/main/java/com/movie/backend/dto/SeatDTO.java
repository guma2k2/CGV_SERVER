package com.movie.backend.dto;

import lombok.Data;

@Data
public class SeatDTO {
    private Long id ;
    private String seat_name;
    private int row_num;
    private int column_num;
    private SeatTypeDTO type;

    private Long roomId ;
    private boolean isReserved;
    private boolean isPaid;
}
