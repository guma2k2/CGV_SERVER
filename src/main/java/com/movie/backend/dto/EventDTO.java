package com.movie.backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class EventDTO {
    private Long id ;

    private LocalDate start_date;
    private RoomDTO room ;

    private MovieDTO movie ;

    private String cinemaName ;

    private Integer price ;
    private String start_time;

    private SubtitleTypeDTO subtitleType ;

    private String endTime ;
    private String startTime;
}
