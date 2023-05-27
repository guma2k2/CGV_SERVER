package com.movie.backend.dto;

import com.movie.backend.entity.Seat;
import lombok.Data;

import java.util.List;

@Data

public class RoomDTO {
    private Long id ;
    private String name ;
    private String capacity ; // 10x10 : columns X rows // width x length

    private String cinemaName ;
    private List<SeatDTO> seats ;

    private CinemaDTO cinema;

}
